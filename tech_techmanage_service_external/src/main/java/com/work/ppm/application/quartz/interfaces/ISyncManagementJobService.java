package com.work.ppm.application.quartz.interfaces;
import java.io.IOException;
import java.text.ParseException;

public interface ISyncManagementJobService {
    /**
     * 添加考勤日期
     */
    void syncManagementJob() throws IOException, ParseException;

    /**
     * 添加考勤管理
     * @throws IOException
     */
    void syncAttendanceJob() throws IOException;
}
