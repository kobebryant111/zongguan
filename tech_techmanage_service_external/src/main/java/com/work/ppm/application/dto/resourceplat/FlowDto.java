package com.work.ppm.application.dto.resourceplat;

import lombok.Data;


/**
 * 工作流提交实例
 */
@Data
public class FlowDto {
    /**
     * 唯一主键getDepid
     */
    private String id;
    /**
     * 审批意见
     */
    private String audit;
    /**
     * 决策
     */
    private String conclusion;
    /**
     * 工作项
     */
    private String workItemId;
    /**
     * 流程实例
     */
    private String processInstId;
    /**
     * 节点名称
     */
    private String applydesc;
    /**
     * 预算编号
     */
    private String budgetNo;
    /**
     * 当前审批人
     */
    private String creator;
    /**
     * 节点定义ID
     */
    private String  activityDefID;
    /**
     * 资源申请类型
     */
    private String  sourceType;
    /**
     * 下个节点审批人
     */
    private String  auditUserId;
    /**
     * 技评编号
     */
    private String  evaluateNo;
    /**
     * 技评地址URL
     */
    private String  evaluateUrl;
    /**
     * 应用系统所属部门id
     */
    private String  depid;

}
