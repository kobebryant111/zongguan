package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmKqProfesstionDto {
	/**
	*	考勤专员编号
	**/
	private String jobid;
	/**
	*	创建人
	**/
	private String createJobid;
	/**
	*	创建日期
	**/
	private Date createDate;
	/**
	*	考勤专员姓名
	**/
	private String jobname;
	/**
	*	主键
	**/
	private Long id;
	/**
	*	字段名
	**/
	private String deptno;
	/**
	*	字段名
	**/
	private String remark;
}
