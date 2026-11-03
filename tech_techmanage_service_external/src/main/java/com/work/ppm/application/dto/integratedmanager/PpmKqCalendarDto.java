package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;



 /**
  *  考勤日历表 传输对象
  */
@Data
public class PpmKqCalendarDto {
	/**
	*	号数
	**/
	@ApiModelProperty(name = "dayOfMonth", value = "号数", dataType = "Long")
	private Long dayOfMonth;
	/**
	*	日历记数
	**/
	@ApiModelProperty(name = "dayOfCalendar", value = "日历记数", dataType = "Long")
	private Long dayOfCalendar;
	/**
	*	月份
	**/
	@ApiModelProperty(name = "monthOfYear", value = "月份", dataType = "Long")
	private Long monthOfYear;
	/**
	*	年份
	**/
	@ApiModelProperty(name = "yearOfCalendar", value = "年份", dataType = "Long")
	private Long yearOfCalendar;
	/**
	*	是否为节假日(0:否，1：是)
	**/
	@ApiModelProperty(name = "isHoliday", value = "是否为节假日(0:否，1：是)", dataType = "String")
	private String isHoliday;
	/**
	*	日期
	**/
	@ApiModelProperty(name = "calendarDate", value = "日期", dataType = "Date")
	private Date calendarDate;
	/**
	*	星期几
	**/
	@ApiModelProperty(name = "dayOfWeek", value = "星期几", dataType = "Long")
	private Long dayOfWeek;

	 /**
	  * 时间区间
	  */
	 private String[] entryTime;

	 /**
	  *开始时间
	  **/
	 private Date entryStartDate;
	 /**
	  * 结束时间
	  */
	 private Date entryEndDate;
}
