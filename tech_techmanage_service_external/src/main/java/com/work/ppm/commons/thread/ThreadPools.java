package com.work.ppm.commons.thread;

/**
 * ThreadPools
 */
public interface ThreadPools<Job extends  Runnable> {
    /**
     * 执行一个任务（job）,这个job必须实现Runnable
     * @param job 任务
     */
    void execute(Job job);

    /**
     * 关闭线程池
     */
    void shudown();

    /**
     * 增加工作线程，即用来执行任务的线程
     * @param num 数量
     */
    void addWorkers(int num);

    /**
     * 减少工作线程
     * @param num 数量
     */
    void removeWorker(int num);

    /**
     * 获取正在等待执行的任务数量
     * @return int
     */
    int getJobSize();
}
