package com.work.ppm.application.model.vendormanager;

import com.work.ppm.commons.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.work.ppm.commons.annotaion.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>
 * 费用支付确认
 * </p>
 * @since 2022-11-06
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PPM_PAYMENT_CONFIRM")
public class PaymentConfirm extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Long id;
    private String instanceId;
    private String flowId;
    /**
     * 流转到费用结算: 0：待流转；1：已流转
     */
    private String flowStatus;
    /**
     * 审批通过时确认时间
     */
    private String approvalPassDate;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 合同名称
     */
    private String contractName;
    /**
     * 供应商
     */
    private String vendor;
    /**
     * 支付提交日期：项目经理提交支付流程日期
     */
    private String paymentSubmitDate;
    /**
     * 费用开始日期
     */
    private String feeStartDate;
    /**
     * 费用结束日期
     */
    private String feeEndDate;
    /**
     * 审批状态
     */
    private String approvalStatus;
    /**
     * 逻辑删除
     */
    private String isDel;
    /**
     * 项目经理(姓名不带工号)
     */
    private String proManager;
    /**
     * 应付金额（初始阶段）
     */
    private Double amountRaw;
    /**
     * 调整金额
     */
    private Double amountSalt;
    /**
     * 应付总金额
     */
    private Double amount;
    /**
     * 应付总金额(含税)
     */
    private Double amountTax;
    /**
     * 税率
     */
    private Double taxRate;
    private String remark;

}