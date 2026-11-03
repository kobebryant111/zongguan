package com.work.ppm.application.dto.suppliermanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;



 /**
  *  供应商花名册表 传输对象
  */
@Data
@ExcelTarget("PpmSupplierRosterInfoDto")
public class PpmSupplierRosterInfoDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
	private Long id;
	/**
	*	父编号
	**/
	@ApiModelProperty(name = "pid", value = "父编号", dataType = "Long")
	private Long pid;
	/**
	*	用工部门
	**/
	@ApiModelProperty(name = "deptId", value = "用工部门", dataType = "String")
	private String deptId;
	 /**
	  *	供应商编号
	  **/
	 @ApiModelProperty(name = "supplierCode", value = "供应商编号", dataType = "String")
	 private String supplierCode;
	/**
	*	供应商名称
	**/
	@ApiModelProperty(name = "supplierName", value = "供应商名称", dataType = "String")
	@Excel(name = "外包供应商名称", orderNum = "3")
	private String supplierName;

	/**
	*	服务方式
	**/
	@ApiModelProperty(name = "serviceMode", value = "服务方式", dataType = "String")
	@Excel(name = "服务方式（是否驻场）", orderNum = "5",replace = {"驻场_Y","非驻场_N"})
	private String serviceMode;
	/**
	*	是否分包或者转包
	**/
	@ApiModelProperty(name = "isSubcontract", value = "是否分包或者转包", dataType = "String")
	@Excel(name = "是否分包或转包", orderNum = "6",replace = {"是_Y","否_N"})
	private String isSubcontract;
	/**
	*	服务系统名称
	**/
	@ApiModelProperty(name = "systemId", value = "服务系统名称", dataType = "String")
	@JsonInclude
	private String systemId;
	/**
	*	服务类型
	**/
	@ApiModelProperty(name = "serviceType", value = "服务类型", dataType = "String")
	@Excel(name = "服务类型", orderNum = "9")
	private String serviceType;
	/**
	*	服务起始时间
	**/
	@ApiModelProperty(name = "startTime", value = "服务起始时间", dataType = "String")
	@Excel(name = "服务起始时间", orderNum = "10")
	private String startTime;
	/**
	*	服务结束时间
	**/
	@ApiModelProperty(name = "endTime", value = "服务结束时间", dataType = "String")
	@Excel(name = "服务结束时间", orderNum = "11")
	private String endTime;

	/**
	*	尽职调查报告
	**/
	@ApiModelProperty(name = "fileNo", value = "尽职调查报告", dataType = "String")
	@JsonInclude
	private String fileNo;
	/**
	*	是否删除
	**/
	@ApiModelProperty(name = "isDel", value = "是否删除", dataType = "String")
	private String isDel;
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
	  *	用工部门名称
	  **/
	 @Excel(name = "用工部门", orderNum = "2")
	 private String deptName;
	 /**
	  *	系统名称
	  **/
	 @Excel(name = "服务系统名称", orderNum = "7")
	 private String systemName;
	 /**
	  *	删除变量
	  **/
	 private String delVal;
	 /**
	  *	服务类型数组
	  **/
	 private String[] serviceTypeArray;

	 @Excel(name = "序号", orderNum = "1")
	 private String rid;

}
