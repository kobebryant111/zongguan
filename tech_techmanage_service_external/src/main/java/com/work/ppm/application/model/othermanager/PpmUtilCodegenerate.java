package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmUtilCodegenerate {
	/**
	*	字段名
	**/
	@Id
	private String code;
	/**
	*	字段名
	**/
	private Long value;
}
