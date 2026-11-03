package com.tansun.wf.activiti.model;

import org.camunda.bpm.engine.impl.db.DbEntity;

import java.io.Serializable;

/**
 * 待办扩展分类
 */
public class TaskExpandPlanEntity implements Serializable ,DbEntity {

	private String id;
	private String wfClassId;		// 流程分类ID
	private String wfClassName;		// 流程分类名称
	private String fieldKey;		// 拓展字段键值
	private String fieldName;		// 拓展字段名称
	private String fieldValue;		// 拓展字段数值
	private String fieldType;			// 类型（字典、字符串等）
	private String fieldUrl;				// 链接
	private String fieldStatus;			// 是否隐藏
	private String tenantId;
	private String isQueryCondition;	// 是否作为查询条件
	
	
	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldUrl() {
		return fieldUrl;
	}

	public void setFieldUrl(String fieldUrl) {
		this.fieldUrl = fieldUrl;
	}

	public String getFieldStatus() {
		return fieldStatus;
	}

	public void setFieldStatus(String fieldStatus) {
		this.fieldStatus = fieldStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns a representation of the object,
	 * as would be stored in the database.
	 * Used when deciding if updates have
	 * occurred to the object or not since
	 * it was last loaded.
	 */
	@Override
	public Object getPersistentState() {
		//TODO STATE
		return null;
	}

	public String getWfClassId() {
		return wfClassId;
	}

	public void setWfClassId(String wfClassId) {
		this.wfClassId = wfClassId;
	}

	public String getWfClassName() {
		return wfClassName;
	}

	public void setWfClassName(String wfClassName) {
		this.wfClassName = wfClassName;
	}

	public String getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getIsQueryCondition() {
		return isQueryCondition;
	}

	public void setIsQueryCondition(String isQueryCondition) {
		this.isQueryCondition = isQueryCondition;
	}
}
