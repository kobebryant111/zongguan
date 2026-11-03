package com.work.ppm.application.services.vendormanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.vendorcontract.AttendanceInfoDto;
import com.work.ppm.application.dto.vendorcontract.PaymentConfirmDto;
import com.work.ppm.application.dto.vendorcontract.VendorContractDto;
import com.work.ppm.application.dto.vendormanager.PaymentConfirmPersonDto;
import com.work.ppm.application.enums.ApprovalStatusEnums;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.vendormanager.AttatchManagement;
import com.work.ppm.application.model.vendormanager.Doc;
import com.work.ppm.application.model.vendormanager.PaymentConfirm;
import com.work.ppm.application.model.vendormanager.PaymentConfirmOther;
import com.work.ppm.application.model.vendormanager.PaymentConfirmPerson;
import com.work.ppm.application.model.vendormanager.VendorContract;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.application.services.vendormanager.IPaymentConfirmPersonService;
import com.work.ppm.application.services.vendormanager.IPaymentConfirmService;
import com.work.ppm.application.services.vendormanager.IVendorContractService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 费用支付确认 服务实现类
 * </p>
 *
 * @since 2022-11-06
 */

@Slf4j
@RequiredArgsConstructor
@Service("paymentConfirmService")
public class PaymentConfirmService extends BaseDaoSupper implements IPaymentConfirmService {
    /**
     * 实体类路径
     */
    private static String PAYMENTCONFIRM= "com.work.ppm.application.model.vendormanager.PaymentConfirm";
    // 员工查询
    private final ICommEmployeeService commEmployeeService;
    //工作流服务
    private final WorkFlowHttpClientService workFlowHttpClientService;
    // 合同
    private final IVendorContractService vendorContractService;
    // 附件
    private final IAttatchManagementService attachManagementService;
    // 人员信息
    private final IPaymentConfirmPersonService paymentConfirmPersonService;
    // 流程定义ID
    private static final String FLOW_ID = "payConfirmEdit";
    //流程名称
    private static final String FLOW_TITLE = "支付确认";

