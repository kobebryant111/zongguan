package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.ISyncIntegInfoJobService;
import com.work.ppm.application.quartz.interfaces.ISyncTestEmployeePoolJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 将外包入场人员信息进行同步到人员池中.
 *
 */
@Slf4j
public class PpmSyncTestEmployeePoolJob implements IQuartzExecutorJob {

    /**
     * 同步数据服务接口
     */
    private ISyncTestEmployeePoolJobService syncTestEmployeePoolJobService =
            (ISyncTestEmployeePoolJobService) SpringUtil.getBean("syncTestEmployeePoolJobService");

    @Override
    public void executeInternal() {
        try {
            log.info("同步外包人员信息到测试人员池定时器开始");
            syncTestEmployeePoolJobService.syncEmployeeToPool();
            log.info("同步外包人员信息到测试人员池定时器结束");
        } catch (Exception e) {
            log.error("同步外包人员信息到测试人员池定时器异常,异常信息为:{}", e);
        }
    }
}
