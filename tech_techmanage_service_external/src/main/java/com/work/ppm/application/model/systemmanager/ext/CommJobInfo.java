package com.work.ppm.application.model.systemmanager.ext;

import com.work.ppm.application.model.systemmanager.CommJob;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class CommJobInfo extends CommJob {
    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 部门名称
     */
    private String deptName;
}
