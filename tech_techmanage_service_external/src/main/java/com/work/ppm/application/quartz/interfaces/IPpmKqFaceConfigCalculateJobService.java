package com.work.ppm.application.quartz.interfaces;

/**
 * 考勤机定时任务
 */
public interface IPpmKqFaceConfigCalculateJobService {
    /**
     * 定时下发考勤机
     */
    void handFaceDeviceTimer() ;

    /**
     * 定时下发人脸考勤机 离岗下发空白照片
     */
    void handFaceDeviceLeavingTimer();

}
