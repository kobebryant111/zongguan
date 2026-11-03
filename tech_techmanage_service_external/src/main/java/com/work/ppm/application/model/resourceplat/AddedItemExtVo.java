package com.work.ppm.application.model.resourceplat;

import lombok.Data;

/**
 * addedItem与addedExt的组合返回对象
 */
@Data
public class AddedItemExtVo extends ResAddedExt {
    /**
     * 节点类型
     */
    private String nodeType;
    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 选项类型
     */
    private String modelType;



    /**
     * 选型配置内容
     */
    private String modelConfigName;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 网络区域名称
     */
    private String netWorkAreaName;

    /**
     * 是否挂载存储 1是0否
     */
    private String isStorage;

    /**
     * 挂载存储大小
     */
    private String storageSize;

    /**
     * 是否挂载存储 1是0否
     */
    private String isNas;

    /**
     * 挂载存储大小
     */
    private String nasSize;
    /**
     * 数据库容量
     */
    private String dbSize;

    /**
     * 所属中心
     */
    private String owncenter;

    /**
     * id
     */
    private String addedid;

}
