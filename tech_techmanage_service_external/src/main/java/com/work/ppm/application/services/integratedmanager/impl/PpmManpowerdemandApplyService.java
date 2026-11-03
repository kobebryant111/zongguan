package com.work.ppm.application.services.integratedmanager.impl;

import com.work.imex.util.StrUtils;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.work.ppm.application.dto.integratedmanager.ManpowerdemandsInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmManpowerdemandApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmManpowerdemandApplyInfoInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmManpowerdemandApplyOrgDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApply;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfo;
import com.work.ppm.application.model.integratedmanager.ext.PpmManpowerdemandApplyInfoOrgInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;
import com.work.ppm.application.services.flowmanager.IPpmAppWorkflowInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmManpowerdemandApplyService;
import com.work.ppm.application.services.othermanager.impl.PpmOutsourcingInfoService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.workflowmanager.IWorkflowOutputService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.CamundaExtUtils;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 人力需求主表
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service("ppmManpowerdemandApplyService")
public class PpmManpowerdemandApplyService extends BaseDaoSupper implements IPpmManpowerdemandApplyService, ICommonDeleteFlowService {
    // 实体类路径
    private static final String PPMMANPOWERDEMANDAPPLY = "com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApply";
    // 流程定义ID
    private static final String FLOW_ID = "wbrlxq";
    //
    private static final String FLOW_TITLE = "人力外包需求申请";
    //数据接口
    private final IMyBatisDao myBatisDao;
    //字典服务
    private final IEosDictEntryService eosDictEntryService;
    //员工服务
    private final ICommEmployeeService commEmployeeService;
    //工作流服务
    private final WorkFlowHttpClientService workFlowHttpClientService;
    //外包商服务
    private final PpmOutsourcingInfoService ppmOutsourcingInfoService;
    //人力需求详情
    private final PpmManpowerdemandApplyInfoService ppmManpowerdemandApplyInfoService;
    //app审批
    @Lazy
    @Autowired
    private IWorkflowOutputService workflowOutputService;

    @Autowired
    private IPpmAppWorkflowInfoService ppmAppWorkflowInfoService;

    @Value("${appUrl.demand}")
    private String appUrlValue;

    @Override
    public void queryPager(PpmManpowerdemandApply apply, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(apply, PpmManpowerdemandApply.class);
        myBatisDao.selectPager(PPMMANPOWERDEMANDAPPLY, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmManpowerdemandApply(PpmManpowerdemandApplyDto applyDto) {
        int count; //记录条数
        if (ObjectUtils.isEmpty(applyDto.getId())) {
            // 如果需要请set 主键id
            Long id = getNextSequence(PpmManpowerdemandApply.class);
            applyDto.setId(id.intValue());
            count = myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLY, applyDto, IMyBatisDao.SQL.INSTER, "addPojo");
        } else {
            count = myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLY, applyDto, IMyBatisDao.SQL.UPDATE, "updatePojo");
        }
        return count;
    }

    @Override
    public PpmManpowerdemandApply queryDetail(PpmManpowerdemandApply apply) {
        return myBatisDao.selectClass(PPMMANPOWERDEMANDAPPLY, apply, "queryDetail");
    }

    @Override
    public Integer updatePpmManpowerdemandApply(PpmManpowerdemandApply apply) {
        return myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLY, apply, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    @Transactional
    public Integer deletePpmManpowerdemandApply(PpmManpowerdemandApply apply) {
        int count = myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLY, apply, IMyBatisDao.SQL.DELETE, "deleteById");
        if (StrUtils.isNotBlank(apply.getInstanceid())) {
            ppmManpowerdemandApplyInfoService.deleteByInstanceid(apply.getInstanceid());
            // 删除流程相关数据
            CamundaUtils.delProjectFlow(apply.getInstanceid());
            CamundaUtils.terminateProcess(apply.getInstanceid());
        }
        return count;
    }

