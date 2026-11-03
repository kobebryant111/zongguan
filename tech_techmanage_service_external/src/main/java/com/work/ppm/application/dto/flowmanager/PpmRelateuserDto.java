package com.work.ppm.application.dto.flowmanager;

import lombok.Data;


 /**
  * 外包人员账号关联
  */
@Data
public class PpmRelateuserDto {
	/**
	*	字段名
	**/
	private Long id;
	/**
	*	字段名
	**/
	private String userno;
	/**
	*	字段名
	**/
	private String relateUserno;
	 /**
	  * 工号
	  */
	 private String userName;
	 /**
	  * 工号
	  */
	 private String realteUserName;
}
