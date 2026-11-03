package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmKqInfoCalculateJobService;
import com.work.ppm.application.quartz.interfaces.IPpmKqRecordCalculateJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 外包人员考勤详情相关计算
 */
@Slf4j
public class PpmKqRecordCalculateJob implements IQuartzExecutorJob {
    /**
     * 外包人员考勤信息计算
     */
    private IPpmKqRecordCalculateJobService ppmKqRecordCalculateJobService =
            (IPpmKqRecordCalculateJobService) SpringUtil.getBean("ppmKqRecordService");

    private IPpmKqInfoCalculateJobService ppmKqInfoCalculateJobService =
            (IPpmKqInfoCalculateJobService) SpringUtil.getBean("ppmKqInfoService");

    @Override
    public void executeInternal() {
        log.info("外包人员考勤详情相关计算 开始");
        try{
            log.warn("=================> 外包人员考勤加班时长跑批计算 开始");
            ppmKqRecordCalculateJobService.overtimeCalaulate();
            log.warn("=================> 外包人员考勤加班时长跑批计算 结束");

            log.warn("=================> 外包人员考勤异常数据跑批计算 开始");
            ppmKqRecordCalculateJobService.recordCalaulate();
            log.warn("=================> 外包人员考勤异常数据跑批计算 结束");

            log.warn("=================> 外包人员考勤信息数据跑批计算 开始");
            ppmKqInfoCalculateJobService.infoCalaulate();
            log.warn("=================> 外包人员考勤信息数据跑批计算 结束");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("外包人员考勤详情相关计算 异常", e);
        }
        log.info("外包人员考勤详情相关计算 结束");

    }

}
