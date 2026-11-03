package com.work.ppm.commons.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 定时器管理
 */
public class QuartzConfig {
    /**
     * 工厂
     */
    @Autowired
    private SpringBeanJobFactory springBeanJobFactory;

    /**
     * 实例对象
     * @return  SchedulerFactoryBean
     */
    @Bean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(springBeanJobFactory);
        return schedulerFactoryBean;
    }

    /**
     * 调度器
     * @return  Scheduler
     */
    @Bean("scheduler")
    @Primary
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}
