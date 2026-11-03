package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 审核结论枚举
 */
@Getter
public enum WorkflowConclusionEnums {

    PASS("01", "通过"),
    RETURN("02", "退回"),
    AUTHORIZE("03", "授权"),
    WITHDRAW("04", "撤回"),
    DISPATCH("05", "调度");

    WorkflowConclusionEnums(String code, String message) {
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
    public static WorkflowConclusionEnums fromCode(Integer code) {
        for (WorkflowConclusionEnums value : WorkflowConclusionEnums.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
