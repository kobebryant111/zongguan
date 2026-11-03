package com.tansun.workflow.model;

public class DebugInfo {
    /**
     * 控制是否开启流程调试，N:不开启，Y:开启
     */
    private String isOpen;
    /**
     * ，指定任务节点id， 默认最后一个节点
     */
    private String targetTaskNodeId;
    /**
     * ，指定任务处理人， 默认按流程图配置生成处理人
     */
    private String targetTaskAssignee;

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getTargetTaskNodeId() {
        return targetTaskNodeId;
    }

    public void setTargetTaskNodeId(String targetTaskNodeId) {
        this.targetTaskNodeId = targetTaskNodeId;
    }

    public String getTargetTaskAssignee() {
        return targetTaskAssignee;
    }

    public void setTargetTaskAssignee(String targetTaskAssignee) {
        this.targetTaskAssignee = targetTaskAssignee;
    }
}
