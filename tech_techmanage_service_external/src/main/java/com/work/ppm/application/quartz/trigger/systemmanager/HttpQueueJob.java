package com.work.ppm.application.quartz.trigger.systemmanager;

import com.work.ppm.commons.lang.queue.HttpQueue;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 手机端异步处理请求定时器
 * 该定时器只在队列异常时，临时执行的定时器，不需要一直启动
 */
@Slf4j
public class HttpQueueJob implements IQuartzExecutorJob {
    /**
     * 队列接口
     */
    @Autowired
    private HttpQueue httpQueue;

    /**
     * 定时器方法
     */
    @Override
    public void executeInternal() {
        try{
            log.warn("================================>>>>>>> 手机端异步处理请求定时器 启动《《《《《《《《");
            httpQueue.run();
        }catch (Exception e) {
            log.error("手机端异步处理请求定时器异常:{}", e);
        }
    }
}
