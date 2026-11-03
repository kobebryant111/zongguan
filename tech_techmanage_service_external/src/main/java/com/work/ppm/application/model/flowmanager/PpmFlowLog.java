package com.work.ppm.application.model.flowmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmFlowLog {
	/**
	*	??PPM_FLOW_LOG_ID ??EOS?????????
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String flowdraftno;
	/**
	*	字段名
	**/
	private String flowcaseno;
	/**
	*	字段名
	**/
	private String userid;
	/**
	*	字段名
	**/
	private Date examine;
	/**
	*	01???
02???
???????GLOBAL_FLOW_CONCLUSION
	**/
	private String conclusion;
	/**
	*	字段名
	**/
	private String suggest;
	/**
	*	1-????0-???
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
	private String applydesc;
	/**
	*	字段名
	**/
	private String deptno;
	/**
	*	字段名
	**/
	private String isfalse;
	/**
	*	字段名
	**/
	private String roleid;
	 /**
	  * 流程id
	  */
	 private String flowid;
	 /**
	  * 流程状态
	  */
	 private String flowstatus;
	 /**
	  * 流程标题
	  */
	 private String flowtitle;
	 /**
	  * 部门名称
	  */
	 private String deptname;
	 /**
	  * 用户名称
	  */
	 private String username;
	 /**
	  * 联系电话
	  */
	 private String tel;
	 /**
	  * 多个id
	  */
	 private String ids;
}
