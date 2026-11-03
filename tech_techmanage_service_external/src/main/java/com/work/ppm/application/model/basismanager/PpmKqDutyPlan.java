package com.work.ppm.application.model.basismanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmKqDutyPlan {
	/**
	*	主键
	**/
	@Id
	private Long id;
	/**
	*	考勤类型A,B,C,D,E,F,G类
	**/
	private String kqType;
	/**
	*	周期，1:周一，2:周二，3:周三，4:周四，5:周五，6:周六，7:周日
	**/
	private String code;
	/**
	*	中文描述
	**/
	private String name;
	/**
	*	创建日期
	**/
	private Date createDate;
	/**
	*	上班开始时间1
	**/
	private String startDate1;
	/**
	*	下班结束时间1
	**/
	private String endDate1;
	/**
	*	上班开始时间2
	**/
	private String startDate2;
	/**
	*	下班结束时间2
	**/
	private String endDate2;
	/**
	*	加班开始时间
	**/
	private String overtime;
	/**
	*	加班小时
	**/
	private Long overHour;
	/**
	*	工作小时
	**/
	private Long workHour;
	/**
	*	是否计算加班（周末和平时加班都会计算），1:是 2:否
	**/
	private String isOvettime;
	/**
	*	是否存在休假（每天都工作），1:是 2:否
	**/
	private String isXiujia;
	/**
	*	是否夸日计算，1:是 2:否
	**/
	private String isOutdate;
	/**
	*	是否跨段，1:是 2:否
	**/
	private String isFract;
	/**
	*	是否轮班
	**/
	private String isLunban;
}
