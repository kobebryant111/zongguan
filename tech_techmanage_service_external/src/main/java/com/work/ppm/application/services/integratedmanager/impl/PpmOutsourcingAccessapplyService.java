package com.work.ppm.application.services.integratedmanager.impl;

import com.google.common.collect.Maps;
import com.work.imex.util.StrUtils;
import com.work.ppm.application.dto.bankmamager.PpmOutSorceInterviewDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqAnnualVacationDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAccessapplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAccessapplyInfoDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardLedgerDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmDevelopGroupLedgerDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.bankmanager.PpmOutSourceInterview;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingAccessapply;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingExt;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingLzsq;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingAccessapplyInfo;
import com.work.ppm.application.model.interpoints.CommInterPointsDto;
import com.work.ppm.application.model.othermanager.PpmOutsourcingInfo;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.model.systemmanager.CommOperator;
import com.work.ppm.application.model.systemmanager.CommOperatorrole;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.model.usermanager.CommOutsourcingPersonSsht;
import com.work.ppm.application.model.usermanager.PpmDaTeamUser;
import com.work.ppm.application.model.usermanager.PpmOutsourcingRiskTime;
import com.work.ppm.application.model.usermanager.PpmRlwbsshtInfo;
import com.work.ppm.application.services.bankmanager.IPpmOutSourceInterviewService;
import com.work.ppm.application.services.external.CallEsbService;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;
import com.work.ppm.application.services.flowmanager.IPpmAppWorkflowInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqAnnualVacationService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingAccessapplyService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingExtService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingLzsqService;
import com.work.ppm.application.services.otherdeptmanager.IPpmAccessCardLedgerService;
import com.work.ppm.application.services.otherdeptmanager.IPpmDevelopGroupLedgerService;
import com.work.ppm.application.services.othermanager.IPpmOutsourcingInfoService;
import com.work.ppm.application.services.projectmanager.IPpmProjectBuildSoftService;
import com.work.ppm.application.services.systemmanager.*;
import com.work.ppm.application.services.usermanager.*;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.application.services.workflowmanager.IWorkflowOutsourcingService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.lang.esb.EsbAction;
import com.work.ppm.commons.lang.other.CodeGenerater;
import com.work.ppm.commons.utils.*;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.text.DecimalFormat;
import java.util.*;

