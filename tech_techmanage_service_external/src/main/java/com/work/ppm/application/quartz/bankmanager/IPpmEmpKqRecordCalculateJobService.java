package com.work.ppm.application.quartz.bankmanager;

/**
 * 外包人员考勤信息计算
 *
 */
public interface IPpmEmpKqRecordCalculateJobService {
    /**
     * 行员考勤信息计算
     */
    void recordCalaulate();

    /**
     * 加班、工作时长计算
     */
    void overtimeCalaulate();

}
