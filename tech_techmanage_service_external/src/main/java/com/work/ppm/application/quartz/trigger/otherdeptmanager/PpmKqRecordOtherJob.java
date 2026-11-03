package com.work.ppm.application.quartz.trigger.otherdeptmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmKqRecordOtherCalculateJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时日常新增非科技外包人员考勤信息
 */
@Slf4j
public class PpmKqRecordOtherJob implements IQuartzExecutorJob {
    /**
     * 定时日常新增非科技外包人员考勤信息
     */
    private IPpmKqRecordOtherCalculateJobService ppmKqRecordOtherCalculateJobService =
            (IPpmKqRecordOtherCalculateJobService) SpringUtil.getBean("ppmKqRecordOtherService");

    @Override
    public void executeInternal() {
        try{
            log.warn("=================> 定时日常新增非科技外包人员考勤信息 开始");
            ppmKqRecordOtherCalculateJobService.addEveryRecordOther();
            log.warn("=================> 定时日常新增非科技外包人员考勤信息 结束");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("定时日常新增非科技外包人员考勤信息 异常", e);
        }

    }

}
