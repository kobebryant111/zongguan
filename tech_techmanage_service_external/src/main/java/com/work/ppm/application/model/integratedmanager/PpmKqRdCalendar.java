package com.work.ppm.application.model.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PpmKqRdCalendar {
    /**
     * 主键
     */
    private String id;
    /**
     *	考勤日期
     **/
    private String attendanceDate;

    /**
     *	号数
     **/
    private Long dayOfMonth;
    /**
     *	日历记数
     **/
    @Id
    private Long dayOfCalendar;
    /**
     *	月份
     **/
    private Long monthOfYear;
    /**
     *	年份
     **/
    private Long yearOfCalendar;
    /**
     *	是否为节假日(N否，Y：是)
     **/
    private String isHoliday;
    /**
     *	日期
     **/
    private Date calendarDate;
    /**
     *	星期几
     **/
    private Long dayOfWeek;

    /**
     *	项目编号
     **/
    private String eidCode;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 工号
     **/
    private String badge;
    /**
     *	考勤时长
     **/
    private BigDecimal attendanceTime;

    /**
     *	考勤状态
     **/
    private String attendanceStatus;
    /**
     *	考勤记录
     **/
    private String attendanceRecord;
    /**
     * 是否出勤
     */
    private String attendanceOrno;

}
