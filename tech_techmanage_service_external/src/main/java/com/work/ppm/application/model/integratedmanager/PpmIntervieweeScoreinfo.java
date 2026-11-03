package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmIntervieweeScoreinfo {
	/**
	*	id
	**/
	@Id
	private Long id;
	/**
	*	身份证id
	**/
	private String idcard;
	/**
	*	面试官工号
	**/
	private String msgid;
	/**
	*	面试官姓名
	**/
	private String msgname;
	 /**
	  *	学历评分
	  **/
	 private String education;
	 /**
	  *	经验评分
	  **/
	 private String experience;
	 /**
	  *	技术评分
	  **/
	 private String technology;
	 /**
	  *	综合评分
	  **/
	 private String comprehensive;

	 /**
	*	评语
	**/
	private String remark;
	/**
	*	流程id
	**/
	private String instanceid;
	/**
	*	对应的ppm_interviewee_info(申请人员信息表)的id
	**/
	private String pid;
}
