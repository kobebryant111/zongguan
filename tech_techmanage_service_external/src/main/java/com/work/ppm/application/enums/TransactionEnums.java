package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 逻辑删除
 */

@Getter
public enum TransactionEnums {

    IN("0", "行内"),
    LOCAL_ACROSS("1", "同城跨行"),
    REMOTE_ACROSS("2", "异地跨行");

    TransactionEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
}
