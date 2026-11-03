package com.work.ppm.application.services.integratedmanager.impl;

import com.work.imex.util.StrUtils;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcUpdateDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcUpdateInfoDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.integratedmanager.PpmEarlyCourseApply;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcUpdate;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingExt;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcUpdateInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcUpdateService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingExtService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.application.services.vendormanager.IExpenseSettlementService;
import com.work.ppm.application.services.vendormanager.IPaymentConfirmService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.*;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  信息变更申请信息表 接口实现类
 */
@Slf4j
@Service("ppmOutsourcUpdateService")
public class PpmOutsourcUpdateService extends BaseDaoSupper implements IPpmOutsourcUpdateService {
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCUPDATE = "com.work.ppm.application.model.integratedmanager.PpmOutsourcUpdate";

    // 流程定义ID
    private static final String FLOW_ID = "xxbgsq";
    //流程名称
    private static final String FLOW_TITLE = "信息变更申请";

    //员工
    @Autowired
    private ICommEmployeeService commEmployeeService;

    //工作流服务
    @Autowired
    private WorkFlowHttpClientService workFlowHttpClientService;

    //花名册
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    //花名册附表
    @Autowired
    private IPpmOutsourcingExtService ppmOutsourcingExtService;

    //附件管理
    @Autowired
    private IAttatchManagementService attatchManagementService;

    //支付确认
    @Autowired
    private IPaymentConfirmService paymentConfirmService;

    //费用结算
    @Autowired
    private IExpenseSettlementService expenseSettlementService;

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private IPpmOutsourcUpdateInfoService ppmOutsourcUpdateInfoService;

    @Autowired
    private IPpmOutsourcingBasicService iPpmOutsourcingBasicService;

