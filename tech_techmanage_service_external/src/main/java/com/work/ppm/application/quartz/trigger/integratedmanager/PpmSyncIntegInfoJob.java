package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.ISyncIntegInfoJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时同步考勤数据到积分信息表
 *
 */
@Slf4j
public class PpmSyncIntegInfoJob implements IQuartzExecutorJob {
    /**
     * 同步数据服务接口
     */
    private ISyncIntegInfoJobService syncIntegInfojobService = (ISyncIntegInfoJobService) SpringUtil.getBean("syncIntegInfoJobService");

    @Override
    public void executeInternal() {
        try {
            log.info("同步考勤数据到积分信息表定时器开始");
            syncIntegInfojobService.syncKqInfoJob();
            log.info("同步考勤数据到积分信息表定时器结束");
        } catch (Exception e) {
            log.error("同步考勤数据到积分信息表定时器异常,异常信息为:{}", e);
        }
    }

}
