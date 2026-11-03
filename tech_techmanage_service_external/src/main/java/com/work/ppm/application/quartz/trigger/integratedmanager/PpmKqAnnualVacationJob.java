package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmKqAnnualVacationJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时外包人员增加对应年假
 */
@Slf4j
public class PpmKqAnnualVacationJob implements IQuartzExecutorJob {
    /**
     * 定时外包人员增加对应年假
     */
    private IPpmKqAnnualVacationJobService ppmKqAnnualVacationJobService =
            (IPpmKqAnnualVacationJobService) SpringUtil.getBean("ppmKqAnnualVacationService");

    @Override
    public void executeInternal() {
        try{
            log.warn("=================> 定时外包人员增加对应年假 开始");
            ppmKqAnnualVacationJobService.validAnnualVacation();
            log.warn("=================> 定时外包人员增加对应年假 结束");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("定时外包人员增加对应年假 异常", e);
        }

    }

}
