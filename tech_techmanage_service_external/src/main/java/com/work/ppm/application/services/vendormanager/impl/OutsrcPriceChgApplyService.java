package com.work.ppm.application.services.vendormanager.impl;

import com.google.common.collect.Lists;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.vendormanager.OutsrcPriceChgApplyDto;
import com.work.ppm.application.dto.vendormanager.OutsrcingPriceChgDto;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.vendormanager.OutsrcAttrChgApply;
import com.work.ppm.application.model.vendormanager.OutsrcPriceChgApply;
import com.work.ppm.application.model.vendormanager.OutsrcingPriceChg;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.vendormanager.IOutsrcPriceChgApplyService;
import com.work.ppm.application.services.vendormanager.IOutsrcingPriceChgService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 人员单价调整 服务实现类
 * </p>
 *
 * @since 2022-12-18
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class OutsrcPriceChgApplyService extends BaseDaoSupper implements IOutsrcPriceChgApplyService {

    /**
     * 实体类路径
     */
    private static String OUTSRCPRICECHGAPPLY= "com.work.ppm.application.model.vendormanager.OutsrcPriceChgApply";

    /**
     * 实体类路径
     */
    private static String OUTSRCPRICECHG= "com.work.ppm.application.model.vendormanager.OutsrcingPriceChg";

    /**
     * 员工查询
     */
    private final ICommEmployeeService commEmployeeService;
    /**
     * 工作流服务
     */
    private final WorkFlowHttpClientService workFlowHttpClientService;
    /**
     * 流程定义ID
     */
    private static final String FLOW_ID = "outsrcingPriceChg";
    /**
     * 流程名称
     */
    private static final String FLOW_TITLE = "单价调整";

    /**
     * 单价调整
     */
    private final IOutsrcingPriceChgService outsrcingPriceChgService;

    /**
     * 外包花名册
     */
    private final IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    private final IPpmKqRecordService ppmKqRecordService;
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public OutsrcPriceChgApplyDto queryDetailsByInsId(String instanceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("instanceId", instanceId);
        OutsrcPriceChgApply outsrcPriceChgApply =
                myBatisDao.selectClass(OUTSRCPRICECHGAPPLY, map, "selectOneByInstanceId");
        OutsrcPriceChgApplyDto dto = JsonUtil.jsonToClass(outsrcPriceChgApply, OutsrcPriceChgApplyDto.class);
        List<OutsrcingPriceChgDto> priceChgDtoList = outsrcingPriceChgService.queryListByInsId(dto.getInstanceId());
        priceChgDtoList.forEach(priceChgDto ->{
            priceChgDto.setDeptName(BusinessUtil.getDeptNameValue(priceChgDto.getDeptName(),1));
        });
        dto.setOutsrcingPriceChgList(priceChgDtoList);
        dto.setDeptName(BusinessUtil.getDeptNameValue(dto.getDeptName(),1));
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOutsrcPriceChgApply(OutsrcPriceChgApply outsrcPriceChgApply) {
        return myBatisDao.executionPojo(OUTSRCPRICECHGAPPLY, outsrcPriceChgApply, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public int updateOutsrcPriceChgApply(OutsrcPriceChgApply outsrcPriceChgApply) {
        return myBatisDao.executionPojo(OUTSRCPRICECHGAPPLY, outsrcPriceChgApply, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int removeFlow(OutsrcPriceChgApply  outsrcPriceChgApply) {
        outsrcPriceChgApply.setIsDel(LogicEnums.INVALID.getCode());
        OutsrcingPriceChg outsrcingPriceChg = JsonUtil.jsonToClass(outsrcPriceChgApply, OutsrcingPriceChg.class);
        myBatisDao.executionPojo(OUTSRCPRICECHGAPPLY,
                outsrcPriceChgApply, IMyBatisDao.SQL.UPDATE, "updateByInstanceId");
        myBatisDao.executionPojo(OUTSRCPRICECHG, outsrcingPriceChg, IMyBatisDao.SQL.UPDATE, "updateByInstanceId");
        // 删除流程相关数据
        CamundaUtils.delProjectFlow(outsrcPriceChgApply.getInstanceId());
        CamundaUtils.terminateProcess(outsrcPriceChgApply.getInstanceId());
        return 1;
    }

    @Override
    public OutsrcPriceChgApplyDto draft(OutsrcPriceChgApplyDto apply, BpsWorkFlowDto bps) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String approvalCirculate(OutsrcPriceChgApplyDto apply, BpsWorkFlowDto bps) {
        List<OutsrcingPriceChgDto> personsList = apply.getOutsrcingPriceChgList();
        // 项目经理：不能跨部门代申请
        CommEmployee employee = commEmployeeService.getCommEmployeeInfo(apply.getApplier());
        if (Objects.isNull(employee)
                || !StringUtil.isNotBlank(String.valueOf(employee.getDepid()))
                || !String.valueOf(employee.getDepid()).equalsIgnoreCase(bps.getDepId())) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "跨部门申请");
        }
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
                // 价格变更申请
                this.addOutsrcPriceChgApply(apply);
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
                this.updateOutsrcPriceChgApply(apply);
                // 属性变更人员多条记录 -> 添加流程实例id
                personsList.stream().forEach(item ->
                        item.setInstanceId(apply.getInstanceId()).setCreateUser(bps.getUserId())
                );
                // 人员单价信息
                outsrcingPriceChgService.batchAddOutsrcingPriceChg(personsList);
                // 日志处理
                bps.setProjectNo(String.valueOf(apply.getId()));
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setApplydesc("单价调整申请发起");
                bps.setRelaData(mPara);
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessInstName("【" + stringBuilder + DateUtil.dateToNoBar(new Date()) + "】" + "人员单价调整");
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setSuggest("提交申请");
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
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
                // 新增或者更新
                this.updateOutsrcPriceChgApply(apply);
                List<OutsrcingPriceChgDto> listOld2 = outsrcingPriceChgService.queryListByInsId(apply.getInstanceId());
                personsList.stream().forEach(x -> {
                    for(OutsrcingPriceChgDto priceChgDto :listOld2) {
                        if (priceChgDto.getUserCode().equalsIgnoreCase(x.getUserCode())) {
                            x.setId(priceChgDto.getId());
                            break;
                        }
                    }
                    if (Objects.isNull(x.getId())) {
                        outsrcingPriceChgService.addOutsrcingPriceChg(x);
                    } else {
                        outsrcingPriceChgService.updateOutsrcingPriceChg(x);
                    }
                });
                // 删除
                Set<String> userCodeSet = personsList.stream()
                        .map(OutsrcingPriceChgDto::getUserCode).collect(Collectors.toSet());
                listOld2.stream().forEach(x -> {
                    if (!userCodeSet.contains(x.getUserCode())) {
                        x.setIsDel("1");
                        outsrcingPriceChgService.updateOutsrcingPriceChg(x);
                    }
                });
                // 日志处理
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(apply.getInstanceId());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setProcessInstName("【" + stringBuilder + DateUtil.dateToNoBar(new Date()) + "】" + "人员单价调整");
                bps.setSuggest("重新提交");
                bps.setApplydesc("单价调整申请发起");
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
            log.error("单价调整申请发起流程失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    public String approvedByMgrL2(OutsrcPriceChgApplyDto apply, BpsWorkFlowDto bps) {
        List<OutsrcingPriceChgDto> personsList = apply.getOutsrcingPriceChgList();
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
                //param.setmPara(mPara);
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 更新记录
            this.updateOutsrcPriceChgApply(apply);
            personsList.stream().forEach(x -> {
                outsrcingPriceChgService.updateOutsrcingPriceChg(x);
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
            CamundaExtUtils.saveNextTaskInfo(taskBean, msgIds.toArray(new String[msgIds.size()]), bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, msgIds.toArray(new String[msgIds.size()]));
        } catch (Exception e) {
            log.error("二级部经理审批失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    public String approvedByMgrL1(OutsrcPriceChgApplyDto apply, BpsWorkFlowDto bps) {
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
                // 更新<单价调整记录>审批状态
                OutsrcingPriceChg outsrcingPriceChg = new OutsrcingPriceChg();
                outsrcingPriceChg.setInstanceId(apply.getInstanceId());
                outsrcingPriceChg.setApprovalStatus("1");
                outsrcingPriceChgService.updateByInstanceId(outsrcingPriceChg);
                // 更新外包花名册依据人员编号
                DecimalFormat decimalFormat = new DecimalFormat("###########.#######");
                apply.getOutsrcingPriceChgList().stream().forEach(x -> {
                    Date chgDate = DateUtil.strToDate( x.getChgDate(), "yyyy-MM-dd");
                    // 更新日期 > 当前日期，依据调度更新
                    if (chgDate.getTime() <= System.currentTimeMillis()) {
                        // 更新basic表price字段
                        PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
                        basic.setJobId(x.getUserCode());
                        basic.setPrice(decimalFormat.format(x.getPostPrice()));
                        basic.setGrade(x.getServiceYear());
                        if (Objects.isNull(basic.getJobId()) ||"".equalsIgnoreCase(basic.getJobId())) {return;}
                        ppmOutsourcingBasicService.updatePpmOutsourcingBasic(basic);
                        // 更新考勤详情表prices字段
                        PpmKqRecordDto ppmKqRecordDto = new PpmKqRecordDto();
                        ppmKqRecordDto.setAttendanceDate(chgDate);
                        ppmKqRecordDto.setAttendanceDateE(new Date());
                        ppmKqRecordDto.setJobId(x.getUserCode());
                        ppmKqRecordDto.setModifiedTime(new Date());
                        ppmKqRecordDto.setPrices(decimalFormat.format(x.getPostPrice()));
                        ppmKqRecordService.updatePpmKqRecordCus(ppmKqRecordDto);
                    }
                });
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            this.updateOutsrcPriceChgApply(apply);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(apply.getInstanceId());
            bps.setApplydesc("一级部门经理审批");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(apply.getInstanceId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("一级部经理审批失败：{}", e);
            throw new BizException(e);
        }
    }

}