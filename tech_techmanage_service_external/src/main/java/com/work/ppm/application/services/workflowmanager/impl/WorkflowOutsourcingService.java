package com.work.ppm.application.services.workflowmanager.impl;

import com.google.common.collect.Lists;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.dto.integratedmanager.ManpowerdemandsInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmInterviewApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmInterviewerInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmManpowerdemandApplyOrgDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAccessapplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingInfoPriceDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.PpmProjectInfoDto;
import com.work.ppm.application.dto.systemmanager.PpmSystemInfoDto;
import com.work.ppm.application.dto.workflowmanager.AccessDetailDto;
import com.work.ppm.application.dto.workflowmanager.CommonApproveProcessDto;
import com.work.ppm.application.dto.workflowmanager.DemandDetailDto;
import com.work.ppm.application.dto.workflowmanager.DemandDetailInfoDto;
import com.work.ppm.application.dto.workflowmanager.DetailFlowLogDto;
import com.work.ppm.application.dto.workflowmanager.InterviewApproveProcessDto;
import com.work.ppm.application.dto.workflowmanager.InterviewDetailDto;
import com.work.ppm.application.dto.workflowmanager.IntervieweeInfoDto;
import com.work.ppm.application.dto.workflowmanager.IntervieweePriceInfoDto;
import com.work.ppm.application.enums.ApprovalRetStatusEnums;
import com.work.ppm.application.enums.FlowCodeEnums;
import com.work.ppm.application.enums.FlowMessageEnums;
import com.work.ppm.application.enums.FlowRuleEnums;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeInfo;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApply;
import com.work.ppm.application.model.integratedmanager.ext.PpmManpowerdemandApplyInfoOrgInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.flowmanager.IPpmAppWorkflowInfoService;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.integratedmanager.IPpmInterviewApplyService;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmManpowerdemandApplyInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmManpowerdemandApplyService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingAccessapplyService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingInfoPriceService;
import com.work.ppm.application.services.othermanager.IPpmOutsourcingInfoService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IPpmProjectInfoService;
import com.work.ppm.application.services.systemmanager.IPpmSystemInfoService;
import com.work.ppm.application.services.workflowmanager.IWorkflowOutsourcingService;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.CamundaExtUtils;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Slf4j
@Service("workflowOutsourcingService")
public class WorkflowOutsourcingService extends WorkflowOutputService implements IWorkflowOutsourcingService {

    @Autowired
    private WorkFlowHttpClientService workFlowHttpClientService;

    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Autowired
    private ICommEmployeeService commEmployeeService;

    @Autowired
    private IPpmOutsourcingAccessapplyService accessapplyService;

    @Autowired
    private IPpmManpowerdemandApplyService demandApplyService;

    @Autowired
    private IPpmIntervieweeInfoService ppmIntervieweeInfoService;

    @Autowired
    private IPpmInterviewApplyService ppmInterviewApplyService;

    @Autowired
    private IPpmOutsourcingInfoPriceService ppmOutsourcingInfoPriceService;

    @Autowired
    private IPpmManpowerdemandApplyInfoService demandApplyInfoService;

    @Autowired
    private IPpmSystemInfoService ppmSystemInfoService;

    @Autowired
    private IPpmProjectInfoService ppmProjectInfoService;

    @Autowired
    private IPpmOutsourcingInfoService ppmOutsourcingInfoService;

    @Autowired
    private IPpmFlowLogService ppmFlowLogService;

    @Autowired
    private ICommDepartmentService commDepartmentService;

    @Autowired
    private IPpmAppWorkflowInfoService ppmAppWorkflowInfoService;

    @Value("${kfBank.productId}")
    private String productId;

    @Value("${kfBank.appPush-serverId}")
    private String serverId;

    @Value("${appUrl.interview}")
    private String interviewUrlValue;

    @Value("${appUrl.access}")
    private String accessUrlValue;

    @Value("${appUrl.demand}")
    private String demandUrlValue;

    @Override
    public Map<String, Object> commonApproveProcess(CommonApproveProcessDto approveProcessDto) {

        //识别业务编码
        String bizTypeCode = approveProcessDto.getBizTypeCode();
        if("0001".equals(bizTypeCode)){
            return demandCommonApproveProcess(approveProcessDto);
        }

        if("0020".equals(bizTypeCode)){
            return accessCommonApproveProcess(approveProcessDto);
        }

        return returnMap(FlowMessageEnums.FAIL.getCode(), FlowMessageEnums.FAIL_ERROR.getMessage(),null);
    }

