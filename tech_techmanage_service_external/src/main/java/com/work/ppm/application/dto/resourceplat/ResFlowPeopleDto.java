package com.work.ppm.application.dto.resourceplat;

import lombok.Data;

/**
 * 流程配置人员
 */
@Data
public class ResFlowPeopleDto {
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
     * 配置人员信息
     */
    private String userId;
}
