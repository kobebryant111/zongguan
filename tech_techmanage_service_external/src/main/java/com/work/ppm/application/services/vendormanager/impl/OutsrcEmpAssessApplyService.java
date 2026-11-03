package com.work.ppm.application.services.vendormanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.vendormanager.OutsrcEmpAssessApplyDto;
import com.work.ppm.application.dto.vendormanager.OutsrcEmpAssessDto;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.vendormanager.OutsrcEmpAssess;
import com.work.ppm.application.model.vendormanager.OutsrcEmpAssessApply;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.vendormanager.IOutsrcEmpAssessApplyService;
import com.work.ppm.application.services.vendormanager.IOutsrcEmpAssessService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 人员考核 服务实现类
 * </p>
 *
 * @since 2023-01-03
 */

@Slf4j
@RequiredArgsConstructor
@Service("outsrcEmpAssessApplyService")
public class OutsrcEmpAssessApplyService extends BaseDaoSupper implements IOutsrcEmpAssessApplyService {

    /**
     * 实体类路径
     */
    private static String OUTSRCASSESSAPPLY= "com.work.ppm.application.model.vendormanager.OutsrcEmpAssessApply";

    private static String OUTSRCEMPASSESS= "com.work.ppm.application.model.vendormanager.OutsrcEmpAssess";
    /**
     * 员工查询
     */
    private final ICommEmployeeService commEmployeeService;

    private final IOutsrcEmpAssessService outsrcEmpAssessService;

    private  final IPpmOutsourcingBasicService ppmOutsourcingBasicService;
    /**
     * 工作流服务
     */
    private final WorkFlowHttpClientService workFlowHttpClientService;
    /**
     * 流程定义ID
     */
    private static final String FLOW_ID = "outsrcEmpAssess";
    /**
     * 流程名称
     */
    private static final String FLOW_TITLE = "外包人员考核";

    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public List<OutsrcEmpAssessApplyDto> queryPager(OutsrcEmpAssessApplyDto outsrcAssessApply, Pager pager) {
        if(!Objects.isNull(outsrcAssessApply.getDeptId())){
            outsrcAssessApply.setDeptId(
                    outsrcAssessApply.getDeptId().replace("dep",""));
        }
        Map<String, Object> map = StringUtil.objectTOMap(outsrcAssessApply, OutsrcEmpAssessApply.class);
        Map<String, Object> mapDto = StringUtil.objectTOMap(outsrcAssessApply, OutsrcEmpAssessApplyDto.class);
        mapDto.putAll(map);
        mapDto.put("approvalStatus","1"); // 审批已通过:"1"
        myBatisDao.selectPager(OUTSRCASSESSAPPLY, mapDto, pager, "queryPagerList");
        List<OutsrcEmpAssessApplyDto> dtoList =
                JsonUtil.jsonToList(pager.getList(), OutsrcEmpAssessApplyDto.class);
        dtoList.forEach(x -> {
            if ("0".equals(x.getAssessType())) {
                List<OutsrcEmpAssessDto>  listAnnual = outsrcEmpAssessService.queryListByInsId(x.getInstanceId());
                x.setTotals(listAnnual.size());
            }
            if ("1".equals(x.getAssessType())) {
                OutsrcEmpAssess outsrcEmpAssess = new OutsrcEmpAssess();
                outsrcEmpAssess.setAssessType("1");
                outsrcEmpAssess.setAssessYear(x.getAssessYear());
                outsrcEmpAssess.setIsDel("0");
                outsrcEmpAssess.setPrjMgrCode(x.getApplier());
                List<OutsrcEmpAssessDto>  listYear = outsrcEmpAssessService.queryList(outsrcEmpAssess);
                x.setTotals(listYear.size());
            }
            x.setDeptName(BusinessUtil.getDeptNameValue(x.getDeptName(),1));
        });
        return dtoList;
    }

    @Override
    public List<OutsrcEmpAssessApply> queryList(OutsrcEmpAssessApply outsrcAssessApply) {
        return myBatisDao.selectListT(OUTSRCASSESSAPPLY, outsrcAssessApply, "getList");
    }

