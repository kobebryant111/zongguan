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
 * 费用结算
 * </p>
 * @since 2022-11-13
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PPM_EXPENSE_SETTLEMENT")
public class ExpenseSettlement extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Long id;
    /**
     * 流程实例
     */
    private String instanceId;
    private String flowId;
    /**
     * 审批状态
     */
    private String approvalStatus;
    /**
     * 供应商名称
     */
    private String vendorName;
    /**
     * 供应商编号
     */
    private String vendorCode;
    /**
     * 税率
     */
    private Double taxRate;
    /**
     * 应付金额 BigDecimal
     */
    private Double amount;
    /**
     * 实付金额 BigDecimal
     */
    private Double amountTax;
    /**
     * 逻辑删除
     */
    private String isDel;
    /**
     * 项目经理
     */
    private String proManager;

    /**
     * 支付确认记录id: 逗号分隔
     */
    private String payConfirmId;

    /**
     * remarks
     */
    private String remark;


}