    /**
     * 人力需求审批
     * @param approveProcessDto 对象
     * @return 返回json
     */
    private Map<String, Object> demandCommonApproveProcess(CommonApproveProcessDto approveProcessDto) {
        String processInstId = approveProcessDto.getProcessInstId();
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
        approveProcessDto.setFlowCase(flowCase);
        approveProcessDto.setProcessDefName(FlowCodeEnums.DEMAND.getMessage());

        if (flowCase.contains(FlowRuleEnums.DH.getCode())) {
            //二级部经理处理 0001/0010/0020
            String flowCode = flowCase.replace(FlowRuleEnums.DH.getCode(), "");
            approveProcessDto.setFlowCode(flowCode);
            if(!verifyDate(task,approveProcessDto,null,flowCase)){
                return returnMap(FlowMessageEnums.FAIL.getCode(),
                        FlowMessageEnums.FAIL_ASSIGNEE_TASK.getMessage(),null);
            }

            //审批
            if(demandDhApproval(task,approveProcessDto)){
                if ("01".equals(approveProcessDto.getConclusion())) {
                    //参数存储处理
                    PpmManpowerdemandApply ppmManpowerdemandApply = new PpmManpowerdemandApply();
                    ppmManpowerdemandApply.setInstanceid(approveProcessDto.getProcessInstId());
                    PpmManpowerdemandApplyOrgDto applyOrgDto = demandApplyService.queryDetails(ppmManpowerdemandApply);

                    PpmManpowerdemandApply apply = new PpmManpowerdemandApply();
                    apply.setId(applyOrgDto.getId().longValue());
                    apply.setManageruserid(approveProcessDto.getAssignee());
                    demandApplyService.updatePpmManpowerdemandApply(apply);
                }
            }
        }

        if (flowCase.contains(FlowRuleEnums.GM.getCode())) {
            //一级部经理处理 0001/0010/0020
            String flowCode = flowCase.replace(FlowRuleEnums.GM.getCode(), "");
            approveProcessDto.setFlowCode(flowCode);
            //
            if(!verifyDate(task,approveProcessDto,"GENERALMANAGER",flowCase)){
                return returnMap(FlowMessageEnums.FAIL.getCode(),
                        FlowMessageEnums.FAIL_ASSIGNEE_TASK.getMessage(),null);
            }

            //审批
            if(demandGmApproval(task,approveProcessDto)){
                //参数存储处理
                if ("01".equals(approveProcessDto.getConclusion())){
                    PpmManpowerdemandApply ppmManpowerdemandApply = new PpmManpowerdemandApply();
                    ppmManpowerdemandApply.setInstanceid(approveProcessDto.getProcessInstId());
                    PpmManpowerdemandApplyOrgDto applyOrgDto = demandApplyService.queryDetails(ppmManpowerdemandApply);

                    PpmManpowerdemandApply apply = new PpmManpowerdemandApply();
                    apply.setId(Long.valueOf(applyOrgDto.getId()));
                    apply.setStatus("1");
                    apply.setCreateuserid(applyOrgDto.getCreateuserid());
                    demandApplyService.updatePpmManpowerdemandApply(apply);
                }
            }
        }

        return returnMap(FlowMessageEnums.PASS.getCode(),FlowMessageEnums.PASS.getMessage(), null);
    }

    /**
     * 准入审批
     * @param approveProcessDto 对象
     * @return 返回
     */
    private Map<String, Object> accessCommonApproveProcess(CommonApproveProcessDto approveProcessDto) {
        String processInstId = approveProcessDto.getProcessInstId();
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
        approveProcessDto.setFlowCase(flowCase);
        approveProcessDto.setProcessDefName(FlowCodeEnums.ACCESS.getMessage());

        if (flowCase.contains(FlowRuleEnums.DH.getCode())) {
            //二级部经理处理 0001/0010/0020
            String flowCode = flowCase.replace(FlowRuleEnums.DH.getCode(), "");
            approveProcessDto.setFlowCode(flowCode);

            if(!verifyDate(task,approveProcessDto,null,flowCase)){
                return returnMap(FlowMessageEnums.FAIL.getCode(),
                        FlowMessageEnums.FAIL_ASSIGNEE_TASK.getMessage(),null);
            }

            //审批
            if (!accessDhApproval(task,approveProcessDto)){
                return returnMap(FlowMessageEnums.FAIL.getCode(),FlowMessageEnums.FAIL.getMessage(), null);
            }
        }
        return returnMap(FlowMessageEnums.PASS.getCode(),FlowMessageEnums.PASS.getMessage(), null);
    }

