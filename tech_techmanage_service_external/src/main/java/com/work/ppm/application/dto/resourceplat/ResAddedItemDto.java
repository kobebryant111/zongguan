package com.work.ppm.application.dto.resourceplat;

import lombok.Data;

import java.util.List;

/**
 * 资源新增子项
 *
 */
@Data
public class ResAddedItemDto {
    /**
     * 主键
     */
    private String id;
    /**
     * 关联RES_ADDED中的ID
     */
    private String addedId;
    /**
     * 节点类型
     */
    private String nodeType;
    /**
     * 选型类型
     */
    private String modelType;
    /**
     * 选型配置
     */
    private String modelConfig;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 所属中心
     */
    private String ownCenter;
    /**
     * 网络区域
     */
    private String networkArea;
    /**
     * 节点架构
     */
    private String nodeArch;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 数据库容量
     */
    private String dbSize;
    /**
     * 是否挂载存储
     */
    private String isStorage;
    /**
     * 挂载存储大小
     */
    private String storageSize;
    /**
     * 是否挂载NAS
     */
    private String isNas;
    /**
     * 挂载NAS大小
     */
    private String nasSize;
    /**
     * 挂载类型
     */
    private String mountType;
    /**
     * 价格
     */
    private String price;
    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 选型配置内容
     */
    private String modelConfigName;
    /**
     * 网络区域名称
     */
    private String networkAreaName;
    /**
     * 套餐价格
     */
    private String comboTotalPrice;
    /**
     * 套餐内的cpu数
     */
    private String cpuNum;
    /**
     * 套餐内的cpu核心数
     */
    private String cpuCode;
    /**
     * 操作系统单价
     */
    private String osPrice;
    /**
     * 操作系统计费模式
     */
    private String osCountModel;
    /**
     * 操作系统的总价
     */
    private String osTotalPrice;
    /**
     * nas存储单价
     */
    private String nasPrice;
    /**
     * nas存储总价
     */
    private String nasTotalPrice;
    /**
     * san存储总价
     */
    private String sanTotalPrice;
    /**
     * 数据库存储单价
     */
    private String dbPrice;
    /**
     * 数据库存储总价
     */
    private String dbTotalPrice;
    /**
     * san存储的单价
     */
    private String sanPrice;
    /**
     * san比例
     */
    private String sanRatio;
    /**
     * 配件总价
     */
    private String hardWareTotalPrice;
    /**
     * 软件总价
     */
    private String softWareTotalPrice;

    /**
     * 归档的SAN套餐的ID
     */
    private String sanSpecialId;
    /**
     * 归档的SAN套餐的单价
     */
    private String sanSpecialPrice;
    /**
     * 归档的SAN套餐的比例
     */
    private String sanSpecialRatio;

    /**
     * 归档的SAN套餐的容量
     */
    private String sanSpecialSize;
    /**
     * SAN套餐的ID
     */
    private String sanId;
    /**
     * 验算价格的CPU总数
     */
    private int cpuTotalNum;
    /**
     * 验算价格的CPU总核数
     */
    private int cpuTotalCode;
    /**
     * 排序字段
     */
    private int softId;
    /**
     * hardWareData
     */
    private List<ResAddedItemPartsDto> hardWareData;

    /**
     * softWareData
     */
    private List<ResAddedItemVersionDto> softWareData;


}
