package com.work.ppm.application.model.rdmanager;

import lombok.Data;

@Data
public class RdQueryDeptType {
    /**
     * 请求类型
     */
    private String rdQueryType;
    /**
     * 请求时间
     */
    private String attendanceDate;
}
