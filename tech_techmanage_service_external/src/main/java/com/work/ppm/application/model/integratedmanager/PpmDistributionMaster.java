package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 入职部门分配
  */
@Data
public class PpmDistributionMaster {
	/**
	*	自增id
	**/
	@Id
	private Integer id;
	/**
	*	发起人名称
	**/
	private String initiateName;
	/**
	*	发起人工号
	**/
	private String initiateId;
	/**
	*	发起时间
	**/
	private Date addDate;
	/**
	*	是否删除(0:正常，1：删除)
	**/
	private String isdel;
	/**
	*	流程编号
	**/
	private String instanceId;
	 /**
	  * 待办人工号
	  */
	 private String upcomBadge;
}
