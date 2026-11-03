package com.work.ppm.application.dto.systemmanager;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 角色树
 */
@Data
public class ImRoleTreeDto {
    /**
     * 角色深度
     */
    private Integer rolLevel;
    /**
     * 父类id
     */
    private String rolParentId;
    /**
     * 角色id
     */
    private String rolId;
    /**
     * 名称
     */
    private String rolName;
    /**
     * 备注
     */
    private String rolDesc;
    /**
     * 分组id
     */
    private String rolGroupId;
    /**
     * 创建者用户
     */
    private String rolCreateUser;
    /**
     * 创建时间
     */
    private Date rolCreateTime;
    /**
     * 节点
     */
    private List<ImRoleTreeDto> children;
    /**
     * 是否特殊菜单(0:特殊，1：正常)
     */
    private Integer falgSpecialRole;
    
}
