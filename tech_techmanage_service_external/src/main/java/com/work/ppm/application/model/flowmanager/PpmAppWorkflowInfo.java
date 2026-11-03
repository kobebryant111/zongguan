package com.work.ppm.application.model.flowmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 对接手机平台信息 实体对象
  */
@Data
public class PpmAppWorkflowInfo {
	/**
	*	唯一编号
	**/
	@Id
	private String id;
	/**
	*	删除待办人工号
	**/
	private String userId;
	/**
	*	任务id
	**/
	private String taskId;
	/**
	*	流程id
	**/
	private String instanceId;
	/**
	*	推送状态
	**/
	private String pushStatus;
 	/**
	 *	推送标题
	 **/
	private String pushTitle;
	/**
	 *	推送详情
	 **/
	private String pushContent;
	/**
	*	创建时间
	**/
	private Date createdTime;
	/**
	*	修改时间
	**/
	private Date modifiedTime;
}
