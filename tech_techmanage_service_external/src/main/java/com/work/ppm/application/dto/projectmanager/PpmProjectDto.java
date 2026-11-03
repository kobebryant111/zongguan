package com.work.ppm.application.dto.projectmanager;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmProjectDto {
	/**
	*	ID
	**/
	private Long id;
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
	private String subprojectno;
	/**
	*	01????
02????
	**/
	private String subtype;
	/**
	*	字段名
	**/
	private String sysno;
	/**
	*	字段名
	**/
	private String subprojectname;
	/**
	*	字段名?
	**/
	private String projectscale;
	/**
	*	字段名
	**/
	private String deptno;
	/**
	*	字段名
	**/
	private String busideptno;
	/**
	*	字段名
	**/
	private String manauserid;
	/**
	*	字段名
	**/
	private String busiuserid;
	/**
	*	字段名
	**/
	private Double planTWorkload;
	/**
	*	字段名
	**/
	private Double actualTWorkload;
	/**
	*	字段名
	**/
	private Double planOWorkload;
	/**
	*	字段名
	**/
	private Double actualOWorkload;
	/**
	*	字段名
	**/
	private Double planBWorkload;
	/**
	*	字段名
	**/
	private Double actualBWorkload;
	/**
	*	字段名
	**/
	private Date builddate;
	/**
	*	字段名
	**/
	private Date startdate;
	/**
	*	项目状态
	**/
	private String status;
	/**
	*	GLOBAL_YESNO
	**/
	private String isdelay;
	/**
	*	字段名
	**/
	private Date expectdate;
	/**
	*	字段名
	**/
	private Date actualdate;
	/**
	*	字段名
	**/
	private Date closedate;
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
	private String orgarea;
	/**
	*	字段名
	**/
	private String isproduction;
	/**
	*	字段名
	**/
	private String isonline;
	/**
	*	字段名
	**/
	private String coupledsys;
	/**
	*	字段名
	**/
	private Long gnd;
	/**
	*	健康率
	**/
	private Long healthscore;
	/**
	*	最新用户故事id
	**/
	private String fjhid;
	/**
	*	用户故事id,故事名称,开发工作量,测试工作量
	**/
	private String issueworkload;
	/**
	*	测试部门
	**/
	private String testdeptno;

	 /**
	  * 系统名称
	  */
	 private String sysname;

	 /**
	  * 员工名称
	  */
	 private String empname;

	 /**
	  * 系统用户ID
	  */
	 private String sysuserid;

	 /**
	  * 系统用户名称
	  */
	 private String sysusername;

	 /**
	  * 计划上线
	  */
	 private String planonline;

	 /**
	  * 机构
	  */
	 private String org;

	 /**
	  * 运维等级
	  */
	 private String vel;

	 /**
	  * 业务部门
	  */
	 private String busdep;

     /**
      * 上线日期
      */
     private String onlineno;

	 /**
	  * 是否配合类名称
	  */
	 private String coupledsysname;

	 /**
	  * 互联网变更场景名称
	  */
	 private String safetytestname;

	 /**
	  * 互联网变更场景类型
	  */
	 private String safetytesttype;
}