    private final IPpmDocumentService ppmDocumentService;
    @Autowired
    private IPpmKqRecordService ppmKqRecordService;
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PaymentConfirm paymentConfirmDto, Pager pager) {
        Map<String, Object> mapDto = StringUtil.objectTOMap(paymentConfirmDto, PaymentConfirmDto.class);
        Map<String, Object> map = StringUtil.objectTOMap(paymentConfirmDto, PaymentConfirm.class);
        mapDto.putAll(map);
        myBatisDao.selectPager(PAYMENTCONFIRM, mapDto, pager, "queryPagerList");
    }

    @Override
    public void queryForSettle(PaymentConfirmDto paymentConfirmDto, Pager pager){
        Map<String, Object> mapDto = StringUtil.objectTOMap(paymentConfirmDto, PaymentConfirmDto.class);
        Map<String, Object> map = StringUtil.objectTOMap(paymentConfirmDto, PaymentConfirm.class);
        mapDto.putAll(map);
        myBatisDao.selectPager(PAYMENTCONFIRM, mapDto, pager,"selectOthers");
    }

    @Override
    public PaymentConfirmDto queryDetailsById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        PaymentConfirm  paymentConfirm = myBatisDao.selectClass(PAYMENTCONFIRM, map, "queryDetailsById");
        PaymentConfirmDto dto = JsonUtil.jsonToClass(paymentConfirm, PaymentConfirmDto.class);
        // 附件文档
        PaymentConfirmDto basicInfo = this.selectOne(paymentConfirm);
        if (!Objects.isNull(basicInfo)) {
            dto.setContractDocList(basicInfo.getContractDocList());
            dto.setAttachDocList(basicInfo.getAttachDocList());
        }
        // 人员考勤信息
        List<PaymentConfirmPersonDto> personList = paymentConfirmPersonService.queryListByInsId(dto.getInstanceId());
        dto.setPayConfirmPersonList(personList);
        // 金额重置：从已有人员信息提取金额
        Double amountRawFromPersonList = 0.0;
        for (PaymentConfirmPersonDto person : personList) {
            amountRawFromPersonList = person.getActualPrice() + amountRawFromPersonList;
        }
        dto.setAmountRaw(amountRawFromPersonList);
        return dto;
    }

    @Override
    public PaymentConfirmDto queryDetailsByInstanceId(String instanceId) {
        // 支付确认: 主体记录查询
        Map<String, Object> map = new HashMap<>();
        map.put("instanceId", instanceId);
        PaymentConfirm  paymentConfirm = myBatisDao.selectClass(PAYMENTCONFIRM, map, "queryDetailsByInstanceId");
        PaymentConfirmDto dto = JsonUtil.jsonToClass(paymentConfirm, PaymentConfirmDto.class);
        // 附件文档
        PaymentConfirmDto basicInfo = this.selectOne(paymentConfirm);
        if (!Objects.isNull(basicInfo)) {
            dto.setContractDocList(basicInfo.getContractDocList());
            dto.setAttachDocList(basicInfo.getAttachDocList());
        }
        // 人员信息
        List<PaymentConfirmPersonDto> personList = paymentConfirmPersonService.queryListByInsId(instanceId);
        dto.setPayConfirmPersonList(personList);
        // 金额重置：从已有人员信息提取金额
        Double amountRawFromPersonList = 0.0;
        for (PaymentConfirmPersonDto person : personList) {
            amountRawFromPersonList = person.getActualPrice() + amountRawFromPersonList;
        }
        dto.setAmountRaw(amountRawFromPersonList);
        return dto;
    }

    @Override
    public List<PaymentConfirm> queryListByIds(List ids) {
        return myBatisDao.selectListT(PAYMENTCONFIRM, ids, "selectListByIds");
    }

    @Override
    public long selectRepetition(HashMap map) {
        if (Objects.isNull(map)) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "参数不为空");
        }
        return myBatisDao.selectCount(PAYMENTCONFIRM, map, "selectRepetition");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addPaymentConfirm(PaymentConfirm paymentConfirm) {
        if (!Objects.isNull(paymentConfirm.getTaxRate())) {
            paymentConfirm.setTaxRate(paymentConfirm.getTaxRate()/100);
        }
        return myBatisDao.executionPojo(PAYMENTCONFIRM, paymentConfirm, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public Integer updatePaymentConfirm(PaymentConfirm paymentConfirm) {
        if (!Objects.isNull(paymentConfirm.getTaxRate())) {
            paymentConfirm.setTaxRate(paymentConfirm.getTaxRate()/100);
        }
        return myBatisDao.executionPojo(PAYMENTCONFIRM, paymentConfirm, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int updateByInstanceId(PaymentConfirm paymentConfirm) {
        return myBatisDao.executionPojo(PAYMENTCONFIRM, paymentConfirm, IMyBatisDao.SQL.UPDATE, "updateByInstanceId");
    }

    @Override
    public int deleteFlowPayConfirm(PaymentConfirm apply) {

        apply.setIsDel(LogicEnums.INVALID.getCode());
        int count = myBatisDao.executionPojo(PAYMENTCONFIRM, apply, IMyBatisDao.SQL.UPDATE, "updateByInstanceId");
        // 删除人员信息
        PaymentConfirmPerson person = new PaymentConfirmPerson();
        person.setInstanceId(apply.getInstanceId());
        person.setIsDel("1");
        person.setUpdateDate(new Date());
        paymentConfirmPersonService.updateByInstanceId(person);
        // 删除流程相关数据
        CamundaUtils.delProjectFlow(apply.getInstanceId());
        CamundaUtils.terminateProcess(apply.getInstanceId());
        return count;
    }

    @Override
    public PaymentConfirmDto selectOne(PaymentConfirm param) {
        String startDate = param.getFeeStartDate();
        String endDate = param.getFeeEndDate();
        if (!StringUtil.isStringNotEmpty(startDate)) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "参数不为空");
        }
        if (!StringUtil.isStringNotEmpty(endDate)) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "参数不为空");
        }
        param.setFeeStartDate(startDate.replace("-","").substring(0,6));
        param.setFeeEndDate(endDate.replace("-","").substring(0,6));
        // 基础信息
        List<PaymentConfirm> paymentConfirm = myBatisDao.selectListT(PAYMENTCONFIRM, param, "selectOne");
        if (Objects.isNull(paymentConfirm) || paymentConfirm.size() == 0) {
            return null;
        }
        // 转dto
        PaymentConfirmDto dto= JsonUtil.jsonToClass(paymentConfirm.get(0), PaymentConfirmDto.class);
        // 取合同id
        HashMap mapForId = new HashMap<>();
        mapForId.put("contractNo", param.getContractNo());
        mapForId.put("isDel", "0");
        List<VendorContract> contractList = vendorContractService.selectByMapSmall(mapForId);
        if (contractList == null || contractList.size() == 0) {
            return dto;
        }
        // 合同文档
        String contractDoc = contractList.get(0).getContractDoc();
        if (StringUtil.isStringNotEmpty(contractDoc)) {
            List<String> contractDocList = Arrays.asList(contractDoc.split(",")) ;
            List setContractDocList = new ArrayList();
            contractDocList.forEach(x-> {
                Doc doc = new Doc();
                doc.setCode(x);
                doc.setName(ppmDocumentService.getDetail(x).getDocname());
                setContractDocList.add(doc);
            });
            dto.setContractDocList(setContractDocList);
        }
        // 附件文档
        String attachInfoId = contractList.get(0).getAttachInfo();
        if (StringUtil.isStringNotEmpty(attachInfoId)) {
            List<String> attachInfoIdList = Arrays.asList(attachInfoId.split(",")) ;
            List setAttachDocList = new ArrayList();
            attachInfoIdList.forEach(id -> {
                AttatchManagement attatchManagement = attachManagementService.queryDetailsById(Long.parseLong(id));
                List<String> attachDocList = new ArrayList<>();
                if (!Objects.isNull(attatchManagement) && StringUtil.isNotBlank(attatchManagement.getDoc())) {
                    attachDocList = Arrays.asList(attatchManagement.getDoc().split(","));
                }
                for (String s : attachDocList) {
                    Doc doc = new Doc();
                    doc.setCode(s);
                    doc.setName(ppmDocumentService.getDetail(s).getDocname());
                    setAttachDocList.add(doc);
                }
            });
            dto.setAttachDocList(setAttachDocList);
        }
        // 金额计算
        PaymentConfirmDto personDto = new PaymentConfirmDto();
        personDto.setProManager(param.getProManager());
        personDto.setContractNo(param.getContractNo());
        personDto.setFeeStartDate(startDate.replace("-","").substring(0,8));
        personDto.setFeeEndDate(endDate.replace("-","").substring(0,8));
        List<PaymentConfirmPersonDto> personList = this.queryPersonList(personDto);
        Double amountRawFromPersonList = 0.0;
        for (PaymentConfirmPersonDto person : personList) {
            amountRawFromPersonList = person.getActualPrice() + amountRawFromPersonList;
        }
        dto.setAmountRaw(amountRawFromPersonList);
        return dto;
    }

    private int checkParams(PaymentConfirmDto paymentConfirm) {
        if (Objects.isNull(paymentConfirm)) {
            return -1;
        }
        // 参数：项目经理，合同编号
        if (Objects.isNull(paymentConfirm.getProManager())
                || Objects.isNull(paymentConfirm.getContractNo())) {
            return -1;
        }
        // 日期区间
        String startDate = paymentConfirm.getFeeStartDate();
        String endDate = paymentConfirm.getFeeEndDate();
        if (!StringUtil.isStringNotEmpty(startDate) || !StringUtil.isStringNotEmpty(endDate) ) {
            return -1;
        }
        return 0;
    }

    @Override
    public List<PaymentConfirmPersonDto> queryPersonList(PaymentConfirmDto paymentConfirm) {
        AssertUtil.notNull(paymentConfirm, "参数不为空");
        // 参数：项目经理，合同编号
        if (Objects.isNull(paymentConfirm.getProManager())
                || Objects.isNull(paymentConfirm.getContractNo())) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "项目经理/合同编号不为空");
        }
        // 日期区间
        String startDate = paymentConfirm.getFeeStartDate();
        String endDate = paymentConfirm.getFeeEndDate();
        if (!StringUtil.isStringNotEmpty(startDate) || !StringUtil.isStringNotEmpty(endDate) ) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "日期参数不为空");
        }
