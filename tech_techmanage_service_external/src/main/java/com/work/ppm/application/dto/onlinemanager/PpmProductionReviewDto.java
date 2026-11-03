package com.work.ppm.application.dto.onlinemanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import lombok.Data;

import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmProductionReviewDto {
	/**
	*	字段名
	**/
	private Long id;
	/**
	*	字段名
	**/
	private String productionno;
	/**
	*	字段名
	**/
	private String onlineno;
	/**
	*	相关项目名称编号
	**/
	private String projectno;
	/**
	*	相关项目名称
	**/
	private String projectname;
	/**
	*	上线日期
	**/
	private Date chgdate;
	/**
	*	备注
	**/
	private String remark;
	/**
	*	上线后验证
	**/
	private String reviewres;
	/**
	*	字段名
	**/
	private String instanceid;
	/**
	*	字段名
	**/
	private String flowid;
	/**
	*	字段名
	**/
	private Integer isdel;
	/**
	*	字段名
	**/
	private Date createtime;
	/**
	*	字段名
	**/
	private String creators;
	/**
	*	字段名
	**/
	private Date lastmodtime;
	/**
	*	字段名
	**/
	private String lastmoduser;
	/**
	*
	 *对项目评价总体评价{id:01,text:好},{id:02,text:中},{id:03,text:差}
	**/
	private String xmpj;
	/**
	*	项目评价内容
	**/
	private String pjcont;
	/**
	*	字段名
	**/
	private String gbfs;
	/**
	*	字段名
	**/
	private String tsqksm;
	/**
	*	项目经理评价
	**/
	private String jlpjcont;
	/**
	*	对项目经理总体评价{id:01,text:好},{id:02,text:中},{id:03,text:差}
	**/
	private String xmjlpj;
	/**
	*	字段名
	**/
	private String lastmoduserid;
	/**
	*	延期验证   1：是   2：否
	**/
	private String isyqyz;
	/**
	*	计划验证时间
	**/
    @JsonFormat(pattern = "yyyy-MM-dd")
	private Date jhyzdate;
	/**
	*	字段名
	**/
	private String planonline;
	/**
	*	字段名
	**/
	private String isqbsx;
	 /**
	  * 审批业务经理
	  */
	 private String busiuserid;
	 /**
	  * 审批流程
	  */
	 private BpsWorkFlowDto bps;
 }
