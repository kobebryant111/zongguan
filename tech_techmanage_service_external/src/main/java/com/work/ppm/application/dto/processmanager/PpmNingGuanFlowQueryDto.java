package com.work.ppm.application.dto.processmanager;

import lombok.Data;


/**
 * 新支行交付流程
 *
 */
@Data
public class PpmNingGuanFlowQueryDto {
    /**
     * 流程类型
     **/
    private String processType;

    /**
     * 流程标题
     **/
    private String processTitle;

    /**
     * 流程状态
     **/
    private String flowStatus;

    /**
     * 发起时间字符串
     **/
    private String startDate;

    /**
     * 结束时间字符串
     **/
    private String endDate;

    /**
     * 创建人
     **/
    private String creator;

}
