package com.work.ppm.application.dto.systemmanager;

import lombok.Data;

/**
 * 角色菜单
 */
@Data
public class ImPermissionsDto {
    /**
     * 菜单id
     */
    private String[] mids;
    /**
     * 角色id
     */
    private String rid;

    /**
     * 系统类别
     *  1:新应用,2:供应商管理系统
     */
    private Integer sysType;
}
