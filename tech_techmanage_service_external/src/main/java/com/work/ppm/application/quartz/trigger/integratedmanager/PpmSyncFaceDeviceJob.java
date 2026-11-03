package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.ISyncDataJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时解析考勤机同步文件任务
 *
 */
@Slf4j
public class PpmSyncFaceDeviceJob implements IQuartzExecutorJob {
    /**
     * 同步数据服务接口
     */
    private ISyncDataJobService syncDataJobService = (ISyncDataJobService) SpringUtil.getBean("syncDataJobService");

    @Override
    public void executeInternal() {
        try {
            log.info("解析考勤机同步文件定时器开始");
            syncDataJobService.syncFaceDevice();
            log.info("解析考勤机同步文件定时器结束");
        } catch (Exception e) {
            log.error("解析考勤机同步文件定时器异常", e);
        }

    }

}
