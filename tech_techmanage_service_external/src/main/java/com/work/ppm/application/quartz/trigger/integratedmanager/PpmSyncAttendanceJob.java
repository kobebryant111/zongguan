package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.ISyncManagementJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PpmSyncAttendanceJob implements IQuartzExecutorJob {

    /**
     * 添加考勤管理
     */
    private ISyncManagementJobService syncManagementJobService = (ISyncManagementJobService) SpringUtil.getBean("syncManagementJobService");

    @Override
    public void executeInternal() {
        try {
            log.info("添加考勤管理定时器开始");
            syncManagementJobService.syncAttendanceJob();
            log.info("添加考勤管理定时器结束");
        }catch (Exception e){
            log.error("添加考勤管理异常", e);
        }
    }

}
