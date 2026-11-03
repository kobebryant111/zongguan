package com.work.ppm.application.model.otherdeptmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 监保部台账领用申请表 实体对象
  */
@Data
public class PpmAccessCardApply {
	/**
	*	唯一编号
	**/
	@Id
	private String id;
	/**
	*	类型 RECEIVE 领用流程 RETURN 归还流程
	**/
	private String type;
	/**
	*	流程编号
	**/
	private String instanceId;
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
	 *	**/
	private String fileNo;
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
	 *	是否删除 0否 1是
	 **/
	private String isDel;
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
