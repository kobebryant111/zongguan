package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class EosDictEntry {
	/**
	*	字段名
	**/
	@Id
	private String dicttypeid;
	/**
	*	字段名
	**/
	@Id
	private String dictid;
	/**
	*	字段名
	**/
	private String dictname;
	/**
	*	字段名
	**/
	private Long status;
	/**
	*	字段名
	**/
	private Long sortno;
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
	/**
	*	字段名
	**/
	private String filter1;
	/**
	*	字段名
	**/
	private String filter2;
}
