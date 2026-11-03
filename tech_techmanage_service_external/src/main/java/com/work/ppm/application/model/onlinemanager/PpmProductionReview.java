package com.work.ppm.application.model.onlinemanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmProductionReview {
	/**
	*	字段名
	**/
	@Id
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
	*	字段名
	**/
	private String projectno;
	/**
	*	字段名
	**/
	private String projectname;
	/**
	*	字段名
	**/
	private Date chgdate;
	/**
	*	字段名
	**/
	private String remark;
	/**
	*	字段名
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
	*	字段名
	**/
	private String xmpj;
	/**
	*	字段名
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
	private Date jhyzdate;
	/**
	*	字段名
	**/
	private String planonline;
	/**
	*	字段名
	**/
	private String isqbsx;
}
