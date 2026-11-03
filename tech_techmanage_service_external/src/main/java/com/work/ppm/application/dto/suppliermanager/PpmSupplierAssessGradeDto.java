package com.work.ppm.application.dto.suppliermanager;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;



 /**
  *  供应商考核评分表 传输对象
  */
@Data
public class PpmSupplierAssessGradeDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
	private Long id;
	/**
	*	考核类型
	**/
	@ApiModelProperty(name = "assessType", value = "考核类型", dataType = "String")
	private String assessType;
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
	*	外包方式
	**/
	@ApiModelProperty(name = "outsourceType", value = "外包方式", dataType = "String")
	private String outsourceType;
	/**
	*	项目编号
	**/
	@ApiModelProperty(name = "projectId", value = "项目编号", dataType = "String")
	private String projectId;
	/**
	*	考核年份
	**/
	@ApiModelProperty(name = "assessYear", value = "考核年份", dataType = "String")
	private String assessYear;
	/**
	*	考核结果
	**/
	@ApiModelProperty(name = "assessResult", value = "考核结果", dataType = "Double")
	private Double assessResult;
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
}
