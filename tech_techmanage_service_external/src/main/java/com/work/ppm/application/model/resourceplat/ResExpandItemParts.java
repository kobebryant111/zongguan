package com.work.ppm.application.model.resourceplat;

import lombok.Data;

/**
 * 资源扩容项配置
 */
@Data
public class ResExpandItemParts {
    /**
     * 唯一主键
     */
    private String id;
    /**
     * 资源扩容申请ID
     */
    private String extendId;
    /**
     * 资源扩容项ID
     */
    private String extendItemId;
    /**
     * 套餐大类
     */
    private String category;
    /**
     * 套餐小项
     */
    private String subCategory;
    /**
     * 配置项ID
     */
        private String resConfigId;
    /**
     * 配置描述
     */
    private String remark;
    /**
     * 数量
     */
    private String num;
    /**
     * 单价
     */
    private String price;
    /**
     * 总价
     */
    private float totalPrice;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 资源配置
     */
    private String describe;

    /**
     * 计费方式
     */
    private String billingMode;
    /**
     * SAN下归档配置ID
     */
    private String sanRelateId;
    /**
     * SAN下归档容量GB
     */
    private String sanRelateNum;
    /**
     * SAN下归档单价(元)
     */
    private String sanRelatePrice;
    /**
     * 类型为SAN时比例
     */
    private String ratio;
    /**
     * SAN下归档比例
     */
    private String sanRelateRatio;
    /**
     * 是否专用 0否;针对：redis+虚拟机,其他+专用设备，SAN里面的归档数据
     */
    private String isSpecial;
    /**
     * DB云的磁盘容量单价
     */
    private String memPrice;
    /**
     * SAN下归档配置总价(报表统计，导出评审表时使用)
     */
    private float sanRelateTotalPrice;
    /**
     * DB云的磁盘容量总价
     */
    private float memTotalPrice;

}