    @Override
    public Map<String, Object> interviewApproveProcess(InterviewApproveProcessDto approveProcessDto) {
        String processInstId = approveProcessDto.getProcessInstId();
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
        approveProcessDto.setFlowCase(flowCase);
        approveProcessDto.setProcessDefName(FlowCodeEnums.INTERVIEW.getMessage());

        if (flowCase.contains(FlowRuleEnums.DH.getCode())) {
            //二级部经理处理 0001/0010/0020
            String flowCode = flowCase.replace(FlowRuleEnums.DH.getCode(), "");
            approveProcessDto.setFlowCode(flowCode);

            if(!verifyDate(task,approveProcessDto,null,flowCase)){
                return returnMap(FlowMessageEnums.FAIL.getCode(),
                        FlowMessageEnums.FAIL_ASSIGNEE_TASK.getMessage(),null);
            }
            //审批
            if(interviewDhApproval(task,approveProcessDto)){

                Map<String,Object> params = approveProcessDto.getParams();
                //参数存储处理
                List<Map> list = (List<Map>) params.get("eelist");

                list.forEach(obj ->{
                    PpmIntervieweeInfo intervieweeInfo = new PpmIntervieweeInfo();
                    intervieweeInfo.setId(Long.valueOf((String) obj.get("id")));
                    intervieweeInfo.setInstanceid(approveProcessDto.getProcessInstId());
                    intervieweeInfo.setSuggestprice(obj.get("suggestprice")+"");
                    intervieweeInfo.setReason(obj.get("reason")+"");
                    ppmIntervieweeInfoService.updatePpmIntervieweeInfo(intervieweeInfo);
                });
            }
        }

        if (flowCase.contains(FlowRuleEnums.GM.getCode())) {
            //一级部经理处理 0001/0010/0020
            String flowCode = flowCase.replace(FlowRuleEnums.GM.getCode(), "");
            approveProcessDto.setFlowCode(flowCode);

            if(!verifyDate(task,approveProcessDto,null,flowCase)){
                return returnMap(FlowMessageEnums.FAIL.getCode(),
                        FlowMessageEnums.FAIL_ASSIGNEE_TASK.getMessage(),null);
            }
            //审批
            if(interviewGmApproval(task,approveProcessDto)){
                //参数存储处理
                Map<String,Object> params = approveProcessDto.getParams();
                //参数存储处理
                List<PpmIntervieweeInfo> eeList = JsonUtil.jsonToList(params.get("eelist"),PpmIntervieweeInfo.class);
//                ppmIntervieweeInfoService.addPpmIntervieweeInfo(eeList);
                eeList.forEach(obj ->{
                    obj.setInstanceid(approveProcessDto.getProcessInstId());

                    PpmIntervieweeInfo intervieweeInfo = new PpmIntervieweeInfo();
                    intervieweeInfo.setId(obj.getId());
                    intervieweeInfo.setInstanceid(approveProcessDto.getProcessInstId());
                    intervieweeInfo.setSuggestprice(obj.getSuggestprice());
                    intervieweeInfo.setReason(obj.getReason());
                    ppmIntervieweeInfoService.updatePpmIntervieweeInfo(intervieweeInfo);
                });

                if ("01".equals(approveProcessDto.getConclusion())){
                    ppmInterviewApplyService.archiveApproval(eeList);
                }
            }
        }
        return returnMap(FlowMessageEnums.PASS.getCode(),FlowMessageEnums.PASS.getMessage(), null);
    }

