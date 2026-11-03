package com.work.ppm.application.model.resourceplat;

import lombok.Data;

/**
 * 资源统计报表
 */
@Data
public class ResReportInfo {
    /**
     * 唯一主键
     */
    private String id;
    /**
     * 所属中心
     */
    private String ownCenter;
    /**
     * 资源类型中文
     */
    private String resType;
    /**
     * 配置（部分存储的是容量）
     */
        private String describe;
    /**
     * 数量
     */
    private String num;
    /**
     * 单价
     */
    private float price;
    /**
     * 总价
     */
    private float totalPrice;

    /**
     * 申请ID
     */
    private String resAddId;
    /**
     **排序号
     */
    private int px;
    /**
     **CPU核数-虚拟资源保存
     */
    private int cpu;
    /**
     **内存-虚拟资源保存
     */
    private int memory;
    /**
     **硬盘空间-虚拟资源保存
     */
    private float disk;
}
