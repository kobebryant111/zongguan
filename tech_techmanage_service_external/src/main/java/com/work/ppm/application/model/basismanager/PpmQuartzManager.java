package com.work.ppm.application.model.basismanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmQuartzManager {
	/**
	*	编号
	**/
	@Id
	private Long id;
	/**
	*	触发器组名称
	**/
	private String triggerName;
	/**
	*	时间规则表达式
	**/
	private String expresstion;
	/**
	*	任务实现类类名
	**/
	private String className;
	/**
	*	运行状态(1:运行，2：停止，默认：1)
	**/
	private String state;
	/**
	*	添加时间
	**/
	private Date createTime;
	/**
	*	描述
	**/
	private String des;
	/**
	*	添加人
	**/
	private String createName;
	/**
	*	任务名称
	**/
	private String jobName;
	 /**
	  * 系统类别
	  *  1:新应用,2:供应商管理系统
	  */
	private Integer sysType;
}
