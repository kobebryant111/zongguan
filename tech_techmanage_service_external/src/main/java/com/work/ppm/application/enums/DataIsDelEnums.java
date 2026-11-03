package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 逻辑删除
 */

@Getter
public enum DataIsDelEnums {

    NO_DEL("0", "未删除"),
    YES_DEL("1", "已删除");

    DataIsDelEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
}
