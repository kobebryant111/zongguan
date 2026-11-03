package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 考勤日历任务表 实体对象
  */
@Data
public class PpmKqCalendarTodo {
	/**
	*	主键
	**/
	@Id
	private Long id;
	/**
	*	流程实例ID
	**/
	private String instanceid;
	/**
	*	标题
	**/
	private String title;
	/**
	*	流程ID
	**/
	private String flowid;
	/**
	*	备注
	**/
	private String remark;
	/**
	*	状态（0未处理，1已处理）
	**/
	private Long flag;
	/**
	*	创建人
	**/
	private String createid;
	/**
	*	创建时间
	**/
	private Date createdate;
	/**
	*	完成时间
	**/
	private Date completedate;
}
