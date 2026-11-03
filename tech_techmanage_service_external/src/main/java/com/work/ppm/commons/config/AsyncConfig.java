package com.work.ppm.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步线程池配置
 */
@Configuration
public class AsyncConfig {
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 50;
    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 20;

    /**
     * 线程池配置
     * @return ThreadPoolTaskExecutor
     */
    @Bean("asyncTaskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setThreadNamePrefix("tech-async-thread-pool-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
