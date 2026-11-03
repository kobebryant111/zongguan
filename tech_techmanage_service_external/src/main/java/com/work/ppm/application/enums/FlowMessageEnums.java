package com.work.ppm.application.enums;

import lombok.Getter;

/**
 * 对外接口 工作流返回信息
 */

@Getter
public enum FlowMessageEnums {
    PASS("0000","成功"),
    FAIL("9999","失败"),
    FAIL_ERROR("9001","业务状态编码不符合条件"),
    FAIL_NOT_TASK("9002","未查询到有效任务"),
    FAIL_ERROR_TASK("9003","任务未处于有效节点"),
    FAIL_ERROR_INST("9004","instanceId未查询到有效任务"),
    FAIL_ASSIGNEE_TASK("9005","当前任务已处理或已撤回"),
    FAIL_NULL("9100","存在参数为空");

    FlowMessageEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
}
