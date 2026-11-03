package com.work.ppm.application.model.processmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 变更设备名称主表
  */
@Data
public class PpmChagedeviceMaster {
	/**
	*	自增id
	**/
	@Id
	private Long id;
	/**
	*	设备名称
	**/
	private String devices;
}
