package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmOutsourcingTrain {
	/**
	*	主键
	**/
	@Id
	private Long id;
	/**
	*	工号
	**/
	private String jobid;
	/**
	*	姓名
	**/
	private String empname;
	/**
	*	培训时间
	**/
	private Date traintime;
	/**
	*	培训项目(如果项目独立，存项目编号)
	**/
	private String project;
	/**
	*	分数
	**/
	private Double score;
	/**
	*	创建人
	**/
	private String createuserid;
	/**
	*	创建时间
	**/
	private Date createdate;
	/**
	*	培训导师
	**/
	private String traintec;
}
