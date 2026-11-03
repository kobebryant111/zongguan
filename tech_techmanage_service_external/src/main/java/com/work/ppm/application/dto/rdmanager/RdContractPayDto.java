package com.work.ppm.application.dto.rdmanager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 新增研发项目
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RdContractPayDto {
    /**
     * 付款编号
     */
    private String fkbh;
    /**
     * 付款批次
     */
    private int fkpc;
    /**
     * 支付金额
     */
    private BigDecimal zfje;
    /**
     * 支付状态
     */
    private String zfzt;
    /**
     * 完成时间
     */
    private long wcsj;
}
