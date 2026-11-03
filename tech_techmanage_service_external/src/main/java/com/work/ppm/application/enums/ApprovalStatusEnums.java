package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 逻辑删除
 */

@Getter
public enum ApprovalStatusEnums {

//    DRAFT("01", "草稿状态"),
//    IN_APPROVAL("02", "审批中"),
//    APPROVAL_NO("03","退回"),
//    APPROVAL_PASS("04", "审批通过");

    DRAFT("05", "草稿状态"),
    IN_APPROVAL("04", "审批中"),
    WITHDRAW("03","撤回"),
    RETURN("02","退回"),
    PASS("01", "审批通过");

    ApprovalStatusEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
}
