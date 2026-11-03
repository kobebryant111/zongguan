package com.work.ppm.application.model.processmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 变更设备名称子表
  */
@Data
public class PpmChagedeviceChildren {
	/**
	*	自增编号
	**/
	@Id
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