    @Override
    public Map<String, Object> queryDemandDetail(String instanceId) {
        PpmManpowerdemandApply ppmManpowerdemandApply = new PpmManpowerdemandApply();
        ppmManpowerdemandApply.setInstanceid(instanceId);
        PpmManpowerdemandApplyOrgDto applyOrgDto = demandApplyService.queryDetails(ppmManpowerdemandApply);

        DemandDetailDto demandDetailDto = JsonUtil.jsonToClass(applyOrgDto,DemandDetailDto.class);
        demandDetailDto.setId(applyOrgDto.getId().toString());
        demandDetailDto.setApplyDeptId(applyOrgDto.getDeptno());
        demandDetailDto.setApplyDeptName(applyOrgDto.getDept());
        demandDetailDto.setApplyUserId(applyOrgDto.getUserid());
        demandDetailDto.setApplyUserName(applyOrgDto.getUsername());
        demandDetailDto.setSupplytime(applyOrgDto.getSupplytime());

        PpmFlowLog ppmFlowLog = new PpmFlowLog();
        ppmFlowLog.setFlowcaseno(applyOrgDto.getInstanceid());
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
        demandDetailDto.setFlowLog(flowLogDtoList);

        List<PpmManpowerdemandApplyInfoOrgInfo> infoDtos = applyOrgDto.getInfos();
        List<DemandDetailInfoDto> detailInfoDtos = demandDetailDto.getInfos();
        detailInfoDtos.forEach(detailInfoDto ->{
            for(PpmManpowerdemandApplyInfoOrgInfo infoDto : infoDtos){
                if(Objects.equals(detailInfoDto.getId(),infoDto.getId().toString())){
                    detailInfoDto.setStandbyOne(infoDto.getSupplynames());
                    break;
                }
            }

            EosDictEntry userType =
                    eosDictEntryService.getTypeidForDetail("REQUIRE_PEOPLE_TYPE",detailInfoDto.getUsertype());
            if (userType != null){
                detailInfoDto.setUsertype(userType.getDictname());
            }

            EosDictEntry isAdvertise =eosDictEntryService.getTypeidForDetail(
                    "WHETHER_CONTACT_RECRUITMENT",detailInfoDto.getIsadvertise());
            if (isAdvertise != null){
                detailInfoDto.setIsadvertise(isAdvertise.getDictname());
            }

        });
        demandDetailDto.setInfos(detailInfoDtos);

        return returnMap(FlowMessageEnums.PASS.getCode(), FlowMessageEnums.PASS.getMessage(),demandDetailDto);
    }

