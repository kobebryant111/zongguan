package com.work.ppm.application.quartz.trigger.kpi;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmKpiPmJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;

/**
 * 考核自动发起半年度自评
 */
@Slf4j
public class PpmKpiPmLaunchHalfYearSelfEvalJob implements IQuartzExecutorJob {
    /**
     * 考核自动发起定时器
     */
    private IPpmKpiPmJobService ppmKpiPmJobService = (IPpmKpiPmJobService) SpringUtil.getBean("ppmKpiPm1Service");
    @Override
    public void executeInternal() throws JobExecutionException {
        try{

            log.warn("考核自动发起半年度自评始");
            ppmKpiPmJobService.autoLaunchHalfYearSelfEval();
            log.warn("考核自动发起半年度自评结束");
        }catch (Exception e) {
            log.warn("考核自动发起半年度自评异常", e);
        }

    }

}
