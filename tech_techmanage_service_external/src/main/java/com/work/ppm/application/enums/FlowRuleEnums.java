package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 对外接口 流程字典规则命名对应编码
 */

@Getter
public enum FlowRuleEnums {
    //流程编码
    DH("_DH", "二级部经理处理"),
    GM("_GM", "一级部经理处理"),
    IN("_IN", "发起流程");

    FlowRuleEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
}
