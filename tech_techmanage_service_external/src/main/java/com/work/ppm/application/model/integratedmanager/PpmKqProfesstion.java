package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmKqProfesstion {
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
	@Id
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
