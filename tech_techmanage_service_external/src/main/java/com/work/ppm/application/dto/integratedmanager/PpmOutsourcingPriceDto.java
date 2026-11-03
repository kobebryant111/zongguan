package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;


 /**
  * 描述
  */
@Data
public class PpmOutsourcingPriceDto {
	/**
	*	用户名
	**/
	private String username;
	/**
	*	工号
	**/
	private String jobid;
	/**
	*	时间
	**/
	private String time;
	/**
	*	人员单价
	**/
	private String price;
}
