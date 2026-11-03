package com.work.ppm.application.quartz.interfaces;

/**
 * 人员移交定时接口
 */
public interface IPpmStaffTransferJobService {
    /**
     * 定时处理人员移交时间
     */
    void handleTransferTime();
}
