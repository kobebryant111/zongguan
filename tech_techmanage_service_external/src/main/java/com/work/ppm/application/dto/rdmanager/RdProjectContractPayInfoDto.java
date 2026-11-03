package com.work.ppm.application.dto.rdmanager;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 新增研发项目
 *
 */
@Data
public class RdProjectContractPayInfoDto {


    /**
     * 合同id
     */
    @NotBlank(message = "合同id不能为空")
    @ApiModelProperty(value = "合同id")
    private String rdProjectContractId;

    /**
     * 合同编号
     */
    @NotBlank(message = "合同编号不能为空")
    private String contractCode;

    /**
     * 合同名称
     */
    @NotBlank(message = "合同名称不能为空")
    private String contractName;

    /**
     * 是否有项目开始时间
     */
    private String contractStartDateFlag;

    /**
     * 是否有项目结束时间
     */
    private String contractEndDateFlag;
    /**
     * 合同开始时间
     */
    private String contractStartDate;

    /**
     * 合同结束时间
     */
    private String contractEndDate;
    /**
     * 维保开始时间
     */
    private String maintenanceStartDate;
    /**
     * 维保结束时间
     */
    private String maintenanceEndDate;

    /**
     * 合同金额
     */
    private String contractAmount;

    /**
     * 供应商全称
     */
    private String supplierName;

    /**
     * 支付进度
     */
    private String paymentScheduling;


    /**
     * 验收状态
     */
    private String acceptancePhase;

    /**
     * 合同税点
     */
    private String contractTaxPoints;

    /**
     * 发票类型
     */
    private String invoiceType;


}
