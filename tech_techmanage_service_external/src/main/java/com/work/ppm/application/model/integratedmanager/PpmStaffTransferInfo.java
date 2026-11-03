package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 人员移交详情信息 实体对象
  */
@Data
public class PpmStaffTransferInfo {
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
	*	工号
	**/
	private String jobId;
 	/**
	 *	外包方式
	 **/
	private String outsourceType;
	/**
	*	移交时间
	**/
	private Date transferTime;
	/**
	*	移交状态
	**/
	private String transferState;
	/**
	*	供应商编号
	**/
	private String supplierId;
	/**
	*	移交前所属部门
	**/
	private String deptIdBefore;
	/**
	*	移交前项目经理
	**/
	private String pmIdBefore;
	/**
	*	移交后所属部门
	**/
	private String deptIdAfter;
	/**
	*	移交后项目经理
	**/
	private String pmIdAfter;
	/**
	*	移交后系统名称
	**/
	private String systemId;
	/**
	 *	移交后项目名称
	 **/
	private String projectId;
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
