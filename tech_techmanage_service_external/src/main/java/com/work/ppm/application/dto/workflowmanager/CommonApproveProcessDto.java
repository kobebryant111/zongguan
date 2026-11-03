package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
/**
 * 通用审批接口无参数
 */
public class CommonApproveProcessDto {

    /**
     * 业务类型编码
     */
    String bizTypeCode;
    /**
     * 审批人工号
     */
    String assignee;
    /**
     * 流程实例id
     */
    String processInstId;
    /**
     * 审批决策
     */
    String conclusion;
    /**
     * 审批意见
     */
    String suggest;
    /**
     * 审批时间
     */
    String approveTime;
    /**
     * 备用字段1
     */
    String standbyOne;
    /**
     * 备用字段2
     */
    String standbyTwo;

    /**
     * 流程编码
     */
    String flowCode;
    /**
     * 字典id
     */
    String flowCase;
    /**
     * 角色id
     */
    String roleId;

    String processDefName;

    Boolean isPass;

}
