package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import lombok.Data;
import java.util.Date;



 /**
  * 入职部门分配
  */
@Data
public class PpmDistributionMasterDto {
	/**
	*	自增id
	**/
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
	   bps
	  */
	private BpsWorkFlowDto bps;
	 /**
	  * 简历id 多个用 “，” 拼接
	  */
	 private String resumeIds;
	 /**
	  * 待办人工号
	  */
	 private String upcomBadge;
}
