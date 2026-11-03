package com.work.ppm.application.model.vendormanager;

import com.work.ppm.commons.annotaion.Id;
import com.work.ppm.commons.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
public class PaymentConfirmApply extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Long id;
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
    /**
     * remark
     */
    private String remark;

}