package com.work.ppm.application.model.otherdeptmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 对接手机平台信息 实体对象
  */
@Data
public class PpmDevelopGroupInfo {
	/**
	*	唯一编号
	**/
	@Id
	private String id;
	/**
	*	部门编号
	**/
	private String deptId;
	/**
	*	部门名称
	**/
	private String deptName;
	/**
	*	小组名称
	**/
	private String groupName;
	/**
	*	小组长编号
	**/
	private String groupLeaderId;
	/**
	*	小组长名称
	**/
	private String groupLeaderName;
	/**
	*	小组长虚拟机号
	**/
	private String groupLeaderCard;
	/**
	*	小组性质
	**/
	private String groupType;
	/**
	*	创建人
	**/
	private String createdBy;
	/**
	*	创建时间
	**/
	private Date createdTime;
	/**
	*	修改人
	**/
	private String modifiedBy;
	/**
	*	修改时间
	**/
	private Date modifiedTime;
}
