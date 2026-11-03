package com.work.ppm.application.quartz.trigger.kpi;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmKpiPmJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;

/**
 * 考核自动发起自评评价
 */
@Slf4j
public class PpmKpiPmAutoLaunchJob implements IQuartzExecutorJob {
    /**
     * 考核自动发起定时器
     */
    private IPpmKpiPmJobService ppmKpiPmJobService = (IPpmKpiPmJobService) SpringUtil.getBean("ppmKpiPm1Service");
    @Override
    public void executeInternal() throws JobExecutionException {
        try{

            log.warn("考核自动发起自评评价开始");
            ppmKpiPmJobService.autoLaunchSelfEval();
            log.warn("考核自动发起自评评价结束");
        }catch (Exception e) {
            log.warn("考核自动发起自评评价异常", e);
        }

    }

}