//        paymentConfirm.setFeeStartDate(startDate.replace("-","").substring(0,6));
//        paymentConfirm.setFeeEndDate(endDate.replace("-","").substring(0,6));
        paymentConfirm.setFeeStartDate(startDate.replace("-","").substring(0,8));
        paymentConfirm.setFeeEndDate(endDate.replace("-","").substring(0,8));
        // 取合同id
        HashMap mapForId = new HashMap<>();
        mapForId.put("contractNo", paymentConfirm.getContractNo());
        mapForId.put("isDel", "0");
        List<VendorContract> listC = vendorContractService.selectByMapSmall(mapForId);
        paymentConfirm.setContractId(String.valueOf(listC.get(0).getId()));

        List<PaymentConfirmPersonDto> list =
                myBatisDao.selectListT(PAYMENTCONFIRM, paymentConfirm, "selectPersonList");

        list.forEach(obj ->{
                obj.setRequiredAttendanceDays(obj.getRequiredAttendanceDaysq()+"");
                obj.setActualAttendanceDays(
                        obj.getActualAttendanceDaysq() < obj.getActualAttendanceDayst()?
                                obj.getActualAttendanceDaysq()+"":obj.getActualAttendanceDayst()+"");
        });

        return list;
    }

    @Override
    public List<AttendanceInfoDto> queryAttendanceDetails(PaymentConfirmDto paymentConfirm) {
        String startDate = paymentConfirm.getFeeStartDate();
        String endDate = paymentConfirm.getFeeEndDate();
        if (!StringUtil.isStringNotEmpty(startDate) || !StringUtil.isStringNotEmpty(endDate) ) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "参数不为空");
        }
        paymentConfirm.setFeeStartDate(startDate.replace("-","").substring(0,8));
        paymentConfirm.setFeeEndDate(endDate.replace("-","").substring(0,8));
        return myBatisDao.selectListT(PAYMENTCONFIRM, paymentConfirm, "selectAttendanceDetailsCus");
    }

    @Override
    public List<PaymentConfirmOther> exportPersonFee(PaymentConfirmDto paymentConfirm) {
        String startDate = paymentConfirm.getFeeStartDate();
        String endDate = paymentConfirm.getFeeEndDate();
        if (!StringUtil.isStringNotEmpty(startDate) || !StringUtil.isStringNotEmpty(endDate) ) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "参数不为空");
        }
