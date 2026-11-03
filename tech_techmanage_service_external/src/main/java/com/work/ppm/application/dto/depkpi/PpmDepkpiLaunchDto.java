package com.work.ppm.application.dto.depkpi;

import lombok.Data;

/**
 * 发起中心接收参数
 */
@Data
public class PpmDepkpiLaunchDto {
    /**
     * id
     */
    private Long id;
    /**
     * 考核内容
     */
    private String content;
    /**
     * 部门考核类型
     */
    private String itype;
    /**
     * 年份
     */
    private String tyear;

    /**
     * 部门级别
     */
    private Long sign;
}
