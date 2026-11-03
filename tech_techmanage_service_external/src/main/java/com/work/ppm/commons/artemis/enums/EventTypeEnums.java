package com.work.ppm.commons.artemis.enums;

import lombok.Getter;

/**
 * 订阅事件类型
 */
@Getter
public enum EventTypeEnums {

    AUTH_PASS(196893, "人脸认证通过"),
    OPEN_DOOR(199941, "门锁打开"),
    CLOSE_DOOR(199942, "门锁关闭");

    EventTypeEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;


    /**
     * 通过编码取枚举
     *
     * @param code
     * @return
     */
    public static EventTypeEnums fromCode(Integer code) {
        for (EventTypeEnums value : EventTypeEnums.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
