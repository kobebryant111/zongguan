package com.work.ppm.application.model.integratedmanager;

import lombok.Data;

import java.util.Date;

/**
 * 描述
 */
@Data
public class OutRiskResultData {
    /**
     * 工号
     */
    private String jobid;
    /**
     * 排查类型
     */
    private String risktype;
    /**
     * 排查时间
     */
    private Date riskdate;
    /**
     * 排查结果
     */
    private String risksuggest;

    /**
     * 说明情况
     */
    private String explainstate;


}
