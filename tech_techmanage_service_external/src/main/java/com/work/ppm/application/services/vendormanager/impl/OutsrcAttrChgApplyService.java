package com.work.ppm.application.services.vendormanager.impl;

import com.google.common.collect.Lists;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqAnnualVacationDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.vendormanager.OutsrcAttrChgApplyDto;
import com.work.ppm.application.dto.vendormanager.OutsrcingAttrChgDto;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.vendormanager.OutsrcingAttrChg;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeOverinfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqAnnualVacationService;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.application.services.vendormanager.IOutsrcAttrChgApplyService;
import com.work.ppm.application.services.vendormanager.IOutsrcingAttrChgService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.CamundaExtUtils;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.UsersUtil;
import com.tansun.workflow.model.PostInfo;
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
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.model.vendormanager.OutsrcAttrChgApply;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * <p>
 * 人员属性变更 服务实现类
 * </p>
 *
 * @since 2022-12-08
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class OutsrcAttrChgApplyService extends BaseDaoSupper implements IOutsrcAttrChgApplyService {

    /**
     * 实体类路径
     */
    private static String OUTSRCATTRCHGAPPLY= "com.work.ppm.application.model.vendormanager.OutsrcAttrChgApply";
    /**
     * 实体类路径
     */
    private static String OUTSRCATTRCHG= "com.work.ppm.application.model.vendormanager.OutsrcingAttrChg";
    /**
     * 员工查询
     */
    private final ICommEmployeeService commEmployeeService;
    /**
     * 工作流服务
     */
    private final WorkFlowHttpClientService workFlowHttpClientService;

    private final IPpmKqRecordService ppmKqRecordService;

    private final IPpmKqAnnualVacationService kqAnnualVacationService;
    /**
     * 流程定义ID
     */
    private static final String FLOW_ID = "outsrcingAttrChg";
    /**
     * 流程名称
     */
    private static final String FLOW_TITLE = "人员属性变更";

    /**
     * 人员属性变更详情表
     */
    private final IOutsrcingAttrChgService outsrcingAttrChgService;

    private IPpmIntervieweeOverinfoService ppmIntervieweeOverinfoService;

    /**
     * 外包花名册
     */
    private final IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    private final IAttatchManagementService attatchManagementService;

    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public String getOrgName(String orgCode) {
        HashMap map = new HashMap<>();
        map.put("orgCode", orgCode);
        return myBatisDao.selectClass(OUTSRCATTRCHGAPPLY, map, "selectOrgName");
    }

    @Override
    public OutsrcAttrChgApplyDto queryDetailsByInsId(String instanceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("instanceId", instanceId);
        OutsrcAttrChgApply outsrcAttrChgApply =
                myBatisDao.selectClass(OUTSRCATTRCHGAPPLY, map, "selectOneByInstanceId");
        OutsrcAttrChgApplyDto dto = JsonUtil.jsonToClass(outsrcAttrChgApply, OutsrcAttrChgApplyDto.class);
        List<OutsrcingAttrChgDto> attrChgDtoList = outsrcingAttrChgService.queryListByInsId(dto.getInstanceId());
        attrChgDtoList.forEach(attrChgDto ->{
            attrChgDto.setDeptName(BusinessUtil.getDeptNameValue(attrChgDto.getDeptName(),1));
        });
        dto.setOutsrcingAttrChgList(attrChgDtoList);
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOutsrcAttrChgApply(OutsrcAttrChgApplyDto outsrcAttrChgApply) {
        return myBatisDao.executionPojo(OUTSRCATTRCHGAPPLY, outsrcAttrChgApply, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public int updateOutsrcAttrChgApply(OutsrcAttrChgApply outsrcAttrChgApply) {
        return myBatisDao.executionPojo(OUTSRCATTRCHGAPPLY, outsrcAttrChgApply, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeFlow(OutsrcAttrChgApply outsrcAttrChgApply) {
        //校验
        List<OutsrcingAttrChgDto> list = outsrcingAttrChgService.queryListByInsId(outsrcAttrChgApply.getInstanceId());
        boolean flag = true;
        StringJoiner stringJoiner = new StringJoiner(",");
        for(OutsrcingAttrChgDto dto : list){
            PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
            basic.setJobId(dto.getUserCode());
            PpmOutsourcingBasic info = ppmOutsourcingBasicService.queryDetail(basic);
            Long zbyaOld = info.getCeneralOfficeMotion();

            if(!Objects.equals(dto.getAttachId(),zbyaOld) &&
                    !attatchManagementService.judgeMotionNumber(dto.getAttachId(),zbyaOld)){
                stringJoiner.add(dto.getUserName());
                flag = false;
            }
        }

        if(!flag){
            throw new BizException(ApiCode.FAIL, stringJoiner+"对应原办议案总数为0或已无可用人数！");
        }

        outsrcAttrChgApply.setIsDel(LogicEnums.INVALID.getCode());
        OutsrcingAttrChg outsrcingAttrChg = JsonUtil.jsonToClass(outsrcAttrChgApply, OutsrcingAttrChg.class);
        myBatisDao.executionPojo(OUTSRCATTRCHGAPPLY, outsrcAttrChgApply, IMyBatisDao.SQL.UPDATE, "updateByInstanceId");
        myBatisDao.executionPojo(OUTSRCATTRCHG, outsrcingAttrChg, IMyBatisDao.SQL.UPDATE, "updateByInstanceId");

        //将所有的现存数据对应的总办议案人数还原
        list.forEach(dto ->{
            PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
            basic.setJobId(dto.getUserCode());
            PpmOutsourcingBasic info = ppmOutsourcingBasicService.queryDetail(basic);
            Long zbyaOld = info.getCeneralOfficeMotion();
            if (!Objects.equals(dto.getAttachId(),zbyaOld)){
                attatchManagementService.calMotionNumber(dto.getAttachId(),zbyaOld);
            }
        });
        // 删除流程相关数据
        CamundaUtils.delProjectFlow(outsrcAttrChgApply.getInstanceId());
        CamundaUtils.terminateProcess(outsrcAttrChgApply.getInstanceId());
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String approvalCirculate(OutsrcAttrChgApplyDto apply, BpsWorkFlowDto bps) {
        // 页面提交的数据
        List<OutsrcingAttrChgDto> personsList = apply.getOutsrcingAttrChgList();

        //提交校验
        vaildCommitData(apply);

        ProcessBean processBean = null;
        // 流程标题 -> 人员名称拼接
        StringBuilder stringBuilder = new StringBuilder();
        personsList.stream().forEach(x->
                stringBuilder.append(x.getUserName()).append(",")
        );
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        try {
            //流转到二级部门经理
            String deptId = commEmployeeService.getDeptNo(bps.getUserId()).replaceAll("dep", "");
            CommEmployeeDto workManager = UsersUtil.getTwoWorkManager(deptId);
            // 审批人是否异常
            if (StringUtils.isBlank(workManager.getBadge())) {
                throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "当前用户无二级部经理");
            }
            // 判断流程实例是否存在，不存在说明是初始提交
            if (StringUtils.isBlank(apply.getInstanceId())) {
                // 添加外包准入流程记录
                Long key = super.getNextSequence(OutsrcAttrChgApply.class);
                apply.setId(key);
                apply.setFlowId(FLOW_ID);
                apply.setCreateUser(bps.getUserId());
                // 人员属性变更申请
                this.addOutsrcAttrChgApply(apply);
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
                this.updateOutsrcAttrChgApply(apply);
                // 属性变更人员多条记录 -> 添加流程实例id
                personsList.stream().forEach(item ->
                        item.setInstanceId(apply.getInstanceId()).setCreateUser(bps.getUserId())
                );
                // 属性变更人员信息
                outsrcingAttrChgService.batchAddOutsrcingAttrChg(personsList);
                // 日志处理
                bps.setProjectNo(String.valueOf(apply.getId()));
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setApplydesc("人员属性变更申请发起");
                bps.setRelaData(mPara);
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessInstName("【" + stringBuilder + DateUtil.dateToNoBar(new Date()) + "】" + "人员属性变更");
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setSuggest("提交申请");
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);

                personsList.stream().forEach(x-> {
                    PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
                    basic.setJobId(x.getUserCode());
                    PpmOutsourcingBasic info = ppmOutsourcingBasicService.queryDetail(basic);
                    Long zbyaOld = info.getCeneralOfficeMotion();
                    attatchManagementService.calMotionNumber(zbyaOld, x.getAttachId());
                });
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            } else {
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
                // 更新记录
                this.updateOutsrcAttrChgApply(apply);


                // 日志处理
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(apply.getInstanceId());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setProcessInstName("【" + stringBuilder + DateUtil.dateToNoBar(new Date()) + "】" + "人员属性变更");
                bps.setSuggest("重新提交");
                bps.setApplydesc("人员属性变更申请发起");
                bps.setProcessInstDesc(FLOW_TITLE);
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(apply.getInstanceId());
                CamundaUtils.addFlowLogCus(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                //重新提交去对总办议案人数进行更改
                personsList.forEach(x-> {
                    //存在id视为原有数据 重新提交的 前总办议案=该数据对应库中的总办议案 后总办议案=this.总办议案
                    if(!Objects.isNull(x.getId())){ // id不为空
                        OutsrcingAttrChgDto attrChgDto = outsrcingAttrChgService.queryDetailsById(x.getId());
                        if(attrChgDto != null
                                && x.getUserCode().equalsIgnoreCase(attrChgDto.getUserCode())
                                && !x.getAttachId().equals(attrChgDto.getAttachId())){
                            attatchManagementService.calMotionNumber(attrChgDto.getAttachId(), x.getAttachId());
                        }
                        // 同一条记录（相同id）,usercode被更改
                        if(attrChgDto != null && !x.getUserCode().equalsIgnoreCase(attrChgDto.getUserCode())){
                            PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
                            basic.setJobId(x.getUserCode());
                            PpmOutsourcingBasic info = ppmOutsourcingBasicService.queryDetail(basic);
                            Long basicId = info.getCeneralOfficeMotion();
                            attatchManagementService.calMotionNumber(basicId, x.getAttachId());
                        }
                    }else {
                        // 总办议案计算
                        this.calZBYA(x);
                    }
                });
                List<OutsrcingAttrChgDto> listOld2 = outsrcingAttrChgService
                        .queryListByInsId(apply.getInstanceId());
                // 新增或者更新
                personsList.stream().forEach(x -> {
                    for(OutsrcingAttrChgDto attrChgDto :listOld2) {
                        if (attrChgDto.getUserCode().equalsIgnoreCase(x.getUserCode())) {
                            x.setId(attrChgDto.getId());
                            break;
                        }
                    }
                    if (Objects.isNull(x.getId())) {
                        outsrcingAttrChgService.addOutsrcingAttrChg(x);
                    } else {
                        outsrcingAttrChgService.updateOutsrcingAttrChg(x);
                    }
                });
                // 删除
                Set<String> userCodeSet = personsList.stream()
                        .map(OutsrcingAttrChgDto::getUserCode).collect(Collectors.toSet());
                listOld2.stream().forEach(x -> {
                    if (!userCodeSet.contains(x.getUserCode())) {
                        x.setIsDel("1");
                        outsrcingAttrChgService.updateOutsrcingAttrChg(x);
                        PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
                        basic.setJobId(x.getUserCode());
                        PpmOutsourcingBasic info = ppmOutsourcingBasicService.queryDetail(basic);
                        Long zbyaOld = info.getCeneralOfficeMotion();
                        attatchManagementService.calMotionNumber(x.getAttachId(),zbyaOld);
                    }
                });
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            }
        } catch (Exception e) {
            CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            log.error("人员属性申请发起流程失败：{}", e);
            throw new BizException(e);
        }
    }

    // 计算总办议案
    public void calZBYA(OutsrcingAttrChgDto dto){
        PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
        basic.setJobId(dto.getUserCode());
        PpmOutsourcingBasic info = ppmOutsourcingBasicService.queryDetail(basic);
        Long beforeId = info.getCeneralOfficeMotion();
        Boolean flag = false;
        List<OutsrcingAttrChgDto> listTmp2 = outsrcingAttrChgService
                .queryListByInsId(dto.getInstanceId());
        for(OutsrcingAttrChgDto tmp2 : listTmp2) {
            if (tmp2.getUserCode().equalsIgnoreCase(dto.getUserCode())) {
                if (dto.getAttachId().equals(tmp2.getAttachId())){
                    flag = true;
                }
            }
        }
        if (!flag){
            attatchManagementService.calMotionNumber(beforeId, dto.getAttachId());
        }
    }

    @Override
    public String approvedByMgrL2(OutsrcAttrChgApplyDto apply, BpsWorkFlowDto bps) {
        List<OutsrcingAttrChgDto> personsList = apply.getOutsrcingAttrChgList();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);  // 租户
            param.setTaskId(bps.getTaskId());               // 任务id
            param.setUserId(bps.getUserId());               // 处理人
            param.setComment(bps.getSuggest());             // 审批意见
            // 流程变量
            Map mPara = new HashMap();
            mPara.put("status", "return");
            List<String> msgIds = Lists.newArrayList();
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                //继续流转到主持工作的一级部经理
                CommEmployeeDto workManager = UsersUtil.getTopWorkManager(bps.getDepId());
                // 下一步审批人
                if (StringUtils.isBlank(workManager.getBadge())) {
                    throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "当前用户无一级部经理");
                }
                // 查询一部下当前存在的行员
                CommEmployeeDto commEmployeeDto = CommEmployeeDto.builder()
                        .depid(80000053).build();
                msgIds = commEmployeeService.getEmployeeLists(commEmployeeDto)
                        .stream().map(item -> item.getBadge()).collect(Collectors.toList());
                List<PostInfo> userList = new ArrayList<>();
                PostInfo postInfo;
                for (String userId : msgIds) {
                    postInfo = new PostInfo();
                    postInfo.setType("user");
                    postInfo.setUserId(userId);
                    userList.add(postInfo);
                }
                // 下一步审批人
                mPara.put("userList", userList);
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 更新记录
            this.updateOutsrcAttrChgApply(apply);
            personsList.stream().forEach(x -> {
                outsrcingAttrChgService.updateOutsrcingAttrChg(x);
            });
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(apply.getInstanceId());
            bps.setApplydesc("二级部经理审批");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(apply.getInstanceId());
            CamundaUtils.addFlowLog(bps, taskBean);
            // 下一节点记录
            CamundaExtUtils.saveNextTaskInfo(taskBean,msgIds.toArray(new String[msgIds.size()]),bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, msgIds.toArray(new String[msgIds.size()]));
        } catch (Exception e) {
            log.error("二级部经理审批失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String approvedByMgrL1(OutsrcAttrChgApplyDto apply, BpsWorkFlowDto bps) {
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);  // 租户
            param.setTaskId(bps.getTaskId());               // 任务id
            param.setUserId(bps.getUserId());               // 处理人
            param.setComment(bps.getSuggest());             // 审批意见
            // 流程变量
            Map mPara = new HashMap();
            mPara.put("status", "return");
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                // 更新<人员属性变更记录>审批状态
                OutsrcingAttrChg outsrcingAttrChg = new OutsrcingAttrChg();
                outsrcingAttrChg.setInstanceId(apply.getInstanceId());
                outsrcingAttrChg.setApprovalStatus("1");
                outsrcingAttrChgService.updateByInstanceId(outsrcingAttrChg);
                // 更新basic表：合同，总办议案，系统编号，项目编号
                DecimalFormat decimalFormat = new DecimalFormat("###########.#######");
                apply.getOutsrcingAttrChgList().stream().forEach(x -> {
                    Date chgDate = DateUtil.strToDate( x.getChgDate(), "yyyy-MM-dd");
                    // 更新日期 > 当前日期, 由调度更新
                    if (chgDate.getTime() <= System.currentTimeMillis()) {
                        PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
                        basic.setJobId(x.getUserCode());
                        basic.setOutsourceType(x.getOutsourceType());
                        //外包方式
                        String outsourceType = x.getOutsourceType();
                        if ("0".equalsIgnoreCase(outsourceType)) {
                            //项目
                            basic.setUserType("1");
                            basic.setProjectManager(x.getPrjMgrCode());
                        }else{
                            basic.setUserType("0");
                            basic.setSystemPrincipal(x.getPrjMgrCode());
                        }
                        basic.setSystemNo(x.getSystemId());
                        basic.setProjectNumber(x.getProjectId());
                        basic.setNewContractId(x.getContractId());
                        basic.setCeneralOfficeMotion(x.getAttachId());
                        basic.setGrade(x.getServiceYear());
                        basic.setPrice(decimalFormat.format(x.getPrice()));
                        if (Objects.isNull(basic.getJobId())) {return;}
                        ppmOutsourcingBasicService.updatePpmOutsourcingBasicCus(basic);
                        // 更新考勤详情表prices字段
                        PpmKqRecordDto ppmKqRecordDto = new PpmKqRecordDto();
                        ppmKqRecordDto.setAttendanceDate(chgDate);
                        ppmKqRecordDto.setAttendanceDateE(new Date());
                        ppmKqRecordDto.setJobId(x.getUserCode());
                        ppmKqRecordDto.setModifiedTime(new Date());
                        ppmKqRecordDto.setPrices(decimalFormat.format(x.getPrice()));
                        ppmKqRecordDto.setOutsourceType(x.getOutsourceType());
                        ppmKqRecordService.updatePpmKqRecordCus(ppmKqRecordDto);

                        //更新
                        PpmKqAnnualVacationDto annualVacationDto = new PpmKqAnnualVacationDto();
                        annualVacationDto.setJobId(x.getUserCode());
                        annualVacationDto.setAnnualVacationDate(DateUtil.intervalMonth(chgDate,6));
                        annualVacationDto.setAnnualVacationSurplus(0.0);
                        annualVacationDto.setAnnualVacationDays(0.0);
                        kqAnnualVacationService.updatePpmKqAnnualVacation(annualVacationDto);
                    }
                });
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            this.updateOutsrcAttrChgApply(apply);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(apply.getInstanceId());
            bps.setApplydesc("一级部经理审批");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(apply.getInstanceId());
            CamundaUtils.addFlowLog(bps, taskBean);
            // 下一节点记录
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("一级部经理审批失败：{}", e);
            throw new BizException(e);
        }
    }


    /**
     * 对提交的数据做校验
     * @param apply 对象
     */
    private void vaildCommitData(OutsrcAttrChgApplyDto apply){
        List<OutsrcingAttrChgDto> personsList = apply.getOutsrcingAttrChgList();

        // 项目经理：不能跨部门代申请
        CommEmployee employee = commEmployeeService.getCommEmployeeInfo(apply.getApplier());
        if (Objects.isNull(employee)
                || !StringUtil.isNotBlank(String.valueOf(employee.getDepid()))
                || !String.valueOf(employee.getDepid()).equalsIgnoreCase(apply.getBps().getDepId())) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "跨部门申请");
        }

        //人员属性变更重复性校验
        personsList.forEach(person ->{
            OutsrcingAttrChgDto attrChgDto = new OutsrcingAttrChgDto();
            attrChgDto.setUserCode(person.getUserCode());
            attrChgDto.setApprovalStatus("0");
            attrChgDto.setInstanceNotId(person.getInstanceId());
            List<OutsrcingAttrChgDto> list = outsrcingAttrChgService.selectByMapSmall(attrChgDto);
            if (!CollectionUtils.isEmpty(list)){
                throw new BizException(ApiCode.FAIL,person.getUserName()+"重复发起");
            }
        });


        //提交的总办议案修改时判断是否还有可用人数
        boolean flag = true;
        StringJoiner stringJoiner = new StringJoiner(",");
        for(OutsrcingAttrChgDto dto : personsList){
            if(dto.getId() != null){
                OutsrcingAttrChgDto attrChgDto = outsrcingAttrChgService.queryDetailsById(dto.getId());
                if(attrChgDto != null && !dto.getAttachId().equals(attrChgDto.getAttachId())){
                    Long beforeId = attrChgDto.getAttachId();
                    Long afterId = dto.getAttachId();
                    if(!Objects.equals(beforeId,afterId) &&
                            !attatchManagementService.judgeMotionNumber(beforeId,afterId)){
                        stringJoiner.add(dto.getUserName());
                        flag = false;
                    }
                }
            }else {
                PpmOutsourcingBasic paramBasic = new PpmOutsourcingBasic();
                paramBasic.setJobId(dto.getUserCode());
                PpmOutsourcingBasic basic =ppmOutsourcingBasicService.queryDetail(paramBasic);
                Long beforeId = basic.getCeneralOfficeMotion();
                Long afterId = dto.getAttachId();
                if(!Objects.equals(beforeId,afterId) &&
                        !attatchManagementService.judgeMotionNumber(beforeId,afterId)){
                    stringJoiner.add(dto.getUserName());
                    flag = false;
                }
            }
        }

        if(!flag){
            throw new BizException(ApiCode.FAIL,stringJoiner+"所选总办议案可用人数不足。");
        }

        //已删除的数据总办议案可用人数是否充足
        //1 总办议案改变了，判断总办议案与原来的总办议案是否相同
        List<OutsrcingAttrChgDto> listOld = new ArrayList<>();
        if (!Objects.isNull(apply.getInstanceId())) {
            listOld = outsrcingAttrChgService.queryListByInsId(apply.getInstanceId());
        }
        Set<String> userCodeSet = personsList.stream()
                .map(OutsrcingAttrChgDto::getUserCode).collect(Collectors.toSet());
        StringJoiner delJoiner = new StringJoiner(",");
        for(OutsrcingAttrChgDto dto : listOld){
            if (!userCodeSet.contains(dto.getUserCode())) {
                PpmOutsourcingBasic paramBasic = new PpmOutsourcingBasic();
                paramBasic.setJobId(dto.getUserCode());
                PpmOutsourcingBasic basic =ppmOutsourcingBasicService.queryDetail(paramBasic);
                Long beforeId = basic.getCeneralOfficeMotion();
                Long afterId = dto.getAttachId();
                if(!Objects.equals(beforeId, afterId)){
                    //如果总办议案更改了，则取判断原总办议案可用人数是否充足
                    boolean xFlag = attatchManagementService.judgeMotionNumber(afterId,beforeId);
                    delJoiner.add(dto.getUserName());
                    flag = xFlag;
                }
            }
        }
        if(!flag){
            throw new BizException(ApiCode.FAIL,stringJoiner+"原总办议案的可用人数已被占用，无可用人数！");
        }


    }
}