package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 面试申请
  */
@Data
public class PpmInterviewApply {
	/**
	*	id
	**/
	@Id
	private Long id;
	/**
	*	部门
	**/
	private String deptno;
	/**
	*	部门名称
	**/
	private String deptname;
	/**
	*	流程id
	**/
	private String instanceid;
	/**
	*	申请人
	**/
	private String applyer;
	/**
	*	创建时间
	**/
	private String createtime;
	/**
	*	人力需求编号
	**/
	private Long rlId;
	 /**
	  是否符合要求（0：否，1：是）
	  */
	private Integer meet;
	 /**
	   不符合原因
	  */
	private String remarks;
}
