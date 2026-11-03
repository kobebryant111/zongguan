package com.work.ppm.application.model.flowmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * sla 流程信息
  */
@Data
public class PpmFlowSlainfo {
	/**
	*	字段名
	**/
	@Id
	private String id;
	/**
	*	流程实例id
	**/
	private String flowcaseno;
	/**
	*	流程id
	**/
	private String instanceid;
	/**
	*	流程节点编号
	**/
	private String activityid;
	/**
	*	预计完成时间
	**/
	private Date planfinishtime;
	/**
	*	状态 00
	**/
	private String status;
	/**
	*	删除状态
	**/
	private String isdel;
	/**
	*	流程状态 00：流程在当前节点  01：流程不在当前节点
	**/
	private String flowstatus;
	/**
	*	当前处理人
	**/
	private String usersno;
	/**
	*	当前处理人名
	**/
	private String usersname;
	/**
	*	创建时间
	**/
	private Date createtime;
	/**
	*	语音催办次数
	**/
	private String counts;
	/**
	*	语音催办次数修改时间
	**/
	private Date updatecountdate;
}
