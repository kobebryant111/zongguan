package com.work.ppm.application.services.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.vendorcontract.AttendanceInfoDto;
import com.work.ppm.application.dto.vendorcontract.PaymentConfirmDto;
import com.work.ppm.application.dto.vendormanager.PaymentConfirmPersonDto;
import com.work.ppm.application.model.vendormanager.PaymentConfirm;
import com.work.ppm.application.model.vendormanager.PaymentConfirmOther;
import com.work.ppm.commons.pager.Pager;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 费用支付确认 接口描述
 * </p>
 *
 * @since 2022-11-06
 */


public interface IPaymentConfirmService {

    /**
     * 分页
     * @param paymentConfirm 对象
     * @param pager 分页对象
     */
    void queryPager(PaymentConfirm paymentConfirm, Pager pager);

    /**
     * 详情
     * @param id
     */
    PaymentConfirmDto queryDetailsById(Long id);
    /**
     * 支付确认：依据流程实例id查询详情
     * @param instanceId /
     */
    PaymentConfirmDto queryDetailsByInstanceId(String instanceId);

    /**
     * 支付确认：重复记录查询
     * @param map /
     */
    long selectRepetition(HashMap map);

    /**
     * 新增
     * @param paymentConfirm 实例
     */
    Integer addPaymentConfirm(PaymentConfirm paymentConfirm);

    /**
     * 编辑
     * @param paymentConfirm 实例
     */
    Integer updatePaymentConfirm(PaymentConfirm paymentConfirm);

    /**
     * 依据实例id更新记录
     * @param paymentConfirm 实例
     */
    int updateByInstanceId(PaymentConfirm paymentConfirm);

    /**
     * 流程删除
     * @param paymentConfirm 实例
     */
    int deleteFlowPayConfirm(PaymentConfirm paymentConfirm);

    /**
     * 支付确认：主体数据的查询[未包含人员信息]
     * @param paymentConfirm 实例
     */
    PaymentConfirmDto selectOne(PaymentConfirm paymentConfirm);

    /**
     * 支付确认：当前项目经理下[人力性质的]人员信息查询
     * @param paymentConfirm /
     */
    List<PaymentConfirmPersonDto> queryPersonList(PaymentConfirmDto paymentConfirm);

    /**
     * 支付确认-详情：人员考勤详情的导出
     * @param paymentConfirm /
     */
    List<AttendanceInfoDto> queryAttendanceDetails(PaymentConfirmDto paymentConfirm);

    /**
     * 支付确认-详情：人员信息的导出
     * @param paymentConfirm /
     */
    List<PaymentConfirmOther> exportPersonFee(PaymentConfirmDto paymentConfirm);

    /**
     * 定时任务：入口方法
     */
    void cronJob();

    /**
     * 流程审批：草稿发起[manual]
     * @param applyDto /
     * @param bps /
     */
    PaymentConfirmDto draftPayment(PaymentConfirmDto applyDto, BpsWorkFlowDto bps);

    /**
     * 定时任务：流程草稿[auto]
     * @param applyDto /
     * @param bps /
     */
    PaymentConfirmDto cronDraft(PaymentConfirmDto applyDto, BpsWorkFlowDto bps);
    /**
     * 流程审批：项目经理发起
     * @param applyDto /
     * @param bps /
     */
    String approvalCirculate(PaymentConfirmDto applyDto, BpsWorkFlowDto bps);
    /**
     * 流程审批：二级部经理确认
     * @param applyDto /
     * @param bps /
     */
    String confirm(PaymentConfirmDto applyDto, BpsWorkFlowDto bps);

    /**
     * 支付有效性确认：依据合同的有效日期
     * @param entity /
     */
    Boolean queryExpireResult(PaymentConfirm entity);

    /**
     * 费用结算-新增：查询-支付确认查询[已审批通过]
     * @param paymentConfirm /
     * @param page /
     */
    void queryForSettle(PaymentConfirmDto paymentConfirm, Pager page);

    /**
     * select more by ids
     * @param ids /
     */
    List<PaymentConfirm> queryListByIds(List ids);

    /**
     * 查询外包花名册中[项目经理,供应商,人力性质]3个维度下的记录是否有合同未关联性
     * @param dto /
     */
    int isContractJoinByOutsource(PaymentConfirmDto dto);

    /**
     * 查询支付确认未审批通过的数据
     * @param paymentConfirmDto 对象
     * @return List<PaymentConfirmDto>
     */
    List<PaymentConfirmDto> getNotFinishList(PaymentConfirmDto paymentConfirmDto);

}