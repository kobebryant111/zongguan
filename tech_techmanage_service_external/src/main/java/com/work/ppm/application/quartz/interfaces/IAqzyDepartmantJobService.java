package com.work.ppm.application.quartz.interfaces;

import com.work.ppm.application.services.systemmanager.impl.AqzyDepartmantJobService;

import java.io.IOException;

public interface IAqzyDepartmantJobService {
    /**
     * 定时解析安全总部部门同步文件任务
     *
     * @throws IOException
     */
    void AqzyDepartmantJob() throws IOException; 
}
