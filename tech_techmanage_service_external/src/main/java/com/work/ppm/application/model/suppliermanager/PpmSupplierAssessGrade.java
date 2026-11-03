package com.work.ppm.application.model.suppliermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 供应商考核评分表 实体对象
  */
@Data
public class PpmSupplierAssessGrade {
	/**
	*	唯一编号
	**/
	@Id
	private Long id;
	/**
	*	考核类型
	**/
	private String assessType;
	/**
	*	用工部门
	**/
	private String deptId;
	/**
	*	供应商编号
	**/
	private String supplierCode;
	/**
	*	外包方式
	**/
	private String outsourceType;
	/**
	*	项目编号
	**/
	private String projectId;
	/**
	*	考核年份
	**/
	private String assessYear;
	/**
	*	考核结果
	**/
	private Double assessResult;
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