    @Override
    public Map<String, Object> queryInterviewDetail(String instanceId) {
        PpmInterviewApplyDto ppmInterviewApply = new PpmInterviewApplyDto();
        ppmInterviewApply.setInstanceid(instanceId);
        PpmInterviewApplyDto item = ppmInterviewApplyService.interviewScore(ppmInterviewApply);

        List<PpmInterviewerInfoDto> erList = item.getErList();
        StringJoiner joiner = new StringJoiner(",");
        erList.forEach(obj -> joiner.add(obj.getMsgid()));

        InterviewDetailDto applyDto = JsonUtil.jsonToClass(item,InterviewDetailDto.class);
        applyDto.setApplyUserId(item.getApplyer());
        applyDto.setApplyUserName(commEmployeeService.getBadgeAndName(item.getApplyer()));
        applyDto.setApplyDeptId(item.getDeptno());
        applyDto.setApplyDeptName(item.getDeptname());
        applyDto.setMsgName(commEmployeeService.getBadgeAndName(joiner.toString()));
        applyDto.setMeet(item.getMeet() == 1?"是":"否");

        //插入人力需求名称
        ManpowerdemandsInfoDto manpowerdemandsInfo = new ManpowerdemandsInfoDto();
        manpowerdemandsInfo.setId(item.getRlId());
        manpowerdemandsInfo = demandApplyInfoService.queryManpowerdemandsById(manpowerdemandsInfo);
        if(manpowerdemandsInfo != null){
            applyDto.setRlName(manpowerdemandsInfo.getUsername() +
                    BusinessUtil.getDeptNameValue(manpowerdemandsInfo.getDepname(),1) +
                    DateUtil.dateToStr(manpowerdemandsInfo.getSupplytime(),"yyyy-MM-dd") +
                    manpowerdemandsInfo.getUsertypename());
        }

        //插入面试人员信息
        List<PpmIntervieweeInfoDto> ppmIntervieweeInfos = item.getEeList();
        List<IntervieweeInfoDto> infoDtos = JsonUtil.jsonToList(ppmIntervieweeInfos,IntervieweeInfoDto.class);
        infoDtos.forEach(obj ->{
            //修改原因处理
            EosDictEntry eosDictEntry =
                    eosDictEntryService.getTypeidForDetail("NEW_BUSI_OUTSOURCE_INTERVRESULT",obj.getInterviewresult());
            if(eosDictEntry != null){
                obj.setInterviewresultName(eosDictEntry.getDictname());
            }

            //插入年限
            String suggestLevel = "";
            if(StringUtils.isNotEmpty(obj.getSuggestlevel())){
                switch (obj.getSuggestlevel()){
                    case "1":
                        suggestLevel = "1 (1-3年)";
                        break;
                    case "2":
                        suggestLevel = "2 (3-5年)";
                        break;
                    case "3":
                        suggestLevel = "3 (5-7年)";
                        break;
                    case "4":
                        suggestLevel = "4 (7年以上)";
                        break;
                    default:
                        break;
                }
                obj.setSuggestlevel(suggestLevel);
            }

            String researchersName = "";
            if(StringUtils.isNotEmpty(obj.getResearchers())){
                switch (obj.getResearchers()){
                    case "1":
                        researchersName = "人力外包";
                        break;
                    case "2":
                        researchersName = "项目人力";
                        break;
                    default:
                        break;
                }
                obj.setResearchersName(researchersName);
            }else {
                obj.setResearchersName("");
            }
        });

        applyDto.setEeList(infoDtos);

        //根据入场人员判断供应商及对应的单价信息
        List<IntervieweePriceInfoDto> priceInfoDtos = new ArrayList<>();
        infoDtos.forEach(info ->{
            PpmOutsourcingInfoPriceDto infoPriceDto = ppmOutsourcingInfoPriceService.getOutsourcPrices(
                    Long.parseLong(info.getWbsid()),info.getPosition());

            if(infoPriceDto == null){
                return;
            }

            //定义回参单价对象
            IntervieweePriceInfoDto priceInfoDto = new IntervieweePriceInfoDto();
            priceInfoDto.setOutsourcname(infoPriceDto.getOutsourcname());
            priceInfoDto.setPositionName(infoPriceDto.getPositionName());
            priceInfoDto.setRemarks(StringUtils.isNotEmpty(infoPriceDto.getRemark())?infoPriceDto.getRemark():"");

            String firstStartPrice =
                    infoPriceDto.getFirststartprie() != null ? infoPriceDto.getFirststartprie()+"":"";
            String firstEndPrice =
                    infoPriceDto.getFirstendprie() != null ? infoPriceDto.getFirstendprie()+"":"";
            String secondStartPrice =
                    infoPriceDto.getSecondstartprie() != null ? infoPriceDto.getSecondstartprie()+"":"";
            String secondEndPrice =
                    infoPriceDto.getSecondendprie() != null ? infoPriceDto.getSecondendprie()+"":"";
            String thirdStartPrice =
                    infoPriceDto.getThirdstartprie() != null ? infoPriceDto.getThirdstartprie()+"":"";
            String thirdEndPrice =
                    infoPriceDto.getThirdendprie() != null ? infoPriceDto.getThirdendprie()+"" : "";
            String fourthStartPrice =
                    infoPriceDto.getFourthstartprie() != null ? infoPriceDto.getFourthstartprie()+"":"";
            String fourthEndPrice =
                    infoPriceDto.getFourthendprie() != null ? infoPriceDto.getFourthendprie()+"":"";
            priceInfoDto.setFirstPrice(firstStartPrice + "-" + firstEndPrice);
            priceInfoDto.setSecondPrice(secondStartPrice + "-" + secondEndPrice);
            priceInfoDto.setThirdPrice(thirdStartPrice + "-" + thirdEndPrice);
            priceInfoDto.setFourthPrice(fourthStartPrice + "-" + fourthEndPrice);
            priceInfoDtos.add(priceInfoDto);
        });
        List<IntervieweePriceInfoDto> infoDtoList = priceInfoDtos.stream().distinct().collect(Collectors.toList());
        applyDto.setPrices(infoDtoList);

        PpmFlowLog ppmFlowLog = new PpmFlowLog();
        ppmFlowLog.setFlowcaseno(item.getInstanceid());
        List<PpmFlowLogDto> flowLogDtos = ppmFlowLogService.queryList(ppmFlowLog);
        List<DetailFlowLogDto> flowLogDtoList = JsonUtil.jsonToList(flowLogDtos,DetailFlowLogDto.class);
        flowLogDtoList.forEach(flowLogDto ->{
            if(StringUtils.isEmpty(flowLogDto.getConclusion())){
                applyDto.setCurrentUserId(flowLogDto.getUserid());
                applyDto.setCurrentUserName(flowLogDto.getUsername());
                return;
            }

            for (ApprovalRetStatusEnums approvalStatus : ApprovalRetStatusEnums.values()){
                if (Objects.equals(approvalStatus.getCode(),flowLogDto.getConclusion())){
                    flowLogDto.setConclusion(approvalStatus.getMessage());
                    break;
                }
            }
        });
        applyDto.setFlowLog(flowLogDtoList);
        return returnMap(FlowMessageEnums.PASS.getCode(), FlowMessageEnums.PASS.getMessage(),applyDto);
    }