    @Override
    public int savePpmManpowerDemandApply(PpmManpowerdemandApply apply) {
        int i = myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLY, apply, IMyBatisDao.SQL.INSTER, "addPojo");
        return i;
    }

    @Override
    public PpmManpowerdemandApplyDto save(PpmManpowerdemandApplyDto applyDto,
                                          List<PpmManpowerdemandApplyInfo> infos, CommOperatorDoLoginDto user) {
        try {
            // 申请人姓名处理
            if (!StringUtil.isNotBlank(applyDto.getUsername())) {
                CommEmployee emp = commEmployeeService.getCommEmployeeInfo(applyDto.getUserid());
                applyDto.setUsername(emp.getEmpname() + "(" + applyDto.getUserid() + ")");
            }
            // 判断流程实例是否存在，不存在说明是初始提交
            if (!StringUtil.isNotBlank(applyDto.getInstanceid())) {
                applyDto.setCreatetime(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                applyDto.setCreateuserid(applyDto.getUserid());
            }
            // 人力需求申请入库
            this.addPpmManpowerdemandApply(applyDto);
            ppmManpowerdemandApplyInfoService.addPpmIntervieweeInfo(infos,
                    applyDto.getInstanceid(), applyDto.getUserid());

        } catch (Exception e) {
            throw new BizException(e);
        }
        return applyDto;
    }

    @Override
    @Transactional("defaultTx")
    public String liuzhuan(PpmManpowerdemandApplyDto applyDto, BpsWorkFlowDto bps) {
        ProcessBean processBean = null;
        try {
            // 申请人姓名处理
            if (!StringUtil.isNotBlank(applyDto.getUsername())) {
                CommEmployee emp = commEmployeeService.getCommEmployeeInfo(applyDto.getUserid());
                applyDto.setUsername(emp.getEmpname() + "(" + applyDto.getUserid() + ")");
            }
            //继续流转到主持工作的二级部经理
            CommEmployeeDto workManager = UsersUtil.getTwoWorkManager(applyDto.getDeptno());
            // 下一步审批人
            if (StringUtils.isBlank(workManager.getBadge())) {
                throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "当前用户无二级部经理");
            }
            // 判断流程实例是否存在，不存在说明是初始提交
            if (StringUtils.isBlank(applyDto.getInstanceid())) {
                applyDto.setCreatetime(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                applyDto.setCreateuserid(bps.getUserId());
                this.addPpmManpowerdemandApply(applyDto);

                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(String.valueOf(applyDto.getId()));
                // 流程参数
                Map mPara = new HashMap();
                mPara.put("userId", workManager.getBadge());
                processInfo.setmPara(mPara);// 流程变量
                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(processBean.getProcessInstanceId());
                // 回填流程实例编码
                applyDto.setInstanceid(processBean.getProcessInstanceId());
                this.addPpmManpowerdemandApply(applyDto);
                ppmManpowerdemandApplyInfoService.addPpmIntervieweeInfo(applyDto.getInfos(),
                        applyDto.getInstanceid(), bps.getUserId());
                // 日志处理
                bps.setProjectNo(String.valueOf(applyDto.getId()));
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setApplydesc("人力需求发起");
                bps.setRelaData(mPara);
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessInstName("【" + applyDto.getUsername() + DateUtil.dateToNoBar(new Date()) + "】" + "人力需求");
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setSuggest("提交申请");

                //推送待办
                String imgTxUrl = appUrlValue + "?flowId=";
                String imgTxDsc = applyDto.getUsername() + "于"
                        + DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")
                        + "发起人力需求，请及时登录手机工作平台处理。";
                workflowOutputService.pushTask(workManager.getBadge(),"人力外包需求二级部经理审批",
                        imgTxDsc,imgTxUrl,bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"));

                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            } else {
                this.addPpmManpowerdemandApply(applyDto);
                ppmManpowerdemandApplyInfoService.addPpmIntervieweeInfo(applyDto.getInfos(),
                        applyDto.getInstanceid(), bps.getUserId());
                // 执行任务
                TaskBean param = new TaskBean();
                param.setTenantId(BusinessConstant.TENANT_ID);// 租户
                param.setTaskId(bps.getTaskId());// 任务id
                param.setUserId(bps.getUserId());// 处理人
                param.setComment(bps.getSuggest());// 审批意见

                Map mPara = new HashMap();
                mPara.put("userId", workManager.getBadge());
                param.setmPara(mPara);// 流程变量
                // 提交任务
                workFlowHttpClientService.submitTask(param);
                // 日志处理
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(applyDto.getInstanceid());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setSuggest("重新提交");
                bps.setApplydesc("人力需求发起");
                bps.setProcessInstDesc(FLOW_TITLE);

                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(applyDto.getInstanceid());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);

                //推送待办
                String imgTxUrl = appUrlValue + "?flowId=";
                String imgTxDsc = applyDto.getUsername() + "于"
                        + DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")
                        + "发起人力需求，请及时登录手机工作平台处理。";
                workflowOutputService.pushTask(workManager.getBadge(),"人力外包需求二级部经理审批",
                        imgTxDsc,imgTxUrl,bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"));
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            }
        } catch (Exception e) {
            CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            log.error("外包需求发起流程失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    @Transactional("defaultTx")
    public String twoManagerAudit(PpmManpowerdemandApplyDto applyDto, BpsWorkFlowDto bps) {
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            String[] msgIds = null;
            boolean flag = false;
            StringJoiner stringJoiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                msgIds = commEmployeeService.queryUserIdByRole("GENERALMANAGER").split(",");
                //更新业务数据
                applyDto.setManageruserid(bps.getUserId());
                PpmManpowerdemandApply apply = JsonUtil.jsonToClass(applyDto, PpmManpowerdemandApply.class);
                this.updatePpmManpowerdemandApply(apply);

                for (String msgId : msgIds){
                    stringJoiner.add(msgId);
                }

                flag = true;
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(applyDto.getInstanceid());
            bps.setApplydesc("二级部主持工作经理审批");
            bps.setRelaData(mPara);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(applyDto.getInstanceid());

            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,msgIds,bps);

            String imgTxUrl = appUrlValue + "?flowId=";

            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                workflowOutputService.deleteTask(ppmAppWorkflowInfoDto, imgTxUrl);
            }else {
                ppmAppWorkflowInfoDto =
                        ppmAppWorkflowInfoService.queryDetailForPushState("pushError",bps.getProcessInstId());
            }

            if (flag && ppmAppWorkflowInfoDto != null){
                //推送待办
                workflowOutputService.pushTask(stringJoiner.toString(),"人力外包需求一级部经理审批",
                        ppmAppWorkflowInfoDto.getPushContent(),imgTxUrl,
                        bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"));
            }

            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, msgIds);
        } catch (Exception e) {
            log.error("二级部门审批失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    @Transactional("defaultTx")
    public String confirm(PpmManpowerdemandApplyDto applyDto, BpsWorkFlowDto bps) {
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
//            String[] msgIds;
//            StringJoiner stringJoiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
//            msgIds = commEmployeeService.queryUserIdByRole("GENERALMANAGER").split(BusinessUtil.REGEX_COMMA);
//            for (String msgId : msgIds){
//                stringJoiner.add(msgId);
//            }
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                //更新业务数据
                applyDto.setStatus("1");
                applyDto.setCreateuserid(bps.getUserId());
                PpmManpowerdemandApply apply = JsonUtil.jsonToClass(applyDto, PpmManpowerdemandApply.class);
                this.updatePpmManpowerdemandApply(apply);

            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(applyDto.getInstanceid());
            bps.setApplydesc("一级部总经理室审批");
            bps.setRelaData(mPara);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(applyDto.getInstanceid());
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
            log.error("一级部总经理室审批失败：{}", e);
            throw new BizException(e);
        }
    }


    @Override
    public List<PpmManpowerdemandApplyInfoInfoDto> queryPagerList(ManpowerdemandsInfoDto infoDto, Pager pager, CommOperatorDoLoginDto user) {
        return ppmManpowerdemandApplyInfoService.queryPagerList(infoDto, pager, user);
    }

    @Override
    public PpmManpowerdemandApplyOrgDto queryDetails(PpmManpowerdemandApply demandApply) {
        //查询主记录数据
        Map<String, Object> map = JsonUtil.objectTOMap(demandApply, PpmManpowerdemandApply.class);
        PpmManpowerdemandApply apply = myBatisDao.selectClass(PPMMANPOWERDEMANDAPPLY, map, "queryDetailByInstanceid");
        PpmManpowerdemandApplyOrgDto applyDto = JsonUtil.jsonToClass(apply, PpmManpowerdemandApplyOrgDto.class);

        //查询详细数据
        PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo = new PpmManpowerdemandApplyInfo();
        ppmManpowerdemandApplyInfo.setInstanceid(demandApply.getInstanceid());
        List<PpmManpowerdemandApplyInfo> applys = ppmManpowerdemandApplyInfoService.queryPagerList(ppmManpowerdemandApplyInfo);
        List<PpmManpowerdemandApplyInfoOrgInfo> applysDtos = JsonUtil.jsonToList(applys, PpmManpowerdemandApplyInfoOrgInfo.class);
        //外包商集合
        applysDtos.stream().forEach(item -> {
            if (item.getSupplyids() != null && !"".equals(item.getSupplyids())) {
                item.setOinfos(ppmOutsourcingInfoService.getListByNos(item.getSupplyids()));
            }
        });
        applyDto.setInfos(applysDtos);

        return applyDto;
    }

    @Override
    public Integer updateStatus(PpmManpowerdemandApplyInfo applyInfo) {
        //更新数据
        int i = ppmManpowerdemandApplyInfoService.updateStatus(applyInfo);
        if (i == 1) {
            //更新成功，查询还有没有未完成的人力需求数据
            PpmManpowerdemandApplyInfo map = new PpmManpowerdemandApplyInfo();
            map.setInstanceid(applyInfo.getInstanceid());
            map.setStatus("0");
            List<PpmManpowerdemandApplyInfo> list = ppmManpowerdemandApplyInfoService.getList(map);
            //没有未完成的话更新主数据
            if (list == null || list.size() <= 0) {
                PpmManpowerdemandApply ppmManpowerdemandApply = new PpmManpowerdemandApply();
                ppmManpowerdemandApply.setInstanceid(applyInfo.getInstanceid());
                i = myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLY, ppmManpowerdemandApply,
                        IMyBatisDao.SQL.UPDATE, "updateManpowerdemandApplyFinish");
            }

        }
        return i;
    }

    @Override
    public void doDelete(BpsWorkFlowDto bps) {
        PpmManpowerdemandApply ppmManpowerDemandApply = new PpmManpowerdemandApply();
        ppmManpowerDemandApply.setInstanceid(bps.getProcessInstId());
        int i = myBatisDao.executionPojo(PPMMANPOWERDEMANDAPPLY, ppmManpowerDemandApply,
                IMyBatisDao.SQL.DELETE, "deleteByInstanceid");
        if (i == 1) {
            ppmManpowerdemandApplyInfoService.deleteByInstanceid(bps.getProcessInstId());
        }
    }

    @Override
    public int updateFlowStatus(long id) {
        PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo = new PpmManpowerdemandApplyInfo();
        ppmManpowerdemandApplyInfo.setId(id);
        ppmManpowerdemandApplyInfo = ppmManpowerdemandApplyInfoService.queryDetail(ppmManpowerdemandApplyInfo);
        int i = 0;
        if (null != ppmManpowerdemandApplyInfo) {
            i = this.updateStatus(ppmManpowerdemandApplyInfo);
        }

        return i;
    }
}
