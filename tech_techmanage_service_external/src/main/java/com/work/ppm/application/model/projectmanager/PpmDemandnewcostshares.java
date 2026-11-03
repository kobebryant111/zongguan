package com.work.ppm.application.model.projectmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 分摊
  */
@Data
public class PpmDemandnewcostshares {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	需求编号
	**/
	private String demandno;
	/**
	*	部门编号
	**/
	private String deptno;
	/**
	*	分摊比例
	**/
	private Integer proportion;
	/**
	*	机构 org,部门 dep,空的情况默认为部门
	**/
	private String orgdep;
	/**
	*	分摊确认人
	**/
	private String manageruser;
}
