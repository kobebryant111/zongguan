package com.work.ppm.commons.artemis.enums;

import lombok.Getter;

/**
 * 门禁点反控类型
 */
@Getter
public enum ControlTypeEnums {
    // 0-常开 1-门闭 2-门开 3-常闭
    NORMALLY_OPEN(0, "常开"),
    CLOSE(1, "门闭"),
    OPEN(2, "门开"),
    NORMALLY_CLOSED(3, "常闭");

    ControlTypeEnums(Integer code, String message) {
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
    public static ControlTypeEnums fromCode(Integer code) {
        for (ControlTypeEnums value : ControlTypeEnums.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
