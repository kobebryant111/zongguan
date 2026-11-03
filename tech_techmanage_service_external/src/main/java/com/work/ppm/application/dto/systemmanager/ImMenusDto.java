package com.work.ppm.application.dto.systemmanager;

import lombok.Data;

import java.util.List;

/**
 * 菜单
 */
@Data
public class ImMenusDto {
    /**
     * 踩点编号
     */
    private String mid;
    /**
     * 路由id
     */
    private String routeid;
    /**
     * 菜单编码
     */
    private String code;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 深度
     */
    private Integer type;
    /**
     * 排序
     */
    private Integer ordernum;
    /**
     * 地址
     */
    private String url;
    /**
     * 父类id
     */
    private String parentid;
    /**
     * icon
     */
    private String icon;
    /**
     * 字段名
     */
    private String readme;
    /**
     * boolean
     */
    private Boolean flag = true;
    /**
     * 节点
     */
    private List<ImMenusDto> children;
    /**
     * 是否关联分配权限
     */
    private boolean authRel;

    /**
     * 系统类别
     *  1:新应用,2:供应商管理系统
     */
    private Integer sysType;
}
