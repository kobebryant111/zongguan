package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmTecShenpiConfig {
	/**
	*	ID
	**/
	@Id
	private Long id;
	/**
	*	部门编号
	**/
	private String deptno;
	/**
	*	员工号
	**/
	private String userid;
}
