package com.work.ppm.application.model.otherdeptmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;

 /**
  * 研发小组台账 实体对象
  */
@Data
public class PpmDevelopGroupLedger {
	/**
	*	唯一编号
	**/
	@Id
	private String id;
	/**
	*	系统管理
	**/
	private String jobId;
	/**
	*	小组编号
	**/
	private String groupId;
	/**
	*	是否星级人员
	**/
	private String ifStarPerson;
	/**
	*	备注
	**/
	private String remark;
	/**
	*	创建人
	**/
	private String createdBy;
	/**
	*	创建时间
	**/
	private Date createdTime;
	/**
	*	修改人
	**/
	private String modifiedBy;
	/**
	*	修改时间
	**/
	private Date modifiedTime;
}
