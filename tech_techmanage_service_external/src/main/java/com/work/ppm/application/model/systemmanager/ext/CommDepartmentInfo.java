package com.work.ppm.application.model.systemmanager.ext;

import com.work.ppm.application.model.systemmanager.CommDepartment;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class CommDepartmentInfo extends CommDepartment {
    /**
     * 分行IT支持部经理名称
     */
    private String empname;
    /**
     * 分行IT支持部经理id
     */
    private String badge;
    /**
     *分行运营部总经理名称
     */
    private String username;
    /**
     *分行运营部总经理id
     */
    private String userid;
    /**
     * 分管总经理
     */
    private String fgManager;

    /**
     * 分管总经理名称
     */
    private String fgManagername;
}
