package com.work.ppm.application.model.resourceplat;

import lombok.Data;

/**
 * 总办报表
 */
@Data
public class ReportZb {
    /**
     * 所属中心
     */
    private String ownCenterName;
    /**
     * 选型类型/软件名称
     */
    private String type;
    /**
     * 选型配置/软件版本
     */
    private String description;
    /**
     * 数量/容量
     */
    private String count;
    /**
     * 单价
     */
    private String price;
    /**
     * 总价
     */
    private float totalPrice;
    /**
     * 开始的行号
     */
    private int beginRow;
    /**
     * 结束的行号
     */
    private int endRow;

}
