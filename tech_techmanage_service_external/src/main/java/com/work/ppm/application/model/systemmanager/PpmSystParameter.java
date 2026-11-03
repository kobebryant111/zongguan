package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmSystParameter {
	/**
	*	??PPM_SYST_PARAMETER_ID ??EOS?????????
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String key;
	/**
	*	字段名
	**/
	private String value;
	/**
	*	字段名
	**/
	private String remark;
	/**
	*	1-????0-???
	**/
	private Long isdel;
	/**
	*	字段名
	**/
	private Date createtime;
	/**
	*	字段名
	**/
	private String creators;
	/**
	*	字段名
	**/
	private Date lastmodtime;
	/**
	*	字段名
	**/
	private String lastmoduser;
}
