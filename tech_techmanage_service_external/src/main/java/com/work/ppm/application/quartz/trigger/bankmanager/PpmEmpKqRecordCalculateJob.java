package com.work.ppm.application.quartz.trigger.bankmanager;

import com.work.ppm.application.quartz.bankmanager.IPpmEmpKqInfoCalculateJobService;
import com.work.ppm.application.quartz.bankmanager.IPpmEmpKqRecordCalculateJobService;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 行员考勤详情相关计算
 *
 */
@Slf4j
public class PpmEmpKqRecordCalculateJob implements IQuartzExecutorJob {
    /**
     * 行员考勤信息计算
     */
    private IPpmEmpKqRecordCalculateJobService ppmKqRecordCalculateJobService =
            (IPpmEmpKqRecordCalculateJobService) SpringUtil.getBean("ppmEmpKqRecordService");

    private IPpmEmpKqInfoCalculateJobService ppmKqInfoCalculateJobService =
            (IPpmEmpKqInfoCalculateJobService) SpringUtil.getBean("ppmEmpKqInfoService");

    @Override
    public void executeInternal() {
        log.info("外包人员考勤详情相关计算 开始");
        try {
            log.warn("=================> 行员考勤加班时长跑批计算 开始");
            ppmKqRecordCalculateJobService.overtimeCalaulate();
            log.warn("=================> 行员考勤加班时长跑批计算 结束");

            log.warn("=================> 行员考勤异常数据跑批计算 开始");
            ppmKqRecordCalculateJobService.recordCalaulate();
            log.warn("=================> 行员考勤异常数据跑批计算 结束");

            log.warn("=================> 行员考勤信息数据跑批计算 开始");
            ppmKqInfoCalculateJobService.infoCalaulate();
            log.warn("=================> 行员考勤信息数据跑批计算 结束");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("行员考勤详情相关计算 异常", e);
        }
        log.info("行员考勤详情相关计算 结束");

    }

}
