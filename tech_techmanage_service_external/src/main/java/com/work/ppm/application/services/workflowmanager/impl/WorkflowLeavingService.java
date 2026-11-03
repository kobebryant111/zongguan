package com.work.ppm.application.services.workflowmanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.dto.workflowmanager.CommonApproveProcessDto;
import com.work.ppm.application.dto.workflowmanager.DetailFlowLogDto;
import com.work.ppm.application.dto.workflowmanager.leave.LeavingDetailDto;
import com.work.ppm.application.enums.ApprovalRetStatusEnums;
import com.work.ppm.application.enums.FlowCodeEnums;
import com.work.ppm.application.enums.FlowMessageEnums;
import com.work.ppm.application.enums.FlowRuleEnums;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingExt;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingLzsq;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.flowmanager.IPpmAppWorkflowInfoService;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingExtService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingLzsqService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.application.services.workflowmanager.IWorkflowLeavingService;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.utils.CamundaExtUtils;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 科管->开放银行->星云->手机工作平台
 * 门禁卡申请/归还流程
 */
@Slf4j
@RequiredArgsConstructor
@Service("workflowLeavingService")
public class WorkflowLeavingService extends WorkflowOutputService implements IWorkflowLeavingService {

    //工作流服务
    private final WorkFlowHttpClientService workFlowHttpClientService;

    private final ICommEmployeeService commEmployeeService;

    private final ICommDepartmentService commDepartmentService;

    private final IEosDictEntryService eosDictEntryService;

    private final IPpmFlowLogService ppmFlowLogService;

    private final IPpmAppWorkflowInfoService ppmAppWorkflowInfoService;

    private final IPpmOutsourcingLzsqService ppmOutsourcingLzsqService;

    private final IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    private final IPpmOutsourcingExtService ppmOutsourcingExtService;

    private final IAttatchManagementService attatchManagementService;

    @Override
    public Map<String, Object> commonApproveProcess(CommonApproveProcessDto approveProcessDto) {

        //识别业务编码
        String bizTypeCode = approveProcessDto.getBizTypeCode();
        if("0029".equals(bizTypeCode)){
            return approveProcess(approveProcessDto);
        }

        return returnMap(FlowMessageEnums.FAIL.getCode(), FlowMessageEnums.FAIL_ERROR.getMessage(),null);
    }

