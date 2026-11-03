package com.work.ppm.application.dto.rdmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 合同管理-批量新建 DTO
 *
 * @date 2026/2/10
 */
@Data
public class ContractBatchCreationDto {

    /**
     * 预算编号
     */
    @Excel(name = "预算编号", orderNum = "1")
    @ApiModelProperty(value = "预算编号")
    private String budgetCode;

    /**
     * 预算名称
     */
    @Excel(name = "预算名称", orderNum = "2")
    @ApiModelProperty(value = "预算名称")
    private String budgetName;

    /**
     * 类型
     */
    @Excel(name = "类型", orderNum = "3",
            replace = {"系统开发_kaifa","系统测试_ceshi","运行维护_yunwei","设备采购_caigou","科技项目咨询_zixun","外购成品软件_waigou","其他_qita","_null"})
    @ApiModelProperty(value = "类型")
    private String type;

    /**
     * 供应商(计划)
     */
    @Excel(name = "供应商(计划)", orderNum = "4")
    @ApiModelProperty(value = "供应商(计划)")
    private String supplier;

    /**
     * 预算金额（万元）
     */
    @Excel(name = "预算金额（万元）", orderNum = "5")
    @ApiModelProperty(value = "预算金额（万元）")
    private String budgetAmount;

    /**
     * 审批通过时间
     */
    @Excel(name = "审批通过时间", orderNum = "6")
    @ApiModelProperty(value = "审批通过时间")
    private String approvedDate;

    /**
     * 软件研发人月
     */
    @Excel(name = "软件研发人月", orderNum = "7")
    @ApiModelProperty(value = "软件研发人月")
    private String softwareManpower;

    /**
     * 辅助研发人月
     */
    @Excel(name = "辅助研发人月", orderNum = "8")
    @ApiModelProperty(value = "辅助研发人月")
    private String assistManpower;

    /**
     * 风控研发人月
     */
    @Excel(name = "风控研发人月", orderNum = "9")
    @ApiModelProperty(value = "风控研发人月")
    private String riskManagementManpower;

    /**
     * 关联项目编码
     */
    @Excel(name = "关联项目编码", orderNum = "10")
    @ApiModelProperty(value = "关联项目编码")
    private String rdProjectCode;

    /**
     * 关联项目
     */
    @Excel(name = "关联项目", orderNum = "11")
    @ApiModelProperty(value = "关联项目")
    private String rdProjectName;

    /**
     * 是否为研发项目
     */
    @Excel(name = "是否为研发项目", orderNum = "12", replace = {"是_Y","否_N","_null"})
    @ApiModelProperty(value = "是否为研发项目")
    private String isResearchProject;

    /**
     * 占比
     */
    @Excel(name = "占比(%)", orderNum = "13")
    @ApiModelProperty(value = "占比(%)")
    private BigDecimal proportion;

    /**
     * 合同编号
     */
    @Excel(name = "合同编号", orderNum = "14")
    @ApiModelProperty(value = "合同编号")
    private String contractCode;

    /**
     * 合同名称
     */
    @Excel(name = "合同名称", orderNum = "15")
    @ApiModelProperty(value = "合同名称")
    private String contractName;

    /**
     * 合同开始时间
     */
    @Excel(name = "合同开始时间", orderNum = "16")
    @ApiModelProperty(value = "合同开始时间")
    private String contractStartDate;

    /**
     * 合同结束时间
     */
    @Excel(name = "合同结束时间", orderNum = "17")
    @ApiModelProperty(value = "合同结束时间")
    private String contractEndDate;

    /**
     * 合同金额（万元）
     */
    @Excel(name = "合同金额（万元）", orderNum = "18")
    @ApiModelProperty(value = "合同金额（万元）")
    private String contractAmount;

    /**
     * 供应商全称
     */
    @Excel(name = "供应商全称", orderNum = "19")
    @ApiModelProperty(value = "供应商全称")
    private String supplierName;

    /**
     * 费用类型
     */
    @Excel(name = "费用类型", orderNum = "20")
    @ApiModelProperty(value = "费用类型")
    private String expenseType;

    /**
     * 报销项目
     */
    @Excel(name = "报销项目", orderNum = "21")
    @ApiModelProperty(value = "报销项目")
    private String reimbursementItem;

    /**
     * 对应会计科目
     */
    @Excel(name = "对应会计科目", orderNum = "22")
    @ApiModelProperty(value = "对应会计科目")
    private String accountSubject;

    /**
     * 是否取尾款发票
     */
    @Excel(name = "是否取尾款发票", orderNum = "23", replace = {"是_true","否_false","_null"})
    @ApiModelProperty(value = "是否取尾款发票")
    private String isInvoice;

    /**
     * 初始化 校验入参
     * @return 结果
     */
    public boolean init() {
        return budgetCode != null &&
                budgetName != null &&
                type != null &&
                supplier != null &&
                StringUtil.isNumeric(budgetAmount) &&
                StringUtil.isValidDateYmd(approvedDate) &&
                StringUtil.isNumericWithTwoDecimals(softwareManpower) &&
                StringUtil.isNumericWithTwoDecimals(assistManpower) &&
                StringUtil.isNumericWithTwoDecimals(riskManagementManpower) &&
                rdProjectCode != null &&
                rdProjectName != null &&
                isResearchProject != null &&
                proportion != null &&
                contractCode != null &&
                contractName != null &&
                StringUtil.isNumeric(contractAmount) &&
                supplierName != null &&
                expenseType != null &&
                reimbursementItem != null &&
                accountSubject != null &&
                isInvoice != null;
    }
}
