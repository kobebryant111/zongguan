package com.work.ppm.application.model.integratedmanager;

import lombok.Data;

/**
 * 描述
 */
@Data
public class OutTrainData {
    /**
     * 工号
     */
    private String jobid;
    /**
     * 培训时间
     */
    private String traintime;
    /**
     * 项目
     */
    private String project;
    /**
     * 分数
     */
    private String score;
}
