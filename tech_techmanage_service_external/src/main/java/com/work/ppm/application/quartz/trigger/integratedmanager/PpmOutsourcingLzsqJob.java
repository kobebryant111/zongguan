package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmOutsourcingLzsqJobService;
import lombok.extern.slf4j.Slf4j;

/**
 * 外包员工离职自动变更员工状态
 */
@Slf4j
public class PpmOutsourcingLzsqJob implements IQuartzExecutorJob {
    /**
     * 外包人员离职
     */
    private IPpmOutsourcingLzsqJobService ppmOutsourcingLzsqJobService = (IPpmOutsourcingLzsqJobService) SpringUtil.getBean("ppmOutsourcingLzsqService");
    @Override
    public void executeInternal() {
        try{

            log.info("外包离职定时器开始");
            ppmOutsourcingLzsqJobService.outQuitChange();
            log.info("外包离职定时器结束");
        }catch (Exception e) {
            log.error("外包离职定时器异常", e);
        }

    }

}
