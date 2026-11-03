package com.work.ppm.application.quartz.interfaces;


/**
  * 接口描述
  */
public interface IPpmKpiPmJobService {

    /**
     * 自动发起自评
     */
    void autoLaunchSelfEval();

    /**
     * 自动发起经理半年度自评
     */
    void autoLaunchHalfYearSelfEval();
}

