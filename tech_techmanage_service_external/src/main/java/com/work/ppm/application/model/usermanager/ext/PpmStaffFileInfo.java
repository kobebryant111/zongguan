package com.work.ppm.application.model.usermanager.ext;

import com.work.ppm.application.model.usermanager.PpmStaffFile;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class PpmStaffFileInfo extends PpmStaffFile {
    /**
     * 部门名称
     */
    private String depName;
    /**
     * 岗位名称
     */
    private String jobName;
    /**
     * 邮件
     */
    private String email;
    /**
     * 文本
     */
    private String contel;
    /**
     * 分组名称
     */
    private String groupName;
}
