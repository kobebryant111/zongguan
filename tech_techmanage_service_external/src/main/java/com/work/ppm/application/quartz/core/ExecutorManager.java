package com.work.ppm.application.quartz.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程管理类
 */
public class ExecutorManager {
    /**
     * 线程池
     */
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    /**
     * 同步锁
     */
    private final static String UNQIELINK = "EXECUTOR";

    /**
     * 双向同步 确保只有一个实例
     * @return ExecutorService
     */
    public static ExecutorService getExecutor() {
        synchronized (UNQIELINK) {
            if(null == executor) {
                executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            }
        }
        return executor;
    }
}
