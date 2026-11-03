package com.work.ppm.application.model.usermanager;

import lombok.Data;


 /**
  * 描述
  */
@Data
public class PpmRlwbsshtInfo {
	/**
	*	合同id
	**/
	private String htid;
	/**
	*	工号
	**/
	private String jobid;
	/**
	*	开始时间
	**/
	private String starttime;
	/**
	*	结束时间
	**/
	private String endtime;
}
