package com.work.ppm.application.dto.flowmanager;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;



 /**
  *  对接手机平台信息 传输对象
  */
@Data
public class PpmAppWorkflowInfoDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "String")
	private String id;
	/**
	*	删除待办人工号
	**/
	@ApiModelProperty(name = "userId", value = "删除待办人工号", dataType = "String")
	private String userId;
	/**
	*	任务id
	**/
	@ApiModelProperty(name = "taskId", value = "任务id", dataType = "String")
	private String taskId;
	/**
	*	流程id
	**/
	@ApiModelProperty(name = "instanceId", value = "流程id", dataType = "String")
	private String instanceId;
	/**
	*	推送状态
	**/
	@ApiModelProperty(name = "pushStatus", value = "推送状态", dataType = "String")
	private String pushStatus;
	 /**
	  *	推送标题
	  **/
	 @ApiModelProperty(name = "pushTitle", value = "推送标题", dataType = "String")
	 private String pushTitle;
	 /**
	  *	推送内容
	  **/
	 @ApiModelProperty(name = "pushContent", value = "推送内容", dataType = "String")
	 private String pushContent;
	/**
	*	创建时间
	**/
	@ApiModelProperty(name = "createdTime", value = "创建时间", dataType = "Date")
	private Date createdTime;
	/**
	*	修改时间
	**/
	@ApiModelProperty(name = "modifiedTime", value = "修改时间", dataType = "Date")
	private Date modifiedTime;
}
