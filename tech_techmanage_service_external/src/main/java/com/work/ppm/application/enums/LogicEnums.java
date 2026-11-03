package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 逻辑删除
 */

@Getter
public enum LogicEnums {

    VALID("0", "记录有效"),
    INVALID("1", "记录被删除");

    LogicEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
}
