package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 人员移交信息 实体对象
  */
@Data
public class PpmStaffTransfer {
	/**
	*	唯一编号
	**/
	@Id
	private Long id;
	/**
	*	流程编号
	**/
	private String instanceid;
	/**
	*	申请人编号
	**/
	private String applyerId;
	/**
	*	申请人部门编号
	**/
	private String applyerDeptId;
	/**
	*	流程状态码 -1 已关闭 0 未发起 1已发起
	**/
	private String state;
	/**
	*	流程状态码名称 -1 已关闭 0未发起 >1 根据实际流程节点展示
	**/
	private String stateName;
	/**
	*	当前处理人编号
	**/
	private String currentId;
	/**
	*	当前处理人名称
	**/
	private String currentName;
	/**
	*	创建时间
	**/
	private Date createdTime;
	/**
	*	创建人
	**/
	private String createdBy;
	/**
	*	修改时间
	**/
	private Date modifiedTime;
	/**
	*	修改人
	**/
	private String modifiedBy;
}
