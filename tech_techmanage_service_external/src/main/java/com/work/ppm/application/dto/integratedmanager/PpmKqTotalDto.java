package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;


 /**
  * 描述
  */
@Data
public class PpmKqTotalDto {
	/**
	*	ID
	**/
	private Long id;
	/**
	*	工号
	**/
	private String jobid;
	/**
	*	年
	**/
	private String tyear;
	/**
	*	月
	**/
	private String tmonth;
	/**
	*	标准时数
	**/
	private String mworktime;
	/**
	*	标准天数
	**/
	private String mworkday;
	/**
	*	工作时数
	**/
	private String worktime;
	/**
	*	工作天数
	**/
	private String workday;
	/**
	*	旷工天数
	**/
	private Double unworkday;
	/**
	*	请假天数
	**/
	private Double lvworkday;
	/**
	*	迟到分钟
	**/
	private Double latemin;
	/**
	*	早退分钟
	**/
	private Double earmin;
	/**
	*	迟到次数
	**/
	private Double latecs;
	/**
	*	早退次数
	**/
	private Double earcs;
	/**
	*	加班时数
	**/
	private Double overtime;
	/**
	*	加班天数
	**/
	private Double overtimeday;
	/**
	*	其他
	**/
	private String othertime;
	/**
	*	是否封帐
	**/
	private String isfz;
	/**
	*	二级部门经理
	**/
	private String badge;
	/**
	*	调休时间
	**/
	private Double tiaoxiutime;
	/**
	*	外包方式
	**/
	private String outsourcetype;
	/**
	*	项目负责人
	**/
	private String cfmuserid;
	/**
	*	所属部门
	**/
	private String deptno;
	/**
	*	忘记打卡次数
	**/
	private Double wjdkcs;
	/**
	*	字段名
	**/
	private Double xiujiatime;
	/**
	*	字段名
	**/
	private String lrrgh;
	/**
	*	字段名
	**/
	private Double ysynj;
}
