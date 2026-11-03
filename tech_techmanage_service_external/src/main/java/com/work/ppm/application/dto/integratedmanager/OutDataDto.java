package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;

/**
 * 外包档案传入参数
 */
@Data
public class OutDataDto {
    /**
     * 工号
     */
    private String jobid;
    /**
     * 年份
     */
    private String year;
    /**
     * 部门编码
     */
    private String deptno;
    /**
     * 公司编号
     */
    private String outsourceno;


}
