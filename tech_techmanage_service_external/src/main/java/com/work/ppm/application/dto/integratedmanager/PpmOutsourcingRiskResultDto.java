package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmOutsourcingRiskResultDto {
	/**
	*	主键
	**/
	private Long id;
	/**
	*	工号
	**/
	private String jobid;
	/**
	*	姓名
	**/
	private String username;
	/**
	*	身份证号
	**/
	private String certno;
	/**
	*	部门id
	**/
	private String depid;
	/**
	*	部门名称
	**/
	private String depname;
	/**
	*	公司名称
	**/
	private String orgname;
	/**
	*	公司代码
	**/
	private String orgcode;
	/**
	*	说明情况
	**/
	private String explainstate;
	/**
	*	二级部反馈
	**/
	private String depfeedback;
	/**
	*	处理状态
	**/
	private Long dealstatus;
	/**
	*	总经理工号
	**/
	private String manageuserid;
	/**
	*	排查意见
	**/
	private String risksuggest;
	/**
	*	排查日期
	**/
	private Date riskdate;
	/**
	*	备注
	**/
	private String remark;
	/**
	*	排查类型
	**/
	private Long risktype;
	/**
	*	录入修改人
	**/
	private String regid;
	/**
	*	录入修改时间
	**/
	private Date regtime;
	/**
	*	总经理意见
	**/
	private String managesuggest;
	/**
	*	年
	**/
	private String tyear;
	/**
	*	月
	**/
	private String tmonth;
}
