package com.work.ppm.application.model.otherdeptmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 监保部台账门禁卡流程详情 实体对象
  */
@Data
public class PpmAccessCardApplyInfo {
	/**
	*	唯一编号
	**/
	@Id
	private String id;
	/**
	*	流程编号
	**/
	private String instanceId;
	/**
	 *	业务id bizObjectId
	 **/
	private String bizObjectId;
	/**
	*	工号
	**/
	private String jobId;
	/**
	*	部门编号
	**/
	private String deptId;
	/**
	*	人员编号
	**/
	private String virtualMachineId;
	/**
	*	门禁卡编号
	**/
	private String accessCardNumber;
	/**
	*	是否已领用
	**/
	private String ifReceiveCard;
	/**
	*	领用日期
	**/
	private Date receiveTime;
	/**
	*	是否已归还
	**/
	private String ifReturnCard;
	/**
	*	归还日期
	**/
	private Date returnTime;
	/**
	*	备注
	**/
	private String remark;
	/**
	 *	是否删除 0否 1是
	 **/
	private String isDel;
	/**
	*	创建时间
	**/
	private Date createdTime;
	/**
	*	创建人
	**/
	private String createdBy;
	/**
	*	修改时间
	**/
	private Date modifiedTime;
	/**
	*	修改人
	**/
	private String modifiedBy;
}
