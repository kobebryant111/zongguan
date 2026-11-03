package com.work.ppm.application.dto.rdmanager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 新增研发项目
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RdContractPurchaseDto {
    /**
     * 合同编号
     */
    private String htbh;
    /**
     * 合同名称
     */
    private String htmc;
    /**
     * 合同开始时间
     */
    private long htksrq;
    /**
     * 合同结束时间
     */
    private long htzzrq;
    /**
     * 维保开始时间
     */
    private long wbkssj;
    /**
     * 维保结束时间
     */
    private long wbjssj;
    /**
     * 合同金额
     */
    private BigDecimal htje;
    /**
     * 税率
     */
    private int sl;
    /**
     * 发票类型
     */
    private String fplx;
    /**
     * 供应商名称
     */
    private String yf;
    /**
     * 付款信息
     */
    private ArrayList<RdContractPayDto> fklist;
}
