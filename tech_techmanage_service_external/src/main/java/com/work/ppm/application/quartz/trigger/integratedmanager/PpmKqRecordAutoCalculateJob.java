package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmKqRecordCalculateJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 自动修改外包人员考勤异常
 */
@Slf4j
public class PpmKqRecordAutoCalculateJob implements IQuartzExecutorJob {
    /**
     * 自动修改外包人员考勤异常
     */
    private IPpmKqRecordCalculateJobService ppmKqRecordCalculateJobService =
            (IPpmKqRecordCalculateJobService) SpringUtil.getBean("ppmKqRecordService");

    @Override
    public void executeInternal() {
        try{
            log.warn("=================> 外包人员考勤异常自动结算 开始");
            ppmKqRecordCalculateJobService.autoRecordHandleCalaulate();
            log.warn("=================> 外包人员考勤异常自动结算 结束");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("外包人员考勤异常自动结算 异常", e);
        }

    }

}
