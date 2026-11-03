package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class EosDictType {
	/**
	*	字段名
	**/
	@Id
	private String dicttypeid;
	/**
	*	字段名
	**/
	private String dicttypename;
	/**
	*	字段名
	**/
	private Long rank;
	/**
	*	字段名
	**/
	private String parentid;
	/**
	*	字段名
	**/
	private String seqno;
}