/**
 * 新准入外包流程
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service("ppmOutsourcingAccessapplyService")
public class PpmOutsourcingAccessapplyService extends BaseDaoSupper
        implements IPpmOutsourcingAccessapplyService, ICommonDeleteFlowService {
    // 实体类路径
    private static String PPMOUTSOURCINGACCESSAPPLY = "com.work.ppm.application.model.integratedmanager.PpmOutsourcingAccessapply";
    /**
     * 实体类路径
     */
    private static String INSERTINFO = "com.work.ppm.application.model.interpoints.CommInterPointsDto";
    // 流程定义ID
    private static final String FLOW_ID = "wbryzr";
    //流程名称
    private static final String FLOW_TITLE = "外包人员准入";
    // 数据dao
    private final IMyBatisDao myBatisDao;
    //
    private final ICommDepartmentService commDepartmentService;

    private final IPpmOutsourcingInfoService ppmOutsourcingInfoService;
    // 业务字典
    private final IEosDictEntryService eosDictEntryService;
    // 员工登录
    private final ICommOperatorService commOperatorService;

    private final ICommEmployeeService commEmployeeService;
    // 员工登录角色
    private final ICommOperatorroleService commOperatorroleService;
    //工作流服务
    private final WorkFlowHttpClientService workFlowHttpClientService;
    // 外包风控排查时间接口
    private final IPpmOutsourcingRiskTimeService ppmOutsourcingRiskTimeService;
    // 小组人员接口
    private final IPpmDaTeamUserService ppmDaTeamUserService;
    // 小组接口
    private final IPpmDaTeamGroupService ppmDaTeamGroupService;
    // 外包人员基础接口
    private final IPpmOutsourcingBasicService ppmOutsourcingBasicService;
    // 外包人员扩展
    private final IPpmOutsourcingExtService ppmOutsourcingExtService;
    // 人力合同
    private final IPpmRlwbsshtInfoService ppmRlwbsshtInfoService;
    // 项目外包所属合同
    private final ICommOutsourcingPersonSshtService commOutsourcingPersonSshtService;
    // 项目管理
    private final IPpmProjectBuildSoftService ppmProjectBuildSoftService;
    // 项目管理
    private final IPpmAccessCardLedgerService accessCardLedgerService;
    //附件管理
    @Autowired
    private IAttatchManagementService attatchManagementService;
    //app审批
    @Lazy
    @Autowired
    private IWorkflowOutsourcingService workflowOutsourcingService;

    @Autowired
    private IPpmOutSourceInterviewService ppmOutSourceInterviewService;

    @Value("${appUrl.access}")
    private String appUrlValue;

    @Autowired
    private IPpmOutsourcingLzsqService ppmOutsourcingLzsqService;

    private final CallEsbService esbService;

    @Value("${amterm.pubId}")
    private String pubId;

    //app对接
    private final IPpmAppWorkflowInfoService ppmAppWorkflowInfoService;
    //年假
    private final IPpmKqAnnualVacationService ppmKqAnnualVacationService;
    //小组管理
    private final IPpmDevelopGroupLedgerService developGroupLedgerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PpmOutsourcingAccessapplyDto add(PpmOutsourcingAccessapplyDto applyDto, BpsWorkFlowDto bps) throws Exception {

        // 清理数据
        if (StringUtil.isNotBlank(applyDto.getDeptno())) {
            String deptno = applyDto.getDeptno().replaceAll("dep", "");
            applyDto.setDeptno(deptno);
        }

        if(StringUtils.isNotEmpty(applyDto.getJobId())){
            // 工号是否重复
            if (ppmOutsourcingBasicService.selJobIdRepart(applyDto.getJobId())) {
                throw new BizException(ApiCode.FAIL,"工号重复");
            }
        }

        // 更新数据
        if (null != applyDto.getId()) {
            //面试官数据处理
            interviewDataHand(applyDto);
            this.updatePpmOutsourcingAccessapply(applyDto);
            return null;
        }
        try {

            // 是否重复入职
            boolean falg = ppmOutsourcingBasicService.repeatEntry("", applyDto.getCertifNumber());
            if (falg) {
                throw new BizException(ApiCode.FAIL, "该员工为在岗状态，需再次入场请先完成离场申请");
            }
            PpmOutsourcingAccessapplyDto accessapplyDto = getCheckedApply(applyDto.getCertifNumber(), bps.getUserId());
            if (null != accessapplyDto && !"无".equals(accessapplyDto.getEntry())) {
                throw new BizException(ApiCode.FAIL, accessapplyDto.getEntry());
            }
            ProcessBean processBean;
            // 添加外包准入流程记录
            Long key = super.getNextSequence(PpmOutsourcingAccessapply.class);
            applyDto.setInsteanceTitle(bps.getProcessInstName());
            applyDto.setId(key);
            applyDto.setFlowId(FLOW_ID);
            applyDto.setCreateTime(new Date());
            applyDto.setIsdel(0L); // 未删除
            // 新增在场状态 - 正常和提前入场
//             modifyPresenceState(ppmOutsourcingAccessapply);
            myBatisDao.executionPojo(PPMOUTSOURCINGACCESSAPPLY,
                    applyDto, IMyBatisDao.SQL.INSTER, "addPojo");

            //面试官数据处理
            interviewDataHand(applyDto);

            // 流程实例参数设置
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(bps.getUserId());// 提交人id
            processInfo.setStartUserId(bps.getUserId());// 流程发起人id
            processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(applyDto.getId()));
            // 开始并提交流程
            processBean = workFlowHttpClientService.startProcess(processInfo);
            applyDto.setInstanceId(processBean.getProcessInstanceId());
            this.updatePpmOutsourcingAccessapply(applyDto);
            // 日志处理
            bps.setProjectNo(String.valueOf(applyDto.getId()));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setSuggest("添加");
            bps.setApplydesc("外包人员准入申请");
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());
            bps.setProcessInstName("【" + applyDto.getUserName() + DateUtil.dateToNoBar(new Date()) + "】" + "人员准入");
            bps.setProcessInstDesc(FLOW_TITLE);
            bps.setToDB(false);
            CamundaUtils.addFlowLog(bps, null);

            attatchManagementService.calMotionNumber(null,applyDto.getCeneralOfficeMotion());
            return applyDto;
        } catch (Exception e) {
            log.error("异常",e);
            throw new Exception();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String liuzhuan(PpmOutsourcingAccessapplyDto applyDto, BpsWorkFlowDto bps) throws Exception {
//        ProcessBean processBean = null;
        try {
            applyDto.setIsdel(0L);  // 未删除
            applyDto.setCreators(bps.getUserId());
            applyDto.setLastmodTime(new Date());// 最后审批时间
            applyDto.setLastmodUser(bps.getUserId());        // 最后审批人
            applyDto.setChildmanageruserid(bps.getUserId());     // 当前节点审批人工号
            this.updatePpmOutsourcingAccessapply(applyDto);
            // 查找部门总经理室
            applyDto = this.queryDetail(applyDto);
            String depId = applyDto.getDeptno().replaceAll("dep", "");
            // 下一步审批人
            EosDictEntry workManager = UsersUtil.getChargeManager(depId);
            // 执行任务
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            // 流程参数
            Map mPara = new HashMap();
            mPara.put("userId", workManager.getDictname());
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(applyDto.getInstanceId());
            bps.setApplydesc(bps.getProcessInstDesc());
            bps.setRelaData(mPara);
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setSuggest("提交申请");
            bps.setApplydesc("外包人员准入申请");
            bps.setProcessInstDesc(FLOW_TITLE);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(applyDto.getInstanceId());

            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);

            //推送待办
//            String imgTxUrl = appUrlValue + "?flowId=";
//            String imgTxDsc = bps.getUserName() + "(" + bps.getUserId() + ")" +
//                    DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss") + applyDto.getUserName() +
//                    "外包人员准入流程发起，请及时登录手机工作平台处理。";
//            workflowOutsourcingService.pushTask(workManager.getBadge(),"外包人员准入二级部经理审批",imgTxDsc,
//                    imgTxUrl,bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"));
            PpmOutsourcingLzsq info = new PpmOutsourcingLzsq();
            info.setTitle("【"+ applyDto.getUserName()+applyDto.getJobId()+"】"+"人员准入");
            ppmOutsourcingLzsqService.handFaceDevice(taskBean, info);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            CamundaUtils.deleteProcessInstance(applyDto.getInstanceId());
            log.error("外包人员准入流程发起失败：{}", e);
            throw new Exception(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String twoManagerAudit(PpmOutsourcingAccessapplyDto applyDto, BpsWorkFlowDto bps) throws Exception {
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            String[] msgIds = null;
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
//                msgIds = commEmployeeService.queryUserIdByRole("INFORMATIONPERSON").split(",");
                mPara.put("status", "pass");
                //更新业务数据
                this.updatePpmOutsourcingAccessapply(applyDto);
                // 详情
                PpmOutsourcingAccessapplyDto applyItem = this.queryDetail(applyDto);
                // 归档数据
                if (ObjectUtils.isEmpty(applyItem)) {
                    throw new BizException(ApiCode.FAIL, "外包人员准入流程数据归档失败，请联系管理员!");
                } else {
                    this.addOutsourceAudit(applyItem, bps);
                }
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setApplydesc("总经理室审批");
            bps.setRelaData(mPara);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());

            CamundaUtils.addFlowLog(bps, taskBean);
//            CamundaExtUtils.saveNextTaskInfo(taskBean,msgIds,bps);
            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                workflowOutsourcingService.deleteTask(ppmAppWorkflowInfoDto, appUrlValue + "?flowId=");
            }
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, msgIds);
        } catch (Exception e) {
            log.error("总经理室审批失败：{}", e);
            throw new Exception(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String rsAudit(PpmOutsourcingAccessapplyDto applyDto, BpsWorkFlowDto bps) throws Exception {
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            String[] msgIds = null;
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                msgIds = commEmployeeService.queryUserIdByRole("RISKPERSON").split(",");
                mPara.put("status", "pass");
                //更新业务数据
                this.updatePpmOutsourcingAccessapply(applyDto);
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setApplydesc("研发管理部人事信息审核");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,msgIds,bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, msgIds);
        } catch (Exception e) {
            log.error("研发管理部人事信息审核失败：{}", e);
            throw new Exception(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String fkzxAudit(PpmOutsourcingAccessapplyDto applyDto, BpsWorkFlowDto bps) throws Exception {
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
                //更新业务数据
                this.updatePpmOutsourcingAccessapply(applyDto);
                // 详情
                PpmOutsourcingAccessapplyDto applyItem = this.queryDetail(applyDto);
                // 归档数据
                if (ObjectUtils.isEmpty(applyItem)) {
                    throw new BizException(ApiCode.FAIL, "外包人员准入流程数据归档失败，请联系管理员!");
                } else {
                    this.addOutsourceAudit(applyItem, bps);
                }
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setApplydesc("研发管理部风控征信审核");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("研发管理部风控征信审核失败：{}", e);
            throw new Exception(e);
        }
    }

    @Override
    public PpmOutsourcingAccessapplyDto createJoid() {
        EosDictEntry entry = eosDictEntryService.getTypeidForDetail("CREATEJOBID", "PREFIX");
        String joid = "";
        for (; ; ) {
            Integer num = myBatisDao.selectClass(PPMOUTSOURCINGACCESSAPPLY, null, "createJoid");
            if (null != num) {
                joid = getJobId(entry.getDictname(), num);
                // 是否重复
                PpmOutsourcingAccessapplyDto param = new PpmOutsourcingAccessapplyDto();
                param.setJobId(joid);
                PpmOutsourcingAccessapplyDto apply = this.queryDetail(param);
                if (null != apply) {
                    continue;
                }
                break;
            }
        }
        PpmOutsourcingAccessapplyDto dto = new PpmOutsourcingAccessapplyDto();
        dto.setJobId(joid);
        return dto;
    }

    @Override
    public PpmOutsourcingAccessapplyDto getWindControl(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto) {
        EsbAction esb = new EsbAction();
        // 工商
        ApiResult<String> gs = esb.getBusiness(ppmOutsourcingAccessapplyDto.getUserName(),
                ppmOutsourcingAccessapplyDto.getCertifNumber());
        // 法院
        ApiResult<String> fy = esb.getCourtInfo(ppmOutsourcingAccessapplyDto.getUserName(),
                ppmOutsourcingAccessapplyDto.getCertifNumber());
//        // 是否重复入职
//        boolean falg = ppmOutsourcingBasicService
//                .repeatEntry(ppmOutsourcingAccessapplyDto.getUserName(),ppmOutsourcingAccessapplyDto.getCertifNumber());

        ppmOutsourcingAccessapplyDto.setBusiness(gs.getResults());
        ppmOutsourcingAccessapplyDto.setCourt(fy.getResults());
//        if(falg) {
//            ppmOutsourcingAccessapplyDto.setEntry("重复入职");
//        } else {
//            ppmOutsourcingAccessapplyDto.setEntry("无");
//        }
        return ppmOutsourcingAccessapplyDto;
    }

    @Override
    public String svnApprove(BpsWorkFlowDto bps) {
        Map<String, String> map = new HashMap<>();
        // 通过
        if ("01".equals(bps.getConclusion())) {
            map.put("status", "-1");
        }
        bps.setApplydesc("新SVN权限申请");
        bps.setProcessInstDesc("新SVN权限申请");
        bps.setRelaData(map);
        BpsUtil.finshWorkItem(bps);
        String message = BpsUtil.queryProcessInfo(bps);
        return message;
    }

    @Override
    public PpmOutsourcingAccessapplyDto queryDetail(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapply) {
        PpmOutsourcingAccessapply eneity = myBatisDao.selectClass(PPMOUTSOURCINGACCESSAPPLY,
                ppmOutsourcingAccessapply, "queryDetail");
        PpmOutsourcingAccessapplyDto dto = JsonUtil.jsonToClass(eneity, PpmOutsourcingAccessapplyDto.class);
        return dto;
    }

    @Override
    public Integer updatePpmOutsourcingAccessapply(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapply) {
        PpmOutsourcingAccessapply entry = JsonUtil.jsonToClass(ppmOutsourcingAccessapply,
                PpmOutsourcingAccessapply.class);
        return myBatisDao.executionPojo(PPMOUTSOURCINGACCESSAPPLY,
                entry, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deletePpmOutsourcingAccessapply(PpmOutsourcingAccessapply ppmOutsourcingAccessapply) {
        PpmOutsourcingAccessapplyDto accessapplyDto =
                queryDetail(JsonUtil.jsonToClass(ppmOutsourcingAccessapply,PpmOutsourcingAccessapplyDto.class));
        if(accessapplyDto != null && accessapplyDto.getCeneralOfficeMotion() != null){
            //删除总办议案已用人数
            attatchManagementService.calMotionNumberMinus(accessapplyDto.getCeneralOfficeMotion());
        }

        int count = myBatisDao.executionPojo(PPMOUTSOURCINGACCESSAPPLY,
                ppmOutsourcingAccessapply, IMyBatisDao.SQL.DELETE, "deleteById");
        if (StrUtils.isNotBlank(ppmOutsourcingAccessapply.getInstanceId())) {
            // 删除流程相关数据
            CamundaUtils.delProjectFlow(ppmOutsourcingAccessapply.getInstanceId());
            CamundaUtils.terminateProcess(ppmOutsourcingAccessapply.getInstanceId());
        }
        return count;
    }

    @Override
    public void doDelete(BpsWorkFlowDto bps) {
        PpmOutsourcingAccessapply entity = new PpmOutsourcingAccessapply();
        entity.setInstanceId(bps.getProcessInstId());
        this.deletePpmOutsourcingAccessapply(entity);
    }

    @Override
    public PpmOutsourcingAccessapplyInfoDto queryDetailExt(PpmOutsourcingAccessapplyInfoDto ppmOutsourcingAccessapplyInfo) {
        PpmOutsourcingAccessapplyInfo info = JsonUtil.jsonToClass(ppmOutsourcingAccessapplyInfo, PpmOutsourcingAccessapplyInfo.class);
        PpmOutsourcingAccessapplyInfo eneity = myBatisDao.selectClass(PPMOUTSOURCINGACCESSAPPLY, info, "queryDetailExt");
        PpmOutsourcingAccessapplyInfoDto dto = JsonUtil.jsonToClass(eneity, PpmOutsourcingAccessapplyInfoDto.class);
        return dto;
    }

    @Override
    public PpmOutsourcingAccessapplyDto getCheckedIdCard(String certifNumber, String userid) {
        // 是否重复入职
        boolean falg = ppmOutsourcingBasicService.repeatEntry("", certifNumber);
        if (falg) {
            PpmOutsourcingAccessapplyDto item = new PpmOutsourcingAccessapplyDto();
            item.setEntry("重复入职");
            return item;
        }
        return this.getCheckedApply(certifNumber, userid);
    }

    @Override
    public String selJobIdRepart(String jobId) {
        // 工号是否重复
        boolean falg = ppmOutsourcingBasicService.selJobIdRepart(jobId);
        if (falg) {
            return "工号重复";
        }
        return null;
    }

    @Override
    public PpmOutsourcingAccessapplyDto getCheckedApply(String certifNumber, String userid) {
        PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapply = new PpmOutsourcingAccessapplyDto();
        ppmOutsourcingAccessapply.setCertifNumber(certifNumber);

        PpmOutsourcingAccessapplyDto item = new PpmOutsourcingAccessapplyDto();
        Map entity = myBatisDao.selectClass(PPMOUTSOURCINGACCESSAPPLY, ppmOutsourcingAccessapply, "checkApply");
        if (null != entity && !userid.equals(entity.get("FLOWCREATEUSERID"))) {
            item.setEntry("该人员已发起准入申请，请联系" + entity.get("CREATORS"));
        } else {
            item.setEntry("无");
        }
        return item;
    }

    @Override
    public PpmOutsourcingAccessapplyDto getCheckedSecurity(String name, String idCard) {
        PpmOutsourcingAccessapplyDto item = new PpmOutsourcingAccessapplyDto();
        EsbAction esb = new EsbAction();
        // 公安核查
        ApiResult<String> ga = esb.publicSecurity(name,
                idCard);
        item.setPublicSecurity(ga.getResults());
        return item;
    }

//    @Override
//    public PpmOutsourcingAccessapplyDto queryInstanceIdForJobId(String jobId) {
//        PpmOutsourcingAccessapply accessapply = new PpmOutsourcingAccessapply();
//        accessapply.setJobId(jobId);
//        PpmOutsourcingAccessapply temp =
//                myBatisDao.selectClass(PPMOUTSOURCINGACCESSAPPLY, accessapply, "queryInstanceIdForJobId");
//        return JsonUtil.jsonToClass(temp,PpmOutsourcingAccessapplyDto.class);
//    }


    @Override
    public Map<String, String> getCardInfo(PpmOutsourcingAccessapplyDto accessapply) {
        PpmOutsourcingAccessapplyDto entity = this.queryDetail(accessapply);
        if (ObjectUtils.isEmpty(entity)) {
            throw new BizException(ApiCode.FAIL, "参数异常");
        }
        //取部门名称
        String depName;
        EosDictEntry dictEntry = eosDictEntryService.getTypeidForDetail("DEPT_ALIAS", entity.getDeptno());
        if (ObjectUtils.isEmpty(dictEntry)) {
            CommDepartment department = commDepartmentService.queryDetail(CommDepartment.builder()
                    .depcode(entity.getDeptno()).build());
            depName = department.getLocation();
        } else {
            depName = dictEntry.getDictname();
        }
        // 取外包商名称
        String comName;
        dictEntry = eosDictEntryService.getTypeidForDetail("OUTS_ALIAS", entity.getOutsourceNo());
        if (ObjectUtils.isEmpty(dictEntry)) {
            PpmOutsourcingInfo outsourcingInfo = ppmOutsourcingInfoService.queryDetailByOrgCode(
                    PpmOutsourcingInfo.builder().orgcode(entity.getOutsourceNo()).build());
            comName = outsourcingInfo.getOutsourcname();
        } else {
            comName = dictEntry.getDictname();
        }
        // 获取拼音
        String pinyin = PinyinUtils.getPinyin(entity.getUserName(), " ");
        Map<String, String> params = Maps.newHashMap();
        params.put("companyName", comName);
        params.put("companyName2", comName);
        params.put("companyName3", comName);
        params.put("depName", depName);
        params.put("depName2", depName);
        params.put("zhName", entity.getUserName());
        params.put("zhName2", entity.getUserName());
        params.put("zhName3", entity.getUserName());
        params.put("pingyin", pinyin);
        params.put("codeNumber", entity.getEntranceCard());
        params.put("codeNumber2", entity.getEntranceCard());
        params.put("faceImage", entity.getFaceimage());
        return params;
    }

    @Override
    public long countEntranceCard(String prex) {
        Map params = Maps.newHashMap();
        params.put("prex", prex);
        return myBatisDao.selectCount(PPMOUTSOURCINGACCESSAPPLY, params, "countEntranceCard");
    }

    @Override
    public String getScore(PpmOutSorceInterviewDto ppmOutSorceInterviewDto) {
        String universalToolUse = ppmOutSorceInterviewDto.getUniversalToolUse();
        String projectManageKnowledge = ppmOutSorceInterviewDto.getProjectManageKnowledge();
        String commExpression = ppmOutSorceInterviewDto.getCommExpression();
        String projectExperience = ppmOutSorceInterviewDto.getProjectExperience();
        String projectScaleXmjl = ppmOutSorceInterviewDto.getProjectScaleXmjl();
        String projectScaleJcjl = ppmOutSorceInterviewDto.getProjectScaleJcjl();
        String codeDevelopment = ppmOutSorceInterviewDto.getCodeDevelopment();
        String architectureDesign = ppmOutSorceInterviewDto.getArchitectureDesign();
        String profesKnowledge = ppmOutSorceInterviewDto.getProfesKnowledge();
        String standCondition = ppmOutSorceInterviewDto.getStandCondition();
        String productAbility = ppmOutSorceInterviewDto.getProductAbility();
        String msemployRole = ppmOutSorceInterviewDto.getMsemployRole();
        Map queryMap = new HashMap<>();
        queryMap.put("viewRole" , msemployRole);
        if(StringUtil.isStringNotEmpty(projectScaleXmjl)) {
            queryMap.put("projectScale" , projectScaleXmjl);
        }else if(StringUtil.isStringNotEmpty(projectScaleJcjl)) {
            queryMap.put("projectScale" , projectScaleJcjl);
        }
        int scoreNum = 0;
        List<CommInterPointsDto> insertInfo = myBatisDao.selectListT(INSERTINFO, queryMap, "insertInfo");
        if (insertInfo == null && insertInfo.size() <= 0) {
            return null;
        }

        for (CommInterPointsDto commInterPointsDto : insertInfo) {
            Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(commInterPointsDto, CommInterPointsDto.class);
            switch (msemployRole) {
                case "projl":
                    if ("ustool".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + universalToolUse) == null ?
                                "" : commRdEmployeeMap.get("score" + universalToolUse).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("progl".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + projectManageKnowledge) == null ?
                                "" : commRdEmployeeMap.get("score" + projectManageKnowledge).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("comaex".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + commExpression) == null ?
                                "" : commRdEmployeeMap.get("score" + commExpression).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("projy".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + projectExperience) == null ?
                                "" : commRdEmployeeMap.get("score" + projectExperience).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }
                    break;
                case "techjl":
                    if ("daima".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + codeDevelopment) == null ?
                                "" : commRdEmployeeMap.get("score" + codeDevelopment).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("jiagou".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + architectureDesign) == null ?
                                "" : commRdEmployeeMap.get("score" + architectureDesign).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("comaex".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + commExpression) == null ?
                                "" : commRdEmployeeMap.get("score" + commExpression).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("projy".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + projectExperience) == null ?
                                "" : commRdEmployeeMap.get("score" + projectExperience).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }
                    break;
                case "testjl":
                    if ("zhuanye".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + profesKnowledge) == null ?
                                "" : commRdEmployeeMap.get("score" + profesKnowledge).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("progl".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + projectManageKnowledge) == null ?
                                "" : commRdEmployeeMap.get("score" + projectManageKnowledge).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("stcon".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + standCondition) == null ?
                                "" : commRdEmployeeMap.get("score" + standCondition).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("comaex".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + commExpression) == null ?
                                "" : commRdEmployeeMap.get("score" + commExpression).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("projy".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + projectExperience) == null ?
                                "" : commRdEmployeeMap.get("score" + projectExperience).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }
                    break;
                case "pmjl":
                    if ("ustool".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + universalToolUse) == null ?
                                "" : commRdEmployeeMap.get("score" + universalToolUse).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("pronl".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + productAbility) == null ?
                                "" : commRdEmployeeMap.get("score" + productAbility).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("comaex".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + commExpression) == null ?
                                "" : commRdEmployeeMap.get("score" + commExpression).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("projy".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + projectExperience) == null ?
                                "" : commRdEmployeeMap.get("score" + projectExperience).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }
                    break;
                case "dev":
                    if ("daima".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + codeDevelopment) == null ?
                                "" : commRdEmployeeMap.get("score" + codeDevelopment).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("jiagou".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + architectureDesign) == null ?
                                "" : commRdEmployeeMap.get("score" + architectureDesign).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("comaex".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + commExpression) == null ?
                                "" : commRdEmployeeMap.get("score" + commExpression).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("projy".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + projectExperience) == null ?
                                "" : commRdEmployeeMap.get("score" + projectExperience).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }
                    break;
                case "testps":
                    if ("zhuanye".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + profesKnowledge) == null ?
                                "" : commRdEmployeeMap.get("score" + profesKnowledge).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("stcon".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + standCondition) == null ?
                                "" : commRdEmployeeMap.get("score" + standCondition).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("comaex".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + commExpression) == null ?
                                "" : commRdEmployeeMap.get("score" + commExpression).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("projy".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + projectExperience) == null ?
                                "" : commRdEmployeeMap.get("score" + projectExperience).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }
                    break;
                case "needps":
                    if ("ustool".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + universalToolUse) == null ?
                                "" : commRdEmployeeMap.get("score" + universalToolUse).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("pronl".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + productAbility) == null ?
                                "" : commRdEmployeeMap.get("score" + productAbility).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("comaex".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + commExpression) == null ?
                                "" : commRdEmployeeMap.get("score" + commExpression).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }else if ("projy".equals(commInterPointsDto.getResearchNape())) {
                        String scoreQuery = commRdEmployeeMap.get("score" + projectExperience) == null ?
                                "" : commRdEmployeeMap.get("score" + projectExperience).toString();
                        scoreNum += Integer.valueOf(scoreQuery);
                    }
                    break;
            }
        }
        return String.valueOf(scoreNum);
    }

    /**
     * 外包人员申请通过 加入相关数据
     *
     * @param ppmOutsourcingAccessapplyDto 实体类
     * @param bps                          工作流dto
     */
    private void addOutsourceAudit(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto, BpsWorkFlowDto bps) {
        ppmOutsourcingAccessapplyDto.setAppltbegTime(new Date());
        this.updatePpmOutsourcingAccessapply(ppmOutsourcingAccessapplyDto);
        PpmOutsourcingBasic basic = addUserBaisc(ppmOutsourcingAccessapplyDto);
        addUserExt(ppmOutsourcingAccessapplyDto, basic);
        addAccessCardLedger(basic.getJobId());
        //仅人力外包存在年假
        addUserAnnualVacation(basic);
        //小组管理台账
        addDevelopGroupLedger(basic);

        if ("1".endsWith(ppmOutsourcingAccessapplyDto.getOutsourceType())) {  // 人力外包
            // 保存外包合同
            saveRlwbHt(ppmOutsourcingAccessapplyDto.getContractId(),
                    ppmOutsourcingAccessapplyDto.getJobId());
        } else {    // 项目外包
            // 流程结束 数据归档
            savePersonSsht(ppmOutsourcingAccessapplyDto.getContractId(),
                    ppmOutsourcingAccessapplyDto.getJobId());
        }

        //插入排查表
        PpmOutsourcingRiskTime prt = new PpmOutsourcingRiskTime();
        //getDASTemplate().getPrimaryKey(prt);
        prt.setJobid(ppmOutsourcingAccessapplyDto.getJobId().toLowerCase());
        Date date = new Date();
        prt.setJoindate(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 3);
        prt.setDailyriskdate(c.getTime());
        c.add(Calendar.MONTH, 3);
        prt.setRiskdate(c.getTime());
        c.add(Calendar.MONTH, 6);
        prt.setSpecialinvestigationdate(c.getTime());
        prt.setRegid(bps.getUserId());
        prt.setRegtime(new Date());
        ppmOutsourcingRiskTimeService.addPpmOutsourcingRiskTime(prt);
        // 生成系统用户
        if (StringUtil.isNotBlank(ppmOutsourcingAccessapplyDto.getJobId())) {
            CommOperator operator = new CommOperator();
            operator.setPassword("ZwsUcorZkCrsujLiL6T2vQ==");
            operator.setOperatorname(ppmOutsourcingAccessapplyDto.getUserName());
            operator.setStatus("running");
            operator.setMaccode("否");
            operator.setUserid(ppmOutsourcingAccessapplyDto.getJobId());
            int id = commOperatorService.addCommOperator(operator);
            CommOperatorrole rrole = new CommOperatorrole();
            rrole.setIslose("0");
            rrole.setRoleid("OS0001");
            rrole.setOperatorid(id);
            commOperatorroleService.addCommOperatorrole(rrole);
        }
        // 项目结算
        PpmDaTeamUser tesm = new PpmDaTeamUser();
        tesm.setGw(ppmOutsourcingAccessapplyDto.getPosition());
        if ("0".equals(ppmOutsourcingAccessapplyDto.getUserType())) {
            tesm.setState(2);  //1:行员 2.人力外包3.项目外包
        } else {
            tesm.setState(3);   //1:行员 2.人力外包3.项目外包
        }

        if (!StringUtil.isNotBlank(ppmOutsourcingAccessapplyDto.getPosition())) {
            tesm.setGw("");
        } else {
            tesm.setGw(ppmOutsourcingAccessapplyDto.getPosition());
        }
        tesm.setUserid(ppmOutsourcingAccessapplyDto.getJobId());
        tesm.setId(CodeGenerater.generate("USER", 16, "yyyyMMdd") + "T");
//        if (StringUtil.isNotBlank(ppmOutsourcingAccessapplyDto.getGroupId())) {
//            tesm.setTeamgrpupid(ppmOutsourcingAccessapplyDto.getGroupId());
//        }

        ppmDaTeamUserService.addPpmDaTeamUser(tesm);

        ppmOutsourcingLzsqService.updatePpmOutsourcingLzsq(basic);
        // 发送信息
//        if (StringUtil.isNotBlank(ppmOutsourcingAccessapplyDto.getGroupId())) {
//            PpmDaTeamGroup teamItem = ppmDaTeamGroupService.queryDetail(ppmOutsourcingAccessapplyDto.getGroupId());
//            if (null != teamItem) {
//                String id = teamItem.getGroupuserid();
//                String context = "您负责的小组有成员" +
//                        ppmOutsourcingAccessapplyDto.getUserName() +
//                        "(" + ppmOutsourcingAccessapplyDto.getJobId() +
//                        ")加入，请知晓";
//                // 发信息
//                // BpsUtil.sendSms(id,context);
//            }
//        }
    }

    /**
     * 外包扩展
     *
     * @param ppmOutsourcingAccessapplyDto 对象
     * @param basic                        基础信息对象
     */
    private void addUserExt(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto,
                            PpmOutsourcingBasic basic) {
        // 扩展信息
        PpmOutsourcingExt ext = new PpmOutsourcingExt();
        ext.setUserId(basic.getJobId());                    // 工号
        ext.setCardType(basic.getCertificateType());        // 证件类型
        ext.setCardId(basic.getCertificateNumber());        // 证件号
        ext.setPoliticalStatus(ppmOutsourcingAccessapplyDto.getPoliticalStatus());      // 政治面貌
        ext.setAddress(ppmOutsourcingAccessapplyDto.getAddress());          // 家庭地址
        ext.setCertificate(ppmOutsourcingAccessapplyDto.getCertificate());  // 所获证书
        ext.setLearnExperience(ppmOutsourcingAccessapplyDto.getLearnExperience()); // 学习经历
        ext.setMainItem(ppmOutsourcingAccessapplyDto.getMainItem());            // 项目经历
        ext.setMainJobs(ppmOutsourcingAccessapplyDto.getMainJobs());            // 主要工作经历
        ext.setMainReward(ppmOutsourcingAccessapplyDto.getMainReward());        // 奖励记录
        ext.setMainFamily(ppmOutsourcingAccessapplyDto.getMainFamily());        // 家庭成员
        ext.setAttendanceType(ppmOutsourcingAccessapplyDto.getAttendanceType());    // 考勤类型
        ext.setAttendanceTime(ppmOutsourcingAccessapplyDto.getAttendanceTime());     // 考勤时间
        ext.setSpecialAuth(ppmOutsourcingAccessapplyDto.getSpecialAuth());              // 特殊权限
        ext.setAuthDes(ppmOutsourcingAccessapplyDto.getAuthDes());                  // 权限描述
        ext.setVirtualDesktop(ppmOutsourcingAccessapplyDto.getIscreditquery());     // 是否申请虚拟桌面
        ext.setVirtualDesktopSettings(ppmOutsourcingAccessapplyDto.getApplyreg());      // 虚拟桌面配置
        ext.setSvnLimit(ppmOutsourcingAccessapplyDto.getSvnLimit());        // 是否开通SVN权限
        ext.setSvnPath(ppmOutsourcingAccessapplyDto.getSvnRoad());          // svn地址
        ext.setSvnApplication(ppmOutsourcingAccessapplyDto.getApplysvnno());        // svn流程申请编号
        ext.setCreditReport(ppmOutsourcingAccessapplyDto.getCreditReport());                 // 征信报告
        ext.setSociaiSecrityPaymentProve(ppmOutsourcingAccessapplyDto.getSociaiSecrityPaymentProve());  //  社保缴纳证明/合同
        ext.setConfidentialityCommitment(ppmOutsourcingAccessapplyDto.getConfidentialityCommitment());      // 保密承诺书
        ext.setDueDiligenceReport(ppmOutsourcingAccessapplyDto.getDueDiligenceReport());  //  尽调报告
        ext.setQtFile(ppmOutsourcingAccessapplyDto.getQtFile());  //  其他附件
        ext.setOtherFile(ppmOutsourcingAccessapplyDto.getOtherFile());      // 其他证明文件
        ext.setPartnerName(ppmOutsourcingAccessapplyDto.getPartnerName());      // 配偶名称
        ext.setIdcardNumber(ppmOutsourcingAccessapplyDto.getIdcardNumber());        // 配置证件号
        ext.setSpouseCertificateType(basic.getCertificateType());   //  配偶证件类型
        ext.setDutyType(ppmOutsourcingAccessapplyDto.getDutyType());    // 轮班类型 1:正常 2:轮班制
        ext.setGrade(ppmOutsourcingAccessapplyDto.getGrade());      // 等级
        ext.setPrice(ppmOutsourcingAccessapplyDto.getPrice());      // 单价
        ext.setWhetherMeetsClaim(ppmOutsourcingAccessapplyDto.getWhetherMeetsClaim()); // 是否符合准入要求
        ext.setMeetRequirementsReason(ppmOutsourcingAccessapplyDto.getMeetRequirementsReason());    // 符合要求的原因
        ext.setWhetherAttendance(ppmOutsourcingAccessapplyDto.getWhetherAttendance());      // 是否考勤(0:不考勤，1：考勤)
        ext.setContractId(ppmOutsourcingAccessapplyDto.getContractId());                // 合同id
        ext.setIsProjectEssential(ppmOutsourcingAccessapplyDto.getIskey());     //是否关键人员
        ext.setReasonsIncreasingStaff(ppmOutsourcingAccessapplyDto.getAddreason());     // 增加人员原因
        ext.setNoAttendanceReason(ppmOutsourcingAccessapplyDto.getNotattendancereason());  //   不参加考勤的原因
        ext.setVacation(0.0);                   // 休假时间
        Date date = new Date();
        ext.setVacationUpdateTime(date);        // 休假更新时间
        ext.setAccessFlowTime(date);      // 准入流程结束时间
        ext.setLetterOfCommitment(ppmOutsourcingAccessapplyDto.getLetterOfCommitment());
        ext.setLiabilityStatement(ppmOutsourcingAccessapplyDto.getLiabilityStatement());
        ext.setMedicalExamination(ppmOutsourcingAccessapplyDto.getMedicalExamination());
        ppmOutsourcingExtService.addPpmOutsourcingExt(ext);
    }

    /**
     * 存储门禁卡
     *
     * @param jobId 对象
     */
    private void addAccessCardLedger(String jobId) {
        // 扩展信息
        PpmAccessCardLedgerDto accessCardLedgerDto = PpmAccessCardLedgerDto.builder().build();
        accessCardLedgerDto.setJobId(jobId);
        accessCardLedgerDto.setId(DateUtil.getDateRandom());
        accessCardLedgerService.addPpmAccessCardLedger(accessCardLedgerDto);
    }


    /**
     * 增加年假
     * @param basic 对象
     */
    private void addUserAnnualVacation(PpmOutsourcingBasic basic) {
        PpmKqAnnualVacationDto ppmKqAnnualVacationDto = new PpmKqAnnualVacationDto();
        ppmKqAnnualVacationDto.setJobId(basic.getJobId());
        ppmKqAnnualVacationDto = ppmKqAnnualVacationService.queryDetail(ppmKqAnnualVacationDto);
        if (ppmKqAnnualVacationDto == null){
            ppmKqAnnualVacationDto = new PpmKqAnnualVacationDto();
            ppmKqAnnualVacationDto.setJobId(basic.getJobId());
            ppmKqAnnualVacationDto.setAnnualVacationDate(DateUtil.intervalMonth(basic.getEntryTime(),6));
            ppmKqAnnualVacationDto.setAnnualVacationDays(0.0);
            ppmKqAnnualVacationDto.setAnnualVacationSurplus(0.0);
            ppmKqAnnualVacationService.addPpmKqAnnualVacation(ppmKqAnnualVacationDto);
        }else {
            ppmKqAnnualVacationDto.setJobId(basic.getJobId());
            ppmKqAnnualVacationDto.setAnnualVacationDate(DateUtil.intervalMonth(basic.getEntryTime(),6));
            ppmKqAnnualVacationDto.setAnnualVacationDays(0.0);
            ppmKqAnnualVacationDto.setAnnualVacationSurplus(0.0);
            ppmKqAnnualVacationService.updatePpmKqAnnualVacation(ppmKqAnnualVacationDto);
        }
    }

    /**
     * 增加小组台账
     * @param basic 对象
     */
    private void addDevelopGroupLedger(PpmOutsourcingBasic basic) {
        PpmDevelopGroupLedgerDto developGroupLedgerDto = new PpmDevelopGroupLedgerDto();
        developGroupLedgerDto.setId(DateUtil.getDateRandom());
        developGroupLedgerDto.setJobId(basic.getJobId());
        developGroupLedgerService.addPpmDevelopGroupLedger(developGroupLedgerDto);
    }

    /**
     * 保存外包基础信息
     *
     * @param ppmOutsourcingAccessapplyDto 对象
     * @return PpmOutsourcingBasic
     */
    private PpmOutsourcingBasic addUserBaisc(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto) {
        PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
        basic.setCertificateNumber(ppmOutsourcingAccessapplyDto.getCertifNumber());
        // 年龄
        int age = this.getAgeByBrithday(ppmOutsourcingAccessapplyDto.getBirthday());
        basic.setAge(age);              // 年龄
        basic.setMaritalStatus(ppmOutsourcingAccessapplyDto.getMaritalStatus());       // 婚姻状况
//        person.setWorktime(Integer.parseInt(String.valueOf(worktime))+ "");
        basic.setUserName(ppmOutsourcingAccessapplyDto.getUserName());  // 外包人员名称
        basic.setOutsourceNo(ppmOutsourcingAccessapplyDto.getOutsourceNo());       // 外包公司编号
        basic.setSex(ppmOutsourcingAccessapplyDto.getSex());       // 外包性别
        basic.setCertificateType(ppmOutsourcingAccessapplyDto.getCertifType()); // 外包身份证类型
        basic.setCertificateNumber(ppmOutsourcingAccessapplyDto.getCertifNumber());   // 证件号码
        basic.setMobile(ppmOutsourcingAccessapplyDto.getMobile());         // 联系方式
        basic.setDeptId(ppmOutsourcingAccessapplyDto.getDeptno().replaceAll("dep", ""));               // 部门
        basic.setOutsourceType(ppmOutsourcingAccessapplyDto.getOutsourceType());     // 外包分类
        basic.setUserType(ppmOutsourcingAccessapplyDto.getUserType());               // 结算方式
        basic.setJobId(ppmOutsourcingAccessapplyDto.getJobId().toLowerCase());       // 工号
        basic.setCollege(ppmOutsourcingAccessapplyDto.getSchool());                           // 毕业学院
        basic.setEducation(ppmOutsourcingAccessapplyDto.getEducation() + "");                     // 学历
        basic.setPoliticalStatus(ppmOutsourcingAccessapplyDto.getPoliticalStatus());                     //         政治面貌
        basic.setBirthday(ppmOutsourcingAccessapplyDto.getBirthday());                     // 生日
        basic.setProfession(ppmOutsourcingAccessapplyDto.getSpecialty());                         // 专业
        basic.setGroupId(ppmOutsourcingAccessapplyDto.getGroupId());                   //  所在小组
        basic.setPostId(ppmOutsourcingAccessapplyDto.getPosition());                    // 岗位
        basic.setGrade(ppmOutsourcingAccessapplyDto.getGrade());
        basic.setPrice(ppmOutsourcingAccessapplyDto.getPrice());
//        basic.setState("0");                        // 外包人员状态（0：在岗，1离岗）
        // 工作内容
        basic.setVmAccount(ppmOutsourcingAccessapplyDto.getVmAccount());
        basic.setEntranceCard(ppmOutsourcingAccessapplyDto.getEntranceCard());
        basic.setWorkContent(ppmOutsourcingAccessapplyDto.getWorkContent());
        basic.setHometown(ppmOutsourcingAccessapplyDto.getNativeplace());                   // 籍贯
        basic.setProjectNumber(ppmOutsourcingAccessapplyDto.getProjectno());            // 项目名称编号
        basic.setProjectManager(ppmOutsourcingAccessapplyDto.getManagerUserid());       // 项目经理
        basic.setIsOtherProject(ppmOutsourcingAccessapplyDto.getIsOtherProject());      // 是否为其他项目
        basic.setSdprojectName(ppmOutsourcingAccessapplyDto.getSdprojectname());        // 其他项目
        basic.setSdmanageruserId(ppmOutsourcingAccessapplyDto.getSdmanageruserid());    // 其他项目经理
        basic.setPersonInCharge(ppmOutsourcingAccessapplyDto.getOrganizers());          // 行内负责人
        basic.setSystemNo(ppmOutsourcingAccessapplyDto.getSystemnos());               // 系统名称
        basic.setSystemPrincipal(ppmOutsourcingAccessapplyDto.getSystemPrincipal());      // 系统负责人
        basic.setPurchaseItem(ppmOutsourcingAccessapplyDto.getPurchaseItem());          // 采购项目
        basic.setBeginTime(ppmOutsourcingAccessapplyDto.getBeginTime());     // 有效时间-入场时间

//        basic.setEntryTime(ppmOutsourcingAccessapplyDto.getEntryTime());        // 入职时间
        basic.setFaceimage(ppmOutsourcingAccessapplyDto.getFaceimage());        // 人脸照片路径
        // 在场状态
        modifyPresenceState(ppmOutsourcingAccessapplyDto);
        basic.setPresenceState(ppmOutsourcingAccessapplyDto.getPresenceState());    // 在场状态
        //对入职时间进行处理
        Date date = new Date();//4月4日
        Date entryTime = ppmOutsourcingAccessapplyDto.getEntryTime(); // 4月6日
        basic.setEntryTime(entryTime);
        basic.setState("0");
        basic.setEvaluationLevel(ppmOutsourcingAccessapplyDto.getEvaluationLevel());
        basic.setLevelReason(ppmOutsourcingAccessapplyDto.getLevelReason());
        basic.setEvaluatorId(ppmOutsourcingAccessapplyDto.getEvaluatorId());
        basic.setEvaluationTime(ppmOutsourcingAccessapplyDto.getEvaluationTime());
        // 基本信息添加数据
        ppmOutsourcingBasicService.addPpmOutsourcingBasic(basic);
        return basic;
    }

    /**
     * 更新在场状态 - 正常、提前入职
     *
     * @param ppmOutsourcingAccessapply 对象
     */
    private void modifyPresenceState(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapply) {
        String proattrbutes;
        if (StringUtil.isNotBlank(ppmOutsourcingAccessapply.getProjectno())) {
            proattrbutes =
                    ppmProjectBuildSoftService.queryProjectNoForProattrbutes(ppmOutsourcingAccessapply.getProjectno());
        } else {
            proattrbutes = "01";
        }

        // “外包方式”为项目外包且“结算方式”为项目的这种情况,走正常的新业务逻辑
        if ("0".equals(ppmOutsourcingAccessapply.getOutsourceType()) &&
                "1".equals(ppmOutsourcingAccessapply.getUserType())
                && "02".equals(proattrbutes)) {
            ppmOutsourcingAccessapply.setPresenceState("2");       // 提前入场
        } else {
            ppmOutsourcingAccessapply.setPresenceState("1");       // 正常
        }
    }

    /**
     * 保存外包人员的所属合同
     *
     * @param sshtid 编号
     * @param jobid  工号
     */
    private void saveRlwbHt(String sshtid, String jobid) {
        PpmRlwbsshtInfo info = new PpmRlwbsshtInfo();
        info.setStarttime(DateUtil.dateToIsoDateFormat(new Date()));
        info.setHtid(sshtid);
        info.setJobid(jobid);
        ppmRlwbsshtInfoService.addPpmRlwbsshtInfo(info);
    }

    /**
     * 保存数据
     *
     * @param sshtid 合同编号
     * @param jobid  工号
     */
    private void savePersonSsht(String sshtid, String jobid) {
        CommOutsourcingPersonSsht ssht = new CommOutsourcingPersonSsht();
        ssht.setState("0");
        ssht.setJobid(jobid);
        CommOutsourcingPersonSsht item = commOutsourcingPersonSshtService.queryDetail(ssht);
        Date date = new Date();
        if (null != item) {
            if (!sshtid.equals(item.getSshtid())) {
                // 更新shuj
                item.setEndtime(DateUtil.dateToIsoDateFormat(date));
                item.setState("1");
                commOutsourcingPersonSshtService.updateCommOutsourcingPersonSsht(item);
            }
        }
        if (StringUtil.isNotBlank(sshtid) && !"0000".equals(sshtid)) {
            CommOutsourcingPersonSsht per = new CommOutsourcingPersonSsht();
            per.setJobid(jobid);
            per.setStarttime(DateUtil.dateToIsoDateFormat(date));
            per.setSshtid(sshtid);
            per.setState("0");                         //合同中
            commOutsourcingPersonSshtService.addCommOutsourcingPersonSsht(per);
        }
    }

    /**
     * 通过生日计算年龄
     *
     * @param brithday 生日
     * @return int
     */
    private int getAgeByBrithday(String brithday) {
        int age = 0;
        Date parseBirthday = DateUtil.dataFormat(brithday, "yyyy-MM-dd");

        Calendar now = Calendar.getInstance();
        now.setTime(new Date());// 当前时间

        Calendar birth = Calendar.getInstance();
        birth.setTime(parseBirthday);// 生日

        if (birth.after(now)) {
            age = 0;
        } else {
            age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) > birth
                    .get(Calendar.DAY_OF_YEAR)) {
                age = age + 1;
            }
        }
        return age;
    }

    /**
     * 通过身份证获得生日日期
     *
     * @param certifnumber 身份证号
     * @return String
     */
    private String getBirthdayBycertno(String certifnumber) {
        String birthday = "";
        if (certifnumber != null && !"".equals(certifnumber)) {
            if (certifnumber.length() == 18) {//18位身份证
                //step1
                String year = certifnumber.substring(6, 10);
                String month = certifnumber.substring(10, 12);
                String day = certifnumber.substring(12, 14);
                birthday = year + "-" + month + "-" + day;
            }
        }
        return birthday;
    }

    /**
     * 获取员工工号
     * <p>
     * 生成由前缀+年份+4位序列号
     * 如：wb220001
     * </p>
     *
     * @param prefix
     * @param num
     * @return {@link String}
     */
    private String getJobId(String prefix, Integer num) {
        // 行员编码固定4位
        DecimalFormat decimalFormat = new DecimalFormat("0000");
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        return prefix + year.substring(2) + decimalFormat.format(num);
    }

    /**
     * 外包准入流程面试官数据处理
     * @param applyDto
     * @return
     */
    private void interviewDataHand(PpmOutsourcingAccessapplyDto applyDto){
        log.info("------------面试官数据处理开始---------");
        //面试官信息数据处理
        List<PpmOutSorceInterviewDto> ppmList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(applyDto.getScoreList())){
            ppmList = applyDto.getScoreList();
            if(!CollectionUtils.isEmpty(ppmList)){
                log.info("当前流程开始处理流程id：{}",applyDto.getInstanceId());
                ppmList.forEach(ppm->{
                    PpmOutSourceInterview ppmInfo = new PpmOutSourceInterview();
                    String deptno = ppm.getDeptno().replaceAll("dep", "");
                    ppmInfo.setId(applyDto.getId());
                    ppmInfo.setCertifNumber(applyDto.getCertifNumber());
                    ppmInfo.setMsgId(ppm.getMsgid());
                    ppmInfo.setFlowPathId(applyDto.getInstanceId());
                    if(StringUtils.isNotEmpty(ppm.getMsgid())){
                        List<PpmOutSourceInterview> list = ppmOutSourceInterviewService.selInfo(ppmInfo);
                        ppmInfo.setMsgRole(ppm.getMsgRole());
                        ppmInfo.setMsemployRole(ppm.getMsemployRole());
                        ppmInfo.setUniversalToolUse(ppm.getUniversalToolUse());
                        ppmInfo.setProjectManageKnowledge(ppm.getProjectManageKnowledge());
                        ppmInfo.setCommExpression(ppm.getCommExpression());
                        ppmInfo.setProjectExperience(ppm.getProjectExperience());
                        ppmInfo.setProjectScale(ppm.getProjectScale());
                        ppmInfo.setProjectScaleXmjl(ppm.getProjectScaleXmjl());
                        ppmInfo.setProjectScaleJcjl(ppm.getProjectScaleJcjl());
                        ppmInfo.setScoreResult(ppm.getScoreResult());
                        ppmInfo.setCodeDevelopment(ppm.getCodeDevelopment());
                        ppmInfo.setArchitectureDesign(ppm.getArchitectureDesign());
                        ppmInfo.setProfesKnowledge(ppm.getProfesKnowledge());
                        ppmInfo.setStandCondition(ppm.getStandCondition());
                        ppmInfo.setProductAbility(ppm.getProductAbility());
                        ppmInfo.setInterCode(ppm.getMsgno());
                        ppmInfo.setInterdepCode(deptno);
                        if(CollectionUtils.isEmpty(list)){
                            ppmOutSourceInterviewService.addInterviewList(ppmInfo);
                        }else{
                            ppmOutSourceInterviewService.updInterData(ppmInfo);
                        }
                    }
                });
            }
        }
    }
}
