package com.work.ppm.application.model.usermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmOutsourcingRiskTime {
	/**
	*	工号
	**/
	@Id
	private String jobid;
	/**
	*	专项排查时间
	**/
	private Date specialinvestigationdate;
	/**
	*	风险排查时间
	**/
	private Date riskdate;
	/**
	*	录入修改人工号
	**/
	private String regid;
	/**
	*	录入修改时间
	**/
	private Date regtime;
	/**
	*	备注
	**/
	private String remark;
	/**
	*	日常排查
	**/
	private Date dailyriskdate;
	/**
	*	入场时间
	**/
	private Date joindate;
}
