package com.work.ppm.application.model.rdmanager;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目合同
 *
 */
@Data
public class RdProjectContract {

    /**
     * 主键
     */
    @TableId(value = "PK_ID", type = IdType.UUID)
    private String pkId;


    /**
     * 预算编号
     */
    @NotBlank(message = "预算编号不能为空")
    @TableField
    private String budgetCode;


    /**
     * 预算名称
     */
    @NotBlank(message = "预算名称不能为空")
    @TableField
    private String budgetName;

    /**
     * 合同编号
     */
    @NotBlank(message = "合同编号不能为空")
    @TableField
    private String contractCode;

    /**
     * 合同名称
     */
    @NotBlank(message = "合同名称不能为空")
    @TableField
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
    @TableField
    private String contractStartDate;

    /**
     * 合同结束时间
     */
    @TableField
    private String contractEndDate;
    /**
     * 维保开始时间
     */
    @TableField
    private String maintenanceStartDate;
    /**
     * 维保结束时间
     */
    @TableField
    private String maintenanceEndDate;
    /**
     * 合同金额
     */
    @TableField
    private String contractAmount;

    /**
     * 支付进度
     */
//    @TableField
    private String paymentSche;

    /**
     * 项目分摊比例
     */
    @TableField
    private String projectAllocationRatio;


    /**
     * 类型
     */
    @TableField
    private String type;


    /**
     * 预算金额（元）
     */
    @TableField
    private String budgetAmount;

    /**
     * 供应商(计划)
     */
    @TableField
    private String supplier;

    /**
     * 审批通过时间
     */
    @TableField
    private String approvedDate;

    /**
     * 是否为研发项目 y是 n 否
     */
    @TableField
    private String isResearchProject;


    /**
     * 科技项目技术创新点
     */
    @TableField
    private String techInnoPoint;


    /**
     * 合同税点
     */
    @TableField
    private String contractTaxPoints;


    /**
     * 供应商全称
     */
    @TableField
    private String supplierName;

    /**
     * 费用类型
     */
    @TableField
    private String expenseType;


    /**
     * 报销项目
     */
    @TableField
    private String reimbursementItem;


    /**
     * 对应会计科目
     */
    @TableField
    private String accountSubject;


    /**
     * 是否取尾款发票 y是 n 否
     */
    @TableField
    private String isInvoice;


    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;

    /**
     * 支付进度
     */
//    @TableField
    private BigDecimal paymentScheduling;


    /**
     * 验收状态
     */
    @TableField
    private String acceptancePhase;


    /**
     * 是否删除 0 正常  1删除
     */
    @TableField
    private String deleted;

    /**
     * 发票类型 Z增值税专用发票、P增值税普通发票
     */
    @TableField
    private String invoiceType;
    /**
     * 备注栏
     */
    @TableField
    private String remarkLan;

    /**
     * 软件研发人月
     */
    @TableField
    private String softwareManpower;

    /**
     * 辅助研发人月
     */
    @TableField
    private String assistManpower;

    /**
     * 风控研发人月
     */
    @TableField
    private String riskManagementManpower;

}
