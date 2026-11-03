package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.ISyncManagementJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时添加考勤日期
 *
 */
@Slf4j
public class PpmSyncManagementJob implements IQuartzExecutorJob {
    /**
     * 添加考勤日期
     */
    private ISyncManagementJobService syncManagementJobService = (ISyncManagementJobService) SpringUtil.getBean("syncManagementJobService");

    @Override
    public void executeInternal() {
        try {
            log.info("添加考勤日期定时器开始");
            syncManagementJobService.syncManagementJob();
            log.info("添加考勤日期定时器结束");
        }catch (Exception e){
            log.error("添加考勤日期异常", e);
        }
    }

}
