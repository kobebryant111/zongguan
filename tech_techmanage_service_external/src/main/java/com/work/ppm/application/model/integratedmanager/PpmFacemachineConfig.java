package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmFacemachineConfig {
	/**
	*	id
	**/
	@Id
	private String id;
	/**
	*	考勤机名称
	**/
	private String name;
	/**
	*	考勤机位置
	**/
	private String location;
	/**
	*	考勤机ip
	**/
	private String deviceip;
	/**
	*	考勤机端口
	**/
	private String deviceport;
	/**
	*	iuser编号
	**/
	private String iuserid;
	/**
	*	设备编号
	**/
	private String deviceid;
	/**
	*	1:使用 0:删除
	**/
	private String status;
	/**
	*	0:否,1:是
	**/
	private String isuse;
}
