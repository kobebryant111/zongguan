package com.work.ppm.application.services.integratedmanager.impl;

import com.google.common.collect.Lists;
import com.work.imex.util.StrUtils;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmInterviewApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmInterviewerInfoDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.enums.InterviewEduLevelEnums;
import com.work.ppm.application.enums.InterviewMeetEnums;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.integratedmanager.PpmInterviewApply;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeInfo;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeOverinfo;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeScoreinfo;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfo;
import com.work.ppm.application.model.integratedmanager.ext.PpmInterviewApplyInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;
import com.work.ppm.application.services.flowmanager.IPpmAppWorkflowInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmInterviewApplyService;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeOverinfoService;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeScoreinfoService;
import com.work.ppm.application.services.integratedmanager.IPpmInterviewerInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmManpowerdemandApplyInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmManpowerdemandApplyService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.ICommParttimejobService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.workflowmanager.IWorkflowOutputService;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.CamundaExtUtils;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import com.tansun.workflow.model.PostInfo;
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
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * 外包面试申请
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service("ppmInterviewApplyService")
public class PpmInterviewApplyService extends BaseDaoSupper implements IPpmInterviewApplyService, ICommonDeleteFlowService {
    //实体类路径
    private static String PPMINTERVIEWAPPLY = "com.work.ppm.application.model.integratedmanager.PpmInterviewApply";
    // 流程定义ID
    private static final String FLOW_ID = "wbryms";
    //流程名称
    private static final String FLOW_TITLE = "外包人员面试";
    //数据接口
    private final IMyBatisDao myBatisDao;
    //字典服务
    private final IEosDictEntryService eosDictEntryService;
    //员工
    private final ICommEmployeeService commEmployeeService;
    //部门
    private final ICommDepartmentService commDepartmentService;
    //行员兼职
    private final ICommParttimejobService commParttimejobService;
    //外包信息接口
    private final IPpmIntervieweeInfoService ppmIntervieweeInfoService;
    //面试官信息
    private final IPpmInterviewerInfoService ppmInterviewerInfoService;
    //工作流服务
    private final WorkFlowHttpClientService workFlowHttpClientService;
    //面试官打分
    private final IPpmIntervieweeScoreinfoService ppmIntervieweeScoreinfoService;
    //面试归档
    private final IPpmIntervieweeOverinfoService ppmIntervieweeOverinfoService;
    //人力需求主表接口
    private final IPpmManpowerdemandApplyService ppmManpowerdemandApplyService;
    //人力需求详细表
    private final IPpmManpowerdemandApplyInfoService ppmManpowerdemandApplyInfoService;
    //app审核
    @Lazy
    @Autowired
    private IWorkflowOutputService workflowOutputService;

    @Value("${appUrl.interview}")
    private String appUrlValue;

    //app对接
    private final IPpmAppWorkflowInfoService ppmAppWorkflowInfoService;

