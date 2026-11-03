package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmSystemListJob;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PpmSystemListJob implements IQuartzExecutorJob {
    private IPpmSystemListJob ppmSystemListJob = (IPpmSystemListJob) SpringUtil.getBean("ISystemListJob");
    @Override
    public void executeInternal() {
        try {
            log.info("同步系统列表定时器开始");
            ppmSystemListJob.syncSystemListJob();
            log.info("同步系统列表定时器结束");
        } catch (Exception e) {
            log.error("同步系统列表定时器异常", e);
        }

    }
}
