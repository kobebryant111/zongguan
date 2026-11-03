package com.work.ppm.application.model.suppliermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 供应商花名册表 实体对象
  */
@Data
public class PpmSupplierRosterInfo {
	/**
	*	唯一编号
	**/
	@Id
	private Long id;
	/**
	*	父编号
	**/
	private Long pid;
	/**
	*	用工部门
	**/
	private String deptId;
	/**
	*	供应商名称
	**/
	private String supplierName;
	 /**
	  * 供应商编号
	  */
	private String supplierCode;
	/**
	*	供应商类型
	**/
	private String supplierType;
	/**
	*	服务方式
	**/
	private String serviceMode;
	/**
	*	是否分包或者转包
	**/
	private String isSubcontract;
	/**
	*	服务系统名称
	**/
	private String systemId;
	/**
	*	服务项目名称
	**/
	private String projectId;
	/**
	*	服务类型
	**/
	private String serviceType;
	/**
	*	服务起始时间
	**/
	private String startTime;
	/**
	*	服务结束时间
	**/
	private String endTime;
	/**
	*	考核结果
	**/
	private String accessResult;
	/**
	*	考核年份
	**/
	private String supplierYear;
	/**
	*	尽职调查报告
	**/
	private String fileNo;
	/**
	*	是否删除
	**/
	private String isDel;
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
