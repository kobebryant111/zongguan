package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 常用的xml对应id
 */

@Getter
public enum XmlIdEnums {

    PAGER("queryPagerList", "分页查询"),
    ADD("addPojo", "新增数据"),
    QUERY("queryDetail", "查询详情"),
    QUERYS("getList", "查询数据集"),
    UPDATE("updatePojo", "修改数据"),
    DELETE("deleteById", "删除数据"),
    BASE("queryBaseInfo", "关联查询数据"),
    BASES("queryBaseInfoList", "关联查询数据集");

    XmlIdEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
}
