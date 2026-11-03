package com.work.ppm.application.model.integratedmanager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 供应商合同外包关联实体
 * </p>
 *
 * @since 2026-03-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PpmSupplierOutsourcingRequest extends PpmSupplierOutsourcing {
    /**
     * 批量删除
     */
    private List<PpmSupplierOutsourcing> list;

}
