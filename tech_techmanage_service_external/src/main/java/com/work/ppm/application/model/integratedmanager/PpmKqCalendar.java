package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;

 /**
  * 考勤日历表 实体对象
  */
@Data
public class PpmKqCalendar {
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
}
