package com.work.ppm.commons.thread;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 默认的线程池
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPools<Job> {
    /**
     * 线程池维护工作者线程的最大数量
     */
    private static final int MAX_WORKER_NUMBERS = 30;
    /**
     * 线程池维护工作者线的默认数量
     */
    private static  final int DEFAULT_WORKER_NUMBERS = 5;
    /**
     * 线程池维护工作者线程最小数量
     */
    private static final int MIN_WORKER_NUNMBERS = 1;
    /**
     * 维护一个工作列表，里面加入客户端发起的工作
     */
    private final LinkedList<Job> jobs = new LinkedList<>();
    /**
     * 工作者者线程列表
     */
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());
    /**
     * 工作者线程的数量
     */
    private int workerNum;
    /**
     * 每个工作者线程号生成
     */
    private AtomicLong threadNum = new AtomicLong();

    /**
     * DefaultThreadPool
     * @param num 数据
     */
    public DefaultThreadPool(int num) {
        if(num > MAX_WORKER_NUMBERS) {
            this.workerNum = DEFAULT_WORKER_NUMBERS;
        } else {
            this.workerNum = num;
        }
        // 初始线程池
        initializeWorkers(workerNum);
    }

    /**
     * 初始化每个工作者线程
     * @param num 数量
     */
    private void initializeWorkers(int num) {
        for (int i = num; i > 0; i--) {
            Worker worker = new Worker();
            workers.add(worker);
            // 启动工作者线程
            Thread thread = new Thread(worker);
            thread.start();
        }
    }

    /**
     * 执行一个任务（job），这个job必须需实现Runnable
     * @param job
     */
    @Override
    public void execute(Job job) {
        // 如果job为null 抛出异常
        if(null == job) {
            throw  new NullPointerException();
        }
        // 这里进行执行，当供大于求时，考虑如何临时添加线程数
        if(null != job) {
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    /**
     * 关闭线程池
     */
    @Override
    public void shudown() {
       workers.stream().forEach(item -> {
           item.shutdown();
       });
    }

    /**
     * 增加工作者线程，即用来执行任务的线程
     * @param num 数量
     */
    @Override
    public void addWorkers(int num) {
        // 加锁，防止该线程还没增加完成而下个线程继续增加导致工作者线程超过最大值
        synchronized (jobs) {
            if(num + this.workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum += num;
        }
    }

    /**
     * 减少工作者线程数
     * @param num
     */
    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            if(num >= this.workerNum) {
                throw  new BizException(ApiCode.INTERNAL_SERVER_ERROR,"超过了已有的线程数量");
            }
            for (int i = num; i > 0; i--) {
                Worker worker = workers.get(i);
                if(null != worker) {
                    // 关闭该线程并从列表移除
                    worker.shutdown();
                    workers.remove(i);
                }
            }
            this.workerNum -= num;
        }
    }

    @Override
    public int getJobSize() {
        return workers.size();
    }

    /**
     * 消费者
     */
    class Worker implements Runnable {
        /**
         * 表示是否运行该 worker
         */
        private volatile boolean running = true;
        @Override
        public void run() {
            while ( running) {
                Job job = null;
                // 线程等待 / 通知机制
                synchronized (jobs) {
                    if(jobs.isEmpty()) {
                        try{
                            // 线程等待唤醒
                            jobs.wait();
                        }catch (InterruptedException e) {
                            // 感知到外部对该线程的中断操作，返回
                            Thread.currentThread().interrupt();
                            return ;
                        }
                    }
                    job = jobs.removeFirst();
                }
                // 执行job
                if(null != job) {
                    job.run();
                }
            }
        }

        /**
         * 终止该线程
         */
        public void shutdown() {
            running = false;
        }
    }
}
