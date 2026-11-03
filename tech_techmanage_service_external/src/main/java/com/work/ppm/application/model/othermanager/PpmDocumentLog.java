package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmDocumentLog {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String catalogno;
	/**
	*	字段名
	**/
	private String documentno;
	/**
	*	字段名
	**/
	private String dealkind;
	/**
	*	字段名
	**/
	private Date oprdate;
	/**
	*	字段名
	**/
	private String userid;
	/**
	*	字段名
	**/
	private String username;
	/**
	*	字段名
	**/
	private String remark;
}
