package com.work.ppm.application.dto.basismanager;

import lombok.Data;



 /**
  * 描述
  */
@Data
public class PpmMissionInfoDto {
	/**
	*	任务ID
	**/
	private String missionId;
	/**
	*	任务名称
	**/
	private String missionName;
	/**
	*	任务对应项目,流程ID
	**/
	private String missionRefId;
	/**
	*	任务描述
	**/
	private String missionDesc;
	/**
	*	计划完成时间
	**/
	private String planFinishDate;
	/**
	*	项目类型  01项目  02流程  03非项目 04自动分配
	**/
	private String sourceType;
	/**
	*	创建时间
	**/
	private String createDate;
	/**
	*	创建人
	**/
	private String createUser;
	/**
	*	最新更新时间
	**/
	private String updateDate;
	/**
	*	更新人
	**/
	private String updateUser;
	/**
	*	任务指派人
	**/
	private String assignUser;
	/**
	*	流程任务状态
	**/
	private String isfalse;
	/**
	*	0未删除 1删除
	**/
	private String isDel;
	/**
	*	删除时间
	**/
	private String abandonDate;
	/**
	*	字段名
	**/
	private String tasklevel;
	/**
	*	字段名
	**/
	private String taskysstatus;
	/**
	*	字段名
	**/
	private String finishtatus;
	/**
	*	模板ID
	**/
	private String modelid;
	/**
	*	治理类型
	**/
	private String jgzltype;
	/**
	*	模板名字
	**/
	private String modeltext;
	/**
	*	发送邮件类型 01 不发送  02发邮件给执行人 03发邮件给执行人并抄送部门负责人
	**/
	private String sendEmailType;
	/**
	*	任务开始时间
	**/
	private String planStateDate;
	/**
	*	cmdb系统modelid
	**/
	private String systemmodelid;
	/**
	*	关联任务编号
	**/
	private String missionconid;
	/**
	*	关联任务名称
	**/
	private String missionconname;
	/**
	*	任务督办人
	**/
	private String superviseruser;
	/**
	*	任务主办人
	**/
	private String directuser;
	/**
	*	任务通知人
	**/
	private String noticeuser;
	/**
	*	任务咨询人
	**/
	private String consultuser;
	/**
	*	任务状态: 00进行中  01验收中 02验收通过 03验收不通过
	**/
	private String status;
	/**
	*	任务通知人名称
	**/
	private String noticeusername;
	/**
	*	任务咨询人名称
	**/
	private String consultusername;
	/**
	*	任务模式
	**/
	private String missiontype;
	/**
	*	任务重要程度
	**/
	private String missionimportance;
	/**
	*	任务来源
	**/
	private String tasksource;
	/**
	*	字段名
	**/
	private String quality;
	/**
	*	督办验收时间
	**/
	private String dbfinishDate;
	/**
	*	主办完成时间
	**/
	private String zbfinishDate;
	/**
	*	任务完成进度
	**/
	private String process;
	/**
	*	字段名
	**/
	private String templateId;
}
