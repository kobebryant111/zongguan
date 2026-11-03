package com.work.ppm.application.model.rdmanager;

import lombok.Data;

@Data
public class RdQueryEmployee {
    /**
     * 请求类型
     */
    private String rdQueryType;
    /**
     * 请求时间
     */
    private String attendanceDate;
    /**
     * 姓名
     **/
    private String empname;
    /**
     * 所属部门
     */
    private String chargeName;
}
