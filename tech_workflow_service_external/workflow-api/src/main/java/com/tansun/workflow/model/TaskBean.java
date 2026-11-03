package com.tansun.workflow.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskBean extends BaseBean {
	/**
	* 序列id
	*/
	private static final long serialVersionUID = 6518018763776138229L;
	/**
	 * 租户id
	 */
	private String tenantId;
//	@ApiModelProperty("任务实例ID")
	/**
	 * 任务ID
	 */
	private String taskId;
//	@ApiModelProperty("流程实列ID")
	/**
	 * 流程实列ID
	 */
	private String processInstanceId;
//	@ApiModelProperty("执行实列ID")
	/**
	 * 执行实列ID
	 */
	private String executionId;
//	@ApiModelProperty(value = "下一审批人",example = "数组会作为“nextUser”流程变量的的内容")
	/**
	 * 下一审批人，数组会作为“nextUser”流程变量的的内容
	 */
	private List<String> nextParticipates;
	//下一岗位
	/**
	 * 下一岗位
	 */
	private List<PostInfo> nextPost;
	/**
	 * 下一任务节点id
	 */
	private String nextTaskId;
//	@ApiModelProperty("退回节点定义的Id")
	/**
	 * 下一个节点定义的Id
	 */
	private String nextActivityId;
	// 退回到哪些节点，普通串行只有一个，并行网关可能有多个

	/**
	 * 下一节点集合
	 */
	private List<String> nextActivityIds;

	/**
	 * 取消的活动id，用于退回接口
	 */
	private List<String> cancelActivityIds;
	/**
	 * 活动id
	 */
	private String activityId;
	/**
	 * 下一任务名
	 */
	private String nextTaskName;
	/**
	 * 总条数
	 */
	private String total;
	/**
	 * 当前页索引
	 */
	private String currentPageIndex;
	/**
	 * 流程参数
	 */
//	@ApiModelProperty("流程参数")
	private Map mPara;
	/**
	 * 待办扩展数据
	 */
//	@ApiModelProperty("待办扩展数据")
	private Map <String,String> taskExpandData;
	/**
	 * 任务明细
	 */
//	@ApiModelProperty("任务明细")
	private List<Map> taskList;
	/**
	 * 任务定义集合
	 */
	private List<Map> taskDefinitionList;
	/**
	 * 机构id
	 */
//	@ApiModelProperty("机构id")
	private String orgId;
	/**
	 * 用户id
	 */
//	@ApiModelProperty("用户id")
	private String userId;
	/**
	 * 是否完成
	 */
	private String isFinished;
//	@ApiModelProperty("批注")
	/**
	 * 批注
	 */
	private String comment;
//	@ApiModelProperty("流程是否结束，已结束：Y，未结束：N")
	/**
	 * 流程是否结束，已结束：Y，未结束：N
	 */
	private String isEnd;
	/**
	 * 流程分类编号
	 */
	private String wfClassifyCode;
	/**
	 * 流程分类名称
	 */
	private String wfClassifyName;
	/**
	 * 列，集合
	 */
	private List<Map> columns;
	/**
	 * 参数
	 */
	private String field;
	/**
	 * 任务实体类集合
	 */
	private List taskBeanList;
	/**
	 * 按钮
	 */
	private String actButton;
	/**
	 * 按钮类名
	 */
	private String actClassName;
	/**
	 * 流程开始类名
	 */
	private String processStartedClassName;
	/**
	 * 流程完成类名
	 */
	private String processCompletedClassName;
	/**
	 * 用户任务处理
	 */
	private String userTaskAssignment;
	/**
	 * 起始用户id
	 */
	private String startUserId;
	/**
	 * 起始用户名字
	 */
	private String startUserName;
	/**
	 * 机构名
	 */
	private String orgName;
	/**
	 * 本地变量
	 */
	private Map<String, Object> localVariables;
	/**
	 * 下一机构id
	 */
	private String nextOrgId;
	/**
	 * 表单数据
	 */
	private Map<String, Object> formData;
	/**
	 * 加签岗位
	 */
	private String addTaskPosition;
	/**
	 * 会签意见，赞成：agree 、反对：against、弃权：noOpinion
	 */
//	@ApiModelProperty(value = "会签意见",example = "赞成：agree 、反对：against、弃权：noOpinion")
	private String votingOpinion;
    /**
     * 抄送用户
     */
	private List<String> ccUsers;

	/**
	 * 加签用户
	 */
	private List<String> addTaskUsers;

    /**
     * 抄送角色
     */
    private List<String> ccRoles;

	/**
	 * 责任集合
	 */
	private List<Map<String, Object>> creditList;
	/**
	 * 流程名称
	 */
	private List<Map<String, Object>> creditHiTaskList;
	/**
	 * 流程变量map
	 */
	private Map<String, Object> processVariableMap;
	/**
	 * 任务池执行数据列表
	 */
//	@ApiModelProperty("任务池执行数据列表")
	private List<HiTaskPoolExecuteData> hiTaskPoolExecuteDataList;
	/**
	 * 实体对象
	 */
	private List<ButtonModel> actButtonModel;//实体对象
	/**
	 * 上一节点审批人同机构编码code
	 */
//	@ApiModelProperty("上一节点审批人同机构编码code")
	private  String beforeNodeOrgId;
	/**
	 * 上一节点审批人上级机构同机构编码code
	 */
//	@ApiModelProperty("上一节点审批人上级机构同机构编码code")
	private String beforeNodeParentOrgId;
	/**
	 * 与上一个节点审批人下级机构同机构
	 */
//	@ApiModelProperty("与上一个节点审批人下级机构同机构")
	private List<String> beforeNodeChildOrgId;
	/**
	 * 与上一个节点审批人同部门
	 */
//	@ApiModelProperty("与上一个节点审批人同部门")
	private String beforeNodeDeptId;
	/**
	 * 与上一个节点审批人上级部门同部门
	 */
//	@ApiModelProperty("与上一个节点审批人上级部门同部门")
	private String beforeNodeParentDeptId;
	/**
	 * 与上一个节点审批人下级部门同部门
	 */
//	@ApiModelProperty("与上一个节点审批人下级部门同部门")
	private List<String> beforeNodeChildDeptId;
	/**
	 * 加机构编号
	 */
	private List<String> addDeptCodes;
	/**
	 * 加角色编号
	 */
	private List<String> addRoleCodes;
	/**
	 * 删除任务id集合
	 */
	private List<String> deleteTaskIds;
	/**
	 * 是否可退回自身节点
	 */
//	@ApiModelProperty("是否可退回自身节点")
	private String isBackOwn;
	/**
	 * 是否可连续退回
	 */
//	@ApiModelProperty("是否可连续退回")
	private String isReBack;
	/**
	 * 子流程启动流程参数
	 */
//	@ApiModelProperty("子流程启动流程参数")
	private List<ProcessInfo> subProcessInfos;
	/**
	 * 编辑变量
	 */
	private Map<String,Object> editorVariables;
	/**
	 * 按钮模型
	 */
	public List<ButtonModel> getActButtonModel() {
		return actButtonModel;
	}

	public void setActButtonModel(List<ButtonModel> actButtonModel) {
		this.actButtonModel = actButtonModel;
	}

    /**
     * 规则入参
     */
    private Map<String, Object> ruleParams;
    
    /**
     * 并行多节点传参
     */
    private Map<String, Object> nextUsersMap;
    
    /**
     * users
     */
    private List<String> users;
	/**
	 * 流程名称
	 */
	private String leaveTransferId;
	/**
	 * 是否复制拓展数据
	 */
	private String isCopyHiTaskExpandData;



	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public Map<String, Object> getNextUsersMap() {
		return nextUsersMap;
	}

	public void setNextUsersMap(Map<String, Object> nextUsersMap) {
		this.nextUsersMap = nextUsersMap;
	}

	public Map<String, Object> getFormData() {
		return formData;
	}

	public void setFormData(Map<String, Object> formData) {
		this.formData = formData;
	}

	public String getUserTaskAssignment() {
		return userTaskAssignment;
	}

	public void setUserTaskAssignment(String userTaskAssignment) {
		this.userTaskAssignment = userTaskAssignment;
	}

	public List<Map<String, Object>> getCreditHiTaskList() {
		return creditHiTaskList;
	}

	public void setCreditHiTaskList(List<Map<String, Object>> creditHiTaskList) {
		this.creditHiTaskList = creditHiTaskList;
	}

	public String getActClassName() {
		return actClassName;
	}

	public void setActClassName(String actClassName) {
		this.actClassName = actClassName;
	}

	public String getProcessStartedClassName() {
		return processStartedClassName;
	}

	public void setProcessStartedClassName(String processStartedClassName) {
		this.processStartedClassName = processStartedClassName;
	}

	public String getProcessCompletedClassName() {
		return processCompletedClassName;
	}

	public void setProcessCompletedClassName(String processCompletedClassName) {
		this.processCompletedClassName = processCompletedClassName;
	}
	
	public String getActButton() {
		return actButton;
	}

	public void setActButton(String actButton) {
		this.actButton = actButton;
	}
	
	public List<Map<String, Object>> getCreditList() {
		return creditList;
	}

	public void setCreditList(List<Map<String, Object>> creditList) {
		this.creditList = creditList;
	}

	public List getTaskBeanList() {
		return taskBeanList;
	}

	public void setTaskBeanList(List taskBeanList) {
		this.taskBeanList = taskBeanList;
	}

	public String getWfClassifyCode() {
		return wfClassifyCode;
	}

	public void setWfClassifyCode(String wfClassifyCode) {
		this.wfClassifyCode = wfClassifyCode;
	}

	public String getWfClassifyName() {
		return wfClassifyName;
	}

	public void setWfClassifyName(String wfClassifyName) {
		this.wfClassifyName = wfClassifyName;
	}

	public List<Map> getColumns() {
		return columns;
	}

	public void setColumns(List<Map> columns) {
		this.columns = columns;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(String isFinished) {
		this.isFinished = isFinished;
	}

	public TaskBean() {
		super();
	}

	public TaskBean(String retCode, String retMsg) {
		super(retCode, retMsg);
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<String> getNextParticipates() {
		return nextParticipates;
	}

	public void setNextParticipates(List<String> nextParticipates) {
		this.nextParticipates = nextParticipates;
	}

	public String getNextTaskId() {
		return nextTaskId;
	}

	public void setNextTaskId(String nextTaskId) {
		this.nextTaskId = nextTaskId;
	}

	public String getNextActivityId() {
		return nextActivityId;
	}

	public void setNextActivityId(String nextActivityId) {
		this.nextActivityId = nextActivityId;
	}

	public String getNextTaskName() {
		return nextTaskName;
	}

	public void setNextTaskName(String nextTaskName) {
		this.nextTaskName = nextTaskName;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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

	public Map getmPara() {
		return mPara;
	}

	public void setmPara(Map mPara) {
		this.mPara = mPara;
	}

	public List<Map> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Map> taskList) {
		this.taskList = taskList;
	}

	public List<Map> getTaskDefinitionList() {
		return taskDefinitionList;
	}

	public void setTaskDefinitionList(List<Map> taskDefinitionList) {
		this.taskDefinitionList = taskDefinitionList;
	}

	public Map<String, String> getTaskExpandData() {
		return taskExpandData;
	}

	public void setTaskExpandData(Map<String, String> taskExpandData) {
		this.taskExpandData = taskExpandData;
	}

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getNextOrgId() {
        return nextOrgId;
    }

    public void setNextOrgId(String nextOrgId) {
        this.nextOrgId = nextOrgId;
    }


    public Map<String, Object> getProcessVariableMap() {
        return processVariableMap;
    }

    public void setProcessVariableMap(Map<String, Object> processVariableMap) {
        this.processVariableMap = processVariableMap;
    }

    public Map<String, Object> getRuleParams() {
        return ruleParams;
    }

    public void setRuleParams(Map<String, Object> ruleParams) {
        this.ruleParams = ruleParams;
    }

    public Map<String, Object> getLocalVariables() {
        return localVariables;
    }

    public void setLocalVariables(Map<String, Object> localVariables) {
        this.localVariables = localVariables;
    }
    public List<String> getCcUsers() {
        return ccUsers;
    }

    public void setCcUsers(List<String> ccUsers) {
        this.ccUsers = ccUsers;
    }

    public List<String> getCcRoles() {
        return ccRoles;
    }

    public void setCcRoles(List<String> ccRoles) {
        this.ccRoles = ccRoles;
    }
	
	public List<HiTaskPoolExecuteData> getHiTaskPoolExecuteDataList() {
		return hiTaskPoolExecuteDataList;
	}

	public void setHiTaskPoolExecuteDataList(List<HiTaskPoolExecuteData> hiTaskPoolExecuteDataList) {
		this.hiTaskPoolExecuteDataList = hiTaskPoolExecuteDataList;
	}
	
	public String getVotingOpinion() {
		return votingOpinion;
	}
	
	public void setVotingOpinion(String votingOpinion) {
		this.votingOpinion = votingOpinion;
	}

	public String getAddTaskPosition() {
		return addTaskPosition;
	}

	public void setAddTaskPosition(String addTaskPosition) {
		this.addTaskPosition = addTaskPosition;
	}

	public List<String> getAddTaskUsers() {
		return addTaskUsers;
	}

	public void setAddTaskUsers(List<String> addTaskUsers) {
		this.addTaskUsers = addTaskUsers;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getBeforeNodeOrgId() {
		return beforeNodeOrgId;
	}

	public void setBeforeNodeOrgId(String beforeNodeOrgId) {
		this.beforeNodeOrgId = beforeNodeOrgId;
	}

	public String getBeforeNodeParentOrgId() {
		return beforeNodeParentOrgId;
	}

	public void setBeforeNodeParentOrgId(String beforeNodeParentOrgId) {
		this.beforeNodeParentOrgId = beforeNodeParentOrgId;
	}

	public List<String> getBeforeNodeChildOrgId() {
		return beforeNodeChildOrgId;
	}

	public void setBeforeNodeChildOrgId(List<String> beforeNodeChildOrgId) {
		this.beforeNodeChildOrgId = beforeNodeChildOrgId;
	}

	public String getBeforeNodeDeptId() {
		return beforeNodeDeptId;
	}

	public void setBeforeNodeDeptId(String beforeNodeDeptId) {
		this.beforeNodeDeptId = beforeNodeDeptId;
	}

	public String getBeforeNodeParentDeptId() {
		return beforeNodeParentDeptId;
	}

	public void setBeforeNodeParentDeptId(String beforeNodeParentDeptId) {
		this.beforeNodeParentDeptId = beforeNodeParentDeptId;
	}

	public List<String> getBeforeNodeChildDeptId() {
		return beforeNodeChildDeptId;
	}

	public void setBeforeNodeChildDeptId(List<String> beforeNodeChildDeptId) {
		this.beforeNodeChildDeptId = beforeNodeChildDeptId;
	}

    public List<String> getAddDeptCodes() {
        return addDeptCodes;
    }

    public void setAddDeptCodes(List<String> addDeptCodes) {
        this.addDeptCodes = addDeptCodes;
    }

	public List<String> getAddRoleCodes() {
		return addRoleCodes;
	}

	public void setAddRoleCodes(List<String> addRoleCodes) {
		this.addRoleCodes = addRoleCodes;
	}

	public List<String> getDeleteTaskIds() {
		return deleteTaskIds;
	}

	public void setDeleteTaskIds(List<String> deleteTaskIds) {
		this.deleteTaskIds = deleteTaskIds;
	}

	public String getLeaveTransferId() {
		return leaveTransferId;
	}

	public void setLeaveTransferId(String leaveTransferId) {
		this.leaveTransferId = leaveTransferId;
	}

	public String getIsBackOwn() {
		return isBackOwn;
	}

	public void setIsBackOwn(String isBackOwn) {
		this.isBackOwn = isBackOwn;
	}

	public String getIsReBack() {
		return isReBack;
	}

	public void setIsReBack(String isReBack) {
		this.isReBack = isReBack;
	}

	public String getIsCopyHiTaskExpandData() {
		return isCopyHiTaskExpandData;
	}

	public void setIsCopyHiTaskExpandData(String isCopyHiTaskExpandData) {
		this.isCopyHiTaskExpandData = isCopyHiTaskExpandData;
	}

	public List<ProcessInfo> getSubProcessInfos() {
		return subProcessInfos;
	}

	public void setSubProcessInfos(List<ProcessInfo> subProcessInfos) {
		this.subProcessInfos = subProcessInfos;
	}

	public Map<String, Object> getEditorVariables() {
		return editorVariables;
	}

	public void setEditorVariables(Map<String, Object> editorVariables) {
		this.editorVariables = editorVariables;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public List<PostInfo> getNextPost() {
		return nextPost;
	}

	public void setNextPost(List<PostInfo> nextPost) {
		this.nextPost = nextPost;
	}

	public List<String> getNextActivityIds() {
		return nextActivityIds;
	}

	public void setNextActivityIds(List<String> nextActivityIds) {
		this.nextActivityIds = nextActivityIds;
	}

	public List<String> getCancelActivityIds() {
		return cancelActivityIds;
	}

	public void setCancelActivityIds(List<String> cancelActivityIds) {
		this.cancelActivityIds = cancelActivityIds;
	}
}
