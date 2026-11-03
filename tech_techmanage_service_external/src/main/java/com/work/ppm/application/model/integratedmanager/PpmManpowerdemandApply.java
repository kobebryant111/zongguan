package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmManpowerdemandApply {
	/**
	*	id
	**/
	@Id
	private Long id;
	/**
	*	用户id
	**/
	private String userid;
	/**
	*	用户名
	**/
	private String username;
	/**
	*	部门名
	**/
	private String dept;
	/**
	*	部门id
	**/
	private String deptno;
	/**
	*	流程号
	**/
	private String instanceid;
	/**
	*	创建时间
	**/
	private String createtime;
	/**
	*	创建人
	**/
	private String createuserid;
	/**
	*	提至供应商时间
	**/
	private Date supplytime;
	/**
	*	审批的主持工作经理
	**/
	private String manageruserid;
	/**
	*	综合部人员确认
	**/
	private String confirmuserid;
	/**
	*	流程状态0：未完成，1：流程已完成，2：面试完成
	**/
	private String status;
}
