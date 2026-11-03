package com.work.ppm.application.model.depkpi;

import lombok.Data;

/**
 * 代办查询数据类
 */
@Data
public class PpmDepKpiDb {
    /**
     * 代办类型
     */
    private String dbtype;
    /**
     *	状态：0：草稿中；1：待初审；2：待终审；3：执行中；4：待自评；5：待终评；6：已完成；
     **/
    private Long status;
    /**
     * 数量
     */
    private Long num;

}
