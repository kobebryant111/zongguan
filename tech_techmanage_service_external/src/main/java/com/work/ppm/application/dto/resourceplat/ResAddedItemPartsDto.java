package com.work.ppm.application.dto.resourceplat;

import lombok.Data;

/**
 * ResAddedItemPartsDto
 */
@Data
public class ResAddedItemPartsDto {
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
    private String num;
    /**
     * 价格
     */
    private String price;
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
