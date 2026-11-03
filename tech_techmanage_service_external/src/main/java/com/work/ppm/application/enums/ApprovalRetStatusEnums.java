package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 手机平台返回状态信息
 */

@Getter
public enum ApprovalRetStatusEnums {

    IN_APPROVAL("04", "撤回"),
    WITHDRAW("03","授权"),
    RETURN("02","拒绝"),
    PASS("01", "通过");

    ApprovalRetStatusEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
}
