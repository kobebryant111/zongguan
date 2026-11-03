package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 逻辑删除
 */

@Getter
public enum SettleFreqEnums {

    MONTHLY("0", "按月结算"),
    QUARTERLY("1", "按季结算"),
    YEARLY("2", "按年结算");

    SettleFreqEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
}
