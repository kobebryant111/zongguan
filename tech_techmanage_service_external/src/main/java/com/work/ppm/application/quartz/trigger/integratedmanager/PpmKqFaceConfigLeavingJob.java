package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmKqFaceConfigCalculateJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时下发人脸考勤机 离岗
 */
@Slf4j
public class PpmKqFaceConfigLeavingJob implements IQuartzExecutorJob {
    /**
     * 定时下发人脸考勤机
     */
    private IPpmKqFaceConfigCalculateJobService ppmKqFaceConfigCalculateJobService =
            (IPpmKqFaceConfigCalculateJobService) SpringUtil.getBean("ppmKqFaceConfigService");

    @Override
    public void executeInternal() {
        try{
            log.warn("=================> 定时下发人脸考勤机 开始");
            ppmKqFaceConfigCalculateJobService.handFaceDeviceLeavingTimer();
            log.warn("=================> 定时下发人脸考勤机 结束");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("定时下发人脸考勤机 异常", e);
        }

    }

}
