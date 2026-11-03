package com.work.ppm.application.quartz.interfaces;

import java.io.IOException;

/**
 * 同步数据服务接口
 */
public interface ISyncDataJobService {

    /**
     * 同步部门任务
     */
    void syncDepartmentJob() throws IOException;

    /**
     * 同步行员任务
     */
    void syncEmployeeJob() throws IOException;
    /**
     * 同步岗位任务
     */
    void syncPostJob() throws IOException;
    /**
     * 同步考勤机数据
     */
    void syncFaceDevice() throws IOException;
}