    @Override
    public int addPpmOutsourcUpdate(PpmOutsourcUpdateDto ppmOutsourcUpdateDto) {
        PpmOutsourcUpdate ppmOutsourcUpdate = JsonUtil.jsonToClass(ppmOutsourcUpdateDto, PpmOutsourcUpdate.class);
        // 如果需要请set 主键id
        // ppmOutsourcUpdate.setId(getNextSequence(PpmOutsourcUpdate.class));
        myBatisDao.executionPojo(PPMOUTSOURCUPDATE, ppmOutsourcUpdate, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmOutsourcUpdateDto queryDetail(PpmOutsourcUpdateDto ppmOutsourcUpdateDto) {
        PpmOutsourcUpdate ppmOutsourcUpdate = JsonUtil.jsonToClass(ppmOutsourcUpdateDto, PpmOutsourcUpdate.class);
        PpmOutsourcUpdate data = myBatisDao.selectClass(PPMOUTSOURCUPDATE, ppmOutsourcUpdate,"queryDetail");
        return JsonUtil.jsonToClass(data, PpmOutsourcUpdateDto.class);
    }

    @Override
    public int updatePpmOutsourcUpdate(PpmOutsourcUpdateDto ppmOutsourcUpdateDto) {
        PpmOutsourcUpdate ppmOutsourcUpdate = JsonUtil.jsonToClass(ppmOutsourcUpdateDto, PpmOutsourcUpdate.class);
        return myBatisDao.executionPojo(PPMOUTSOURCUPDATE, ppmOutsourcUpdate, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePpmOutsourcUpdate(PpmOutsourcUpdateDto ppmOutsourcUpdateDto) {
        String instanceId = ppmOutsourcUpdateDto.getInstanceid();
        List<PpmOutsourcUpdateInfoDto> list = ppmOutsourcUpdateInfoService
                .queryListForInstanceId(instanceId);
//        list.forEach(infoDto -> {
//            if(!attatchManagementService
//                    .judgeMotionNumber(infoDto.getCeneralOfficeMotionAf(),
//                    infoDto.getCeneralOfficeMotionBe())){
//                throw new BizException(ApiCode.FAIL, "原总办议案总数为0或已无可用人数");
//            }
//        });


        int count = 0;
        if (StrUtils.isNotBlank(instanceId)) {
            count = myBatisDao.executionPojo(
                    PPMOUTSOURCUPDATE, ppmOutsourcUpdateDto, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");
            // 清理关联业务数据
            ppmOutsourcUpdateInfoService.deleteByInstanceId(instanceId);
            // 删除流程相关数据
            CamundaUtils.delProjectFlow(instanceId);
            CamundaUtils.terminateProcess(instanceId);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String managerCreated(PpmOutsourcUpdateDto ppmOutsourcUpdateDto) {
        BpsWorkFlowDto bps = ppmOutsourcUpdateDto.getBps();
        List<PpmOutsourcUpdateInfoDto> updateInfoDtos = ppmOutsourcUpdateDto.getUpdateInfoDtos();

        //校验
        vaildCommitData(ppmOutsourcUpdateDto);

        if (ppmOutsourcUpdateDto.getId() != null ) {
            this.updatePpmOutsourcUpdate(ppmOutsourcUpdateDto);

            updateInfoDtos.forEach(applyInfoDto ->
                    ppmOutsourcUpdateInfoService.updatePpmOutsourcUpdateInfo(applyInfoDto));

            return null;
        }
        try {
            ProcessBean processBean;
            // 添加信息变更流程记录
            ppmOutsourcUpdateDto.setId(getNextSequence(PpmOutsourcUpdate.class));
            ppmOutsourcUpdateDto.setFlowId(FLOW_ID);
            ppmOutsourcUpdateDto.setCreatedTime(new Date());
            ppmOutsourcUpdateDto.setState("0");
            ppmOutsourcUpdateDto.setStateName("未发起");
            ppmOutsourcUpdateDto.setCurrentId(ppmOutsourcUpdateDto.getApplyer());
//            insertPpmEarlyCourseApply(ppmEarlyCourseApplyDto);

            // 流程实例参数设置
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(bps.getUserId());// 提交人id
            processInfo.setStartUserId(bps.getUserId());// 流程发起人id
            processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(ppmOutsourcUpdateDto.getId()));
            // 开始并提交流程
            processBean = workFlowHttpClientService.startProcess(processInfo);
            ppmOutsourcUpdateDto.setInstanceid(processBean.getProcessInstanceId());
            addPpmOutsourcUpdate(ppmOutsourcUpdateDto);
            StringBuilder userName = new StringBuilder();
            StringJoiner joiner = new StringJoiner(",");
            updateInfoDtos.forEach(infoDto ->{
                userName.append(infoDto.getUserName()).append(",");
                infoDto.setInstanceid(ppmOutsourcUpdateDto.getInstanceid());
                infoDto.setPid(ppmOutsourcUpdateDto.getId());
                joiner.add(infoDto.getUserName());
            });
            ppmOutsourcUpdateInfoService.addPpmOutsourcUpdateInfos(updateInfoDtos);
            // 日志处理
            bps.setProjectNo(String.valueOf(ppmOutsourcUpdateDto.getId()));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setSuggest("添加");
            bps.setApplydesc("外包人员信息变更申请");
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());

            bps.setProcessInstName("【" + joiner + DateUtil.dateToNoBar(new Date()) + "】" + FLOW_TITLE);
            bps.setProcessInstDesc(FLOW_TITLE);
            bps.setToDB(false);
            CamundaUtils.addFlowLog(bps, null);
            //新发起时，只存在前后的总办议案修改时需要增减人数
            updateInfoDtos.forEach(infoDto -> attatchManagementService.calMotionNumber(
                    infoDto.getCeneralOfficeMotionBe(),infoDto.getCeneralOfficeMotionAf()));
            return "pass";
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String managerInit(PpmOutsourcUpdateDto ppmOutsourcUpdateDto) {
        List<PpmOutsourcUpdateInfoDto> updateInfoDtos = ppmOutsourcUpdateDto.getUpdateInfoDtos();
        BpsWorkFlowDto bps = ppmOutsourcUpdateDto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        //校验
        vaildCommitData(ppmOutsourcUpdateDto);
        String depId = "";
        //更换部门ID
        if (updateInfoDtos != null && updateInfoDtos.size() > 0) {
            String jobId = updateInfoDtos.get(0).getJobId();
            depId = iPpmOutsourcingBasicService.getDepIdByJobId(jobId);
        }
        ppmOutsourcUpdateDto.setApplyerDept(depId);
        ProcessBean processBean = null;
        try {
            // 第一次流程发起
            if (!StringUtil.isNotBlank(ppmOutsourcUpdateDto.getInstanceid())) {
                bps.setProcessDefName(FLOW_ID);
                StringJoiner joiner = new StringJoiner(",");
                updateInfoDtos.forEach(ai -> joiner.add(ai.getUserName()));
                bps.setProcessInstName("【" + joiner + DateUtil.dateToNoBar(new Date()) + "】" + FLOW_TITLE);

                // 如果需要请set 主键id
                ppmOutsourcUpdateDto.setId(getNextSequence(PpmEarlyCourseApply.class));
                ppmOutsourcUpdateDto.setCreatedTime(new Date());

                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(String.valueOf(ppmOutsourcUpdateDto.getId()));
                Map mPara = new HashMap();
                //获取下一步处理人 业务字典配置部门总经理室审批
                String currentId = "";
                String currentName = "";
                if (StringUtil.isNotBlank(ppmOutsourcUpdateDto.getApplyerDept())) {
                    EosDictEntry chargeManager = UsersUtil.getChargeManager(ppmOutsourcUpdateDto.getApplyerDept());
                    // 总经理室审批作为下一步处理人
                    currentId = chargeManager.getDictname();
                    currentName = chargeManager.getFilter2();
                    mPara.put("userId", currentId);
                    // 流程变量
                    processInfo.setmPara(mPara);
                }
                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);

                // 变更申请
                ppmOutsourcUpdateDto.setInstanceid(processBean.getProcessInstanceId());
                ppmOutsourcUpdateDto.setState("1");
                ppmOutsourcUpdateDto.setStateName("处理中");
                ppmOutsourcUpdateDto.setCurrentId(currentId);
                ppmOutsourcUpdateDto.setCurrentName(currentName);
                ppmOutsourcUpdateDto.setCreatedBy(ppmOutsourcUpdateDto.getApplyer());
                ppmOutsourcUpdateDto.setCreatedTime(new Date());
                PpmOutsourcUpdate ppmOutsourcUpdate =
                        JsonUtil.jsonToClass(ppmOutsourcUpdateDto,PpmOutsourcUpdate.class);
                myBatisDao.executionPojo(PPMOUTSOURCUPDATE, ppmOutsourcUpdate, IMyBatisDao.SQL.INSTER, "addPojo");

                //总办议案修改时需要增减人数
//                updateInfoDtos.forEach(infoDto -> attatchManagementService.calMotionNumber(
//                        infoDto.getCeneralOfficeMotionBe(),infoDto.getCeneralOfficeMotionAf()));

                // 申请变更人员
                updateInfoDtos.forEach(ai -> {
                    ai.setPid(ppmOutsourcUpdateDto.getId());
                    ai.setInstanceid(ppmOutsourcUpdateDto.getInstanceid());
                    ai.setCreatedBy(ppmOutsourcUpdateDto.getApplyer());
                    ai.setCreatedTime(new Date());
                });
                //信息变更人员信息
                ppmOutsourcUpdateInfoService.addPpmOutsourcUpdateInfos(updateInfoDtos);

                // 日志处理
                bps.setProjectNo(String.valueOf(ppmOutsourcUpdateDto.getId()));
                bps.setConclusion("01");
                bps.setApplydesc("信息变更申请发起");
                bps.setRelaData(mPara);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setSuggest("提交申请");
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(processBean.getProcessInstanceId());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);

                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            } else {

                // 执行任务
                TaskBean param = new TaskBean();
                param.setTenantId(BusinessConstant.TENANT_ID);// 租户
                param.setTaskId(bps.getTaskId());// 任务id
                String nowUserId = bps.getUserId();
                param.setUserId(nowUserId);// 处理人
                param.setComment(bps.getSuggest());// 审批意见
                Map mPara = new HashMap();
                String currentId = "";
                String currentName = "";
                if (StringUtil.isNotBlank(ppmOutsourcUpdateDto.getApplyerDept())) {
                    EosDictEntry chargeManager = UsersUtil.getChargeManager(ppmOutsourcUpdateDto.getApplyerDept());
                    // 总经理室审批作为下一步处理人
                    currentId = chargeManager.getDictname();
                    currentName = chargeManager.getFilter2();
                    mPara.put("userId", currentId);
                    // 流程变量
                    param.setmPara(mPara);
                }
                // 提交任务
                workFlowHttpClientService.submitTask(param);
                // 日志处理
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(ppmOutsourcUpdateDto.getInstanceid());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion("01");
                bps.setSuggest("重新提交");
                bps.setApplydesc(FLOW_TITLE);

                ppmOutsourcUpdateDto.setState("1");
                ppmOutsourcUpdateDto.setStateName("处理中");
                ppmOutsourcUpdateDto.setCurrentId(currentId);
                ppmOutsourcUpdateDto.setCurrentName(currentName);
                ppmOutsourcUpdateDto.setModifiedBy(nowUserId);
                ppmOutsourcUpdateDto.setModifiedTime(new Date());
                updatePpmOutsourcUpdate(ppmOutsourcUpdateDto);

                // 第一次流程后只做流转更新
                updateInfoDtos.forEach(ai -> ai.setInstanceid(ppmOutsourcUpdateDto.getInstanceid()));
                ppmOutsourcUpdateInfoService.addPpmOutsourcUpdateInfos(updateInfoDtos);

                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(ppmOutsourcUpdateDto.getInstanceid());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);

                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            }
        } catch (Exception e) {
            if(processBean != null){
                CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            }

            e.printStackTrace();
            log.error("添加并发起流程失败！");
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String departmentHeadApproval(PpmOutsourcUpdateDto updateDto) {
        BpsWorkFlowDto bps = updateDto.getBps();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            String nowUserId = bps.getUserId();
            param.setUserId(nowUserId);// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
//            mPara.put("status", "return");
//            updateDto.setState("0");
//            updateDto.setStateName("重新提交");
//            updateDto.setCurrentId(updateDto.getApplyer());
//            updateDto.setCurrentName(updateDto.getApplyerName());
//            List<String> msgIds = Lists.newArrayList();
//            List<String> msgNames;
//            // 通过状态
//            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
//                mPara.put("status", "pass");
//                CommEmployeeDto commEmployeeDto = new CommEmployeeDto();
//                //永赢租赁金融科技部-总经理室
//                commEmployeeDto.setDepid(80000053);
//                msgIds = commEmployeeService.getEmployeeLists(commEmployeeDto)
//                        .stream().map(CommEmployeeDto::getBadge).collect(Collectors.toList());
//                msgNames = commEmployeeService.getEmployeeLists(commEmployeeDto)
//                        .stream().map(CommEmployeeDto::getEmpname).collect(Collectors.toList());
//
//                StringJoiner joiner = new StringJoiner(",");
//                msgIds.forEach(joiner::add);
//                StringJoiner joinerName = new StringJoiner(",");
//                msgNames.forEach(joinerName::add);
//
//                updateDto.setState("2");
//                updateDto.setStateName("处理中");
//                updateDto.setCurrentId(joiner.toString());
//                updateDto.setCurrentName(joinerName.toString());
//            }
//            param.setmPara(mPara);

            mPara.put("status", "return");
            updateDto.setState("0");
            updateDto.setStateName("重新提交");
            updateDto.setCurrentId(updateDto.getApplyer());
            updateDto.setCurrentName(updateDto.getApplyerName());
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                updateDto.setState("-1");
                updateDto.setStateName("已结束");
                updateDto.setCurrentId("");
                updateDto.setCurrentName("");
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("总经理室处理");
            bps.setRelaData(mPara);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);

            updateDto.setModifiedBy(bps.getUserId());
            updateDto.setModifiedTime(new Date());
            updatePpmOutsourcUpdate(updateDto);

            if (ObjectUtils.isEmpty(taskBean) || "Y".equals(taskBean.getIsFinished())) {
                //审批通过时，将变更后的信息修改入花名册
                saveOutSourcingBasic(updateDto);
            }
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
//            // 获取流程信息
//            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
//            CamundaUtils.addFlowLog(bps, taskBean);
//            CamundaExtUtils.saveNextTaskInfo(taskBean,msgIds.toArray(new String[msgIds.size()]),bps);
//
//            updateDto.setModifiedBy(bps.getUserId());
//            updateDto.setModifiedTime(new Date());
//            updatePpmOutsourcUpdate(updateDto);
//
//            if (ObjectUtils.isEmpty(taskBean) || "Y".equals(taskBean.getIsFinished())) {
//                //审批通过时，将变更后的信息修改入花名册
//                saveOutSourcingBasic(updateDto);
//            }
//            // 返回下一节点信息
//            return CamundaUtils.findNextTaskInfo(taskBean, msgIds.toArray(new String[msgIds.size()]));
        } catch (Exception e) {
            log.error("总经理室审批失败！");
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String generalManagerApproval(PpmOutsourcUpdateDto ppmOutsourcUpdateDto) {
        BpsWorkFlowDto bps = ppmOutsourcUpdateDto.getBps();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            ppmOutsourcUpdateDto.setState("0");
            ppmOutsourcUpdateDto.setStateName("重新提交");
            ppmOutsourcUpdateDto.setCurrentId(ppmOutsourcUpdateDto.getApplyer());
            ppmOutsourcUpdateDto.setCurrentName(ppmOutsourcUpdateDto.getApplyerName());
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                ppmOutsourcUpdateDto.setState("-1");
                ppmOutsourcUpdateDto.setStateName("已结束");
                ppmOutsourcUpdateDto.setCurrentId("");
                ppmOutsourcUpdateDto.setCurrentName("");
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

            ppmOutsourcUpdateDto.setModifiedBy(bps.getUserId());
            ppmOutsourcUpdateDto.setModifiedTime(new Date());
            updatePpmOutsourcUpdate(ppmOutsourcUpdateDto);

            if (ObjectUtils.isEmpty(taskBean) || "Y".equals(taskBean.getIsFinished())) {
                //审批通过时，将变更后的信息修改入花名册
                saveOutSourcingBasic(ppmOutsourcUpdateDto);
            }
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("一级部总经理室审批失败！");
            e.printStackTrace();
            throw new BizException(e);
        }
    }

    /**
     * 变更后的信息修改入花名册
     * @param ppmOutsourcUpdateDto 对象
     */
    private void saveOutSourcingBasic(PpmOutsourcUpdateDto ppmOutsourcUpdateDto){
        List<PpmOutsourcUpdateInfoDto> updateInfoDtos = ppmOutsourcUpdateDto.getUpdateInfoDtos();
        updateInfoDtos.forEach(dto ->{
            PpmOutsourcingBasicInfo basicDto = ppmOutsourcingBasicService.getBaseInfo(dto.getJobId());
            if(basicDto != null){
                basicDto.setPostId(dto.getPositionAf());
                basicDto.setVmAccount(dto.getVmAccountAf());
                basicDto.setEntranceCard(dto.getEntranceCardAf());
                basicDto.setNewContractId(dto.getNewContractIdAf());
                basicDto.setCeneralOfficeMotion(dto.getCeneralOfficeMotionAf());
                basicDto.setFaceimage(dto.getFaceimageAf());
                basicDto.setOutsourceType(dto.getOutsourceTypeAf());
                basicDto.setUserType(dto.getUserTypeAf());
                basicDto.setDeptId(dto.getDeptIdAf());
                basicDto.setSystemNo(dto.getSystemNoAf());
                basicDto.setPersonInCharge(dto.getPersonInChargeAf());
                basicDto.setEvaluationLevel(dto.getEvaluationLevelAf());
                basicDto.setLevelReason(dto.getLevelReasonAf());
                basicDto.setEvaluatorId(dto.getEvaluatorIdAf());
                basicDto.setEvaluationTime(dto.getEvaluationTime());

                PpmOutsourcingBasic outsourcingBasic = JsonUtil.jsonToClass(basicDto, PpmOutsourcingBasic.class);
                ppmOutsourcingBasicService.updatePpmOutsourcingBasicUpdate(outsourcingBasic);
            }

            PpmOutsourcingExt ext = new PpmOutsourcingExt();
            ext.setUserId(dto.getJobId());
            PpmOutsourcingExt extDto = ppmOutsourcingExtService.queryDetail(ext);
            if(extDto != null){
                extDto.setCreditReport(dto.getCreditReportAf());
                extDto.setSociaiSecrityPaymentProve(dto.getSsPaymentProveAf());
                extDto.setConfidentialityCommitment(dto.getConfidentCommitmentAf());
                extDto.setOtherFile(dto.getOtherFileAf());
                extDto.setDueDiligenceReport(dto.getDueDiligenceReportAf());
                extDto.setQtFile(dto.getQtFileAf());
                ppmOutsourcingExtService.updatePpmOutsourcingExt(extDto);
            }
        });

        log.info("信息变更完成。");
    }

    /**
     * 对提交的数据做校验
     * @param updateDto 对象
     */
    private void vaildCommitData(PpmOutsourcUpdateDto updateDto){
        List<PpmOutsourcUpdateInfoDto> updateInfoDtos = updateDto.getUpdateInfoDtos();

        //判断是否有申请人员
        if(updateInfoDtos == null || updateInfoDtos.isEmpty()){
            throw new BizException(ApiCode.FAIL, "无人员信息！");
        }

        updateInfoDtos.forEach(infoDto -> {
            PpmOutsourcUpdateInfoDto updateInfoDto = new PpmOutsourcUpdateInfoDto();
            updateInfoDto.setJobId(infoDto.getJobId());
            List<PpmOutsourcUpdateInfoDto> list =
                    ppmOutsourcUpdateInfoService.queryBaseList(updateInfoDto);
            //判断是否有已保存和提交的申请人员
            long num = list.stream()
                    .filter(obj -> !"-1".equals(obj.getState()))
                    .filter(obj -> {
                        if(StringUtils.isNotEmpty(updateDto.getInstanceid())){
                            return !updateDto.getInstanceid().equals(obj.getInstanceid());
                        }
                        return true;
                    }).count();
            if(num > 0){
                throw new BizException(ApiCode.FAIL,"该员工存在未完成的信息变更流程！");
            }

            PpmOutsourcingBasicInfo basicInfo = ppmOutsourcingBasicService.getBaseInfo(infoDto.getJobId());
            if(basicInfo == null){
                throw new BizException(ApiCode.FAIL,"外包人员员工编号异常！");
            }
            //判断是否有在支付确认中的申请人员
//            ExpenseSettlementDto expenseSettlementDto = new ExpenseSettlementDto();
//            expenseSettlementDto.setApprovalStatus(ApprovalStatusEnums.PASS.getCode());
//            expenseSettlementDto.setIsDel("0");
//            expenseSettlementDto.setProManager(basicInfo.getPersonInCharge());
//            List<ExpenseSettlementDto> expenseSettlements =
//                    expenseSettlementService.getNotFinishList(expenseSettlementDto);
//
//            StringJoiner joiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
//            if(expenseSettlements != null && !expenseSettlements.isEmpty()){
//                expenseSettlements.forEach(settlementDto -> {
//                    String payConfirm =  settlementDto.getPayConfirmId();
//                    if(StringUtil.isNotEmpty(payConfirm)){
//                        String[] payConfirms =  payConfirm.split(BusinessUtil.REGEX_COMMA);
//                        for (String str : payConfirms){
//                            joiner.add("'" + str +"'");
//                        }
//                    }
//                });
//
//                PpmOutsourcUpdateInfoDto updateInfoDto1 = new PpmOutsourcUpdateInfoDto();
//                updateInfoDto1.setJobId(infoDto.getJobId());
//                updateInfoDto1.setPayConfirmId(joiner.toString());
//
//                List<PpmOutsourcUpdateInfoDto> updateInfoDtos1 =
//                        ppmOutsourcUpdateInfoService.queryDetailExtList(updateInfoDto1,"getExpenseCaseList");
//                if(updateInfoDtos1 != null && !updateInfoDtos1.isEmpty()){
//                    throw new BizException(ApiCode.FAIL,"提交数据存在正在费用结算的人员！");
//                }
//            }

            //判断是否有在支付确认中的申请人员
//            PaymentConfirmDto paymentConfirmDto = new PaymentConfirmDto();
//            paymentConfirmDto.setApprovalStatus(ApprovalStatusEnums.PASS.getCode());
//            paymentConfirmDto.setIsDel("0");
//            paymentConfirmDto.setProManager(basicInfo.getPersonInCharge());
//            List<PaymentConfirmDto> confirmDtos = paymentConfirmService.getNotFinishList(paymentConfirmDto);
//            if(confirmDtos != null && !confirmDtos.isEmpty()){
//                confirmDtos.forEach(confirmDto ->{
//                    PpmOutsourcUpdateInfoDto updateInfoDto1 = new PpmOutsourcUpdateInfoDto();
//                    updateInfoDto1.setJobId(infoDto.getJobId());
//                    updateInfoDto1.setPayConfirmNo(confirmDto.getContractNo());
//                    List<PpmOutsourcUpdateInfoDto> updateInfoDtos1 = ppmOutsourcUpdateInfoService.queryDetailExtList(
//                        updateInfoDto1,"getConfirmCaseList");
//                    if(updateInfoDtos1 != null && !updateInfoDtos1.isEmpty()){
//                        throw new BizException(ApiCode.FAIL,"提交数据存在正在支付确认的人员！");
//                    }
//                });
//            }
        });

        //判断提交的是否有重复的申请人员
        List<String> disList =
                updateInfoDtos.stream().map(PpmOutsourcUpdateInfoDto::getJobId).distinct().collect(Collectors.toList());
        if(updateInfoDtos.size() != disList.size()){
            throw new BizException(ApiCode.FAIL, "提交数据存在重复人员！");
        }

    }
}