    @Override
    public Map<String, Object> queryAccessDetail(String instanceId) {
        PpmOutsourcingAccessapplyDto ppmOutsourcingApply = new PpmOutsourcingAccessapplyDto();
        ppmOutsourcingApply.setInstanceId(instanceId);
        PpmOutsourcingAccessapplyDto item = accessapplyService.queryDetail(ppmOutsourcingApply);

        AccessDetailDto applyDto = JsonUtil.jsonToClass(item,AccessDetailDto.class);
        applyDto.setApplyUserId(item.getCreators());
        applyDto.setInstanceid(item.getInstanceId());
        applyDto.setFaceimage(null);
        CommEmployee commEmployeeDto = commEmployeeService.getCommEmployeeInfo(item.getCreators());
        if(commEmployeeDto != null){
            applyDto.setApplyUserName(commEmployeeDto.getEmpname());
        }

        applyDto.setApplyDeptId(commEmployeeService.getDeptNo(item.getCreators()));
        applyDto.setApplyDeptName(
                commDepartmentService.getDetpIdForDeptName(commEmployeeService.getDeptNo(item.getCreators())));


        //供应商名称
        applyDto.setOutsourceName(ppmOutsourcingInfoService.queryNameByOrgCode(item.getOutsourceNo()));
        applyDto.setPmId(item.getOrganizers());
        applyDto.setPmName(commEmployeeService.getCommEmployeeInfo(item.getOrganizers()).getEmpname());

        if("1".equals(item.getOutsourceType())){
            PpmSystemInfoDto ppmSystemInfoDto = new PpmSystemInfoDto();
            ppmSystemInfoDto.setId(Long.parseLong(item.getSystemnos()));
            PpmSystemInfoDto ppmSystemInfoDtoVal = ppmSystemInfoService.queryDetail(ppmSystemInfoDto);
            if(ppmSystemInfoDtoVal != null){
                applyDto.setSystemName(ppmSystemInfoDtoVal.getName());
                applyDto.setProjectName("");
            }

        }else if("0".equals(item.getOutsourceType())){
            PpmProjectInfoDto ppmProjectInfoDto = new PpmProjectInfoDto();
            ppmProjectInfoDto.setId(Long.parseLong(item.getProjectno()));
            PpmProjectInfoDto ppmProjectInfoDtoVal = ppmProjectInfoService.queryDetail(ppmProjectInfoDto);
            if(ppmProjectInfoDtoVal != null){
                applyDto.setProjectName(ppmProjectInfoDtoVal.getName());
                applyDto.setSystemName("");
            }
        }else {
            applyDto.setSystemName("");
            applyDto.setProjectName("");
        }

        //存储外包方式
        EosDictEntry eosDictEntry =
                eosDictEntryService.getTypeidForDetail("NEWKG_WBFS",applyDto.getOutsourceType());
        if(eosDictEntry != null){
            applyDto.setOutsourceTypeName(eosDictEntry.getDictname());
        }

        //岗位
        EosDictEntry positionObj =
                eosDictEntryService.getTypeidForDetail("BUSI_OUTSOURCE_POSITION",item.getPosition());
        if(eosDictEntry != null) {
            applyDto.setPositionName(positionObj.getDictname());
        }
        PpmFlowLog ppmFlowLog = new PpmFlowLog();
        ppmFlowLog.setFlowcaseno(item.getInstanceId());
        List<PpmFlowLogDto> flowLogDtos = ppmFlowLogService.queryList(ppmFlowLog);
        List<DetailFlowLogDto> flowLogDtoList = JsonUtil.jsonToList(flowLogDtos,DetailFlowLogDto.class);
        flowLogDtoList.forEach(flowLogDto ->{
            if(StringUtils.isEmpty(flowLogDto.getConclusion())){
                applyDto.setCurrentUserId(flowLogDto.getUserid());
                applyDto.setCurrentUserName(flowLogDto.getUsername());
                return;
            }

            for (ApprovalRetStatusEnums approvalStatus : ApprovalRetStatusEnums.values()){
                if (Objects.equals(approvalStatus.getCode(),flowLogDto.getConclusion())){
                    flowLogDto.setConclusion(approvalStatus.getMessage());
                    break;
                }
            }
        });
        applyDto.setFlowLog(flowLogDtoList);
        return returnMap(FlowMessageEnums.PASS.getCode(), FlowMessageEnums.PASS.getMessage(),applyDto);
    }

