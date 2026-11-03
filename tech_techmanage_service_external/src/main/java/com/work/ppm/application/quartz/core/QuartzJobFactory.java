package com.work.ppm.application.quartz.core;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.lang.reflect.Method;

/**
 * 定时器工厂
 */
@Slf4j
@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {
    /**
     * 执行方法名称
     */
    private final static String EXECUTOR_METHOD = "executeInternal";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail detail = (JobDetail) context.getJobDetail();
        JobKey key = detail.getKey();
        log.info("=================>>>>>>【{}】任务开始", key.getName());
        try{
            Class<?> clazz = Class.forName(key.getName());
            Object obj = clazz.newInstance();
            Method method = clazz.getDeclaredMethod(EXECUTOR_METHOD);
            method.invoke(obj);
            log.info("=============================>>>>>>【{}】任务结束", key.getName());
        }catch (Exception e) {
            log.error("定时器工厂异常:{}", e);
        }
    }
}
