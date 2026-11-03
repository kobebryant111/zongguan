package com.work.ppm.application.dto.rdmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改合同信息
 *
 */
@Data
public class RdProjectContractInfoDto {

    /**
     * 合同id
     */
    @ApiModelProperty(value = "合同id")
    private String rdProjectContractId;

    /**
     * 预算编号
     */
    @ApiModelProperty(value = "预算编号")
    private String budgetCode;

    /**
     * 预算名称
     */
    @ApiModelProperty(value = "预算名称")
    private String budgetName;

    /**
     * 预算金额
     */
    @ApiModelProperty(value = "预算金额")
    private String budgetAmount;


    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String type;


    /**
     * 供应商(计划)
     */
    @ApiModelProperty(value = "供应商(计划)")
    private String supplier;


    /**
     * 审批通过时间
     */
    @ApiModelProperty(value = "审批通过时间")
    private String approvedDate;

    /**
     * 软件研发人月
     */
    @ApiModelProperty(value = "软件研发人月")
    private String softwareManpower;

    /**
     * 辅助研发人月
     */
    @ApiModelProperty(value = "辅助研发人月")
    private String assistManpower;

    /**
     * 风控研发人月
     */
    @ApiModelProperty(value = "风控研发人月")
    private String riskManagementManpower;

}