    /**
     * 面试二级部经理处理
     * @param task 任务对象
     * @param approveProcessDto 对象
     * @return boolean T/F
     */
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    private boolean interviewDhApproval(Map task,CommonApproveProcessDto approveProcessDto){
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(task.get("taskId")+"");// 任务id
            param.setUserId(approveProcessDto.getAssignee());// 处理人
            param.setComment(APP_SUGGEST_PREFIX + approveProcessDto.getSuggest());// 审批意见
            Map params = new HashMap();
            params.put("status", "return");
            boolean flag = false;
            List<String> msgIds = Lists.newArrayList();

            StringJoiner stringJoiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(approveProcessDto.getConclusion())) {
                //获取msgIds
                CommEmployeeDto commEmployeeDto = new CommEmployeeDto();
                //永赢租赁金融科技部-总经理室
                commEmployeeDto.setDepid(80000053);
                msgIds = commEmployeeService.getEmployeeLists(commEmployeeDto)
                        .stream().map(CommEmployeeDto::getBadge).collect(Collectors.toList());
                msgIds.forEach(stringJoiner::add);
                params.put("status", "pass");
                flag = true;
            }
            param.setmPara(params);
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
            bps.setRelaData(params);
            // 获取流程信息
            TaskBean taskQuery = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskQuery);
            CamundaExtUtils.saveNextTaskInfo(taskQuery,msgIds.toArray(new String[msgIds.size()]),bps);
            CamundaUtils.findNextTaskInfo(taskQuery, msgIds.toArray(new String[msgIds.size()]));
            assert taskQuery != null;
            //推送手机平台
            //删除待办
            String imgTxUrl = interviewUrlValue + "?flowId=";
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(param.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                deleteTask(ppmAppWorkflowInfoDto, imgTxUrl);
            }else {
                ppmAppWorkflowInfoDto =
                        ppmAppWorkflowInfoService.queryDetailForPushState("pushError",bps.getProcessInstId());
            }

            //推送待办
            if (flag && ppmAppWorkflowInfoDto != null){
                pushTask(stringJoiner.toString(),"外包人员面试一级部经理审批",
                        ppmAppWorkflowInfoDto.getPushContent(),imgTxUrl,
                        bps.getProcessInstId(),taskQuery.getTaskList().get(0).get("taskId"));
            }
        } catch (Exception e) {
            log.error("二级部经理审批失败：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 面试一级部经理处理
     * @param task 任务对象
     * @param approveProcessDto 对象
     * @return boolean T/F
     */
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    private boolean interviewGmApproval(Map task,InterviewApproveProcessDto approveProcessDto){
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
            TaskBean taskQuery = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskQuery);
            CamundaExtUtils.saveNextTaskInfo(taskQuery,null,bps);
            // 返回下一节点信息
            CamundaUtils.findNextTaskInfo(taskQuery, null);

            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(param.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                deleteTask(ppmAppWorkflowInfoDto, demandUrlValue + "?flowId=");
            }
            // 返回下一节点信息
        } catch (Exception e) {
            log.error("一级部总经理室审批失败：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 需求二级部经理处理
     * @param task 任务对象
     * @param approveProcessDto 对象
     * @return boolean T/F
     */
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    private boolean demandDhApproval(Map task,CommonApproveProcessDto approveProcessDto){
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(task.get("taskId")+"");// 任务id
            param.setUserId(approveProcessDto.getAssignee());// 处理人
            param.setComment(APP_SUGGEST_PREFIX + approveProcessDto.getSuggest());// 审批意见
            Map params = new HashMap();
            params.put("status", "return");
            boolean flag = false;
            String[] msgIds = null;

            StringJoiner stringJoiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(approveProcessDto.getConclusion())) {
                params.put("status", "pass");
                //获取msgIds
                msgIds = commEmployeeService.queryUserIdByRole("GENERALMANAGER").split(",");
                for (String msgId : msgIds){
                    stringJoiner.add(msgId);
                }

                flag = true;
            }
            param.setmPara(params);
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
            bps.setRelaData(params);
            // 获取流程信息
            TaskBean taskQuery = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskQuery);
            CamundaExtUtils.saveNextTaskInfo(taskQuery,msgIds,bps);
            CamundaUtils.findNextTaskInfo(taskQuery, msgIds);
            //推送手机平台
            //删除待办
            String imgTxUrl = demandUrlValue + "?flowId=";
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(param.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                deleteTask(ppmAppWorkflowInfoDto, imgTxUrl);
            }else {
                ppmAppWorkflowInfoDto =
                        ppmAppWorkflowInfoService.queryDetailForPushState("pushError",bps.getProcessInstId());
            }

            if (flag && ppmAppWorkflowInfoDto != null){
                //推送待办
                pushTask(stringJoiner.toString(),"人力外包需求一级部经理审批",
                        ppmAppWorkflowInfoDto.getPushContent(),imgTxUrl,bps.getProcessInstId(),
                        taskQuery.getTaskList().get(0).get("taskId"));
            }

        } catch (Exception e) {
            log.error("二级部经理审批失败：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 需求一级部经理处理
     * @param task 任务对象
     * @param approveProcessDto 对象
     * @return boolean T/F
     */
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    private boolean demandGmApproval(Map task,CommonApproveProcessDto approveProcessDto){
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
            TaskBean taskQuery = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskQuery);
            CamundaExtUtils.saveNextTaskInfo(taskQuery,null,bps);
            // 返回下一节点信息
            CamundaUtils.findNextTaskInfo(taskQuery, null);
            //删除手机端待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(param.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                deleteTask(ppmAppWorkflowInfoDto, demandUrlValue + "?flowId=");
            }


        } catch (Exception e) {
            log.error("一级部总经理室审批失败：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 准入二级部经理处理
     * @param task 任务对象
     * @param approveProcessDto 对象
     * @return boolean T/F
     */
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    private boolean accessDhApproval(Map task,CommonApproveProcessDto approveProcessDto){
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(task.get("taskId")+"");// 任务id
            param.setUserId(approveProcessDto.getAssignee());// 处理人
            param.setComment(APP_SUGGEST_PREFIX + approveProcessDto.getSuggest());// 审批意见
            Map params = new HashMap();
            params.put("status", "return");
            String[] msgIds = null;
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(approveProcessDto.getConclusion())) {
                params.put("status", "pass");
                //获取msgIds
                msgIds = commEmployeeService.queryUserIdByRole("INFORMATIONPERSON").split(",");
            }
            param.setmPara(params);
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
            bps.setRelaData(params);
            // 获取流程信息
            TaskBean taskQuery = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskQuery);
            CamundaExtUtils.saveNextTaskInfo(taskQuery,msgIds,bps);
            CamundaUtils.findNextTaskInfo(taskQuery, msgIds);

            //推送手机平台
            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(param.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                deleteTask(ppmAppWorkflowInfoDto,demandUrlValue + "?flowId=");
            }
        } catch (Exception e) {
            log.error("二级部经理审批失败：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 获取下一步处理人数组
     * @return 数组
     */
    private List<String> getMsgIds(String roleId){
        List<String> msgIds;
        if(StringUtils.isNotEmpty(roleId)){
            msgIds = Arrays.stream(commEmployeeService.queryUserIdByRole(roleId)
                    .split(BusinessUtil.REGEX_COMMA)).collect(Collectors.toList());
        }else {
            CommEmployeeDto commEmployeeDto = new CommEmployeeDto();
            //永赢租赁金融科技部-总经理室
            commEmployeeDto.setDepid(80000053);
            msgIds = commEmployeeService.getEmployeeLists(commEmployeeDto)
                    .stream().map(CommEmployeeDto::getBadge).collect(Collectors.toList());
        }
        return msgIds;
    }

    /**
     * 获取下一步处理人数组
     * @return 字符串
     */
    private String getMsgIdToString(String roleId){
        StringJoiner stringJoiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
        getMsgIds(roleId).forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    /**
     * 校验
     * @param task 任务
     * @param approveProcessDto 对象
     * @param roleId 角色id
     * @return
     */
    private boolean verifyDate(Map task,CommonApproveProcessDto approveProcessDto,String roleId,String flowCase){
        if (flowCase.contains(FlowRuleEnums.DH.getCode())){
            if(task.get("assignee") != null && !Objects.equals(approveProcessDto.getAssignee(),task.get("assignee"))){
                return false;
            }
        }

        if (flowCase.contains(FlowRuleEnums.GM.getCode())){
            //当前处理人是否为空
            if(task.get("assignee") != null){
                //如果task.get("assignee")有值，表示已授权到一个人
                if (!Objects.equals(approveProcessDto.getAssignee(),task.get("assignee"))){
                    return false;
                }
            }else {
                //如果task.get("assignee")没有值
                String assignees = getMsgIdToString(roleId);
                if (!assignees.contains(approveProcessDto.getAssignee())){
                    return false;
                }
            }
        }
        return true;
    }
}
