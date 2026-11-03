package com.tansun.workflow.model;

import com.fasterxml.jackson.annotation.JsonInclude;
//import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6245008777685141034L;

	public String getProcessDefineKey() {
		return processDefineKey;
	}

	public void setProcessDefineKey(String processDefineKey) {
		this.processDefineKey = processDefineKey;
	}

	/**
	 * 租户id
	 */
//	@ApiModelProperty("租户id")
	private String tenantId;


	private String processDefineKey;
	/**
	 * 流程名称
	 */
//	@ApiModelProperty("流程名称")
	private String processName;

	/**
	 * 流程定义ID
	 */
//	@ApiModelProperty("流程定义ID")
	private String processDefineId;

	/**
	 * 流程实列ID
	 */
//	@ApiModelProperty("流程实列ID")
	private String processInstanceId;

	/**
	 * 执行实列ID
	 */
//	@ApiModelProperty("执行实列ID")
	private String executionId;
	
	/**
	 * 当前任务id
	 */
//	@ApiModelProperty("当前任务id")
	private String currentTaskId;

	/**
	 * 总条数
	 */
	private String total;

	/**
	 * 当前页索引
	 */
	private String currentPageIndex;

	/**
	 * 查询结果集合
	 */
	private List<Map<String, Object>> resultList;

	/**
	 * 查询结果对象
	 */
	private Object resultObject;

	/**
	 * 需要在前段展示树结构数据
	 */
	private String name;
	
	/**
     * 扩展数据
     */
//	@ApiModelProperty("扩展数据")
	private Map<String, String> extendedData;

	/**
	 * 当前节点id
	 */
	private String currentActivityId;

	/**
	 * 分类编码
	 */
	private String classfyCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getExtendedData() {
		return extendedData;
	}

	public void setExtendedData(Map<String, String> extendedData) {
		this.extendedData = extendedData;
	}

	public ProcessBean() {
		super();
	}

	public ProcessBean(String retCode, String retMsg) {
		super(retCode, retMsg);
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessDefineId() {
		return processDefineId;
	}

	public void setProcessDefineId(String processDefineId) {
		this.processDefineId = processDefineId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(String currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

	public String getCurrentTaskId() {
		return currentTaskId;
	}

	public void setCurrentTaskId(String currentTaskId) {
		this.currentTaskId = currentTaskId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

    public void setCurrentActivityId(String currentActivityId) {
        this.currentActivityId = currentActivityId;
    }

	public String getCurrentActivityId() {
		return currentActivityId;
	}

	public String getClassfyCode() {
		return classfyCode;
	}

	public void setClassfyCode(String classfyCode) {
		this.classfyCode = classfyCode;
	}
}
