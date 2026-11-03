package com.work.ppm.application.services.workflowmanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardApplyDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardApplyInfoDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardLedgerDto;
import com.work.ppm.application.dto.workflowmanager.AccessCardDetailDto;
import com.work.ppm.application.dto.workflowmanager.AccessCardDetailInfoDto;
import com.work.ppm.application.dto.workflowmanager.CommonApproveParamsDto;
import com.work.ppm.application.dto.workflowmanager.DetailFlowLogDto;
import com.work.ppm.application.dto.workflowmanager.ReturnCardDetailDto;
import com.work.ppm.application.dto.workflowmanager.ReturnCardDetailInfoDto;
import com.work.ppm.application.enums.ApprovalRetStatusEnums;
import com.work.ppm.application.enums.FlowCodeEnums;
import com.work.ppm.application.enums.FlowMessageEnums;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.flowmanager.IPpmAppWorkflowInfoService;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.otherdeptmanager.IPpmAccessCardApplyInfoService;
import com.work.ppm.application.services.otherdeptmanager.IPpmAccessCardApplyService;
import com.work.ppm.application.services.otherdeptmanager.IPpmAccessCardLedgerService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.workflowmanager.IWorkflowAccessCardService;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.CamundaExtUtils;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.JsonUtil;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * 科管->开放银行->星云->手机工作平台
 * 门禁卡申请/归还流程
 */
@Slf4j
@Service("workflowAccessCardService")
public class WorkflowAccessCardService extends WorkflowOutputService implements IWorkflowAccessCardService {

    /**
     * 工作流服务
     */
    @Autowired
    private WorkFlowHttpClientService workFlowHttpClientService;

    @Autowired
    private ICommEmployeeService commEmployeeService;

    @Autowired
    private IPpmAccessCardApplyService accessCardApplyService;

    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Autowired
    private IPpmAccessCardApplyInfoService accessCardApplyInfoService;

    @Autowired
    private IPpmAccessCardLedgerService accessCardLedgerService;

    @Autowired
    private IPpmFlowLogService ppmFlowLogService;

    @Autowired
    private IPpmAppWorkflowInfoService ppmAppWorkflowInfoService;

    @Override
    public Map<String, Object> commonApproveProcess(CommonApproveParamsDto approveParamsDto) {

        //识别业务编码
        String bizTypeCode = approveParamsDto.getBizTypeCode();
        if("0031".equals(bizTypeCode)){
            return accessCardApproveProcess(approveParamsDto);
        }

        if("0032".equals(bizTypeCode)){
            return returnCardApproveProcess(approveParamsDto);
        }

        return returnMap(FlowMessageEnums.FAIL.getCode(), FlowMessageEnums.FAIL_ERROR.getMessage(),null);
    }

    /**
     * 人力需求审批
     * @param approveParamsDto 对象
     * @return 返回json
     */
    private Map<String, Object> accessCardApproveProcess(CommonApproveParamsDto approveParamsDto) {

        String processInstId = approveParamsDto.getProcessInstId();
        TaskBean taskBean = null;
        try {
            taskBean = workFlowHttpClientService.getTasksByProcessInstanceId(processInstId);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.toString());
        }

