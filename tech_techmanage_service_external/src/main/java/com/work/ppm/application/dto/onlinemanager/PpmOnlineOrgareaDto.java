package com.work.ppm.application.dto.onlinemanager;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmOnlineOrgareaDto {
	/**
	*	是否配合类
	**/
	private String coupledsys;
	/**
	*	互联网变更场景
	**/
	private String safetytesttype;
	/**
	*	ID
	**/
	private Long id;
	/**
	*	子项目编号
	**/
	private String projectno;
	/**
	*	上线日期
	**/
	private Date planonline;
	/**
	*	上线范围
	**/
	private String orgarea;
	/**
	*	是否删除
	**/
	private Integer isdel;
	/**
	*	创建时间
	**/
	private Date createtime;
	/**
	*	创建者
	**/
	private String creators;
	/**
	*	更新时间
	**/
	private Date lastmodtime;
	/**
	*	更新者
	**/
	private String lastmoduser;
	/**
	*	字段名
	**/
	private String onlineno;
	/**
	*	字段名
	**/
	private String isonline;
	/**
	*	业务总经理
	**/
	private String linkman;
	/**
	*	业务总经理名称
	**/
	private String linkname;
	 /**
	  *	互联网变更场景
	  **/
	 private String safetytestname;
	 /**
	  *	系统编号
	  */
	 private String sysNo;
	 /**
	  * 上线流程名称
	  */
	 private String projectname;
}
