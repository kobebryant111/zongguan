package com.work.ppm.application.quartz.core;

import org.quartz.JobExecutionException;

/**
 * 执行定时器任务接口
 */
public interface IQuartzExecutorJob {
    /**
     * 执行定时器任务
     * @throws JobExecutionException JobExecutionException
     */
    void executeInternal() throws JobExecutionException;
}
