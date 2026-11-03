package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 面试是否符合要求
 */
@Getter
public enum InterviewMeetEnums {

    PASS(1, "通过"),
    REFUSE(0, "不通过");

    InterviewMeetEnums(Integer code, String message) {
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
    public static InterviewMeetEnums fromCode(Integer code) {
        for (InterviewMeetEnums value : InterviewMeetEnums.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
