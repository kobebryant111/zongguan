package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;


/**
 * 考勤日历表 传输对象
 *
 */
@Data
public class PpmKqCalendarExcelDto {
    /**
     * 日期
     **/
    @Excel(name = "calendar_date", orderNum = "1",format = "yyyy/MM/dd")
    private Date calendarDate;
    /**
     * 星期几
     **/
    @Excel(name = "day_of_week", orderNum = "2")
    private Long dayOfWeek;
    /**
     * 号数
     **/
    @Excel(name = "day_of_month", orderNum = "3")
    private Long dayOfMonth;
    /**
     * 日历记数
     **/
    @Excel(name = "day_of_calendar", orderNum = "4")
    private Long dayOfCalendar;
    /**
     * 月份
     **/
    @Excel(name = "month_of_year", orderNum = "6")
    private Long monthOfYear;
    /**
     * 年份
     **/
    @Excel(name = "year_of_calendar", orderNum = "7")
    private Long yearOfCalendar;
    /**
     *	是否为节假日(N否，Y：是)
     **/
    @Excel(name = "is_holiday", orderNum = "8")
    private String isHoliday;
}
