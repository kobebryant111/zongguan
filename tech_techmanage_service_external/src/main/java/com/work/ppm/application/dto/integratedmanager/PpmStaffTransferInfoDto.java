package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;



 /**
  *  人员移交详情信息 传输对象
  */
@Data
public class PpmStaffTransferInfoDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
	private Long id;
	/**
	*	流程编号
	**/
	@ApiModelProperty(name = "instanceid", value = "流程编号", dataType = "String")
	private String instanceid;
	/**
	*	工号
	**/
	@ApiModelProperty(name = "jobId", value = "工号", dataType = "String")
	private String jobId;
	/**
	 *	姓名
	 **/
	private String userName;
	 /**
	  *	外包方式
	  **/
	 @ApiModelProperty(name = "outsourceType", value = "外包方式", dataType = "String")
	 private String outsourceType;
	/**
	*	移交时间
	**/
	@ApiModelProperty(name = "transferTime", value = "移交时间", dataType = "Date")
	private Date transferTime;
	/**
	*	移交状态
	**/
	@ApiModelProperty(name = "transferState", value = "移交状态", dataType = "String")
	private String transferState;
	/**
	*	供应商编号
	**/
	@ApiModelProperty(name = "supplierId", value = "供应商编号", dataType = "String")
	private String supplierId;
	/**
	*	移交前所属部门
	**/
	@ApiModelProperty(name = "deptIdBefore", value = "移交前所属部门", dataType = "String")
	private String deptIdBefore;
	/**
	*	移交前项目经理
	**/
	@ApiModelProperty(name = "pmIdBefore", value = "移交前项目经理", dataType = "String")
	private String pmIdBefore;
	/**
	*	移交后所属部门
	**/
	@ApiModelProperty(name = "deptIdAfter", value = "移交后所属部门", dataType = "String")
	private String deptIdAfter;
	/**
	*	移交后项目经理
	**/
	@ApiModelProperty(name = "pmIdAfter", value = "移交后项目经理", dataType = "String")
	private String pmIdAfter;
	/**
	*	移交后系统名称
	**/
	@ApiModelProperty(name = "systemId", value = "移交后系统名称", dataType = "String")
	private String systemId;
	 /**
	  *	移交后项目名称
	  **/
	 @ApiModelProperty(name = "projectId", value = "移交后项目名称", dataType = "String")
	 private String projectId;
	/**
	*	创建时间
	**/
	@ApiModelProperty(name = "createdTime", value = "创建时间", dataType = "Date")
	private Date createdTime;
	/**
	*	创建人
	**/
	@ApiModelProperty(name = "createdBy", value = "创建人", dataType = "String")
	private String createdBy;
	/**
	*	修改时间
	**/
	@ApiModelProperty(name = "modifiedTime", value = "修改时间", dataType = "Date")
	private Date modifiedTime;
	/**
	*	修改人
	**/
	@ApiModelProperty(name = "modifiedBy", value = "修改人", dataType = "String")
	private String modifiedBy;

	/**
	 * 供应商名称
	 */
	private String supplierName;
	/**
	 *移交前所属部门名称
	 */
	private String deptIdBeforeName;
	 /**
	  *移交前项目经理名称
	  */
	private String pmIdBeforeName;
	 /**
	  *移交后所属部门名称
	  */
	private String deptIdAfterName;
	 /**
	  *移交后项目经理名称
	  */
	private String pmIdAfterName;
	 /**
	  *移交后系统名称
	  */
	private String systemName;
	 /**
	  *移交后项目名称
	  */
	 private String projectName;
	 /**
	  *移交状态名称
	  */
	private String transferStateName;
	 /**
	  *申请人编号
	  */
	private String applyerId;
	 /**
	  *申请人名称
	  */
	private String applyerName;
	 /**
	  *申请人部门
	  */
	private String applyerDeptId;
	 /**
	  *申请人部门名称
	  */
	private String applyerDeptName;
	 /**
	  * 流程状态
	  */
	 private String state;
	 /**
	  * 流程状态名称
	  */
	 private String stateName;
	 /**
	  * 外包方式名称
	  */
	 private String outsourceTypeName;
	 /**
	  * 删除参数
	  */
	 private String delVal;
}
