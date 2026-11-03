package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmKqRecordCalculateJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时推送考勤提示消息
 */
@Slf4j
public class PpmKqSendMessageJob implements IQuartzExecutorJob {
    /**
     * 定时推送考勤提示消息
     */
    private IPpmKqRecordCalculateJobService ppmKqRecordCalculateJobService =
            (IPpmKqRecordCalculateJobService) SpringUtil.getBean("ppmKqRecordService");

    @Override
    public void executeInternal() {
        try{
            log.warn("=================> 定时推送考勤提示消息 开始");
            ppmKqRecordCalculateJobService.sendMessage();
            log.warn("=================> 定时推送考勤提示消息 结束");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("定时推送考勤提示消息 异常", e);
        }

    }

}