    @Override
    public OutsrcEmpAssessApplyDto queryDetailsById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        OutsrcEmpAssessApplyDto dto = myBatisDao.selectClass(OUTSRCASSESSAPPLY, map, "selectOneById");
        if ("0".equalsIgnoreCase(dto.getAssessType())) {
            List<OutsrcEmpAssessDto> outsrcEmpAssessList = outsrcEmpAssessService.queryListByInsId(dto.getInstanceId());
            outsrcEmpAssessList.forEach(outsrcEmpAssessDto ->
                    outsrcEmpAssessDto.setDeptName(BusinessUtil.getDeptNameValue(outsrcEmpAssessDto.getDeptName(),1)));
            dto.setOutsrcEmpAssessList(outsrcEmpAssessList);
        }
        if ("1".equalsIgnoreCase(dto.getAssessType())) {
            OutsrcEmpAssess outsrcEmpAssess = new OutsrcEmpAssess();
            outsrcEmpAssess.setPrjMgrCode(dto.getApplier());
            outsrcEmpAssess.setAssessYear(dto.getAssessYear());
            outsrcEmpAssess.setAssessType("1");
            outsrcEmpAssess.setIsDel("0");
            List<OutsrcEmpAssessDto> outsrcEmpAssessList = outsrcEmpAssessService.queryList(outsrcEmpAssess);

            outsrcEmpAssessList.forEach(outsrcEmpAssessDto ->
                    outsrcEmpAssessDto.setDeptName(BusinessUtil.getDeptNameValue(outsrcEmpAssessDto.getDeptName(),1)));
            dto.setOutsrcEmpAssessList(outsrcEmpAssessList);
        }