        if(taskBean == null){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_ERROR_INST.getMessage(),null);
        }

        //解决手机端已打开页面，但pc端撤回的情况
        PpmAccessCardApplyDto accessCardApplyDto =
                accessCardApplyService.queryDetailForInstanceId(approveParamsDto.getProcessInstId());
        if (accessCardApplyDto == null){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_ERROR_INST.getMessage(),null);
        }

        //获取任务人列表
        List<Map> taskList = taskBean.getTaskList();

        //list判断
        if(CollectionUtils.isEmpty(taskList)){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_NOT_TASK.getMessage(),null);
        }

        //获取任务
        Map task = taskList.get(0);

        //查询推送的字典
        EosDictEntry eosDictEntry =
                eosDictEntryService.getTypenameForDetail(task.get("taskKey").toString(),"WORKFLOW_KEY_DICT");
        if (eosDictEntry == null){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_NOT_TASK.getMessage(),null);
        }
        //通过节点id筛选对应流程节点，从而走不同的审批流程

        String flowCase = eosDictEntry.getDictid();
        approveParamsDto.setFlowCase(flowCase);
        approveParamsDto.setProcessDefName(FlowCodeEnums.ACCESS_CARD.getMessage());

        if (flowCase.contains("_02")) {
            // 02 对应 科技部负责人审批
            String flowCode = flowCase.replace("_02", "");
            approveParamsDto.setFlowCode(flowCode);
            if(!verifyDate(task,approveParamsDto,null,"gmApprove")){
                return returnMap(FlowMessageEnums.FAIL.getCode(),
                        FlowMessageEnums.FAIL_ASSIGNEE_TASK.getMessage(),null);
            }

            if(accessCardGmApproval(task,approveParamsDto,accessCardApplyDto)){
                //无需修改流程详情和台账表

                //记录当前处理人
                accessCardApplyService.updatePpmAccessCardApply(accessCardApplyDto);
            }else {
                return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_ERROR_TASK.getMessage(), null);
            }
        }else if (flowCase.contains("_03")) {
            // 03 对应 监保部审批
            String flowCode = flowCase.replace("_03", "");
            approveParamsDto.setFlowCode(flowCode);
            if(!verifyDate(task,approveParamsDto,null,"monitorApprove")){
                return returnMap(FlowMessageEnums.FAIL.getCode(),
                        FlowMessageEnums.FAIL_ASSIGNEE_TASK.getMessage(),null);
            }

            if(accessCardMonitorApproval(task,approveParamsDto,accessCardApplyDto)){
                //无需修改流程详情和台账表

                //记录当前处理人
                accessCardApplyService.updatePpmAccessCardApply(accessCardApplyDto);
            }else {
                return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_ERROR_TASK.getMessage(), null);
            }
        }else if (flowCase.contains("_04")) {
            // 04 对应 监保部确认
            String flowCode = flowCase.replace("_04", "");
            approveParamsDto.setFlowCode(flowCode);
            if(!verifyDate(task,approveParamsDto,"JBB_AFFIRM",null)){
                return returnMap(FlowMessageEnums.FAIL.getCode(),
                        FlowMessageEnums.FAIL_ASSIGNEE_TASK.getMessage(),null);
            }

            if(accessCardAffirmApproval(task,approveParamsDto,accessCardApplyDto,"Receive")){
                //无需修改流程详情和台账表

                //记录当前处理人
                accessCardApplyService.updatePpmAccessCardApply(accessCardApplyDto);
            }else {
                return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_ERROR_TASK.getMessage(), null);
            }
        }

        return returnMap(FlowMessageEnums.PASS.getCode(),FlowMessageEnums.PASS.getMessage(), null);
    }

    /**
     * 归还门禁卡流程
     * @param approveParamsDto 对象
     * @return 返回
     */
    private Map<String, Object> returnCardApproveProcess(CommonApproveParamsDto approveParamsDto) {
        String processInstId = approveParamsDto.getProcessInstId();
        TaskBean taskBean = null;
        try {
            taskBean = workFlowHttpClientService.getTasksByProcessInstanceId(processInstId);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.toString());
        }

        if(taskBean == null){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_ERROR_INST.getMessage(),null);
        }

        PpmAccessCardApplyDto accessCardApplyDto =
                accessCardApplyService.queryDetailForInstanceId(approveParamsDto.getProcessInstId());

        //获取任务人列表
        List<Map> taskList = taskBean.getTaskList();

        //list判断
        if(CollectionUtils.isEmpty(taskList)){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_NOT_TASK.getMessage(),null);
        }
        //获取任务
        Map task = taskList.get(0);

        //查询推送的字典
        EosDictEntry eosDictEntry =
                eosDictEntryService.getTypenameForDetail(task.get("taskKey").toString(),"WORKFLOW_KEY_DICT");
        if (eosDictEntry == null){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_NOT_TASK.getMessage(),null);
        }
        //通过节点id筛选对应流程节点，从而走不同的审批流程
        String flowCase = eosDictEntry.getDictid();
        approveParamsDto.setFlowCase(flowCase);
        approveParamsDto.setProcessDefName(FlowCodeEnums.RETURN_CARD.getMessage());

        if (flowCase.contains("_02")) {
            // 02 对应 科技部负责人审批
            String flowCode = flowCase.replace("_02", "");
            approveParamsDto.setFlowCode(flowCode);
            if(!verifyDate(task,approveParamsDto,"JBB_AFFIRM",null)){
                return returnMap(FlowMessageEnums.FAIL.getCode(),
                        FlowMessageEnums.FAIL_ASSIGNEE_TASK.getMessage(),null);
            }

            if(accessCardAffirmApproval(task,approveParamsDto,accessCardApplyDto,"Return")){
                //无需修改流程详情和台账表

                //记录当前处理人
                accessCardApplyService.updatePpmAccessCardApply(accessCardApplyDto);
            }else {
                return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_ERROR_TASK.getMessage(), null);
            }
        }

        return returnMap(FlowMessageEnums.PASS.getCode(),FlowMessageEnums.PASS.getMessage(), null);
    }

    @Override
    public Map<String, Object> queryAccessCardDetail(String instanceId) {
        PpmAccessCardApplyDto accessCardApplyDto = accessCardApplyService.queryDetailForInstanceId(instanceId);
        if (accessCardApplyDto == null || !"Receive".equals(accessCardApplyDto.getType())){
            return returnMap(FlowMessageEnums.FAIL.getCode(),
                    FlowMessageEnums.FAIL_ERROR_INST.getMessage(),null);
        }

        AccessCardDetailDto accessCardDetailDto = JsonUtil.jsonToClass(accessCardApplyDto,AccessCardDetailDto.class);
        accessCardDetailDto.setApplyUserId(accessCardApplyDto.getApplyerId());
        accessCardDetailDto.setApplyUserName(accessCardApplyDto.getApplyerName());
        accessCardDetailDto.setApplyDeptId(accessCardApplyDto.getApplyerDeptId());
        accessCardDetailDto.setApplyDeptName(accessCardApplyDto.getApplyerDeptName());

        TaskBean taskBean = null;
        try {
            taskBean = workFlowHttpClientService.getTasksByProcessInstanceId(instanceId);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.toString());
        }

        if(taskBean == null){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_ERROR_INST.getMessage(),null);
        }

        //获取任务人列表
        List<Map> taskList = taskBean.getTaskList();

        //list判断
        if(CollectionUtils.isEmpty(taskList)){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_NOT_TASK.getMessage(),null);
        }
        //获取任务
        EosDictEntry eosDictEntry = eosDictEntryService.getTypeidForDetail("WORKFLOW_KEY_DICT","0031_04");

        if (eosDictEntry != null && Objects.equals(taskList.get(0).get("taskKey"),eosDictEntry.getDictname())){
            accessCardDetailDto.setIfUpdateData("Y");
        }else {
            accessCardDetailDto.setIfUpdateData("N");
        }
        accessCardDetailDto.setCurrentUserId(accessCardApplyDto.getCurrentId());
        accessCardDetailDto.setCurrentUserName(accessCardApplyDto.getCurrentName());

        List<PpmAccessCardApplyInfoDto> accessCardApplyInfoDtos =
                accessCardApplyInfoService.queryDetailList(
                        PpmAccessCardApplyInfoDto.builder().instanceId(instanceId).build(),"getListForInstanceId");
        accessCardApplyInfoDtos.forEach(dto -> dto.setStandbyTwo(dto.getBizObjectId()));

        List<AccessCardDetailInfoDto> accessCardDetailInfoDtos =
                JsonUtil.jsonToList(accessCardApplyInfoDtos, AccessCardDetailInfoDto.class);

        accessCardDetailInfoDtos.forEach(accessCardDetailInfoDto -> {
            if ("W".equals(accessCardDetailInfoDto.getIfMakeCard())){
                accessCardDetailInfoDto.setIfMakeCard("Y");
            }
        });
        accessCardDetailDto.setReturnList(accessCardDetailInfoDtos);

        PpmFlowLog ppmFlowLog = new PpmFlowLog();
        ppmFlowLog.setFlowcaseno(instanceId);
        List<PpmFlowLogDto> flowLogDtos = ppmFlowLogService.queryList(ppmFlowLog);
        flowLogDtos.forEach(flowLogDto ->{
            if (StringUtils.isEmpty(flowLogDto.getConclusion())){
                return;
            }

            for (ApprovalRetStatusEnums approvalStatus : ApprovalRetStatusEnums.values()){
                if (Objects.equals(approvalStatus.getCode(),flowLogDto.getConclusion())){
                    flowLogDto.setConclusion(approvalStatus.getMessage());
                    break;
                }
            }
        });

        List<DetailFlowLogDto> flowLogDtoList = JsonUtil.jsonToList(flowLogDtos,DetailFlowLogDto.class);
        accessCardDetailDto.setFlowLog(flowLogDtoList);

        return returnMap(FlowMessageEnums.PASS.getCode(), FlowMessageEnums.PASS.getMessage(),accessCardDetailDto);
    }

    @Override
    public Map<String, Object> queryReturnCardDetail(String instanceId) {
        PpmAccessCardApplyDto accessCardApplyDto = accessCardApplyService.queryDetailForInstanceId(instanceId);
        if (accessCardApplyDto == null || !"Return".equals(accessCardApplyDto.getType())){
            return returnMap(FlowMessageEnums.FAIL.getCode(),
                    FlowMessageEnums.FAIL_ERROR_INST.getMessage(),null);
        }

        ReturnCardDetailDto returnCardDetailDto = JsonUtil.jsonToClass(accessCardApplyDto,ReturnCardDetailDto.class);
        returnCardDetailDto.setApplyUserId(accessCardApplyDto.getApplyerId());
        returnCardDetailDto.setApplyUserName(accessCardApplyDto.getApplyerName());
        returnCardDetailDto.setApplyDeptId(accessCardApplyDto.getApplyerDeptId());
        returnCardDetailDto.setApplyDeptName(accessCardApplyDto.getApplyerDeptName());

        TaskBean taskBean = null;
        try {
            taskBean = workFlowHttpClientService.getTasksByProcessInstanceId(instanceId);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.toString());
        }

        if(taskBean == null){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_ERROR_INST.getMessage(),null);
        }

        //获取任务人列表
        List<Map> taskList = taskBean.getTaskList();

        //list判断
        if(CollectionUtils.isEmpty(taskList)){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_NOT_TASK.getMessage(),null);
        }
        //获取任务
        EosDictEntry eosDictEntry = eosDictEntryService.getTypeidForDetail("WORKFLOW_KEY_DICT","0032_02");

        if (eosDictEntry != null && Objects.equals(taskList.get(0).get("taskKey"),eosDictEntry.getDictname())){
            returnCardDetailDto.setIfUpdateData("Y");
        }else {
            returnCardDetailDto.setIfUpdateData("N");
        }
        returnCardDetailDto.setCurrentUserId(accessCardApplyDto.getCurrentId());
        returnCardDetailDto.setCurrentUserName(accessCardApplyDto.getCurrentName());

        List<PpmAccessCardApplyInfoDto> accessCardApplyInfoDtos =
                accessCardApplyInfoService.queryDetailList(
                        PpmAccessCardApplyInfoDto.builder().instanceId(instanceId).build(),"getListForInstanceId");
        accessCardApplyInfoDtos.forEach(dto -> dto.setStandbyTwo(dto.getBizObjectId()));

        List<ReturnCardDetailInfoDto> returnCardDetailInfoDtos =
                    JsonUtil.jsonToList(accessCardApplyInfoDtos, ReturnCardDetailInfoDto.class);

        returnCardDetailInfoDtos.forEach(returnCardDetailInfoDto -> {
            if ("W".equals(returnCardDetailInfoDto.getIfMakeCard())){
                returnCardDetailInfoDto.setIfMakeCard("Y");
            }
        });

        returnCardDetailDto.setReturnList(returnCardDetailInfoDtos);

        PpmFlowLog ppmFlowLog = new PpmFlowLog();
        ppmFlowLog.setFlowcaseno(instanceId);
        List<PpmFlowLogDto> flowLogDtos = ppmFlowLogService.queryList(ppmFlowLog);
        flowLogDtos.forEach(flowLogDto ->{
            if (StringUtils.isEmpty(flowLogDto.getConclusion())){
                return;
            }

            for (ApprovalRetStatusEnums approvalStatus : ApprovalRetStatusEnums.values()){
                if (Objects.equals(approvalStatus.getCode(),flowLogDto.getConclusion())){
                    flowLogDto.setConclusion(approvalStatus.getMessage());
                    break;
                }
            }
        });

        List<DetailFlowLogDto> flowLogDtoList = JsonUtil.jsonToList(flowLogDtos,DetailFlowLogDto.class);
        returnCardDetailDto.setFlowLog(flowLogDtoList);

        return returnMap(FlowMessageEnums.PASS.getCode(), FlowMessageEnums.PASS.getMessage(),returnCardDetailDto);
    }

    /**
     * 科技部负责人审批 无需关注returnList是否有值，
     * @param task 任务对象
     * @param approveParamsDto 对象
     * @return boolean T/F
     */
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    private boolean accessCardGmApproval(Map task,CommonApproveParamsDto approveParamsDto,
                                         PpmAccessCardApplyDto accessCardApplyDto){

        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(task.get("taskId")+"");// 任务id
            param.setUserId(approveParamsDto.getAssignee());// 处理人
            param.setComment(APP_SUGGEST_PREFIX + approveParamsDto.getSuggest());// 审批意见
            Map params = new HashMap();
            params.put("status", "return");
            boolean flag = false;
            accessCardApplyDto.setModifiedBy(param.getUserId());
            accessCardApplyDto.setModifiedTime(new Date());
            accessCardApplyDto.setState("0");
            accessCardApplyDto.setStateName("重新提交");
            accessCardApplyDto.setCurrentId(accessCardApplyDto.getApplyerId());
            accessCardApplyDto.setCurrentName(accessCardApplyDto.getApplyerName());

            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(approveParamsDto.getConclusion())) {
                params.put("status", "pass");

                //从字典内获取到下一个节点处理人
                EosDictEntry eosDictEntry =
                        eosDictEntryService.getTypeidForDetail("ACCESS_CARD_FLOW_CURRENT","monitorApprove");

                params.put("userId", eosDictEntry.getDictname());// 下一步审批人

                CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(eosDictEntry.getDictname());
                accessCardApplyDto.setState("2");
                accessCardApplyDto.setStateName("处理中");
                accessCardApplyDto.setCurrentId(commEmployee.getBadge());
                accessCardApplyDto.setCurrentName(commEmployee.getEmpname());

                flag = true;
            }
            param.setmPara(params);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setTaskId(param.getTaskId());
            bps.setUserId(approveParamsDto.getAssignee());
            bps.setSuggest(APP_SUGGEST_PREFIX + approveParamsDto.getSuggest());
            bps.setConclusion(approveParamsDto.getConclusion());
            bps.setProcessInstId(approveParamsDto.getProcessInstId());
            bps.setApplydesc(task.get("taskName").toString());
            bps.setProcessDefName(approveParamsDto.getProcessDefName());
            bps.setRelaData(params);
            // 获取流程信息
            TaskBean taskQuery = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskQuery);
            CamundaExtUtils.saveNextTaskInfo(taskQuery,null,bps);
            CamundaUtils.findNextTaskInfo(taskQuery, null);

            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(param.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                deleteTaskXy(ppmAppWorkflowInfoDto);
            }else {
                ppmAppWorkflowInfoDto =
                        ppmAppWorkflowInfoService.queryDetailForPushState("pushError",bps.getProcessInstId());
            }

            if (flag && ppmAppWorkflowInfoDto != null){
                //推送待办
                pushTaskCommon(accessCardApplyDto.getCurrentId(),"门禁卡领用申请监保部审批",
                        ppmAppWorkflowInfoDto.getPushContent(),bps.getProcessInstId(),
                        taskQuery.getTaskList().get(0).get("taskId"),"1");
            }
        } catch (Exception e) {
            log.error("科技部负责人审批失败：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 监保部审批节点 审批
     * @param task 任务对象
     * @param approveParamsDto 对象
     * @param accessCardApplyDto 业务流程对象
     * @return boolean T/F
     */
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    private boolean accessCardMonitorApproval(Map task,CommonApproveParamsDto approveParamsDto,
                                              PpmAccessCardApplyDto accessCardApplyDto){
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(task.get("taskId")+"");// 任务id
            param.setUserId(approveParamsDto.getAssignee());// 处理人
            param.setComment(APP_SUGGEST_PREFIX + approveParamsDto.getSuggest());// 审批意见
            Map params = new HashMap();
            params.put("status", "return");

            accessCardApplyDto.setModifiedBy(param.getUserId());
            accessCardApplyDto.setModifiedTime(new Date());
            accessCardApplyDto.setState("0");
            accessCardApplyDto.setStateName("重新提交");
            accessCardApplyDto.setCurrentId(accessCardApplyDto.getApplyerId());
            accessCardApplyDto.setCurrentName(accessCardApplyDto.getApplyerName());
            StringJoiner joiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
            StringJoiner joinerTwo = new StringJoiner(BusinessUtil.REGEX_COMMA);
            String[] userIds = null;
            // 通过状态
            boolean flag = false;
            if (WorkflowConclusionEnums.PASS.getCode().equals(approveParamsDto.getConclusion())) {
                params.put("status", "pass");
                flag = true;
                userIds = commEmployeeService.queryUserIdByRole("JBB_AFFIRM").split(BusinessUtil.REGEX_COMMA);
                for (String str : userIds) {
                    joiner.add(str);
                    CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(str);
                    if(commEmployee != null){
                        joinerTwo.add(commEmployee.getEmpname());
                    }
                }

                accessCardApplyDto.setState("3");
                accessCardApplyDto.setStateName("处理中");
                accessCardApplyDto.setCurrentId(joiner.toString());
                accessCardApplyDto.setCurrentName(joinerTwo.toString());

                //获取库里现有的详情列表
                accessCardApplyDto.setAccessCardApplyInfoList(
                        accessCardApplyInfoService.queryDetailList(PpmAccessCardApplyInfoDto.builder().instanceId(
                                approveParamsDto.getProcessInstId()).build(), "getListForInstanceId"));
                accessCardApplyDto.setFileNo(accessCardApplyService.saveFlowCaseDocx(accessCardApplyDto)
                        + BusinessUtil.REGEX_COMMA
                        + accessCardApplyService.saveFlowCaseXlsx(accessCardApplyDto));
            }
            param.setmPara(params);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setTaskId(param.getTaskId());
            bps.setUserId(approveParamsDto.getAssignee());
            bps.setSuggest(APP_SUGGEST_PREFIX + approveParamsDto.getSuggest());
            bps.setConclusion(approveParamsDto.getConclusion());
            bps.setProcessInstId(approveParamsDto.getProcessInstId());
            bps.setApplydesc(task.get("taskName").toString());
            bps.setProcessDefName(approveParamsDto.getProcessDefName());
            bps.setRelaData(params);
            // 获取流程信息
            TaskBean taskQuery = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskQuery);
            CamundaExtUtils.saveNextTaskInfo(taskQuery,userIds,bps);
            CamundaUtils.findNextTaskInfo(taskQuery, userIds);

            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(param.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                deleteTaskXy(ppmAppWorkflowInfoDto);
            }else {
                ppmAppWorkflowInfoDto =
                        ppmAppWorkflowInfoService.queryDetailForPushState("pushError",bps.getProcessInstId());
            }

            if (flag && ppmAppWorkflowInfoDto != null){
                //推送待办
                pushTaskCommon(accessCardApplyDto.getCurrentId(),"门禁卡领用申请监保部确认",
                        ppmAppWorkflowInfoDto.getPushContent(),bps.getProcessInstId(),
                        taskQuery.getTaskList().get(0).get("taskId"),"1");
            }
        } catch (Exception e) {
            log.error("监保部审批失败：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 监保部确认节点 审批
     * @param task 任务对象
     * @param commonApproveParamsDto 对象
     * @param accessCardApplyDto 对象
     * @return boolean T/F
     */
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    private boolean accessCardAffirmApproval(Map task,CommonApproveParamsDto commonApproveParamsDto,
                                             PpmAccessCardApplyDto accessCardApplyDto,String type){
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(task.get("taskId")+"");// 任务id
            param.setUserId(commonApproveParamsDto.getAssignee());// 处理人
            param.setComment(APP_SUGGEST_PREFIX + commonApproveParamsDto.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            accessCardApplyDto.setModifiedBy(param.getUserId());
            accessCardApplyDto.setModifiedTime(new Date());
            accessCardApplyDto.setState("0");
            accessCardApplyDto.setStateName("重新提交");
            accessCardApplyDto.setCurrentId(accessCardApplyDto.getApplyerId());
            accessCardApplyDto.setCurrentName(accessCardApplyDto.getApplyerName());
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(commonApproveParamsDto.getConclusion())) {
                mPara.put("status", "pass");
                accessCardApplyDto.setState("-1");
                accessCardApplyDto.setStateName("已结束");
                accessCardApplyDto.setCurrentId("");
                accessCardApplyDto.setCurrentName("");
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setTaskId(param.getTaskId());
            bps.setUserId(commonApproveParamsDto.getAssignee());
            bps.setSuggest(APP_SUGGEST_PREFIX + commonApproveParamsDto.getSuggest());
            bps.setConclusion(commonApproveParamsDto.getConclusion());
            bps.setProcessInstId(commonApproveParamsDto.getProcessInstId());
            bps.setApplydesc(task.get("taskName").toString());
            bps.setProcessDefName(commonApproveParamsDto.getProcessDefName());
            bps.setRelaData(mPara);

            accessCardApplyService.updatePpmAccessCardApply(accessCardApplyDto);
            // 获取流程信息
            TaskBean taskQuery = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            // 通过或退回都需要 删除手机端待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(param.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                deleteTaskXy(ppmAppWorkflowInfoDto);
            }

            CamundaUtils.addFlowLog(bps, taskQuery);
            CamundaExtUtils.saveNextTaskInfo(taskQuery,null,bps);

            if (ObjectUtils.isEmpty(taskQuery) || "Y".equals(taskQuery.getIsFinished())) {
                List<PpmAccessCardApplyInfoDto> list = JsonUtil.jsonToList(
                        commonApproveParamsDto.getParams().get("returnList"),PpmAccessCardApplyInfoDto.class);
                saveAccessCardInfo(list,bps,type);
            }

            // 返回下一节点信息
            CamundaUtils.findNextTaskInfo(taskQuery, null);
        } catch (Exception e) {
            log.error("监保部确认审批失败：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 校验
     * @param task 任务
     * @param approveParamsDto 对象
     * @param roleId 角色id
     * @param dictId 字典编号
     * @return
     */
    private boolean verifyDate(Map task, CommonApproveParamsDto approveParamsDto,String roleId, String dictId){

        //当前处理人是否为空
        if(task.get("assignee") != null){
            //如果task.get("assignee")有值，表示已授权到一个人
            if (!Objects.equals(approveParamsDto.getAssignee(),task.get("assignee"))){
                return false;
            }
        }else {
            String assignees = "";
            if (StringUtils.isNotEmpty(roleId)){
                assignees = getMsgIdToString(roleId);
            }else if (StringUtils.isNotEmpty(dictId)){
                assignees = eosDictEntryService.getTypeidForDetail("ACCESS_CARD_FLOW_CURRENT",dictId)
                        .getDictname();
            }
          
            if (!assignees.contains(approveParamsDto.getAssignee())){
                return false;
            }
        }
        return true;
    }

    /**
     * 获取下一步处理人数组
     * @return 字符串
     */
    private String getMsgIdToString(String roleId){
        StringJoiner stringJoiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
        Arrays.stream(commEmployeeService.queryUserIdByRole(roleId)
                .split(BusinessUtil.REGEX_COMMA)).collect(Collectors.toList()).forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    /**
     * 修改门禁卡信息
     * @param accessCardApplyInfoDtoList 对象集合
     * @param bps 流程对象
     */
    private void saveAccessCardInfo(List<PpmAccessCardApplyInfoDto> accessCardApplyInfoDtoList,
                                    BpsWorkFlowDto bps,String type ){
        accessCardApplyInfoDtoList.forEach(dto ->{
            accessCardApplyInfoService.updatePpmAccessCardApplyInfo(dto);

            PpmAccessCardLedgerDto ppmAccessCardLedgerDto = accessCardLedgerService.queryDetail(
                    PpmAccessCardLedgerDto.builder().id(dto.getStandbyTwo()).build());
            if(ppmAccessCardLedgerDto != null){
                if ("Receive".equals(type)){
                    ppmAccessCardLedgerDto.setAccessCardNumber(dto.getAccessCardNumber());
                    if ("Y".equals(dto.getIfReceiveCard())){
                        ppmAccessCardLedgerDto.setIfCreateReturn("N");
                        ppmAccessCardLedgerDto.setIfReturnCard("N");
                    }
                    ppmAccessCardLedgerDto.setIfMakeCard("Y");
                    ppmAccessCardLedgerDto.setIfReceiveCard(dto.getIfReceiveCard());
                    ppmAccessCardLedgerDto.setReceiveTime(dto.getReceiveTime());
                    ppmAccessCardLedgerDto.setRemarkJbb(dto.getRemark());
                    ppmAccessCardLedgerDto.setModifiedBy(bps.getUserId());
                    ppmAccessCardLedgerDto.setModifiedTime(new Date());
                    accessCardLedgerService.updatePpmAccessCardLedger(ppmAccessCardLedgerDto);
                    return;
                }

                if ("Return".equals(type)){
                    ppmAccessCardLedgerDto.setIfReturnCard(dto.getIfReturnCard());
                    ppmAccessCardLedgerDto.setReturnTime(dto.getReturnTime());
                    ppmAccessCardLedgerDto.setRemarkJbb(dto.getRemark());
                    ppmAccessCardLedgerDto.setModifiedBy(bps.getUserId());
                    ppmAccessCardLedgerDto.setModifiedTime(new Date());
                    accessCardLedgerService.updatePpmAccessCardLedger(ppmAccessCardLedgerDto);
                }
            }
        });
        log.info("手机端修改门禁卡信息完成。");
    }

    @Override
    public void pushTaskCommon(String userId,String imgTxTtl,String imgTxDsc,
                           String instId,Object taskId,String type){
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(accessCardUrlValue).append("?instanceId=").append(instId);
        if (StringUtils.isNotEmpty(type)){
            stringBuffer.append("&type=").append(type);
        }

        pushTaskXy(userId,imgTxTtl,imgTxDsc,instId,taskId,stringBuffer.toString());
    }

}
