package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmOutsourcingBasicJobService;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步老应用外包数据
 */
@Slf4j
public class PpmOutsourcingBasicJob implements IQuartzExecutorJob {
    /**
     * 外包人员基础接口
     */
    private IPpmOutsourcingBasicJobService ppmOutsourcingBasicJobService = (IPpmOutsourcingBasicJobService) SpringUtil.getBean("ppmOutsourcingBasicService");
    @Override
    public void executeInternal() {
        try{
            ppmOutsourcingBasicJobService.updateOldSystemOut();
        }catch (Exception e) {
            log.error("同步老应用外包数据定时器异常", e);
        }

    }

}
