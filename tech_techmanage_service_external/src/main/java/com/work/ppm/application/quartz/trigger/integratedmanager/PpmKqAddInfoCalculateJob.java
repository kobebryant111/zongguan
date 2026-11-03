package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmKqInfoCalculateJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 外包人员考勤信息新增跑批
 */
@Slf4j
public class PpmKqAddInfoCalculateJob implements IQuartzExecutorJob {
    /**
     * 外包人员考勤信息新增跑批
     */
    private IPpmKqInfoCalculateJobService ppmKqInfoCalculateJobService =
            (IPpmKqInfoCalculateJobService) SpringUtil.getBean("ppmKqInfoService");

    @Override
    public void executeInternal() {
        try{
            log.warn("=================> 外包人员考勤信息新增跑批 开始");
            ppmKqInfoCalculateJobService.addInfoAndRecord();
            log.warn("=================> 外包人员考勤信息新增跑批 结束");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("外包人员考勤信息数据新增 异常", e);
        }

    }

}
