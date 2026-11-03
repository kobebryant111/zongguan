package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmOutsourcingBasicJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时跑批 外包人员入职时间及状态
 */
@Slf4j
public class PpmOutsourcingApplyForBasicJob implements IQuartzExecutorJob {
    /**
     * 定时变更外包人员状态数据
     */
    private IPpmOutsourcingBasicJobService ppmOutsourcingBasicJobService =
            (IPpmOutsourcingBasicJobService) SpringUtil.getBean("ppmOutsourcingBasicService");
    @Override
    public void executeInternal() {

        try{
            log.info("=================> 定时变更外包人员状态数据 开始");
            ppmOutsourcingBasicJobService.updateBasicState();
            log.info("=================> 定时变更外包人员状态数据 结束");
        }catch (Exception e) {
            log.error("定时变更外包人员状态数据异常", e);
        }
    }

}