        return dto;
    }

    @Override
    public OutsrcEmpAssessApplyDto queryDetailsByInsId(String instanceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("instanceId", instanceId);
        OutsrcEmpAssessApply outsrcAssessApply
                = myBatisDao.selectClass(OUTSRCASSESSAPPLY, map, "selectOneByInstanceId");
        OutsrcEmpAssessApplyDto dto = JsonUtil.jsonToClass(outsrcAssessApply, OutsrcEmpAssessApplyDto.class);
        List<OutsrcEmpAssessDto> list = outsrcEmpAssessService.queryListByInsId(instanceId);
        list.forEach(obj -> obj.setDeptName(BusinessUtil.getDeptNameValue(obj.getDeptName(),1)));
        dto.setOutsrcEmpAssessList(list);
        return dto;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOutsrcAssessApply(OutsrcEmpAssessApplyDto apply) {
        apply.setApprovalStatus("1"); // 年度考核无需审批
        List<OutsrcEmpAssessDto>  dtoList = apply.getOutsrcEmpAssessList();
        dtoList.forEach(x->{
            OutsrcEmpAssess oea = new OutsrcEmpAssess();
            oea.setUserCode(x.getUserCode());
            oea.setIsDel("0");
            oea.setAssessYear(x.getAssessYear());
            oea.setAssessType("1"); // 年度考核
            List<OutsrcEmpAssessDto> empYear= outsrcEmpAssessService.queryList(oea);
            x.setAssessType("1");
            if (!Objects.isNull(empYear) && empYear.size()>0) {
                x.setId(empYear.get(0).getId());
                outsrcEmpAssessService.updateOutsrcEmpAssess(x);
            } else {
                outsrcEmpAssessService.addOutsrcingAssess(x);
            }
        });

        OutsrcEmpAssessApply oea = new OutsrcEmpAssessApply();
        oea.setApplier(apply.getApplier())
                .setAssessYear(apply.getAssessYear())
                .setAssessType(apply.getAssessType())
                .setIsDel("0");

        List<OutsrcEmpAssessApply> listYearApply = this.queryList(oea);
        if ((!Objects.isNull(listYearApply)) && listYearApply.size() > 0){
            apply.setId(listYearApply.get(0).getId());
            return this.updateOutsrcEmpAssessApply(apply);
        }
        Long key = super.getNextSequence(OutsrcEmpAssessApply.class);
        apply.setId(key);
        return myBatisDao.executionPojo(OUTSRCASSESSAPPLY, apply, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOutsrcAssessApplyCus(OutsrcEmpAssessApplyDto outsrcAssessApply) {
        return myBatisDao.executionPojo(OUTSRCASSESSAPPLY, outsrcAssessApply, IMyBatisDao.SQL.INSTER, "addPojo");
    }
    @Override
    public int updateOutsrcEmpAssessApply(OutsrcEmpAssessApply outsrcAssessApply) {
        return myBatisDao.executionPojo(OUTSRCASSESSAPPLY, outsrcAssessApply, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int removeFlow(OutsrcEmpAssessApply outsrcEmpAssessApply) {
        outsrcEmpAssessApply.setIsDel(LogicEnums.INVALID.getCode());
        OutsrcEmpAssess outsrcEmpAssess = JsonUtil.jsonToClass(outsrcEmpAssessApply, OutsrcEmpAssess.class);
        myBatisDao.executionPojo(OUTSRCASSESSAPPLY,
                outsrcEmpAssessApply, IMyBatisDao.SQL.UPDATE, "updateByInstanceId");
        myBatisDao.executionPojo(OUTSRCEMPASSESS, outsrcEmpAssess, IMyBatisDao.SQL.UPDATE, "updateByInstanceId");
        // 删除流程相关数据
        CamundaUtils.delProjectFlow(outsrcEmpAssessApply.getInstanceId());
        CamundaUtils.terminateProcess(outsrcEmpAssessApply.getInstanceId());
        return 1;
    }

    /**
     * 对提交的数据做校验
     * @param apply 对象
     */
    private void vaildCommitData(OutsrcEmpAssessApplyDto apply,BpsWorkFlowDto bps){
        if(StringUtils.isEmpty(apply.getAssessYear())){
            throw new BizException(ApiCode.PARAMETER_ERROR, "请填写考核年份！");
        }

        if("0".equals(apply.getAssessType()) && StringUtils.isEmpty(apply.getAssessAnnual())){
            throw new BizException(ApiCode.PARAMETER_ERROR, "请填写考核季度！");
        }

        List<OutsrcEmpAssessDto> personsList = apply.getOutsrcEmpAssessList();
        if(CollectionUtils.isEmpty(personsList)){
            throw new BizException(ApiCode.PARAMETER_ERROR, "无人员信息无法发起流程！");
        }

//        StringJoiner joiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
        personsList.forEach(person ->{
            if(StringUtils.isEmpty(person.getUserCode())){
                throw new BizException(ApiCode.PARAMETER_ERROR, "请填写姓名！");
            }
        });

        // 存在当前季度人员考核时
        if (StringUtils.isBlank(apply.getInstanceId())) {
            StringJoiner stringJoiner = new StringJoiner(", ");
            personsList.forEach(x-> {
                OutsrcEmpAssess outsrcEmpAssess = new OutsrcEmpAssess();
                outsrcEmpAssess.setAssessType("0")
                        .setAssessYear(x.getAssessYear())
                        .setAssessAnnual(x.getAssessAnnual())
                        .setIsDel("0")
                        .setUserCode(x.getUserCode());
                List<OutsrcEmpAssessDto>  list = outsrcEmpAssessService.queryList(outsrcEmpAssess);
                if ((!Objects.isNull(list)) && list.size() > 0 ) {
                    stringJoiner.add(list.get(0).getUserName());
                }
            });
            if (stringJoiner.length() > 0) {
                throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "人员考核重复发起： " + stringJoiner);
            }
        }

        //判断提交的是否有重复的申请人员
        Map<Object, Long> mapGroup = apply.getOutsrcEmpAssessList().stream()
                .collect(Collectors.groupingBy(OutsrcEmpAssess::getUserCode,Collectors.counting()));
        Stream<Object> stream = mapGroup.entrySet().stream()
                .filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey);
        stream.forEach(x -> {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "提交的人员列表有重复人名 " + x);
        });

        String deptId = commEmployeeService.getDeptNo(bps.getUserId()).replaceAll("dep", "");
        CommEmployeeDto workManager = UsersUtil.getTwoWorkManager(deptId);
        // 审批人是否异常
        if (StringUtils.isBlank(workManager.getBadge())) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "当前用户无二级部经理");
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String approvalCirculate(OutsrcEmpAssessApplyDto apply, BpsWorkFlowDto bps) {

        //校验
        vaildCommitData(apply,bps);
        //考核人员列表
        List<OutsrcEmpAssessDto> personsList = apply.getOutsrcEmpAssessList();

        ProcessBean processBean = null;
        try {
            //流转到二级部门经理
            String deptId = commEmployeeService.getDeptNo(bps.getUserId()).replaceAll("dep", "");
            CommEmployeeDto workManager = UsersUtil.getTwoWorkManager(deptId);

            // 判断流程实例是否存在，不存在说明是初始提交
            if (StringUtils.isBlank(apply.getInstanceId())) {
                // 添加外包准入流程记录
                Long key = super.getNextSequence(OutsrcEmpAssessApply.class);
                apply.setId(key);
                apply.setFlowId(FLOW_ID);
                apply.setCreateUser(bps.getUserId());
                this.addOutsrcAssessApplyCus(apply);
                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(String.valueOf(apply.getId()));
                // 流程参数
                Map mPara = new HashMap();
                mPara.put("userId", workManager.getBadge());
                processInfo.setmPara(mPara);// 流程变量
                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(processBean.getProcessInstanceId());
                // 回填流程实例编码
                apply.setInstanceId(processBean.getProcessInstanceId());
                this.updateOutsrcEmpAssessApply(apply);
                // 添加流程实例id
                personsList.forEach(item ->
                        item.setInstanceId(apply.getInstanceId())
                            .setFlowId(FLOW_ID)
                            .setAssessType(apply.getAssessType())
                            .setCreateUser(bps.getUserId())
                );
                outsrcEmpAssessService.batchAddOutsrcEmpAssess(personsList);
                // 日志处理
                bps.setProjectNo(String.valueOf(apply.getId()));
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setApplydesc("外包人员考核申请发起");
                bps.setRelaData(mPara);
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessInstName("【" + bps.getUserName() + DateUtil.dateToNoBar(new Date()) + "】" + "外包人员考核");
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setSuggest("提交申请");
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            } else {
                // 新增或者更新
                this.updateOutsrcEmpAssessApply(apply);
                // 删除当前流程人员历时记录
                OutsrcEmpAssess outsrcEmpAssess = new OutsrcEmpAssess();
                outsrcEmpAssess.setInstanceId(apply.getInstanceId());
                outsrcEmpAssess.setIsDel("1");
                outsrcEmpAssessService.updateByInsId(outsrcEmpAssess);
                // 当前季度-人员考核重复发起的验证
//                this.validateDuplicate(apply);
                // 新增记录
                apply.getOutsrcEmpAssessList().stream().forEach(x-> {
                    x.setAssessType(apply.getAssessType());
                    x.setId(null);
                    x.setInstanceId(apply.getInstanceId());
                    x.setFlowId(FLOW_ID);
                    outsrcEmpAssessService.addOutsrcingAssess(x);
                });
                // 任务参数
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
                bps.setProcessInstId(apply.getInstanceId());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setSuggest("重新提交");
                bps.setApplydesc("外包人员考核申请发起");
                bps.setProcessInstDesc(FLOW_TITLE);
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(apply.getInstanceId());
                CamundaUtils.addFlowLogCus(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            }
        } catch (Exception e) {
            CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            log.error("外包人员考核申请发起流程失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String approvedByMgrL2(OutsrcEmpAssessApplyDto apply, BpsWorkFlowDto bps) {
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);  // 租户
            param.setTaskId(bps.getTaskId());               // 任务id
            param.setUserId(bps.getUserId());               // 处理人
            param.setComment(bps.getSuggest());             // 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                apply.setApprovalStatus("1"); // 记录审批状态
                this.updateOutsrcEmpAssessApply(apply);
                apply.getOutsrcEmpAssessList().stream().forEach(x -> {
                    outsrcEmpAssessService.updateOutsrcEmpAssess(x);
                });
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);

            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(apply.getInstanceId());
            bps.setApplydesc("二级部经理审批");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(apply.getInstanceId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("二级部经理审批失败：{}", e);
            throw new BizException(e);
        }
    }

    /**
     * 年度考核生成<auto>
     */
    @Override
    @Transactional("defaultTx")
    public void cronYearAssess() {
        String str = DateUtil.lastDayOfPreviousQuarter();
        OutsrcEmpAssess outsrcEmpAssess = new OutsrcEmpAssess();
        outsrcEmpAssess.setAssessYear(str.substring(0,4));
        List<OutsrcEmpAssessApplyDto> yearApplyList= outsrcEmpAssessService.getYearAssessApply(outsrcEmpAssess);
        // 人员年度考核清单
        List<OutsrcEmpAssessDto> dtoList = outsrcEmpAssessService.getYearAssess(outsrcEmpAssess);
        dtoList.stream().forEach(x -> {
            OutsrcEmpAssess oea = new OutsrcEmpAssess();
            oea.setUserCode(x.getUserCode());
            oea.setIsDel("0");
            oea.setAssessYear(x.getAssessYear());
            oea.setAssessType("1"); // 年度考核
            List<OutsrcEmpAssessDto> empYear= outsrcEmpAssessService.queryList(oea);
            if (!Objects.isNull(empYear) && empYear.size()>0) {
                x.setId(empYear.get(0).getId());
                outsrcEmpAssessService.updateOutsrcEmpAssess(x);
            } else {
                outsrcEmpAssessService.addOutsrcingAssess(x);
            }
        });
        // yearApply记录
        yearApplyList.stream().forEach(x -> {
            OutsrcEmpAssessApply oeap = new OutsrcEmpAssessApply();
            oeap.setApplier(x.getApplier());
            oeap.setIsDel("0");
            oeap.setAssessYear(x.getAssessYear());
            oeap.setAssessType("1"); // 年度考核
            List<OutsrcEmpAssessApply> empYear= this.queryList(oeap);
            if (!Objects.isNull(empYear) && empYear.size()>0) {
                x.setId(empYear.get(0).getId());
                this.updateOutsrcEmpAssessApply(x);
            } else {
                Long key = super.getNextSequence(OutsrcEmpAssessApply.class);
                x.setId(key);
                this.addOutsrcAssessApplyCus(x);
            }
        });
    }

    /**
     * 季度考核生成<auto>
     */
    @Override
    @Transactional("defaultTx")
    public void cronAnnualAssess() {
        // 上一季度的最后一天
        String lastDay = DateUtil.lastDayOfPreviousQuarter();
        // 取得项目经理列表
        PpmOutsourcingBasicInfo ppmOutsourcingBasicInfo = new PpmOutsourcingBasicInfo();
        ppmOutsourcingBasicInfo.setOutsourceType("1");
        //工号/名称，部门编号/名称，
        List<PpmOutsourcingBasicInfo> mgrList = ppmOutsourcingBasicService.queryMgrList(ppmOutsourcingBasicInfo);
        // 发起季度考核通知
        mgrList.forEach(x -> {
            // 测试用，提交时删除
//            if (!"213188".equals(x.getPersonInCharge())){
//                return;
//            }

            // 生成季度考核申请
            OutsrcEmpAssessApplyDto apply = new OutsrcEmpAssessApplyDto();
            apply.setApplier(x.getPersonInCharge());
            apply.setDeptId(x.getDeptId());
            apply.setAssessType("0"); // 季度考核
            apply.setAssessYear(lastDay.substring(0,4));
            apply.setApprovalStatus("0");
            apply.setPrjMgrName(x.getPersonInChargename());
            apply.setDeptName(BusinessUtil.getDeptNameValue(x.getDepname(),1));
            String annual;
            switch (lastDay.substring(4,6)){
                case "12":
                    annual = "4";
                    break;
                case "09":
                    annual = "3";
                    break;
                case "06":
                    annual = "2";
                    break;
                case "03":
                    annual = "1";
                    break;
                default:
                    annual = "";
            }
            apply.setAssessAnnual(annual);
            // 外包人员 {外包方式->人力}
            OutsrcEmpAssess outsrcEmpAssess = new OutsrcEmpAssess();
            outsrcEmpAssess.setDeptId(x.getDeptId());
            outsrcEmpAssess.setPrjMgrCode(x.getPersonInCharge());
            List<PpmOutsourcingBasicInfo> basicInfoList =
                    outsrcEmpAssessService.getPersonListByMgr(outsrcEmpAssess,apply);
            List listAssess = new ArrayList<>();
            basicInfoList.stream().forEach(y->{
                OutsrcEmpAssessDto dto = new OutsrcEmpAssessDto();
                dto.setUserCode(y.getJobId());
                dto.setUserName(y.getUserName());
                dto.setVendorCode(y.getOutsourceNo());
                dto.setVendorName(y.getOutsourcname());
                dto.setAssessYear(apply.getAssessYear());
                dto.setAssessAnnual(apply.getAssessAnnual());
                dto.setAssessLevel("10"); // 默认 A
                dto.setEvaluation("待评");
                dto.setPosition(y.getPostId());
                dto.setDeptId(y.getDeptId());
                dto.setPrjMgrCode(apply.getApplier());
                dto.setPrjMgrName(x.getPersonInChargename());
                dto.setDeptName(apply.getDeptName());
                dto.setAssessType("0");
                listAssess.add(dto);
            });
            if (listAssess.size()==0) {return;}
            // 添加人员列表
            apply.setOutsrcEmpAssessList(listAssess);
            // 已有人员考核发起
            if (this.checkDuplicate(apply) > 0) {return;}
            BpsWorkFlowDto bps = apply.getBps();
            if (ObjectUtils.isEmpty(bps)) {
                bps = new BpsWorkFlowDto();
            }
            bps.setUserId(apply.getApplier());
            bps.setUserName(x.getPersonInChargename());
            bps.setDepId(apply.getDeptId().replace("dep", ""));
            this.autoDraft(apply,bps);
        });

    }

    /**
     * 定时任务 - 人员重复查询
     */
    public int checkDuplicate ( OutsrcEmpAssessApplyDto apply) {
        // 当前季度-人员考核重复发起
        List<OutsrcEmpAssessDto> dtoList = apply.getOutsrcEmpAssessList();
        StringJoiner stringJoiner = new StringJoiner(", ");
        dtoList.stream().forEach(x -> {
            OutsrcEmpAssess outsrcEmpAssess = new OutsrcEmpAssess();
            outsrcEmpAssess.setAssessType("0"); // 季度考核
            outsrcEmpAssess.setAssessYear(x.getAssessYear());
            outsrcEmpAssess.setAssessAnnual(x.getAssessAnnual());
            outsrcEmpAssess.setIsDel("0");
            outsrcEmpAssess.setUserCode(x.getUserCode());
            List<OutsrcEmpAssessDto> list = outsrcEmpAssessService.queryList(outsrcEmpAssess);
            if ((!Objects.isNull(list)) && list.size() > 0) {
                stringJoiner.add(list.get(0).getUserName());
            }
        });
        return stringJoiner.length();
    }
    /**
     * 手工提交 - 人员重复查询
     */
    public void validateDuplicate ( OutsrcEmpAssessApplyDto apply) {
        // 当前季度-人员考核重复发起
        List<OutsrcEmpAssessDto> dtoList = apply.getOutsrcEmpAssessList();
        StringJoiner stringJoiner = new StringJoiner(", ");
        dtoList.forEach(x -> {
            OutsrcEmpAssess outsrcEmpAssess = new OutsrcEmpAssess();
            outsrcEmpAssess.setAssessType("0");
            outsrcEmpAssess.setAssessYear(x.getAssessYear());
            outsrcEmpAssess.setAssessAnnual(x.getAssessAnnual());
            outsrcEmpAssess.setIsDel("0");
            outsrcEmpAssess.setUserCode(x.getUserCode());
            List<OutsrcEmpAssessDto> list = outsrcEmpAssessService.queryList(outsrcEmpAssess);
            if ((!Objects.isNull(list)) && list.size() > 0
                    && !(Objects.equals(apply.getInstanceId(),list.get(0).getInstanceId()))) {
                stringJoiner.add(list.get(0).getUserName());
            }
        });
        if (stringJoiner.length() > 0) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "人员考核重复发起： " + stringJoiner);
        }
    }

    /**
     * 手工提交 - 草稿
     */
    @Transactional(rollbackFor = Exception.class)
    public OutsrcEmpAssessApplyDto draft(OutsrcEmpAssessApplyDto apply, BpsWorkFlowDto bps) {

        if (Objects.isNull(apply) || Objects.isNull(apply.getOutsrcEmpAssessList())) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "参数不为空");
        }
        for (OutsrcEmpAssessDto dto : apply.getOutsrcEmpAssessList()) {
            if (Objects.isNull(dto.getUserCode()) || "".equals(dto.getUserCode())) {
                throw new BizException(ApiCode.PARAMETER_ERROR, "userCode不为空");
            }
        }
        // 草稿的人员名称有重复
        Map<Object, Long> mapGroup = apply.getOutsrcEmpAssessList().stream()
                .collect(Collectors.groupingBy(OutsrcEmpAssess::getUserCode,Collectors.counting()));
        Stream<Object> stream = mapGroup.entrySet().stream()
                .filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey);
        stream.forEach(x -> {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "提交的人员列表有重复人名 " + x);
        });
        // 点击代办
        if (null != apply.getId()) {
            // 更新申请主表
            this.updateOutsrcEmpAssessApply(apply);
            // 删除当前流程人员历时记录
            OutsrcEmpAssess outsrcEmpAssess = new OutsrcEmpAssess();
            outsrcEmpAssess.setInstanceId(apply.getInstanceId());
            outsrcEmpAssess.setIsDel("1");
            outsrcEmpAssessService.updateByInsId(outsrcEmpAssess);
            // 当前季度-人员考核重复发起的验证
            this.validateDuplicate(apply);
            // 新增记录
            apply.getOutsrcEmpAssessList().stream().forEach(x-> {
                x.setAssessType(apply.getAssessType());
                x.setId(null);
                x.setInstanceId(apply.getInstanceId());
                x.setFlowId(apply.getFlowId());
                outsrcEmpAssessService.addOutsrcingAssess(x);
            });
            return apply;
        }
        // 当前季度-人员考核重复发起的验证
        this.validateDuplicate(apply);
        try {
            ProcessBean processBean;
            // 添加支付申请
            Long key = super.getNextSequence(OutsrcEmpAssessApply.class);
            apply.setId(key);
            apply.setFlowId(FLOW_ID);
            apply.setCreateUser(bps.getUserId());
            this.addOutsrcAssessApplyCus(apply);
            // 流程实例参数设置
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(apply.getApplier());// 提交人id
            processInfo.setStartUserId(apply.getApplier());// 流程发起人id
            processInfo.setStartUserName(apply.getPrjMgrName()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(key));
            // 开始并提交流程
            processBean = workFlowHttpClientService.startProcess(processInfo);
            // 流程实例id
            apply.setInstanceId(processBean.getProcessInstanceId());
            this.updateOutsrcEmpAssessApply(apply);
            apply.getOutsrcEmpAssessList().stream().forEach(x -> {
                x.setInstanceId(apply.getInstanceId());
                x.setAssessType(apply.getAssessType());
                x.setFlowId(FLOW_ID);
                outsrcEmpAssessService.addOutsrcingAssess(x);
            });
            // 日志处理
            bps.setProjectNo(String.valueOf(apply.getId()));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setApplydesc("外包人员考核申请发起");
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());
            bps.setProcessInstName("【" + bps.getUserName() + DateUtil.dateToNoBar(new Date()) + "】" + "外包人员考核申请");
            bps.setProcessInstDesc(FLOW_TITLE);
            bps.setSuggest("提交申请");
            bps.setToDB(false);
            CamundaUtils.addFlowLog(bps, null);
            return apply;
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    /**
     * 定时任务 - 草稿
     */
    @Transactional(rollbackFor = Exception.class)
    public OutsrcEmpAssessApplyDto autoDraft(OutsrcEmpAssessApplyDto apply, BpsWorkFlowDto bps) {
        try {
            ProcessBean processBean;
            // 添加支付申请
            Long key = super.getNextSequence(OutsrcEmpAssessApply.class);
            apply.setId(key);
            apply.setFlowId(FLOW_ID);
            apply.setCreateUser(bps.getUserId());
            this.addOutsrcAssessApplyCus(apply);
            // 流程实例参数设置
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(apply.getApplier());// 提交人id
            processInfo.setStartUserId(apply.getApplier());// 流程发起人id
            processInfo.setStartUserName(apply.getPrjMgrName()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(key));
            // 开始并提交流程
            processBean = workFlowHttpClientService.startProcess(processInfo);
            // 流程实例id
            apply.setInstanceId(processBean.getProcessInstanceId());
            this.updateOutsrcEmpAssessApply(apply);
            apply.getOutsrcEmpAssessList().stream().forEach(x -> {
                x.setInstanceId(apply.getInstanceId());
                x.setAssessType(apply.getAssessType());
                x.setFlowId(FLOW_ID);
                outsrcEmpAssessService.addOutsrcingAssess(x);
            });
            // 日志处理
            bps.setProjectNo(String.valueOf(apply.getId()));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setApplydesc("外包人员考核申请发起");
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());
            bps.setProcessInstName("【" + bps.getUserName() + DateUtil.dateToNoBar(new Date()) + "】" + "外包人员考核申请");
            bps.setProcessInstDesc(FLOW_TITLE);
            bps.setSuggest("提交申请");
            bps.setToDB(false);
            CamundaUtils.addFlowLog(bps, null);
            return apply;
        } catch (Exception e) {
            throw new BizException(e);
        }
    }
}