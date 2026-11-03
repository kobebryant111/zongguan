package com.work.ppm.application.quartz.interfaces;

/**
 * 外包人员考勤次数计算
 */
public interface IPpmKqInfoCalculateJobService {
    /**
     * 外包人员考勤次数计算
     */
    void infoCalaulate() ;

    /**
     * 日常新增外包人员考勤信息
     */
    void addInfoAndRecord();
}