    /**
     * 离场审批
     * @param approveProcessDto 对象
     * @return 返回
     */
    private Map<String, Object> approveProcess(CommonApproveProcessDto approveProcessDto) {
        //1、获取流程编号
        String processInstId = approveProcessDto.getProcessInstId();
        //2.定义任务对象
        TaskBean taskBean = null;
        try {
            //通过流程编号查询任务对象
            taskBean = workFlowHttpClientService.getTasksByProcessInstanceId(processInstId);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.toString());
        }
        //若taskBean为空，返回异常：instanceId未查询到有效任务
        if(taskBean == null){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_ERROR_INST.getMessage(),null);
        }
        //从任务对象中 获取任务人列表
        List<Map> taskList = taskBean.getTaskList();
        //判断任务人列表是否为空
        if(CollectionUtils.isEmpty(taskList)){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_NOT_TASK.getMessage(),null);
        }
        //获取任务
        Map task = taskList.get(0);
        //查询可推送到手机工作平台流程的字典
        EosDictEntry eosDictEntry =
                eosDictEntryService.getTypenameForDetail(task.get("taskKey").toString(),WORKFLOW_KEY_DICT);
        //字典为空则返回异常：业务状态编码不符合条件
        if (eosDictEntry == null){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_ERROR.getMessage(),null);
        }
        //获取字典key 0029_DH
        String flowCase = eosDictEntry.getDictid();
        approveProcessDto.setFlowCase(flowCase);
        //存储流程信息 以备后用
        approveProcessDto.setProcessDefName(FlowCodeEnums.LEAVE.getMessage());
        //判断flowCase是否包含特定状态码：二级部经理处理
        if (flowCase.contains(FlowRuleEnums.DH.getCode())) {
            //二级部经理处理 替换掉状态码 得到 业务类型编码
            String flowCode = flowCase.replace(FlowRuleEnums.DH.getCode(), "");
            approveProcessDto.setFlowCode(flowCode);
            //校验处理人是否正确
            if(!verifyDate(task,approveProcessDto,null,flowCase)){
                return returnMap(FlowMessageEnums.FAIL.getCode(),
                        FlowMessageEnums.FAIL_ASSIGNEE_TASK.getMessage(),null);
            }
            //二级部经理审批
            if (!leavingDhApproval(task,approveProcessDto)){
                return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL.getMessage(), null);
            }
        }
        return returnMap(FlowMessageEnums.PASS.getCode(),FlowMessageEnums.PASS.getMessage(), null);
    }

    /**
     * 离场二级部经理处理
     * @param task 任务对象
     * @param approveProcessDto 对象
     * @return boolean T/F
     */
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    private boolean leavingDhApproval(Map task,CommonApproveProcessDto approveProcessDto){
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(task.get("taskId")+"");// 任务id
            param.setUserId(approveProcessDto.getAssignee());// 处理人
            param.setComment(APP_SUGGEST_PREFIX + approveProcessDto.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(approveProcessDto.getConclusion())) {
                mPara.put("status", "pass");
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setTaskId(param.getTaskId());
            bps.setUserId(approveProcessDto.getAssignee());
            bps.setSuggest(APP_SUGGEST_PREFIX + approveProcessDto.getSuggest());
            bps.setConclusion(approveProcessDto.getConclusion());
            bps.setProcessInstId(approveProcessDto.getProcessInstId());
            bps.setApplydesc(task.get("taskName").toString());
            bps.setProcessDefName(approveProcessDto.getProcessDefName());
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
            //删除手机端待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(param.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                deleteTaskXy(ppmAppWorkflowInfoDto);
            }
            //处理信息
            if (ObjectUtils.isEmpty(taskBean) || "Y".equals(taskBean.getIsFinished())) {
                saveLeavingInfo(approveProcessDto);
            }
            // 返回下一节点信息
            CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("二级部经理审批失败！");
            return false;
        }
        return true;
    }

    /**
     * 校验
     * @param task 任务
     * @param approveProcessDto 对象
     * @param roleId 角色id
     * @param dictId 字典编号
     * @return boolean
     */
    private boolean verifyDate(Map task, CommonApproveProcessDto approveProcessDto,String roleId, String dictId){
        log.debug("角色id:{},flowCode:{}",roleId,dictId);
        //当前处理人是否为空
        if(task.get("assignee") != null){
            //如果task.get("assignee")有值，表示已授权到一个人
            if (!Objects.equals(approveProcessDto.getAssignee(),task.get("assignee"))){
                return false;
            }
        }
        return true;
    }

    /**
     * 修改花名册离场信息
     * @param commonApproveProcessDto 对象
     */
    private void saveLeavingInfo(CommonApproveProcessDto commonApproveProcessDto){
        PpmOutsourcingLzsq outsourcingLzsq =
                ppmOutsourcingLzsqService.getInfoByInstanceId(commonApproveProcessDto.getProcessInstId());

        PpmOutsourcingBasic ppmOutsourcingBasic = ppmOutsourcingBasicService.getBaseInfo(outsourcingLzsq.getJobid());
        ppmOutsourcingBasic.setState("1");
        ppmOutsourcingBasic.setPresenceState("4");
        //离场时间转为花名册离场时间
        Date leavingTime = DateUtil.dataFormat(outsourcingLzsq.getLgsj(), "yyyy-MM-dd");
        Date leavingFlowTime = new Date();
        ppmOutsourcingBasic.setLeavingTime(leavingTime);
        ppmOutsourcingBasicService.updatePpmOutsourcingBasic(ppmOutsourcingBasic);
        //查询扩展信息
        PpmOutsourcingExt ext = new PpmOutsourcingExt();
        ext.setUserId(outsourcingLzsq.getJobid());
        ext = ppmOutsourcingExtService.queryDetail(ext);
        if (ext != null) {
            ext.setLeavFlowTime(leavingFlowTime);
            ppmOutsourcingExtService.updatePpmOutsourcingExt(ext);
        }
        Date leavingApproveTime;
        // 如果审批结束时间早于离职时间，取离职时间，反之取审批结束时间
        if (leavingFlowTime.before(leavingTime)){
            leavingApproveTime = leavingTime;
        }else {
            leavingApproveTime = leavingFlowTime;
        }

        //修改跑批时间
        outsourcingLzsq.setLeavingApproveTime(DateUtil.dateToStr(leavingApproveTime,"yyyy-MM-dd"));
        ppmOutsourcingLzsqService.updatePpmOutsourcingLzsq(outsourcingLzsq);

        //离岗时需要对总办议案做修改
        //总办议案 可用人数 +1
        attatchManagementService.calMotionNumberMinus(ppmOutsourcingBasic.getCeneralOfficeMotion());

        log.info("离场信息完成。");
    }

    @Override
    public Map<String, Object> queryLeavingDetail(String instanceId) {
        PpmOutsourcingLzsq outsourcingLzsq = new PpmOutsourcingLzsq();
        outsourcingLzsq.setInstanceid(instanceId);
        PpmOutsourcingLzsq item = ppmOutsourcingLzsqService.queryDetail(outsourcingLzsq);
        if (item == null){
            return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL_NOT_TASK.getMessage(),null);
        }

        PpmOutsourcingBasicInfo basicInfo = ppmOutsourcingBasicService.getBaseInfo(item.getJobid());

        LeavingDetailDto leavingDetailDto = JsonUtil.jsonToClass(item,LeavingDetailDto.class);
        String applyUserId = item.getCreateid();
        leavingDetailDto.setApplyUserId(applyUserId);
        leavingDetailDto.setApplyUserName(commEmployeeService.getCommEmployeeInfo(applyUserId).getEmpname());
        leavingDetailDto.setApplyDeptId(commEmployeeService.getDeptNo(applyUserId));
        leavingDetailDto.setApplyDeptName(
                commDepartmentService.getDetpIdForDeptName(commEmployeeService.getDeptNo(applyUserId)));
        leavingDetailDto.setCertificateNumber(basicInfo.getCertificateNumber());
        leavingDetailDto.setOutsourcNo(basicInfo.getOutsourceNo());
        leavingDetailDto.setOutsourcName(basicInfo.getOutsourcname());
        leavingDetailDto.setDeptId(basicInfo.getDeptId());
        leavingDetailDto.setDeptName(basicInfo.getDepname());

        PpmFlowLog ppmFlowLog = new PpmFlowLog();
        ppmFlowLog.setFlowcaseno(instanceId);
        List<PpmFlowLogDto> flowLogDtos = ppmFlowLogService.queryList(ppmFlowLog);
        List<DetailFlowLogDto> flowLogDtoList = JsonUtil.jsonToList(flowLogDtos,DetailFlowLogDto.class);
        flowLogDtoList.forEach(flowLogDto ->{
            if(StringUtils.isEmpty(flowLogDto.getConclusion())){
                leavingDetailDto.setCurrentUserId(flowLogDto.getUserid());
                leavingDetailDto.setCurrentUserName(flowLogDto.getUsername());
                return;
            }

            for (ApprovalRetStatusEnums approvalStatus : ApprovalRetStatusEnums.values()){
                if (Objects.equals(approvalStatus.getCode(),flowLogDto.getConclusion())){
                    flowLogDto.setConclusion(approvalStatus.getMessage());
                    break;
                }
            }
        });
        leavingDetailDto.setFlowLog(flowLogDtoList);

        return returnMap(FlowMessageEnums.PASS.getCode(), FlowMessageEnums.PASS.getMessage(),leavingDetailDto);
    }

    @Override
    public void pushTaskCommon(String userId,String imgTxTtl,String imgTxDsc,
                               String instId,Object taskId){
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(leavingUrlValue).append("?id=").append(instId);

        pushTaskXy(userId,imgTxTtl,imgTxDsc,instId,taskId,stringBuffer.toString());
    }
}
