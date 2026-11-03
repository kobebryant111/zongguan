package com.work.ppm.application.model.systemmanager.ext;

import com.work.ppm.application.model.systemmanager.CommRole;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class CommRoleInfo extends CommRole {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 登录名称
     */
    private String operatorName;
    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 机构编号
     */
    private String compId;
}
