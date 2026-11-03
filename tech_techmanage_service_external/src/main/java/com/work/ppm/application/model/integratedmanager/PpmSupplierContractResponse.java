package com.work.ppm.application.model.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 供应商合同信息响应实体
 * </p>
 *
 * @since 2026-03-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ExcelTarget("ppmSupplierContractResponse")
public class PpmSupplierContractResponse extends PpmSupplierContract {

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称", orderNum = "3")
    private String supplierName;
    /**
     * 有效期起止
     */
    private String validPeriod;
    /**
     * 关联人数
     */
    private Integer outsourceNum;
    /**
     * 关联外包人员
     */
    private List<PpmSupplierOutsourcing> outsourceList;

}
