package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmIntervieweeInfo {
	/**
	*	id
	**/
	@Id
	private Long id;
	/**
	*	面试人姓名
	**/
	private String msname;
	/**
	*	面试人性别 0:女1:男
	**/
	private String sex;
	/**
	*	身份证
	**/
	private String idcard;
	/**
	*	出生日期
	**/
	private String birthday;
	/**
	*	工作年限
	**/
	private String workyears;
	/**
	*	建议等级
	**/
	private String suggestlevel;
	/**
	*	外包商id
	**/
	private String wbsid;
	/**
	*	岗位
	**/
	private String position;
	/**
	*	简历
	**/
	private String jl;
	/**
	*	流程id
	**/
	private String instanceid;
	/**
	*	面试评语
	**/
	private String mspy;
	/**
	*	成绩
	**/
	private String score;
	/**
	*	面试结果 0：通过1：不通过
	**/
	private String interviewresult;
	/**
	*	建议单价
	**/
	private String suggestprice;
	/**
	*	修改理由
	**/
	private String reason;
	/**
	*	面试日期
	**/
	private Date msDate;
	 /**
	  * 人员属性（1：人力，2：项目）
	  */
	 private Integer researchers;
}
