package com.work.ppm.application.dto.rdmanager;

import com.baomidou.mybatisplus.annotation.TableField;
import com.work.ppm.application.model.rdmanager.RdContractPayRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

/**
 * 项目合同
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RdPurchaseContract {
    /**
     * 主键
     */
    private String pkId;
    /**
     * 预算编号
     */
//    @NotBlank(message = "预算编号不能为空")
    private String budgetCode;
    private String budgetName;
    /**
     * 合同编号
     */
//    @NotBlank(message = "合同编号不能为空")
    private String contractCode;
    /**
     * 合同名称
     */
    private String contractName;
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
     * 供应商全称
     */
    private String supplierName;
    /**
     * 发票类型 Z增值税专用发票、P增值税普通发票
     */
    private String invoiceType;
    /**
     * 合同税点
     */
    private String contractTaxPoints;
    /**
     * 合同金额
     */
    private String contractAmount;
    /**
     * 付款信息
     */
    private ArrayList<RdContractPayRecord> contractPayRecords;
}
