package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class CommOperatorrole {
	/**
	*	操作员ID
	**/
	@Id
	private Integer operatorid;
	/**
	*	角色ID
	**/
	@Id
	private String roleid;
	/**
	*	备注
	**/
	private String remark;
	/**
	*	是否兼职
	**/
	private String islose;
	/**
	*	兼职id
	**/
	private Long partid;
}
