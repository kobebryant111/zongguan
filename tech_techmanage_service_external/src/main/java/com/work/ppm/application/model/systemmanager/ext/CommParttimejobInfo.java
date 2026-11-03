package com.work.ppm.application.model.systemmanager.ext;

import com.work.ppm.application.model.systemmanager.CommParttimejob;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class CommParttimejobInfo extends CommParttimejob {
    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 部门名称
     */
    private String depName;
    /**
     * 岗位
     */
    private String jobName;
}
