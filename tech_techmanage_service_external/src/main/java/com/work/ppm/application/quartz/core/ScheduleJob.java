package com.work.ppm.application.quartz.core;

import lombok.Data;

/**
 * 定时器调度实体类
 */
@Data
public class ScheduleJob {
    /**
     * 任务编号
     */
    private String jobId;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务状态 0停止，1：启动 2; 删除
     */
    private String jobStatus;
    /**
     * 任务运行时间表达式
     */
    private String cronExpression;
    /**
     * 描述
     */
    private String desc;
}
