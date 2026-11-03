package com.work.ppm.application.dto.resourceplat;

import lombok.Data;


/**
 * ResAddedItemPartsDto
 */
@Data
public class ResAddedItemVersionDto {
    /**
     * 主键
     */
    private String id;
    /**
     * 关联RES_ADDED_ITEM的ID
     */
    private String addedItemId;
    /**
     * 关联RES_ADDED中的ID
     */
    private String addedId;
    /**
     * 软件类型
     */
    private String type;
    /**
     * 软件名称
     */
    private String name;
    /**
     * 软件版本
     */
    private String version;
    /**
     * 软件价格
     */
    private String price;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 软件ID
     */
    private  String softId;
    /**
     * 计费模式
     */
    private  String countModel;
    /**
     * 软件大类
     */
    private String softCategory;
    /**
     * 行软件总价
     */
    private float totalPrice;

}