    @Override
    public List<PpmInterviewApplyDto> queryPager(PpmInterviewApplyDto ppmInterviewApply,
                                                 Pager pager, CommOperatorDoLoginDto user) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmInterviewApply, PpmInterviewApplyDto.class);
        int type = getRoles(user);
        if (4 == type) {
            String deptIdstr = commParttimejobService.queryDeptIdByBadge(user.getUserid());
            if (StringUtil.isNotBlank(deptIdstr)) {
                map.put("deptno", "'" + user.getDeptId() + "'," + deptIdstr + "");
            } else {
                map.put("deptno", user.getDeptId());
            }
        } else if (0 == type) {
            map.put("applyer", user.getUserid());
        }

        myBatisDao.selectPager(PPMINTERVIEWAPPLY, map, pager, "queryPagerList");
        List<PpmInterviewApplyDto> dto = JsonUtil.jsonToList(pager.getList(), PpmInterviewApplyDto.class);
        // 面试后台界面，仅管理员、二级部经理层可以看到单价，其它人处屏蔽
        dto.stream().forEach(ii -> ii.setRoleType(type));
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String daftPpmInterviewApply(PpmInterviewApply ppmInterviewApply, PpmInterviewerInfoDto ppmInterviewerInfo) {
        BpsWorkFlowDto bps = ppmInterviewerInfo.getBps();
        List<PpmIntervieweeInfo> eeList = ppmInterviewerInfo.getEeList();
        try {
            // 第一次流程发起
            if (!StringUtil.isNotBlank(bps.getProcessInstId())) {
                // 如果需要请set 主键id
                ppmInterviewApply.setId(getNextSequence(PpmInterviewApply.class));
                if (null != ppmInterviewerInfo.getRlId()) {
                    ppmInterviewApply.setRlId(Long.parseLong(ppmInterviewerInfo.getRlId()));
                }
                ppmInterviewApply.setCreatetime(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                String deptName = commDepartmentService.getDetpIdForDeptName(ppmInterviewApply.getDeptno());
                ppmInterviewApply.setDeptname(deptName);
                myBatisDao.executionPojo(PPMINTERVIEWAPPLY, ppmInterviewApply, IMyBatisDao.SQL.INSTER, "addPojo");
                // 面试官
                ppmInterviewerInfoService.addPpmInterviewerInfo(ppmInterviewerInfo);
            } else {
                ppmInterviewApply.setRlId(Long.parseLong(ppmInterviewerInfo.getRlId()));
                this.updatePpmInterviewApply(ppmInterviewApply);
                // 面试官
                ppmInterviewerInfoService.addPpmInterviewerInfo(ppmInterviewerInfo);
                // 面试人员
                eeList.stream().forEach(ii -> ii.setInstanceid(ppmInterviewerInfo.getInstanceid()));
                ppmIntervieweeInfoService.addPpmIntervieweeInfo(eeList);
            }
            return "pass";
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String addPpmInterviewApply(PpmInterviewApply ppmInterviewApply, PpmInterviewerInfoDto interviewerInfoDto) {
        List<PpmIntervieweeInfo> eeList = interviewerInfoDto.getEeList();
        BpsWorkFlowDto bps = interviewerInfoDto.getBps();

        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }

        ProcessBean processBean = null;
        try {
            // 第一次流程发起
            if (!StringUtil.isNotBlank(interviewerInfoDto.getInstanceid())) {
                bps.setProcessDefName(FLOW_ID);
                StringJoiner joiner = new StringJoiner(",");
                eeList.stream().forEach(ii -> joiner.add(ii.getMsname()));
                bps.setProcessInstName(joiner + "外包人员面试流程发起");
                // 如果需要请set 主键id
                ppmInterviewApply.setId(getNextSequence(PpmInterviewApply.class));
                if (null != interviewerInfoDto.getRlId()) {
                    ppmInterviewApply.setRlId(Long.parseLong(interviewerInfoDto.getRlId()));
                }
                ppmInterviewApply.setCreatetime(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                String deptName = commDepartmentService.getDetpIdForDeptName(ppmInterviewApply.getDeptno());
                ppmInterviewApply.setDeptname(deptName);

                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(String.valueOf(ppmInterviewApply.getId()));
                Map mPara = new HashMap();
                List<String> msgList = Lists.newArrayList();
                if (StringUtil.isNotBlank(interviewerInfoDto.getMsgid())) {
                    String[] msgIds = interviewerInfoDto.getMsgid().split(",");
                    List<PostInfo> userList = new ArrayList<>();
                    PostInfo postInfo;
                    for (String userId : msgIds) {
                        postInfo = new PostInfo();
                        postInfo.setType("user");
                        postInfo.setUserId(userId);
                        userList.add(postInfo);
                        msgList.add(userId);
                    }
                    mPara.put("userList", userList);// 下一步审批人
                    processInfo.setmPara(mPara);// 流程变量
                }
                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);

                // 面试申请
                ppmInterviewApply.setInstanceid(processBean.getProcessInstanceId());
                myBatisDao.executionPojo(PPMINTERVIEWAPPLY, ppmInterviewApply, IMyBatisDao.SQL.INSTER, "addPojo");

                // 面试官
                interviewerInfoDto.setInstanceid(processBean.getProcessInstanceId());
                // 面试官信息
                ppmInterviewerInfoService.addPpmInterviewerInfo(interviewerInfoDto);
                // 面试人员
                eeList.stream().forEach(ii -> ii.setInstanceid(interviewerInfoDto.getInstanceid()));
                //外包面试人员信息
                ppmIntervieweeInfoService.addPpmIntervieweeInfo(eeList);

                // 日志处理
                bps.setProjectNo(String.valueOf(ppmInterviewApply.getId()));
                bps.setConclusion("01");
                bps.setApplydesc("面试流程发起");
                bps.setRelaData(mPara);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setSuggest("提交申请");
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(processBean.getProcessInstanceId());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,msgList.toArray(new String[msgList.size()]),bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, msgList.toArray(new String[msgList.size()]));
            } else {
                // 第一次流程后只做流转更新
                ppmInterviewApply.setInstanceid(interviewerInfoDto.getInstanceid());
                ppmInterviewApply.setRlId(Long.parseLong(interviewerInfoDto.getRlId()));
                this.updatePpmInterviewApply(ppmInterviewApply);
                // 面试官
                ppmInterviewerInfoService.addPpmInterviewerInfo(interviewerInfoDto);
                // 面试人员
                eeList.stream().forEach(ii -> ii.setInstanceid(interviewerInfoDto.getInstanceid()));
                ppmIntervieweeInfoService.addPpmIntervieweeInfo(eeList);
                // 如果回退，则在发起申请节点 删除之前流程遗留的数据
                List<PpmIntervieweeScoreinfo> scoreList = ppmIntervieweeScoreinfoService.getScoreList(interviewerInfoDto.getInstanceid());
                if (null != scoreList && !scoreList.isEmpty()) {
                    ppmIntervieweeScoreinfoService.deleteByInstanceId(interviewerInfoDto.getInstanceid());
                }
                // 执行任务
                TaskBean param = new TaskBean();
                param.setTenantId(BusinessConstant.TENANT_ID);// 租户
                param.setTaskId(bps.getTaskId());// 任务id
                param.setUserId(bps.getUserId());// 处理人
                param.setComment(bps.getSuggest());// 审批意见
                Map mPara = new HashMap();
                List<String> msgList = Lists.newArrayList();
                if (StringUtil.isNotBlank(interviewerInfoDto.getMsgid())) {
                    String[] msgIds = interviewerInfoDto.getMsgid().split(",");
                    List<PostInfo> userList = new ArrayList<>();
                    PostInfo postInfo;
                    for (String userId : msgIds) {
                        postInfo = new PostInfo();
                        postInfo.setType("user");
                        postInfo.setUserId(userId);
                        userList.add(postInfo);
                        msgList.add(userId);
                    }
                    mPara.put("userList", userList);// 下一步审批人
                    param.setmPara(mPara);// 流程变量
                }
                // 提交任务
                workFlowHttpClientService.submitTask(param);
                // 日志处理
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(interviewerInfoDto.getInstanceid());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion("01");
                bps.setSuggest("重新提交");
                bps.setApplydesc(FLOW_TITLE);
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(interviewerInfoDto.getInstanceid());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,msgList.toArray(new String[msgList.size()]),bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, msgList.toArray(new String[msgList.size()]));
            }
        } catch (Exception e) {
            CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            log.error("添加并发起流程失败！");
            throw new BizException(e);
        }
    }


    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String interviewScore(BpsWorkFlowDto bps, List<PpmIntervieweeScoreinfo> ivsList) {
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
                // 面试打分
                ppmIntervieweeScoreinfoService.addPpmIntervieweeScoreinfo(ivsList);
                // 计算面试成绩 异步处理
                updateSorce(ivsList, bps);
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("面试打分");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            List<String> msgList = Lists.newArrayList();
            if(taskBean != null){
                taskBean.getTaskList().forEach(map -> msgList.add(map.get("assignee").toString()));
            }

            CamundaExtUtils.saveNextTaskInfo(taskBean,msgList.toArray(new String[msgList.size()]),bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, msgList.toArray(new String[msgList.size()]));
        } catch (Exception e) {
            log.error("面试打分失败！");
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String resultEntry(PpmInterviewerInfoDto infoDto) {
        try {
            BpsWorkFlowDto bps = infoDto.getBps();
            List<PpmIntervieweeInfo> ieList = infoDto.getEeList();
            ppmIntervieweeInfoService.addPpmIntervieweeInfo(ieList);
            // 更新是否符合要求
            PpmInterviewApply apply = new PpmInterviewApply();
            apply.setMeet(infoDto.getMeet());
            apply.setRemarks(infoDto.getRemarks());
            apply.setInstanceid(bps.getProcessInstId());
            AssertUtil.notNull(apply.getInstanceid(), "【Instanceid】不能为空");
            this.updatePpmInterviewApply(apply);
            // 开始审批
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            boolean flag = false;
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                if (InterviewMeetEnums.PASS.getCode().equals(infoDto.getMeet())) {
                    mPara.put("status", "pass");
                    PpmInterviewerInfoDto dto = ppmInterviewerInfoService.getItemByDeptInstanceid(bps.getProcessInstId());
                    CommEmployeeDto employeeDto = UsersUtil.getTwoWorkManager(dto.getMsgdeptno());
                    mPara.put("userId", employeeDto.getBadge());   // 二级部主持经理

                    flag = true;
                } else {
                    this.addOverLog(ieList);
                    CamundaUtils.terminateProcess(bps.getProcessInstId());
                    // 获取流程信息
                    TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
                    // 日志处理
                    bps.setProcessDefName(FLOW_ID);
                    bps.setProcessInstId(bps.getProcessInstId());
                    bps.setSuggest(infoDto.getRemarks());
                    bps.setApplydesc("结果录入");
                    bps.setRelaData(mPara);
                    CamundaUtils.addFlowLog(bps, taskBean);
                    CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                    // 返回下一节点信息
                    return CamundaUtils.findNextTaskInfo(taskBean, null);
                }
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("结果录入");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);

            StringJoiner stringJoiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
            ieList.forEach(x -> stringJoiner.add(x.getMsname()));

            String processStartTime = "";
            TaskBean taskInfo = CamundaUtils.findTaskList(bps.getProcessInstId(), bps.getUserId());
            for (Map task : taskInfo.getTaskList()){
                processStartTime = task.get("processStartTime") != null ? task.get("processStartTime").toString() : "";
            }

            String imgTxDsc = bps.getUserName() + "(" + bps.getUserId() +")"+ processStartTime +
                    stringJoiner + "外包人员面试流程发起，请及时登录手机工作平台处理。";

            if (flag){
                workflowOutputService.pushTask(
                        mPara.get("userId").toString(),"外包人员面试单价确认",imgTxDsc
                        ,appUrlValue + "?flowId=",bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"));
            }

            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("结果录入失败！");
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String priceConfirm(PpmInterviewerInfoDto infoDto) {
        BpsWorkFlowDto bps = infoDto.getBps();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            List<String> msgIds = Lists.newArrayList();
            boolean flag = false;
            StringJoiner stringJoiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                CommEmployeeDto commEmployeeDto = new CommEmployeeDto();
                //永赢租赁金融科技部-总经理室
                commEmployeeDto.setDepid(80000053);
                msgIds = commEmployeeService.getEmployeeLists(commEmployeeDto)
                        .stream().map(CommEmployeeDto::getBadge).collect(Collectors.toList());

                msgIds.forEach(stringJoiner::add);
                // 修改应聘信息
                List<PpmIntervieweeInfo> ieList = infoDto.getEeList();
                ppmIntervieweeInfoService.addPpmIntervieweeInfo(ieList);

                flag = true;
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("单价确认");
            bps.setRelaData(mPara);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,msgIds.toArray(new String[msgIds.size()]),bps);

            String imgTxUrl = appUrlValue + "?flowId=";
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());

            if (ppmAppWorkflowInfoDto != null) {
                workflowOutputService.deleteTask(ppmAppWorkflowInfoDto, imgTxUrl);
            }else {
                ppmAppWorkflowInfoDto =
                        ppmAppWorkflowInfoService.queryDetailForPushState("pushError",bps.getProcessInstId());
            }

            if (flag && ppmAppWorkflowInfoDto != null){
                workflowOutputService.pushTask(stringJoiner.toString(),"外包人员面试一级部经理审批",
                        ppmAppWorkflowInfoDto.getPushContent(),imgTxUrl,
                        bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"));
            }

            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, msgIds.toArray(new String[msgIds.size()]));
        } catch (Exception e) {
            log.error("单价确认失败！");
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String zhbConfirm(PpmInterviewerInfoDto interviewerInfoDto) {
        BpsWorkFlowDto bps = interviewerInfoDto.getBps();
        List<PpmIntervieweeInfo> eeList = interviewerInfoDto.getEeList();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
//            StringJoiner stringJoiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
//            CommEmployeeDto commEmployeeDto = new CommEmployeeDto();
//            //永赢租赁金融科技部-总经理室
//            commEmployeeDto.setDepid(80000053);
//            commEmployeeService.getEmployeeLists(commEmployeeDto)
//                    .stream().map(CommEmployeeDto::getBadge).collect(Collectors.toList())
//                    .forEach(stringJoiner::add);

            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                // 修改应聘信息
                ppmIntervieweeInfoService.addPpmIntervieweeInfo(eeList);
                this.archive(eeList);
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("一级部总经理室审批");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                workflowOutputService.deleteTask(ppmAppWorkflowInfoDto,appUrlValue + "?flowId=");
            }

            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("一级部总经理室审批失败！");
            throw new BizException(e);
        }
    }

    @Override
    public List<PpmInterviewApplyDto> exportExcel(PpmInterviewApplyDto ppmInterviewApplyDto,
                                                  CommOperatorDoLoginDto user) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmInterviewApplyDto, PpmInterviewApplyDto.class);
        int type = getRoles(user);
        if (4 == type) {
            String deptIdstr = commParttimejobService.queryDeptIdByBadge(user.getUserid());
            if (StringUtil.isNotBlank(deptIdstr)) {
                map.put("deptno", "'" + user.getDeptId() + "'," + deptIdstr + "");
            } else {
                map.put("deptno", user.getDeptId());
            }

        } else if (0 == type) { // 普通行员
            map.put("applyer", user.getUserid());
        }

        List<PpmInterviewApplyInfo> infos = myBatisDao.selectListT(PPMINTERVIEWAPPLY, map, "queryPagerList");
        List<PpmInterviewApplyDto> dtos = JsonUtil.jsonToList(infos, PpmInterviewApplyDto.class);
        // 业务字典
        // 人员类型
        List<EosDictEntry> typeList = eosDictEntryService.getTypeidForList("RLWBXQLX");
        // 结果
        List<EosDictEntry> msList = eosDictEntryService.getTypeidForList("NEW_BUSI_OUTSOURCE_INTERVRESULT");
        // 状态-入职
        List<EosDictEntry> rzList = eosDictEntryService.getTypeidForList("NEW_BUSI_WBRZ");
        List<EosDictEntry> eosList = new ArrayList<>();
        eosList.addAll(typeList);
        eosList.addAll(msList);
        eosList.addAll(rzList);

        // 面试后台界面，仅管理员、二级部经理层可以看到单价，其它人处屏蔽
        dtos.stream().forEach(ii -> {
            if (0 == type) { // 普通行员屏蔽单价
                ii.setSuggestprice(null);
            }
            // 时间格式为 yyyy-mm-dd
            if (StringUtil.isNotBlank(ii.getCreatetime())) {
                ii.setCreatetime(DateUtil.dateToStr(DateUtil.dataFormat(ii.getCreatetime(), "yyyy-MM-dd HH:mm"), "yyyy-MM-dd"));
            }

            eosList.stream().forEach(t -> {
                // 人员类型
                if (t.getDictid().equals(ii.getPosition()) && "RLWBXQLX".equals(t.getDicttypeid())) {
                    ii.setPosition(t.getDictname());
                }
                // 结果
                if (t.getDictid().equals(ii.getInterviewresult()) && "NEW_BUSI_OUTSOURCE_INTERVRESULT".equals(t.getDicttypeid())) {
                    // 入职
                    if ("0".equals(ii.getInterviewresult())) {
                        ii.setIsrz("未入职");
                    } else if ("1".equals(ii.getInterviewresult())) {
                        ii.setIsrz("入职");
                    }
                    ii.setInterviewresult(t.getDictname());

                }
            });
        });
        return dtos;

    }

    @Transactional("defaultTx")
    @Override
    public void newAndOldDataSysnc() {
        // 主表
        myBatisDao.executionPojo(PPMINTERVIEWAPPLY, null, IMyBatisDao.SQL.INSTER, "newAndOldDataSysnc1");
        // 关联表
        myBatisDao.executionPojo(PPMINTERVIEWAPPLY, null, IMyBatisDao.SQL.INSTER, "newAndOldDataSysnc2");
        // 面试官打分表
        myBatisDao.executionPojo(PPMINTERVIEWAPPLY, null, IMyBatisDao.SQL.INSTER, "newAndOldDataSysnc3");
    }

    @Override
    public PpmInterviewApplyDto interviewScore(PpmInterviewApplyDto ppmInterviewApply) {
        // 面试官
        List<PpmInterviewerInfoDto> erList = ppmInterviewerInfoService.queryListForInstanceid(ppmInterviewApply.getInstanceid());
        // 应聘者
        List<PpmIntervieweeInfoDto> eeList = ppmIntervieweeInfoService.queryListForInstanceid(ppmInterviewApply.getInstanceid());
        if (null != eeList && !eeList.isEmpty()) {
            eeList.stream().forEach(item -> {
                List<PpmIntervieweeScoreinfo> scoreList = ppmIntervieweeScoreinfoService.getScoreList(ppmInterviewApply.getInstanceid(), item.getId() + "");
                item.setScoreList(scoreList);
            });
        }
        // 面试申请
        PpmInterviewApply itm = queryDetail(ppmInterviewApply);
        if (null == itm) {
            return null;
        }
        PpmInterviewApplyDto dto = JsonUtil.jsonToClass(itm, PpmInterviewApplyDto.class);
        dto.setErList(erList);
        dto.setEeList(eeList);
        return dto;
    }

    @Override
    public Integer updatePpmInterviewApply(PpmInterviewApply ppmInterviewApply) {
        return myBatisDao.executionPojo(PPMINTERVIEWAPPLY, ppmInterviewApply, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deletePpmInterviewApply(PpmInterviewApply ppmInterviewApply) {
        int count = myBatisDao.executionPojo(PPMINTERVIEWAPPLY, ppmInterviewApply, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");
        if (StrUtils.isNotBlank(ppmInterviewApply.getInstanceid())) {
            // 清理关联业务数据
            ppmIntervieweeInfoService.deleteByInstanceId(ppmInterviewApply.getInstanceid());
            ppmInterviewerInfoService.deleteByInstanceId(ppmInterviewApply.getInstanceid());
            ppmIntervieweeOverinfoService.deleteByInstanceId(ppmInterviewApply.getInstanceid());
            ppmIntervieweeScoreinfoService.deleteByInstanceId(ppmInterviewApply.getInstanceid());
            // 删除流程相关数据
            CamundaUtils.delProjectFlow(ppmInterviewApply.getInstanceid());
            CamundaUtils.terminateProcess(ppmInterviewApply.getInstanceid());
        }
        return count;
    }

    @Override
    public PpmInterviewApply queryDetail(PpmInterviewApplyDto ppmInterviewApply) {
        return myBatisDao.selectClass(PPMINTERVIEWAPPLY, ppmInterviewApply, "queryDetail");
    }

    /**
     * 更新面试员工成绩
     *
     * @param ivsList
     * @param bps
     */
    private void updateSorce(List<PpmIntervieweeScoreinfo> ivsList, BpsWorkFlowDto bps) {
        // 面试评语
        String mspy = "";
        for (PpmIntervieweeScoreinfo tt : ivsList) {
            // 技术评分
            double technologyScore = 0;
            // 综合评分
            double comprehensiveScore = 0;
            // 经验评分
            double experienceScore = 0;
            // 学历评分
            double education = 0;
            List<PpmIntervieweeScoreinfo> list = ppmIntervieweeScoreinfoService.getScoreList(bps.getProcessInstId(), tt.getPid());
            if (null != list && !list.isEmpty()) {
                // 初始化标记
                for (PpmIntervieweeScoreinfo item : list) {
                    education += InterviewEduLevelEnums.fromCode(item.getEducation()).getValue();
                    technologyScore += Double.valueOf(item.getTechnology());
                    experienceScore += Double.valueOf(item.getExperience());
                    comprehensiveScore += Double.valueOf(item.getComprehensive());
                    mspy += item.getRemark() + "(" + item.getMsgname().substring(0, item.getMsgname().indexOf("(")) + ");";
                }
            }
            int size = list.size();
            // 面试总分
            double sumSocre = technologyScore / size
                    + comprehensiveScore / size
                    + experienceScore / size
                    + education / size;
            BigDecimal totalSocre = BigDecimal.valueOf(sumSocre).setScale(2, RoundingMode.HALF_DOWN);
            // 更新分数
            PpmIntervieweeInfo eeinfo = new PpmIntervieweeInfo();
            eeinfo.setScore(String.valueOf(totalSocre.doubleValue()));
            eeinfo.setIdcard(tt.getIdcard());
            eeinfo.setInstanceid(tt.getInstanceid());
            eeinfo.setId(Long.valueOf(tt.getPid()));
            // 0:不通过, 1: 通过
            eeinfo.setInterviewresult(sumSocre >= 6 ? "1" : "0");
            eeinfo.setMspy(StringUtil.isNotBlank(mspy) ? mspy.substring(0, mspy.length() - 1) : "");
            ppmIntervieweeInfoService.updatePpmIntervieweeInfo(eeinfo);
            mspy = "";
        }
    }

    @Override
    public void doDelete(BpsWorkFlowDto bps) {
        PpmInterviewApply apply = new PpmInterviewApply();
        apply.setInstanceid(bps.getProcessInstId());
        this.deletePpmInterviewApply(apply);
    }

    /**
     * getRoles
     *
     * @param user
     * @return 1:管理员，2：总经理，3：综合部经理层，4：二级部经理层，0：普通行员
     */
    private int getRoles(CommOperatorDoLoginDto user) {
        String depid = user.getDeptId();
        List<ImRoleDoLoginDto> roles = user.getRoles();
        boolean flag = false;
        //判断是否是总经理室
        if ("131".equals(depid)) {
            return 2;
        }
        //判断是否是管理员
        boolean admin = roles.stream().anyMatch(r -> "00000001".equals(r.getRoleId()) || "MS00002".equals(r.getRoleId()));
        if (admin) {
            return 1;
        }
        CommEmployee emp = UsersUtil.getCommEmployeeInfo(user.getUserid());
        if (null == emp) {
            return 0;
        }
        //判断是否是综合部经理层
        if ("132".equals(depid) && ("009".equals(emp.getHeadship()) ||
                "010".equals(emp.getHeadship()) || "030".equals(emp.getHeadship()) || "037".equals(emp.getHeadship()))
        ) {
            return 3;
        }
        // 二级部经理层
        if ("009".equals(emp.getHeadship()) ||
                "010".equals(emp.getHeadship()) || "030".equals(emp.getHeadship()) || "037".equals(emp.getHeadship())
        ) {
            return 4;
        }
        return 0;
    }

    /**
     * 不符合要求时
     *
     * @param eeList 集合
     * @return int
     */
    private int addOverLog(List<PpmIntervieweeInfo> eeList) {
        eeList.stream().forEach(item -> {
            PpmIntervieweeOverinfo overinfo = null;
            PpmIntervieweeInfoDto dto = ppmIntervieweeInfoService.queryDetail(item.getId(), item.getInstanceid());
            overinfo = JsonUtil.jsonToClass(dto, PpmIntervieweeOverinfo.class);
            overinfo.setId(null);
            overinfo.setIsrz("0");  // 不符合要求
            PpmInterviewApplyDto paramDto = new PpmInterviewApplyDto();
            paramDto.setInstanceid(item.getInstanceid());
            // 查询面试申请表
            PpmInterviewApply apply = this.queryDetail(paramDto);
            overinfo.setCreatetime(apply.getCreatetime());
            ppmIntervieweeOverinfoService.addPpmIntervieweeOverinfo(overinfo);
        });
        return 1;
    }

    /**
     * 流程结束添加归档表
     *
     * @param eeList 集合
     * @return int
     */
    private int archive(List<PpmIntervieweeInfo> eeList) {
        eeList.stream().forEach(item -> {
            PpmIntervieweeOverinfo overinfo;
            PpmIntervieweeInfoDto dto = ppmIntervieweeInfoService.queryDetail(item.getId(), item.getInstanceid());
            overinfo = JsonUtil.jsonToClass(dto, PpmIntervieweeOverinfo.class);
            overinfo.setId(null);
            overinfo.setIsrz("1");  // 符合要求
            PpmInterviewApplyDto paramDto = new PpmInterviewApplyDto();
            paramDto.setInstanceid(item.getInstanceid());
            // 查询面试申请表
            PpmInterviewApply apply = this.queryDetail(paramDto);
            overinfo.setCreatetime(apply.getCreatetime());
            ppmIntervieweeOverinfoService.addPpmIntervieweeOverinfo(overinfo);

            // 根据人力需求ID查询面试已通过人数
            int passNum = ppmIntervieweeOverinfoService.queryCarryOutNum(apply.getRlId());
            // 查询人力需求详情
            PpmManpowerdemandApplyInfo pmaItem = ppmManpowerdemandApplyInfoService.queryDetailById(apply.getRlId());
            // 判断如果人数相等 说明该流程完成 更新人力需求主表信息
            if (passNum >= Integer.parseInt(pmaItem.getUsernum())) {
                ppmManpowerdemandApplyService.updateFlowStatus(apply.getRlId());
            }
        });
        return 1;
    }

    @Override
    public void archiveApproval(List<PpmIntervieweeInfo> eeList){
        this.archive(eeList);
    }

}
