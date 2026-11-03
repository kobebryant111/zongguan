package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IWeakPasswordService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class WeakPasswordJob implements IQuartzExecutorJob {
    /**
     * 同步弱口令服务接口
     */
    private IWeakPasswordService weakPasswordService = (IWeakPasswordService) SpringUtil.getBean("weakPasswordService");

    @Override
    public void executeInternal() {
        try {
            log.error("解析同步弱口令定时器开始");
            weakPasswordService.syncWeakPasswordJob();
            log.error("解析同步弱口令定时器结束");
        } catch (Exception e) {
            log.error("解析同步弱口令定时器异常", e);
        }
    }

}
