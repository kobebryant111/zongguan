package com.work.ppm.application.services.integratedmanager.impl;

import cn.hutool.core.util.ObjectUtil;
import com.work.imex.util.StrUtils;
import com.work.ppm.application.dto.esb.req.CommInfArray;
import com.work.ppm.application.dto.esb.req.IssuInfArray;
import com.work.ppm.application.dto.esb.req.PpmKqFaceEsbReqDto;
import com.work.ppm.application.dto.esb.req.PpmKqOutsourcingDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.work.ppm.application.dto.integratedmanager.FaceDeviceInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingLzsqInfoDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingExt;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingLzsq;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.quartz.interfaces.IPpmOutsourcingLzsqJobService;
import com.work.ppm.application.services.external.CallEsbService;
import com.work.ppm.application.services.flowmanager.IPpmAppWorkflowInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingExtService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingLzsqService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.application.services.workflowmanager.IWorkflowLeavingService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.CamundaExtUtils;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.IpUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外包离职
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service("ppmOutsourcingLzsqService")
public class PpmOutsourcingLzsqService extends BaseDaoSupper
        implements IPpmOutsourcingLzsqService, IPpmOutsourcingLzsqJobService {

    private static String PPMOUTSOURCINGLZSQ = "com.work.ppm.application.model.integratedmanager.PpmOutsourcingLzsq";
    // 流程定义ID
    private static final String FLOW_ID = "wbrylc";
    //流程名称
    private static final String FLOW_TITLE = "外包人员离场";
    //数据接口
    private final IMyBatisDao myBatisDao;
    // 员工
    private final ICommEmployeeService commEmployeeService;
    //外包人员基础
    private final IPpmOutsourcingBasicService ppmOutsourcingBasicService;
    //外包人员扩展
    private final IPpmOutsourcingExtService ppmOutsourcingExtService;
    //工作流服务
    private final WorkFlowHttpClientService workFlowHttpClientService;

    private final IAttatchManagementService attatchManagementService;
    //考勤详情
    private final IPpmKqRecordService ppmKqRecordService;

    private final IPpmAppWorkflowInfoService ppmAppWorkflowInfoService;

    private final CallEsbService esbService;

    @Value("${amterm.pubId}")
    private String pubId;

    @Lazy
    @Autowired
    private IWorkflowLeavingService workflowLeavingService;

    @Override
    public void queryPager(PpmOutsourcingLzsq ppmOutsourcingLzsq, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmOutsourcingLzsq, PpmOutsourcingLzsq.class);
        myBatisDao.selectPager(PPMOUTSOURCINGLZSQ, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmOutsourcingLzsq(PpmOutsourcingLzsq ppmOutsourcingLzsq) {
        myBatisDao.executionPojo(PPMOUTSOURCINGLZSQ, ppmOutsourcingLzsq,
                IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmOutsourcingLzsq getInfoByInstanceId(String instanceId){
        PpmOutsourcingLzsq ppmOutsourcingLzsq = new PpmOutsourcingLzsq();
        ppmOutsourcingLzsq.setInstanceid(instanceId);
        return queryDetail(ppmOutsourcingLzsq);
    }

    @Override
    public PpmOutsourcingLzsq queryDetail(PpmOutsourcingLzsq ppmOutsourcingLzsq) {
        return myBatisDao.selectClass(PPMOUTSOURCINGLZSQ, ppmOutsourcingLzsq, "queryDetail");
    }

    @Override
    public List<PpmOutsourcingLzsq> queryLeavingDetailList(String leavingApproveTime) {
        PpmOutsourcingLzsq ppmOutsourcingLzsq = new PpmOutsourcingLzsq();
        ppmOutsourcingLzsq.setLeavingApproveTime(leavingApproveTime);
        return myBatisDao.selectListT(PPMOUTSOURCINGLZSQ, ppmOutsourcingLzsq, "queryLeavingDetailList");
    }

    @Override
    public Integer updatePpmOutsourcingLzsq(PpmOutsourcingLzsq ppmOutsourcingLzsq) {
        return myBatisDao.executionPojo(PPMOUTSOURCINGLZSQ, ppmOutsourcingLzsq,
                IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deletePpmOutsourcingLzsq(PpmOutsourcingLzsq ppmOutsourcingLzsq) {
        int count = myBatisDao.executionPojo(PPMOUTSOURCINGLZSQ, ppmOutsourcingLzsq,
                IMyBatisDao.SQL.DELETE, "deleteByInstanceid");
        if (StrUtils.isNotBlank(ppmOutsourcingLzsq.getInstanceid())) {
            // 删除流程相关数据
            CamundaUtils.delProjectFlow(ppmOutsourcingLzsq.getInstanceid());
            CamundaUtils.terminateProcess(ppmOutsourcingLzsq.getInstanceid());
        }
        return count;
    }

    @Override
    @Transactional("defaultTx")
    public PpmOutsourcingLzsq save(PpmOutsourcingLzsq info, BpsWorkFlowDto bps) {
        // 更新数据
        if (StringUtil.isNotBlank(info.getInstanceid())) {
            this.updatePpmOutsourcingLzsq(info);
            return info;
        }
        try {
            ProcessBean processBean;
            Long businessKey = getNextSequence(PpmOutsourcingLzsq.class);
            // 流程实例参数设置
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(bps.getUserId());// 提交人id
            processInfo.setStartUserId(bps.getUserId());// 流程发起人id
            processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(businessKey));
            // 开始并提交流程
            processBean = workFlowHttpClientService.startProcess(processInfo);
            info.setId(businessKey);
            info.setInstanceid(processBean.getProcessInstanceId());
            info.setCreatedate(DateUtil.dateToStr(new Date(), "yyyy-MM-dd"));
            info.setCreateid(bps.getUserId());
            info.setTitle(info.getUsername() + "【外包人员离场】");
            info.setFlowid(FLOW_ID);
            this.addPpmOutsourcingLzsq(info);

            // 日志处理
            bps.setProjectNo(String.valueOf(businessKey));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setSuggest("添加");
            bps.setApplydesc("外包人员离场申请");
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());
            bps.setProcessInstName("【" + bps.getUserName() + DateUtil.dateToNoBar(new Date()) + "】" + "人员离场");
            bps.setProcessInstDesc(FLOW_TITLE);
            bps.setToDB(false);
            CamundaUtils.addFlowLog(bps, null);
            return info;
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    @Override
    @Transactional("defaultTx")
    public String outLeaveStart(PpmOutsourcingLzsq info, BpsWorkFlowDto bps) {
        //校验
        vaildCommitData(info);

        ProcessBean processBean = null;
        try {
            //根据工号获取部门
            PpmOutsourcingBasic applydto = new PpmOutsourcingBasic();
            applydto.setJobId(info.getJobid());
            PpmOutsourcingBasic apply = ppmOutsourcingBasicService.queryDetail(applydto);
            //根据部门获取总经理室审批
            EosDictEntry chargeManager = UsersUtil.getChargeManager(apply.getDeptId());
            // 第一次流程发起
            if (!StringUtil.isNotBlank(info.getInstanceid())) {
                Long businessKey = getNextSequence(PpmOutsourcingLzsq.class);
                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(String.valueOf(businessKey));
                Map mPara = new HashMap();
                mPara.put("userId", chargeManager.getDictname());
                processInfo.setmPara(mPara);// 流程变量
                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);
                info.setId(businessKey);
                info.setInstanceid(processBean.getProcessInstanceId());
                info.setCreatedate(DateUtil.dateToStr(new Date(), "yyyy-MM-dd"));
                info.setCreateid(bps.getUserId());
                info.setTitle(info.getUsername() + "【外包人员离场】");
                info.setFlowid(FLOW_ID);
                info.setFlag(0);
                this.addPpmOutsourcingLzsq(info);

                // 日志处理
                bps.setProjectNo(String.valueOf(businessKey));
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setSuggest("提交申请");
                bps.setApplydesc("外包人员离场申请");
                bps.setRelaData(mPara);
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstName(info.getUsername() + "【外包人员离场】");
                bps.setProcessInstDesc("外包人员离场流程发起");
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(processBean.getProcessInstanceId());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                // 返回下一节点信息
                //推送待办
//                String imgTxDsc = "项目经理(" + bps.getUserName()
//                        + bps.getUserId() + ")，于"
//                        + info.getCreatedate()
//                        + "，发起离场流程，请及时登录手机工作平台处理。";
//                workflowLeavingService.pushTaskCommon(commEmployeeDto.getBadge(),"外包人员离场二级部经理审批",
//                        imgTxDsc,bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"));
                handFaceDevice(taskBean, info);
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            } else {
                this.updatePpmOutsourcingLzsq(info);
                // 执行任务
                TaskBean param = new TaskBean();
                param.setTenantId(BusinessConstant.TENANT_ID);// 租户
                param.setTaskId(bps.getTaskId());// 任务id
                param.setUserId(bps.getUserId());// 处理人
                param.setComment(bps.getSuggest());// 审批意见
                Map mPara = new HashMap();
                mPara.put("userId", chargeManager.getDictname());
                param.setmPara(mPara);// 流程变量
                // 提交任务
                workFlowHttpClientService.submitTask(param);
                // 日志处理
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(info.getInstanceid());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion("01");
                bps.setSuggest("重新提交");
                bps.setApplydesc(FLOW_TITLE);
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(info.getInstanceid());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                // 返回下一节点信息
                //推送待办
//                String imgTxDsc = "项目经理(" + bps.getUserName()
//                        + bps.getUserId() + ")，于"
//                        + info.getCreatedate()
//                        + "，发起离场流程，请及时登录手机工作平台处理。";
//                workflowLeavingService.pushTaskCommon(commEmployeeDto.getBadge(),"外包人员离场二级部经理审批",
//                        imgTxDsc,bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"));

                handFaceDevice(taskBean, info);
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            }
        } catch (Exception e) {
            CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            log.error("添加并发起流程失败！");
            throw new BizException(e);
        }
    }
    @Override
    public void handFaceDevice( TaskBean taskBean, PpmOutsourcingLzsq info) {
        PpmKqOutsourcingDto empList = new PpmKqOutsourcingDto();
        List<CommInfArray> badges = new ArrayList<>();
        String nextActId = null;
        for (Map task : taskBean.getTaskList()) {
            nextActId = (String) task.get("assignee");

            CommInfArray commInfArray = new CommInfArray();
            commInfArray.setEmpNo(nextActId);
            badges.add(commInfArray);
        }
        String ip = getServerIp();
        IssuInfArray issuInfArray = new IssuInfArray();
        issuInfArray.setList(badges);

        List<IssuInfArray> badgess = new ArrayList<>();
        badgess.add(issuInfArray);
        empList.setList(badgess);
        empList.setID(pubId);
        empList.setSndFlg(false);
        empList.setInfTp("2");
        empList.setRcrdtxt1("您有一条"+ info.getTitle()+"流程，请及时处理。");
        empList.setSvcNm("mobileoa-bnwp-sidecar");
        empList.setCmdbSysName("cfit");
        empList.setBankSysIP(ip);
        empList.setUrl("/MobileOaMercury/yunproxy/sendMessage");
        empList.setSendTp("POST");
        empList.setPsThgFlg("true");
        empList.setHDBnkInrSysNm("mobileoa");
        empList.setHDBnkOutSysIp(IpUtil.getLocalIp());
        try {
            esbService.sendMessage(empList);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("工作平台消息通知异常");
        }
    }

    @Override
    public Integer updatePpmOutsourcingLzsq(PpmOutsourcingBasic basic) {
        return myBatisDao.executionPojo(PPMOUTSOURCINGLZSQ, basic, IMyBatisDao.SQL.UPDATE, "updatePojoOther");
    }

    private String getServerIp() {
        try {
            Enumeration<NetworkInterface> interfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (interfaceEnumeration.hasMoreElements()) {
                NetworkInterface networkInterface = interfaceEnumeration.nextElement();
                if (networkInterface.isLoopback()) {
                    continue;
                }
                Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
                while (inetAddressEnumeration.hasMoreElements()) {
                    InetAddress inetAddress = inetAddressEnumeration.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }
    @Override
    @Transactional("defaultTx")
    public String managerApprove(PpmOutsourcingLzsq info, BpsWorkFlowDto bps) {
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");

                //离场时间转为花名册离场时间
                Date leavingTime = DateUtil.dataFormat(info.getLgsj(), "yyyy-MM-dd");
                Date leavingFlowTime = new Date();
                Boolean flag = leavingFlowTime.before(leavingTime);
                // 变更人员离职状态
                Date leavingApproveTime;
                //如果审批结束时间早于离职时间，取离职时间，反之取审批结束时间
//                if (flag) {
//                    leavingApproveTime = leavingTime;
//                    info.setFlag(2);
//                }else {
                    leavingApproveTime = leavingFlowTime;
                    PpmOutsourcingBasic ppmOutsourcingBasic = ppmOutsourcingBasicService.getBaseInfo(info.getJobid());
                String oldId = info.getJobid().replaceFirst("^(ywwb|k|l)", "lc");
                if ("231398".equals(bps.getAssignee())) {
                    ppmOutsourcingBasic.setOldId(oldId);
                    ppmOutsourcingBasic.setState("1");
                    ppmOutsourcingBasic.setPresenceState("4");
                    ppmOutsourcingBasic.setLeavingTime(leavingTime);
                }
                ppmOutsourcingBasicService.updatePpmOutsourcingBasic(ppmOutsourcingBasic);

                    //查询扩展信息
                    PpmOutsourcingExt ext = new PpmOutsourcingExt();
                    ext.setUserId(info.getJobid());
                    ext = ppmOutsourcingExtService.queryDetail(ext);
                    if (ext != null) {
//                        if ("231398".equals(bps.getAssignee())) {
//                            ext.setOldId(oldId);
//                        }
                        ext.setLeavFlowTime(leavingFlowTime);
                        ext.setOldId(ppmOutsourcingBasic.getOldId());
                        ppmOutsourcingExtService.updatePpmOutsourcingExt(ext);
                    }

                    //离岗时需要对总办议案做修改
                    //总办议案 可用人数 +1
                    attatchManagementService.calMotionNumberMinus(ppmOutsourcingBasic.getCeneralOfficeMotion());
                    info.setFlag(1);
//                }

                //修改跑批时间
                info.setLeavingApproveTime(DateUtil.dateToStr(leavingApproveTime,"yyyy-MM-dd"));
                updatePpmOutsourcingLzsq(info);
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc(bps.getActivityName());
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);

            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                workflowLeavingService.deleteTaskXy(ppmAppWorkflowInfoDto);
            }
            if("二级部经理审批".equals(bps.getActivityName())){
                handFaceDevice(taskBean, info);
            }
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("二级部经理审批失败！",e);
            throw new BizException(e);
        }
    }

    @Override
    public PpmOutsourcingLzsqInfoDto getBaseInfo(PpmOutsourcingLzsq info) {
        PpmOutsourcingLzsqInfoDto lzsqInfo = new PpmOutsourcingLzsqInfoDto();
        if (info.getJobid() != null && !"".equals(info.getJobid())) {
            PpmOutsourcingBasicInfo binfo = ppmOutsourcingBasicService.getBaseInfo(info.getJobid());
            lzsqInfo.setBaseinfo(binfo);
        }
        if (info.getInstanceid() != null && !"".equals(info.getInstanceid())) {
            PpmOutsourcingLzsq lzsq = this.queryDetail(info);
            lzsqInfo.setLzsq(lzsq);
            if (lzsq.getJobid() != null && !"".equals(lzsq.getJobid())) {

                PpmOutsourcingBasicInfo binfo = ppmOutsourcingBasicService.getBaseInfo(lzsq.getJobid());
                lzsqInfo.setBaseinfo(binfo);
            }
        }

        return lzsqInfo;
    }

    @Override
    @Transactional("defaultTx")
    public void outQuitChange() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String sysdate = dtf.format(now);
        Map param = new HashMap();
        param.put("sysdate", sysdate);
        List<PpmOutsourcingLzsq> list = myBatisDao.selectListT(PPMOUTSOURCINGLZSQ, param, "queryOutQuitInfo");
        list.stream().filter(ii -> StringUtil.isNotBlank(ii.getJobid()) &&
                StringUtil.isNotBlank(ii.getLgsj())).forEach(item -> {
                    String jobid = item.getJobid();
                    String yjlgsj = item.getLgsj();
                    log.error("外包离职定时器更新主表记录开始" + jobid);
                    //查询基本信息
                    PpmOutsourcingBasic base = new PpmOutsourcingBasic();
                    base.setJobId(jobid);
                    base = ppmOutsourcingBasicService.queryDetail(base);
                    log.error("basic:" + base);
                    base.setState("1");
                    base.setPresenceState("4");     // 离场
                    ZoneId zone = ZoneId.systemDefault();
                    if (StringUtil.isNotEmpty(yjlgsj)) {
                        base.setLeavingTime(Date.from(LocalDate.parse(yjlgsj, dtf).atStartOfDay(zone).toInstant()));
                    } else {
                        base.setLeavingTime(new Date());
                    }
                    ppmOutsourcingBasicService.updatePpmOutsourcingBasic(base);
                    log.error("外包离职定时器更新主表记录结束");

                    //离岗时需要对总办议案做修改
                    //总办议案 可用人数 +1
                    attatchManagementService.calMotionNumberMinus(base.getCeneralOfficeMotion());

                    log.error("外包离职定时器更新流程表记录开始");
                    PpmOutsourcingLzsq lzsq = item;
                    lzsq.setFlag(3);
                    updatePpmOutsourcingLzsq(lzsq);
                    log.error("外包离职定时器更新流程表记录结束");
                }
        );
    }

    @Override
    public boolean isExist(String jobid) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("jobid", jobid);
        List<PpmOutsourcingLzsq> list = myBatisDao.selectListT(PPMOUTSOURCINGLZSQ, map, "getOutQuitFlows");
        return list.size() > 0;
    }


    /**
     * 对提交的数据做校验
     * @param info 对象
     */
    private void vaildCommitData(PpmOutsourcingLzsq info){

        PpmKqRecordDto kqRecordDto = new PpmKqRecordDto();
        kqRecordDto.setWhetherHandle("1");
        kqRecordDto.setJobId(info.getJobid());
        List<PpmKqRecordDto> recordDtos = ppmKqRecordService.queryBaseInfoList(kqRecordDto);
//        if(recordDtos != null && !recordDtos.isEmpty()){
//            throw new BizException(ApiCode.FAIL,"请先处理该人员异常考勤再发起离场");
//        }

    }

}
