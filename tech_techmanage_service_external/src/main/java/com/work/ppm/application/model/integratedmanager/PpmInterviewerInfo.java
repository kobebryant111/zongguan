package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmInterviewerInfo {
	/**
	*	id
	**/
	@Id
	private Long id;
	/**
	*	流程id
	**/
	private String instanceid;
	/**
	*	面试官工号
	**/
	private String msgid;
	/**
	*	面试官姓名
	**/
	private String msgname;
	/**
	*	面试官部门名称
	**/
	private String msgdeptname;
	/**
	*	面试官部门id
	**/
	private String msgdeptno;
}
