package com.work.ppm.application.model.projectmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 分行项目变更
  */
@Data
public class PpmFhyyxtbgApply {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	申请人
	**/
	private String applyuser;
	/**
	*	分行编号
	**/
	private String compcode;
	/**
	*	申请时间
	**/
	private Date applytime;
	/**
	*	标题
	**/
	private String applyChangeTitle;
	/**
	*	系统编号
	**/
	private String mSysno;
	/**
	*	变更日期
	**/
	private Date changedate;
	/**
	*	分行IT支持部经理
	**/
	private String fhitmanager;
	/**
	*	分行运营部总经理
	**/
	private String fhyybmanager;
	/**
	*	字段名
	**/
	private Date createdate;
	/**
	*	字段名
	**/
	private String createuser;
	/**
	*	字段名
	**/
	private Date updatedate;
	/**
	*	字段名
	**/
	private String updateuser;
	/**
	*	字段名
	**/
	private String instanceid;
	/**
	*	变更类型
	**/
	private String changetype;
	/**
	*	变更项目编号
	**/
	private String projectno;
	/**
	*	缺陷标题
	**/
	private String patchtitle;
	/**
	*	缺陷ID
	**/
	private String pdefectid;
	/**
	*	预计变更时长(小时)
	**/
	private String changehour;
	/**
	*	是否需要申请用户密码
	**/
	private String istopwd;
	/**
	*	备注
	**/
	private String pwdnote;
	/**
	*	使用开始时间
	**/
	private Date usestarttime;
	/**
	*	使用结束时间
	**/
	private Date useendtime;
	/**
	*	使用时长（小时）
	**/
	private String usehour;
	/**
	*	流程编号
	**/
	private String applycode;
	/**
	*	计划使用开始时间
	**/
	private Date planstarttime;
	 /**
	  奋进号的issueId
	  */
	 private String issueId;
	 /**
	  * 上线编号
	  */
	 private String onlineno;
	 /**
	  * 是否涉及网络变更 1:是 ,0: 否
	  */
	 private String ifWebChange;
	 /**
	  * 是否涉及服务器变更  1:是 ,0: 否
	  */
	 private String ifServerChange;
	 /**
	  * 是否涉及中间件变更  1:是 ,0: 否
	  */
	 private String ifMiddleware;
}
