package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 入职部门分配数据权限
  */
@Data
public class PpmOnboardDataauth {
	/**
	*	自增编号
	**/
	@Id
	private Integer id;
	/**
	*	中心id
	**/
	private Integer centerId;
	/**
	*	工号
	**/
	private String userId;
	/**
	*	名称
	**/
	private String userName;
}
