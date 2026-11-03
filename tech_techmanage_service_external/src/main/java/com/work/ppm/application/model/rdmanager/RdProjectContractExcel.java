package com.work.ppm.application.model.rdmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@ExcelTarget("RdProjectContractExcel")
public class RdProjectContractExcel {
    /**
     * 预算编号
     */
    @Excel(name = "预算编号", orderNum = "1")
    private String budgetCode;

    /**
     * 预算名称
     */
    @Excel(name = "预算名称", orderNum = "2")
    private String budgetName;
    /**
     * 类型
     */
    @Excel(name = "类型",replace = {"系统开发_kaifa","系统测试_ceshi","运行维护_yunwei","设备采购_caigou","科技项目咨询_zixun",
            "外购成品软件_waigou","其他_qita","-_null"},orderNum = "3")
    private String type;
    /**
     * 供应商(计划)
     */
    @Excel(name = "供应商(计划)", orderNum = "4")
    private String supplier;
    /**
     * 预算金额（元）
     */
    @Excel(name = "预算金额（万元）", orderNum = "5")
    private String budgetAmount;
    /**
     * 审批通过时间
     */
    @Excel(name = "审批通过时间", orderNum = "6")
    private String approvedDate;
    /**
     * 关联项目编码
     */
    @Excel(name = "关联项目编码", orderNum = "7")
    private String projectCode;
    /**
     * 关联项目
     */
    @Excel(name = "关联项目", orderNum = "8")
    private String projectName;
    /**
     * 是否为研发项目
     */
    @Excel(name = "是否为研发项目", replace = {"是_Y","否_N","_null"} , orderNum = "9")
    private String isResearchProject;
    /**
     * 占比
     */
    @Excel(name = "占比", suffix = "%", orderNum = "10")
    private double ratio;
    /**
     * 合同编号
     */
    @Excel(name = "合同编号", orderNum = "11")
    private String contractCode;
    /**
     * 合同名称
     */
    @Excel(name = "合同名称", orderNum = "12")
    private String contractName;
    /**
     * 合同开始时间
     */
    @Excel(name = "合同开始时间", orderNum = "13")
    private String contractStartDate;

    /**
     * 合同结束时间
     */
    @Excel(name = "合同结束时间", orderNum = "14")
    private String contractEndDate;

    /**
     *维保开始时间
     */
    @Excel(name = "维保开始时间", orderNum = "15")
    private String maintenanceStartDate;
    /**
     * 维保结束时间
     */
    @Excel(name = "维保结束时间", orderNum = "16")
    private String maintenanceEndDate;
    /**
     * 合同金额
     */
    @Excel(name = "合同金额（万元）", orderNum = "17")
    private String contractAmount;
    /**
     * 合同税点
     */
    @Excel(name = "合同税点",suffix = "%",orderNum = "18")
    private String contractTaxPoints;
    /**
     * 发票类型 Z增值税专用发票、P增值税普通发票
     */
    @Excel(name = "发票类型",replace = {"增值税专用发票_Z","增值税普通发票_P","-_null"},orderNum = "19")
    private String invoiceType;
    /**
     * 供应商全称
     */
    @Excel(name = "供应商全称",orderNum = "20")
    private String supplierName;
    /**
     * 支付进度
     */
    @Excel(name = "支付进度", suffix = "%", orderNum = "21")
    private BigDecimal paymentScheduling;
    /**
     * 验收状态
     */
    @Excel(name = "验收状态",orderNum = "22")
    private String acceptancePhase;
    /**
     * 费用类型
     */
    @Excel(name = "费用类型",orderNum = "23")
    private String expenseType;
    /**
     * 报销项目
     */
    @Excel(name = "报销项目",orderNum = "24")
    private String reimbursementItem;

    /**
     * 对应会计科目
     */
    @Excel(name = "对应会计科目",orderNum = "25")
    private String accountSubject;

    /**
     * 是否取尾款发票 y是 n 否
     */
    @Excel(name = "是否取尾款发票",replace = {"是_true","否_false","-_null"},orderNum = "26")
    private String isInvoice;
}
