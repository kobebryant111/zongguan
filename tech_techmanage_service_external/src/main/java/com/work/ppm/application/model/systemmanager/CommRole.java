package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class CommRole {
	/**
	*	角色ID
	**/
	@Id
	private String roleid;
	/**
	*	角色名称
	**/
	private String rolename;
	/**
	*	角色类型
	**/
	private String roletype;
	/**
	*	角色描述
	**/
	private String roledesc;
	/**
	*	有效标志
	**/
	private String isdisabled;
	/**
	*	ID
	**/
	private Long selfid;
	/**
	*	父ID
	**/
	private String parentid;
}
