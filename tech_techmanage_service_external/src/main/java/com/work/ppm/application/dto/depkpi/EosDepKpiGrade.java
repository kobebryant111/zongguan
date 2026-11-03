package com.work.ppm.application.dto.depkpi;

import lombok.Data;

/**
 * 考核层级对象
 */
@Data
public class EosDepKpiGrade {
    /**
     * 部门层级
     */
    private Long dep;
    /**
     * 中心层级
     */
    private Long cen;
    /**
     * 一级部层级
     */
    private Long tec;

}
