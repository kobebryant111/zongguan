package com.tansun.wf.activiti.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.impl.db.DbEntity;

/**
 * 定时配置信息
 */
public class TimerConfigEntity implements Serializable,DbEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6271185226754460050L;
	private String id;//定时配置信息ID
	private String procDefId;//流程定义ID
	private String taskDefKey;//任务节点ID
	private String procInstId;//流程实例ID
	private String executionId;//执行实例ID
	private String cronExpression;//cron时间表达式
	private Date beginTime;//提醒开始时间
	private Date endTime;//提醒借宿时间
	private Long cfrequency;//提醒频率，单位秒
	private String sendType;//提醒方式1邮件
	private Date createTime;//创建时间
	private String note;//定时任务备注
	private String status;//状态0正常1失效
	private String tenantId;//租户ID
	private String timeType;//时间类型：工作日、自然日
	private Long remindNum;//提醒次数，单位次
	private String template;//消息模板
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getCfrequency() {
		return cfrequency;
	}
	public void setCfrequency(Long cfrequency) {
		this.cfrequency = cfrequency;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public Long getRemindNum() {
		return remindNum;
	}
	public void setRemindNum(Long remindNum) {
		this.remindNum = remindNum;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	
	@Override
	public Object getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
        return persistentState;
	}
	
}
