package com.work.ppm.application.services.basismanager;

import com.work.ppm.application.model.basismanager.PpmKqDutyPlan;

import java.util.List;

/**
 * 考勤表
 */
public interface IPpmKqDutyPlanService {
    /**
     * 获取考勤类型
     * @return List<PpmKqDutyPlan>
     */
    List<PpmKqDutyPlan> getKqTypeList();

    /**
     * 根据考勤类型获取考勤时间
     * @param kqType 考勤类型
     * @return PpmKqDutyPlan
     */
    PpmKqDutyPlan getKqTime(String kqType);
}
