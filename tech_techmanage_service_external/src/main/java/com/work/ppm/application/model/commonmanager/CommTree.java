package com.work.ppm.application.model.commonmanager;

import lombok.Data;

/**
 * 公共树实体类
 */
@Data
public class CommTree {
    /**
     * 编号
     */
    private String id;
    /**
     * 文本
     */
    private String text;
    /**
     * 父id
     */
    private String pid;
    /**
     * 父编号
     */
    private String pcode;
    /**
     * 对象
     */
    private Object attr;
    /**
     * 排序
     */
    private Integer ord;
}
