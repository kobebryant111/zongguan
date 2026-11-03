package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class EosUniqueTable {
	/**
	*	字段名
	**/
	@Id
	private String name;
	/**
	*	字段名
	**/
	private Long code;
}
