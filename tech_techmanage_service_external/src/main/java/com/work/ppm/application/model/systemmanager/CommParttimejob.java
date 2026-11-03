package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class CommParttimejob {
	/**
	*	字段名
	**/
	private String headship1;
	/**
	*	兼职员工代码
	**/
	private Long eid;
	/**
	*	工号
	**/
	private String badge;
	/**
	*	姓名
	**/
	private String empname;
	/**
	*	兼职机构代码
	**/
	private Long compid;
	/**
	*	兼职部门代码
	**/
	private Long depid;
	/**
	*	兼职岗位代码
	**/
	private Long jobid;
	/**
	*	兼职条线
	**/
	private String boardline;
	/**
	*	成本分摊比例
	**/
	private Long costcut;
	/**
	*	兼职开始时间
	**/
	private Date begindate;
	/**
	*	兼职结束时间
	**/
	private Date enddate;
	/**
	*	失效标示
	**/
	private String isdisable;
	/**
	*	备注
	**/
	private String remark;
	/**
	*	兼职id
	**/
	private Long partid;
}
