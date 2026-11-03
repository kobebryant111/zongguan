package com.work.ppm.application.services.vendormanager.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.vendorcontract.ExpenseSettlementDto;
import com.work.ppm.application.dto.vendorcontract.PaymentConfirmDto;
import com.work.ppm.application.dto.vendormanager.PaymentConfirmPersonDto;
import com.work.ppm.application.enums.ApprovalStatusEnums;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.vendormanager.ExpenseSettlement;
import com.work.ppm.application.model.vendormanager.PaymentConfirm;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.othermanager.IPpmOutsourcingInfoService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.vendormanager.IExpenseSettlementService;
import com.work.ppm.application.services.vendormanager.IPaymentConfirmService;
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
import com.work.ppm.commons.utils.HttpClient;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * <p>
 * 费用结算 服务实现类
 * </p>
 *
 * @since 2022-11-09
 */
@Slf4j
@RequiredArgsConstructor
@Service("expenseSettlementService")
public class ExpenseSettlementService extends BaseDaoSupper implements IExpenseSettlementService {

    /**
      * 实体类路径
      */
    private static String EXPENSESETTLEMENT= "com.work.ppm.application.model.vendormanager.ExpenseSettlement";
    // 员工查询
    private final ICommEmployeeService commEmployeeService;
    // 支付确认
    private final IPaymentConfirmService paymentConfirmService;
    // 流程定义ID
    private static final String FLOW_ID = "mergeApproval";
    //流程名称
    private static final String FLOW_TITLE = "费用结算";
    // 已流转 <进入费用结算>
    private static final String FLOWED = "1";
    // 未流转 <仍存于支付确认>
    private static final String UN_FLOW = "0";
    //工作流服务
    private final WorkFlowHttpClientService workFlowHttpClientService;
    // 支付确认
    private final IPpmOutsourcingInfoService outsourcingInfoService;
    //外包花名册
    private final IPpmOutsourcingBasicService outsourcingBasicService;

    @Value("${cbmp.url}")
    private String url;
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(ExpenseSettlementDto expenseSettlement, Pager pager) {
        Map<String, Object> mapDto = StringUtil.objectTOMap(expenseSettlement, ExpenseSettlementDto.class);
        Map<String, Object> map = StringUtil.objectTOMap(expenseSettlement, ExpenseSettlement.class);
        mapDto.putAll(map);
        myBatisDao.selectPager(EXPENSESETTLEMENT, mapDto, pager, "queryPagerList");
    }

