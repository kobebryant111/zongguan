package com.work.ppm.application.model.resourceplat;

import lombok.Data;

/**
 * 技评报表
 */
@Data
public class ReportJp {
    /**
     * 子表ID
     */
    private String id;
    /**
     * 所属中心
     */
    private String ownCenterName;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 节点类型
     */
    private String nodeTypeName;
    /**
     * 选型类型
     */
    private String modelTypeName;
    /**
     * 选型配置
     */
    private String modelconfig;
    /**
     * 存储容量
     */
    private String storageConfig;
    /**
     * NAS存储
     */
    private int nasSize;
    /**
     * SAN存储
     */
    private int sanSize;
    /**
     * 归档SAN存储
     */
    private int sanSpecialSize;
    /**
     * 数据库容量
     */
    private int dbSize;
}
