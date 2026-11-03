package com.work.ppm.application.dto.rdmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 财务核算信息
 *
 */
@Data
public class RdProjectContractFinancialInfoDto {


    /**
     * 合同id
     */
    @NotBlank(message = "合同id不能为空")
    @ApiModelProperty(value = "合同id")
    private String rdProjectContractId;

    /**
     * 费用类型
     */
    @NotBlank(message = "费用类型不能为空")
    private String expenseType;

    /**
     * 报销项目
     */
    @NotBlank(message = "报销项目不能为空")
    private String reimbursementItem;


    /**
     * 对应会计科目
     */
    private String accountSubject;

    /**
     * 是否取尾款发票
     */
    private String isInvoice;
    /**
     * 备注栏
     */
    private String remarkLan;

}