    @Override
    public ExpenseSettlementDto queryDetails(ExpenseSettlement expenseSettlement) {
        ExpenseSettlementDto dto = myBatisDao.selectClass(EXPENSESETTLEMENT, expenseSettlement, "queryDetails");
        //ExpenseSettlementDto dto= JsonUtil.jsonToClass(es, ExpenseSettlementDto.class);
        if (StringUtils.isNotEmpty(dto.getPayConfirmId())) {
            List<Long> ids = Arrays.stream(dto.getPayConfirmId().split(",")).map(Long::parseLong)
                    .collect(Collectors.toList());
            List<PaymentConfirm> list = paymentConfirmService.queryListByIds(ids);
            List<PaymentConfirm> listMgr = list.stream().collect(Collectors.collectingAndThen(Collectors
                    .toCollection(() -> new TreeSet<>(Comparator
                            .comparing(PaymentConfirm::getProManager))), ArrayList::new));
            StringBuilder stringBuilder= new StringBuilder();
            listMgr.forEach(x->{
                String name = commEmployeeService.getBadgeAndName(x.getProManager());
                String nameTrim = name.substring(0,name.indexOf("("));
                stringBuilder.append(nameTrim).append(",");
            });
            String nameAppend = stringBuilder.substring(0,stringBuilder.length()-1);
            dto.setProManagerName(nameAppend);
            dto.setPayConfirmList(list);
        }
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addExpenseSettlement(ExpenseSettlement expenseSettlement) {
        if (!Objects.isNull(expenseSettlement.getTaxRate())) {
            expenseSettlement.setTaxRate(expenseSettlement.getTaxRate()/100);
        }
        myBatisDao.executionPojo(EXPENSESETTLEMENT, expenseSettlement, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public Integer updateExpenseSettlement(ExpenseSettlement expenseSettlement) {
        if (!Objects.isNull(expenseSettlement.getTaxRate())) {
            expenseSettlement.setTaxRate(expenseSettlement.getTaxRate()/100);
        }
        return myBatisDao.executionPojo(EXPENSESETTLEMENT, expenseSettlement, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int deleteFlowExpenseSettle(ExpenseSettlement apply) {

        apply.setIsDel(LogicEnums.INVALID.getCode());
        int count = myBatisDao.executionPojo(EXPENSESETTLEMENT, apply, IMyBatisDao.SQL.UPDATE, "updateByInstanceId");
        List list = Arrays.asList(apply.getPayConfirmId().split(","));
        list.forEach(x->{
            Long id = Long.parseLong(x.toString());
            PaymentConfirm paymentConfirm = new PaymentConfirm();
            paymentConfirm.setId(id);
            paymentConfirm.setFlowStatus(UN_FLOW);
            paymentConfirmService.updatePaymentConfirm(paymentConfirm);
        });
        // 流程相关数据->删除
        CamundaUtils.delProjectFlow(apply.getInstanceId());
        CamundaUtils.terminateProcess(apply.getInstanceId());
        return count;
    }

    @Override
    public List<ExpenseSettlement> selectByMapBasic(HashMap map) {
        List<ExpenseSettlement> res = myBatisDao.selectListT(EXPENSESETTLEMENT, map, "selectByMapBasic");
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String approvalCirculate(ExpenseSettlementDto applyDto, BpsWorkFlowDto bps) {


        ProcessBean processBean = null;
        try {
            //流转到一级部门经理
            String deptId = commEmployeeService.getDeptNo(bps.getUserId()).replaceAll("dep", "");
            CommEmployeeDto workManager = UsersUtil.getTopWorkManager(deptId);
            // 审批人是否异常
            if (StringUtils.isBlank(workManager.getBadge())) {
                throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "当前用户无一级部经理");
            }
            // 判断流程实例是否存在，不存在说明是初始提交
            if (StringUtils.isBlank(applyDto.getInstanceId())) {
                // 添加外包准入流程记录
                Long key = super.getNextSequence(ExpenseSettlement.class);
                applyDto.setId(key);
                applyDto.setFlowId(FLOW_ID);
                applyDto.setCreateUser(bps.getUserId());
                applyDto.setApprovalStatus(ApprovalStatusEnums.IN_APPROVAL.getCode());
                this.addExpenseSettlement(applyDto);
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
                applyDto.setInstanceId(processBean.getProcessInstanceId());
                // 税率*100再更新
                applyDto.setTaxRate(applyDto.getTaxRate()*100);
                this.updateExpenseSettlement(applyDto);
                //更新费用支付
                List list = Arrays.asList(applyDto.getPayConfirmId().split(","));
                list.forEach(x->{
                    Long id = Long.parseLong(x.toString());
                    PaymentConfirm paymentConfirm = new PaymentConfirm();
                    paymentConfirm.setId(id);
                    paymentConfirm.setFlowStatus(FLOWED);
                    paymentConfirmService.updatePaymentConfirm(paymentConfirm);
                });
                // 日志处理
                bps.setProjectNo(String.valueOf(applyDto.getId()));
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setApplydesc("费用结算申请发起");
                bps.setRelaData(mPara);
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessInstName("【" + bps.getUserName() + DateUtil.dateToNoBar(new Date()) + "】" + "费用结算申请");
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setSuggest("提交申请");
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null, bps);
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
                param.setmPara(mPara);
                // 提交任务
                workFlowHttpClientService.submitTask(param);
                // 任务提交成功，更新记录审批状态
                applyDto.setApprovalStatus(ApprovalStatusEnums.IN_APPROVAL.getCode());
                this.updateExpenseSettlement(applyDto);
                // 费用支付->flowStatus更新
                List list = Arrays.asList(applyDto.getPayConfirmId().split(","));
                list.forEach(x->{
                    Long id = Long.parseLong(x.toString());
                    PaymentConfirm paymentConfirm = new PaymentConfirm();
                    paymentConfirm.setId(id);
                    paymentConfirm.setFlowStatus(FLOWED);
                    paymentConfirmService.updatePaymentConfirm(paymentConfirm);
                });
                // 日志处理
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(applyDto.getInstanceId());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setSuggest("提交申请");
                bps.setApplydesc("费用结算申请发起");
                bps.setProcessInstDesc(FLOW_TITLE);
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(applyDto.getInstanceId());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            }
        } catch (Exception e) {
            CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            log.error("费用结算申请流程发起失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String confirm(ExpenseSettlementDto applyDto, BpsWorkFlowDto bps) {
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);  // 租户
            param.setTaskId(bps.getTaskId());               // 任务id
            param.setUserId(bps.getUserId());               // 处理人
            param.setComment(bps.getSuggest());             // 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            applyDto.setApprovalStatus(ApprovalStatusEnums.RETURN.getCode());
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                // 审批状态
                applyDto.setApprovalStatus(ApprovalStatusEnums.PASS.getCode());
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            this.updateExpenseSettlement(applyDto);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(applyDto.getInstanceId());
            bps.setApplydesc("一级部经理审批");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(applyDto.getInstanceId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);

            // 如果流程已完成成返回空
            if (ObjectUtils.isEmpty(taskBean) || "Y".equals(taskBean.getIsFinished())) {
                saveExpenseInfo(applyDto);
            }
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
//            new BizException(ApiCode.FAIL);
//            return null;
        } catch (Exception e) {
            log.error("一级部经理审批失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    public List<ExpenseSettlementDto> getNotFinishList(ExpenseSettlementDto expenseSettlementDto) {
        ExpenseSettlement expenseSettlement = JsonUtil.jsonToClass(expenseSettlementDto, ExpenseSettlement.class);
        List<ExpenseSettlement> data =
                myBatisDao.selectListT(EXPENSESETTLEMENT, expenseSettlement,"getNotFinishList");
        return JsonUtil.jsonToList(data,ExpenseSettlementDto.class);
    }

    /**
     * 推送
     * @param expenseSettlementDto 对象
     */
    @Async
    private void saveExpenseInfo(ExpenseSettlementDto expenseSettlementDto){
        String[] payArray = expenseSettlementDto.getPayConfirmId().split(BusinessUtil.REGEX_COMMA);
        List<PaymentConfirm> paymentConfirmList = paymentConfirmService.queryListByIds(Arrays.asList(payArray));

        List<PaymentConfirmDto>  paymentConfirmDtoList =
                JsonUtil.jsonToList(paymentConfirmList,PaymentConfirmDto.class);
        paymentConfirmDtoList.forEach(paymentConfirmDto -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId",paymentConfirmDto.getProManager());
            jsonObject.put("supplier",outsourcingInfoService.queryNameByOrgCode(paymentConfirmDto.getVendor()));
            jsonObject.put("supposeAmt",paymentConfirmDto.getAmountRaw());
            jsonObject.put("allAmt",paymentConfirmDto.getAmount());
            jsonObject.put("taxRate",paymentConfirmDto.getTaxRate());
            jsonObject.put("remark",paymentConfirmDto.getRemark());
            JSONArray payList = new JSONArray();
            try {
                List<PaymentConfirmPersonDto> personDtos = paymentConfirmService.queryPersonList(paymentConfirmDto);
                personDtos.forEach(confirmPersonDto -> {
                    PpmOutsourcingBasicInfo basicInfo =
                            outsourcingBasicService.getBaseInfo(confirmPersonDto.getJobId());
                    JSONObject payObj = new JSONObject();
                    payObj.put("staffName",confirmPersonDto.getUserName());
                    if (basicInfo != null){
                        payObj.put("staffId",basicInfo.getVmAccount());
                    }else {
                        payObj.put("staffId","");
                    }

                    payObj.put("payYear",Integer.valueOf(confirmPersonDto.getPaymentYear()));
                    payObj.put("payMon",Integer.valueOf(confirmPersonDto.getPaymentMonth()));
                    payObj.put("price",confirmPersonDto.getPrice());
                    payObj.put("supposeDays",Double.parseDouble(confirmPersonDto.getRequiredAttendanceDays()));
                    payObj.put("realDays",Double.parseDouble(confirmPersonDto.getActualAttendanceDays()));
                    payObj.put("totalAmt",confirmPersonDto.getActualPrice());
                    payList.add(payObj);
                });
                jsonObject.put("payList",payList);

                String body = JsonUtil.toJSONString(jsonObject);
                log.info("请求体：{}",body);
                String returnBody = HttpClient.post(url,body);
                log.info("returnBody:{}",returnBody);
                if (StringUtils.isNotEmpty(returnBody) ){
                    JSONObject resJson = JSON.parseObject(returnBody);
                    if (Objects.equals("0000",resJson.get("ReturnCode"))){
                        log.info("推送综管支付确认数据成功");
                    }else {
                        log.info("推送综管支付确认数据失败:{}",returnBody);
                    }
                }else {
                    log.info("推送综管支付确认数据失败:{}",returnBody);
                }
            }catch (Exception e){
                log.info("error:{}",e.toString());
            }
        });
    }
}