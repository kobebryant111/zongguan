package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;



 /**
  *  考勤日历任务表 传输对象
  */
@Data
public class PpmKqCalendarTodoDto {
	/**
	*	主键
	**/
	@ApiModelProperty(name = "id", value = "主键", dataType = "Long")
	private Long id;
	/**
	*	流程实例ID
	**/
	@ApiModelProperty(name = "instanceid", value = "流程实例ID", dataType = "String")
	private String instanceid;
	/**
	*	标题
	**/
	@ApiModelProperty(name = "title", value = "标题", dataType = "String")
	private String title;
	/**
	*	流程ID
	**/
	@ApiModelProperty(name = "flowid", value = "流程ID", dataType = "String")
	private String flowid;
	/**
	*	备注
	**/
	@ApiModelProperty(name = "remark", value = "备注", dataType = "String")
	private String remark;
	/**
	*	状态（0未处理，1已处理）
	**/
	@ApiModelProperty(name = "flag", value = "状态（0未处理，1已处理）", dataType = "Long")
	private Long flag;
	/**
	*	创建人
	**/
	@ApiModelProperty(name = "createid", value = "创建人", dataType = "String")
	private String createid;
	/**
	*	创建时间
	**/
	@ApiModelProperty(name = "createdate", value = "创建时间", dataType = "Date")
	private Date createdate;
	/**
	*	完成时间
	**/
	@ApiModelProperty(name = "completedate", value = "完成时间", dataType = "Date")
	private Date completedate;
	 /**
	  * bps
	  */
	 private BpsWorkFlowDto bps;
}
