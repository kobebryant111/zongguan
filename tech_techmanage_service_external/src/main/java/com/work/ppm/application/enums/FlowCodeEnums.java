package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 对外接口 流程对应编码
 */

@Getter
public enum FlowCodeEnums {
    //流程编码 message对应流程引擎
    DEMAND("0001", "wbrlxq"),
    INTERVIEW("0010", "wbryms"),
    ACCESS("0020", "wbryzr"),
    LEAVE("0029", "wbrylc"),
    ACCESS_CARD("0031", "lymjsq"),
    RETURN_CARD("0032", "ghmjsq"),;

    FlowCodeEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
}
