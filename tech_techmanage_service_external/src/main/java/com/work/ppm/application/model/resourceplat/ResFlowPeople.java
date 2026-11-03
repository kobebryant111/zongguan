package com.work.ppm.application.model.resourceplat;

import lombok.Data;

/**
 * 流程配置人员
 */
@Data
public class ResFlowPeople {
    /**
     * 唯一主键
     */
    private String id;
    /**
     * 流程节点定义ID
     */
    private String nodeId;
    /**
     * 流程节点定义名称
     */
    private String nodeName;
    /**
     * 用户ID
     */
        private String userId;
    /**
     * 用户姓名
     */
    private String userName;
}
