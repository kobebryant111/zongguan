package com.work.ppm.application.dto.systemmanager;

import lombok.Data;

/**
 * 角色session
 */
@Data
public class ImRoleDoLoginDto {
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色名称
     */
    private String roleName;

    /**
     是否特殊菜单(0:特殊，1：正常)
     */
    private Integer falgSpecialRole;
}
