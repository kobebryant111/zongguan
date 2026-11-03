package com.work.ppm.application.model.resourceplat;

import lombok.Data;

/**
 * ResAddedItemParts
 */
@Data
public class ResAddedItemParts {
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
     * 配件分类
     */
    private String resConfigId;
    /**
     * 适用场景
     */
    private String applyScene;
    /**
     * 数量
     */
    private int num;
    /**
     * 价格
     */
    private float price;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 配件ID
     */
    private String partId;

    /**
     * 配置
     */
    private String describe;
    /**
     * 配件大类
     */
    private String partCategory;
    /**
     * 行配件总价
     */
    private float totalPrice;

}
