package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

 /**
  *  外包人员考勤年假记录表 传输对象
  */
@Data
public class PpmKqAnnualVacationDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "String")
	private String id;
	/**
	*	工号
	**/
	@ApiModelProperty(name = "jobId", value = "工号", dataType = "String")
	private String jobId;
	/**
	*	可增加年假起始日期
	**/
	@ApiModelProperty(name = "annualVacationDate", value = "可增加年假起始日期", dataType = "Date")
	private Date annualVacationDate;

	/**
	 *	筛选日期
	 **/
	private String annualVacationDateValue;
	/**
	 *	入职日期
	 **/
	private Date entryTime;
	/**
	*	去年年假天数
	**/
	@ApiModelProperty(name = "annualVacationSurplus", value = "去年年假天数", dataType = "Double")
	private Double annualVacationSurplus;
	/**
	*	今年年假天数
	**/
	@ApiModelProperty(name = "annualVacationDays", value = "今年年假天数", dataType = "Double")
	private Double annualVacationDays;
}
