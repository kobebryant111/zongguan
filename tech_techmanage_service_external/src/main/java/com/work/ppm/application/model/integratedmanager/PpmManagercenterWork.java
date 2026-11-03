package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmManagercenterWork {
	/**
	*	要事状态
	**/
	private String state;
	/**
	*	要事进度
	**/
	private String projectprocess;
	/**
	*	录入时间
	**/
	private Date entertime;
	/**
	*	流程ID
	**/
	private String instanceid;
	/**
	*	发起人部门名
	**/
	private String depname;
	/**
	*	发起人名
	**/
	private String creatorname;
	/**
	*	负责部门名
	**/
	private String dealdeptname;
	/**
	*	项目经理名
	**/
	private String projectmanagername;
	/**
	*	协助部门名
	**/
	private String helpdepname;
	/**
	*	类型
	**/
	private String worktype;
	/**
	*	来源
	**/
	private String projectfrom;
	/**
	*	最终验收人
	**/
	private String finalchecker;
	/**
	*	评价等级
	**/
	private String score;
	/**
	*	ID
	**/
	@Id
	private String id;
	/**
	*	创建人
	**/
	private String creator;
	/**
	*	创建部门
	**/
	private String dep;
	/**
	*	联系电话
	**/
	private String telphone;
	/**
	*	创建时间
	**/
	private Date createtime;
	/**
	*	要事名称
	**/
	private String projectname;
	/**
	*	要事编号
	**/
	private String projectno;
	/**
	*	牵头部门ID
	**/
	private String dealdeptid;
	/**
	*	牵头负责人
	**/
	private String projectmanager;
	/**
	*	开始时间
	**/
	private Date planstartdate;
	/**
	*	结束时间
	**/
	private Date planenddate;
	/**
	*	协助部门
	**/
	private String helpdep;
	/**
	*	督办经理
	**/
	private String supply;
	/**
	*	要事简介
	**/
	private String projectdetail;
}
