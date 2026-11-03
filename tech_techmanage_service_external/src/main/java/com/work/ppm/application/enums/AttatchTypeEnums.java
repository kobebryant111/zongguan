package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 逻辑删除
 */

@Getter
public enum AttatchTypeEnums {

    ZBYA("0", "总办议案"),
    INVOICE("1", "发票"),
    OTHER("2", "发票");

    AttatchTypeEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
}
