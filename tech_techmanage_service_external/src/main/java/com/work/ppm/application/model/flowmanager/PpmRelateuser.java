package com.work.ppm.application.model.flowmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 外包人员账号关联
  */
@Data
public class PpmRelateuser {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String userno;
	/**
	*	字段名
	**/
	private String relateUserno;
}