//        paymentConfirm.setFeeStartDate(startDate.replace("-","").substring(0,6));
//        paymentConfirm.setFeeEndDate(endDate.replace("-","").substring(0,6));
          paymentConfirm.setFeeStartDate(startDate.replace("-","").substring(0,8));
          paymentConfirm.setFeeEndDate(endDate.replace("-","").substring(0,8));
        List<PaymentConfirmOther> list =
                myBatisDao.selectListT(PAYMENTCONFIRM, paymentConfirm, "exportPersonList");
        list.forEach(obj ->{
//            obj.setRequiredAttendanceDays(
//                    obj.getRequiredAttendanceDaysq() < obj.getRequiredAttendanceDayst()?
//                            obj.getRequiredAttendanceDaysq()+"":obj.getRequiredAttendanceDayst()+"");
            obj.setRequiredAttendanceDays(obj.getRequiredAttendanceDaysq()+"");
            obj.setActualAttendanceDays(
                    obj.getActualAttendanceDaysq() < obj.getActualAttendanceDayst()?
                            obj.getActualAttendanceDaysq()+"":obj.getActualAttendanceDayst()+"");
        });

        return list;
    }

    @Override
    public void cronJob() {
        List<VendorContractDto> list = vendorContractService.queryListExt();
        BpsWorkFlowDto bps = new BpsWorkFlowDto();
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String fmtDate = fmt.format(date);
        // 遍历合同记录
        list.stream().forEach(x-> {
            CommEmployee employee = commEmployeeService.getCommEmployeeInfo(x.getProManager());
            bps.setUserId(x.getProManager());
            bps.setUserName(employee.getEmpname());
            bps.setDepId(String.valueOf(employee.getDepid()));
            // 参数设置
            PaymentConfirm paymentConfirm = new PaymentConfirm();
            paymentConfirm.setFeeStartDate(x.getStartDate());
            paymentConfirm.setFeeEndDate(x.getEndDate());
            paymentConfirm.setContractNo(x.getContractNo());
            paymentConfirm.setProManager(x.getProManager());
            // 参数校验
            PaymentConfirmDto dto = JsonUtil.jsonToClass(paymentConfirm, PaymentConfirmDto.class);
            int tmp = this.checkParams(dto);
            if ( tmp < 0) {
                return;
            }
            // 主体信息查询 [不含有人员信息]
            PaymentConfirmDto basicPay = this.selectOne(paymentConfirm);
            // 无主体信息时
            if (Objects.isNull(basicPay)) {
                return;
            }
            // 人员信息列表为空时
            List<PaymentConfirmPersonDto> personList = this.queryPersonList(dto);
            if (Objects.isNull(personList) || personList.size() == 0) {
                return;
            }
            basicPay.setPayConfirmPersonList(personList);
            // 合同失效90天之后
            PaymentConfirm payExpire = new PaymentConfirm();
            payExpire.setContractNo(x.getContractNo());
            payExpire.setFeeStartDate(x.getStartDate());
            payExpire.setFeeEndDate(x.getEndDate());
            payExpire.setVendor(x.getVendor());
            Boolean flag = this.queryExpireResult(payExpire);
            if (Objects.isNull(flag) || !flag) {
                return;
            }
            // 重复申请 [维度：项目经理+合同编号+供应商+时间区间]
            HashMap map = new HashMap<>();
            map.put("contractNo", x.getContractNo());
            map.put("proManager", x.getProManager());
            map.put("vendor", x.getVendor());
            map.put("feeStartDate", x.getStartDate());
            map.put("feeEndDate", x.getEndDate());
            if (this.selectRepetition(map) > 0) {
                return;
            }
            try {
                basicPay.setFeeStartDate(x.getStartDate());
                basicPay.setFeeEndDate(x.getEndDate());
                basicPay.setVendor(x.getVendor());
                basicPay.setAmount(basicPay.getAmountRaw());
                basicPay.setAmountSalt(0.0);
                basicPay.setAmountTax(((basicPay.getAmountRaw()*100
                        + basicPay.getAmountRaw()*basicPay.getTaxRate()*100)/100));
                basicPay.setTaxRate(basicPay.getTaxRate()*100);
                basicPay.setProManager(x.getProManager());
                basicPay.setPaymentSubmitDate(fmtDate);
                this.cronDraft(basicPay, bps);
            } catch (Exception e) {
                log.info("异常执行-合同编号：" + basicPay.getContractNo());
            }
        });

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentConfirmDto cronDraft(PaymentConfirmDto applyDto, BpsWorkFlowDto bps) {

//        if (null != applyDto.getId()) {
//            this.updatePaymentConfirm(applyDto);
//            return applyDto;
//        }
        try {
            ProcessBean processBean;
            // 添加支付申请
            Long key = super.getNextSequence(PaymentConfirm.class);
            applyDto.setId(key);
            applyDto.setFlowId(FLOW_ID);
            applyDto.setCreateUser(bps.getUserId());
            applyDto.setApprovalStatus(ApprovalStatusEnums.DRAFT.getCode());
            this.addPaymentConfirm(applyDto);
            // 流程实例参数设置
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(applyDto.getProManager());// 提交人id
            processInfo.setStartUserId(applyDto.getProManager());// 流程发起人id
            processInfo.setStartUserName(applyDto.getProManager()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(key));
            // 开始并提交流程
            processBean = workFlowHttpClientService.startProcess(processInfo);
            // 流程实例id
            applyDto.setInstanceId(processBean.getProcessInstanceId());
            // 税率*100再更新
            applyDto.setTaxRate(applyDto.getTaxRate()*100);
            this.updatePaymentConfirm(applyDto);
            // 人员信息
            applyDto.getPayConfirmPersonList().stream().forEach(x -> {
                x.setInstanceId(applyDto.getInstanceId());
                paymentConfirmPersonService.addPaymentConfirmPerson(x);
            });
            // 日志处理
            bps.setProjectNo(String.valueOf(applyDto.getId()));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setApplydesc("支付确认申请发起");
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());
            bps.setProcessInstName("【" + bps.getUserName() + DateUtil.dateToNoBar(new Date()) + "】" + "支付确认申请");
            bps.setProcessInstDesc(FLOW_TITLE);
            bps.setSuggest("提交申请");
            //bps.setToDB(false);
            CamundaUtils.addFlowLog(bps, null);
            return applyDto;
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentConfirmDto draftPayment(PaymentConfirmDto applyDto, BpsWorkFlowDto bps) {

        // 合同失效90天之后，无法提交支付确认
        Boolean flag = this.queryExpireResult(applyDto);
        if (Objects.isNull(flag) || !flag) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "合同/文件已失效");
        }
        // 项目经理：同一部门代申请
        CommEmployee employee = commEmployeeService.getCommEmployeeInfo(applyDto.getProManager());
        if (Objects.isNull(employee)
                || !StringUtil.isNotBlank(String.valueOf(employee.getDepid()))
                || !String.valueOf(employee.getDepid()).equalsIgnoreCase(bps.getDepId())) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "跨部门申请");
        }
        if (null != applyDto.getId()) {
            this.updatePaymentConfirm(applyDto);
            // 人员信息
            // 历时数据删除
            PaymentConfirmPerson person = new PaymentConfirmPerson();
            person.setInstanceId(applyDto.getInstanceId());
            person.setIsDel("1");
            paymentConfirmPersonService.updateByInstanceId(person);
            // 新增记录
            applyDto.getPayConfirmPersonList().stream().forEach(x -> {
                x.setInstanceId(applyDto.getInstanceId());
                paymentConfirmPersonService.addPaymentConfirmPerson(x);
            });
            return applyDto;
        }
        // 申请记录唯一 [维度：项目经理+合同编号+供应商+时间区间]
        HashMap map = new HashMap<>();
        map.put("contractNo", applyDto.getContractNo());
        map.put("proManager", applyDto.getProManager());
        map.put("vendor", applyDto.getVendor());
        map.put("feeStartDate", applyDto.getFeeStartDate());
        map.put("feeEndDate", applyDto.getFeeEndDate());
        if (!StringUtil.isStringNotEmpty(applyDto.getInstanceId()) && this.selectRepetition(map) > 0 ) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "重复申请");
        }
        try {
            ProcessBean processBean;
            // 添加支付申请
            Long key = super.getNextSequence(PaymentConfirm.class);
            applyDto.setId(key);
            applyDto.setFlowId(FLOW_ID);
            applyDto.setCreateUser(bps.getUserId());
            applyDto.setApprovalStatus(ApprovalStatusEnums.DRAFT.getCode());
            this.addPaymentConfirm(applyDto);
            // 流程实例参数设置
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(bps.getUserId());// 提交人id
            processInfo.setStartUserId(bps.getUserId());// 流程发起人id
            processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(key));
            // 开始并提交流程
            processBean = workFlowHttpClientService.startProcess(processInfo);
            // 流程实例id
            applyDto.setInstanceId(processBean.getProcessInstanceId());
            // 税率*100再更新
            applyDto.setTaxRate(applyDto.getTaxRate()*100);
            this.updatePaymentConfirm(applyDto);
            // 人员信息
            applyDto.getPayConfirmPersonList().stream().forEach(x -> {
                x.setId(null);
                x.setInstanceId(applyDto.getInstanceId());
                paymentConfirmPersonService.addPaymentConfirmPerson(x);
            });
            // 日志处理
            bps.setProjectNo(String.valueOf(applyDto.getId()));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setApplydesc("支付确认申请发起");
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());
            bps.setProcessInstName("【" + bps.getUserName() + DateUtil.dateToNoBar(new Date()) + "】" + "支付确认申请");
            bps.setProcessInstDesc(FLOW_TITLE);
            bps.setSuggest("提交申请");
            bps.setToDB(false);
            CamundaUtils.addFlowLog(bps, null);
            return applyDto;
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String approvalCirculate(PaymentConfirmDto applyDto, BpsWorkFlowDto bps) {

        //提交数据校验
        vaildCommitData(applyDto,bps);

        ProcessBean processBean = null;
        try {
            //流转到二级部门经理
            String deptId = commEmployeeService.getDeptNo(bps.getUserId()).replaceAll("dep", "");
            CommEmployeeDto workManager = UsersUtil.getTwoWorkManager(deptId);
            // 审批人是否异常
            if (StringUtils.isBlank(workManager.getBadge())) {
                throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "当前用户无二级部经理");
            }
            // 判断流程实例是否存在，不存在说明是初始提交
            if (StringUtils.isBlank(applyDto.getInstanceId())) {
                // 添加外包准入流程记录
                Long key = super.getNextSequence(PaymentConfirm.class);
                applyDto.setId(key);
                applyDto.setFlowId(FLOW_ID);
                applyDto.setCreateUser(bps.getUserId());
                applyDto.setApprovalStatus(ApprovalStatusEnums.IN_APPROVAL.getCode());
                // 支付确认记录
                this.addPaymentConfirm(applyDto);
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
                // 更新流程id
                this.updatePaymentConfirm(applyDto);
                // 人员信息
                applyDto.getPayConfirmPersonList().stream().forEach(x -> {
                    x.setId(null);
                    x.setInstanceId(applyDto.getInstanceId());
                    paymentConfirmPersonService.addPaymentConfirmPerson(x);
                });
                // 日志处理
                bps.setProjectNo(String.valueOf(applyDto.getId()));
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setApplydesc("支付确认申请发起");
                bps.setRelaData(mPara);
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessInstName("【" + bps.getUserName() + DateUtil.dateToNoBar(new Date()) + "】" + "支付确认申请");
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
                // 更新记录
                applyDto.setApprovalStatus(ApprovalStatusEnums.IN_APPROVAL.getCode());
                this.updatePaymentConfirm(applyDto);
                // 人员信息历史记录<清除>
                PaymentConfirmPerson person = new PaymentConfirmPerson();
                person.setInstanceId(applyDto.getInstanceId());
                person.setIsDel("1");
                paymentConfirmPersonService.updateByInstanceId(person);
                // 新增人员信息记录<id置空>
                applyDto.getPayConfirmPersonList().stream().forEach(x -> {
                    x.setId(null);
                    x.setInstanceId(applyDto.getInstanceId());
                    paymentConfirmPersonService.addPaymentConfirmPerson(x);
                });
                // 日志处理
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(applyDto.getInstanceId());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setSuggest("提交申请");
                bps.setApplydesc("支付确认申请发起");
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
            log.error("支付确认申请发起流程失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String confirm(PaymentConfirmDto applyDto, BpsWorkFlowDto bps) {
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
            this.updatePaymentConfirm(applyDto);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(applyDto.getInstanceId());
            bps.setApplydesc("二级部经理审批");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(applyDto.getInstanceId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("二级部经理审批失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    public  Boolean queryExpireResult(PaymentConfirm entity) {
        if (Objects.isNull(entity)) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "参数不为空");
        }
        return myBatisDao.selectClass(PAYMENTCONFIRM, entity, "selectExpireResult");
    }

    @Override
    public  int isContractJoinByOutsource(PaymentConfirmDto dto) {
        if (Objects.isNull(dto)) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "参数不为空");
        }
        return myBatisDao.selectClass(PAYMENTCONFIRM, dto, "selectCountJoinContract");
    }

    @Override
    public List<PaymentConfirmDto> getNotFinishList(PaymentConfirmDto paymentConfirmDto) {
        PaymentConfirm paymentConfirm = JsonUtil.jsonToClass(paymentConfirmDto, PaymentConfirm.class);
        List<PaymentConfirm> data = myBatisDao.selectListT(PAYMENTCONFIRM, paymentConfirm,"getNotFinishList");
        return JsonUtil.jsonToList(data,PaymentConfirmDto.class);
    }

    /**
     * 对提交的数据做校验
     * @param applyDto 对象
     * @param bps 流程对象
     */
    private void vaildCommitData(PaymentConfirmDto applyDto,BpsWorkFlowDto bps){
        // 外包花名册下若有属于人力性质的人员未关联合同，则无法提交
        int countByOutsource = this.isContractJoinByOutsource(applyDto);
        if (countByOutsource > 0) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "外包花名册存有合同未关联");
        }
        // 合同失效90天之后，无法提交支付确
        Boolean flag = this.queryExpireResult(applyDto);
        if (Objects.isNull(flag) || !flag) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "合同/文件已失效");
        }
        // 项目经理：不能跨部门代申请
        CommEmployee employee = commEmployeeService.getCommEmployeeInfo(applyDto.getProManager());
        if (Objects.isNull(employee)
                || !StringUtil.isNotBlank(String.valueOf(employee.getDepid()))
                || !String.valueOf(employee.getDepid()).equalsIgnoreCase(bps.getDepId())) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "跨部门申请");
        }
        // 申请记录唯一 [维度：项目经理+合同编号+供应商+时间区间]
        HashMap map = new HashMap<>();
        map.put("contractNo", applyDto.getContractNo());
        map.put("proManager", applyDto.getProManager());
        map.put("vendor", applyDto.getVendor());
        map.put("feeStartDate", applyDto.getFeeStartDate());
        map.put("feeEndDate", applyDto.getFeeEndDate());
        if (!StringUtil.isStringNotEmpty(applyDto.getInstanceId()) && this.selectRepetition(map) > 0 ) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "重复申请");
        }

        List<PaymentConfirmPersonDto> confirmPersonDtos = applyDto.getPayConfirmPersonList();
        confirmPersonDtos.forEach(confirmPersonDto -> {
            PpmKqRecordDto kqRecordDto = new PpmKqRecordDto();
            kqRecordDto.setWhetherHandle("1");
            kqRecordDto.setJobId(confirmPersonDto.getJobId());
            kqRecordDto.setAttendanceDateSt(applyDto.getFeeStartDate());
            kqRecordDto.setAttendanceDateEnd(applyDto.getFeeEndDate());
            List<PpmKqRecordDto> recordDtos = ppmKqRecordService.queryBaseInfoList(kqRecordDto);
            if(recordDtos != null && !recordDtos.isEmpty()){
                throw new BizException(ApiCode.FAIL,"周期内存在未处理的异常考勤，请先处理完异常流程！");
            }
        });
    }
}