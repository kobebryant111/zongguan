package com.work.ppm.commons.artemis.enums;

import lombok.Getter;

/**
 * 请求返回编码枚举
 */
@Getter
public enum RestStateEnums {
    // 0-常开 1-门闭 2-门开 3-常闭
    SUCCESS("0", "成功"),
    ERROR("1", "失败");

    RestStateEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;


    /**
     * 通过编码取枚举
     *
     * @param code
     * @return
     */
    public static RestStateEnums fromCode(Integer code) {
        for (RestStateEnums value : RestStateEnums.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
