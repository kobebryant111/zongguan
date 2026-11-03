package com.work.ppm.application.dto.processmanager;

import lombok.Data;


 /**
  * 变更设备名称主表
  */
@Data
public class PpmChagedeviceMasterDto {
	/**
	*	自增id
	**/
	private Long id;
	/**
	*	设备名称
	**/
	private String devices;
}
