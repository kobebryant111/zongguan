package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 外包人员考勤年假记录表 实体对象
  */
@Data
public class PpmKqAnnualVacation {
	/**
	*	唯一编号
	**/
	@Id
	private String id;
	/**
	*	工号
	**/
	private String jobId;
	/**
	*	可增加年假起始日期
	**/
	private Date annualVacationDate;
	/**
	*	去年年假天数
	**/
	private Double annualVacationSurplus;
	/**
	*	今年年假天数
	**/
	private Double annualVacationDays;
	/**
	 *	筛选日期
	 **/
	private String annualVacationDateValue;
	/**
	 *	入职日期
	 **/
	private Date entryTime;
}
