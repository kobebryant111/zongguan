package com.work.ppm.application.quartz.interfaces;

/**
 * 外包人员基础
 */
public interface IPpmOutsourcingBasicJobService {
    /**
     * 更新老应用外包数据
     */
    void updateOldSystemOut();

    /**
     * 更新外包人员在岗状态
     */
    void updateBasicState();
}
