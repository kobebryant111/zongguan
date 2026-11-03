package com.work.ppm.application.model.integratedmanager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 供应商合同信息实体
 * </p>
 *
 * @since 2026-03-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PpmSupplierContractRequest extends PpmSupplierContract {

    /**
     * 关联状态：Y-有效 N-失效
     */
    private String relateState;

}
