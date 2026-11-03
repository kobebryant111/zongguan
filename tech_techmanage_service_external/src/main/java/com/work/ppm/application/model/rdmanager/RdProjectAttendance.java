package com.work.ppm.application.model.rdmanager;

import lombok.Data;

import java.util.Date;

/**
 * 考勤月份管理
 *
 */
@Data
public class RdProjectAttendance {
    /**
     *	考勤日期
     **/
    private String attendanceDate;
    /**
     *	日期
     **/
    private Date calendarDate;
    /**
     *	考勤状态
     **/
    private String attendanceStatus;
}
