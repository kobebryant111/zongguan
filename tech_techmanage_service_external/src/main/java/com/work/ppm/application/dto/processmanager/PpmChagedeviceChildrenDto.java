package com.work.ppm.application.dto.processmanager;

import lombok.Data;


 /**
  * 变更设备名称子表
  */
@Data
public class PpmChagedeviceChildrenDto {
	/**
	*	自增编号
	**/
	private Long id;
	/**
	*	主表id
	**/
	private Long mid;
	/**
	*	选项名称
	**/
	private String itemName;
	/**
	*	选项类型
	**/
	private Integer itemType;
	/**
	*	是否必填（0：是，1：否）
	**/
	private Integer notifications;
	 /**
	   字段名称
	  */
	private String fields;
	 /**
	  排序
	  */
	private Integer soft;
}
