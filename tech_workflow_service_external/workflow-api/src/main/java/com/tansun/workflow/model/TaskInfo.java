package com.tansun.workflow.model;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TaskInfo extends BaseInfo {

	private static final long serialVersionUID = 1L;
	private String tenantId;
	private List<String> tenantIds;
//	@ApiModelProperty("机构代码")
	private String   orgId;
//	@ApiModelProperty("用户名")
	private String   userId;
//	@ApiModelProperty("角色列表")
	private List<String> roleList;
	
	private  List<String>  orgList;

	private String formScence;

	private  List<String>  deptList;
//	@ApiModelProperty("当前页码")
	private String   currentPage;
//	@ApiModelProperty("每页数据大小")
	private String   pageSize;
//	@ApiModelProperty("3:已办,非3:待办")
	private String   state;

	public String getTaskDefineKey() {
		return taskDefineKey;
	}

	public void setTaskDefineKey(String taskDefineKey) {
		this.taskDefineKey = taskDefineKey;
	}

	//	@ApiModelProperty("过滤器")
	private String   filter;
//	@ApiModelProperty("流程定义标识")
	private String    processDefineKey;
	private String taskDefineKey;	//任务定义id
    /**
     * 目前TaskInfo类有processDefineId和processDefinitionId两个字段
     * 统一使用processDefinitionId
     */
	@Deprecated
	private String processDefineId;
//	@ApiModelProperty("流程实例id")
	private String processInstanceId;
//	@ApiModelProperty("执行实列ID")
	private String     executionId;
//	@ApiModelProperty("活动id")
    private String taskId;
//	@ApiModelProperty("活动id")
    private List<String> taskIds;
//	@ApiModelProperty("")
    private String activityId;
//	@ApiModelProperty("活动名称")
    private String taskName;
    private String groupId;
//	@ApiModelProperty("流程参数")
	private List<ValueType>   mPara;
//	@ApiModelProperty("活动列表信息")
	private List<Map> taskInfo;
//	@ApiModelProperty("目标参与者")
	private String tUserId;
//	@ApiModelProperty("流程/任务节点状态")
	private String processStatus;

	private Map<String, Object> localVariables;
	private String wfClassifyCode;
	private List<String> wfClassifyCodes;
//	@ApiModelProperty("待办扩展查询条件")
	private Map<String,String> taskExpandCondition;
	private Set<String> fromUsers;
	private Set<String> toUsers;

	private String comment;

	/**根据taskId返回任务信息start*/
//	@ApiModelProperty("根据taskId返回任务信息")
	private String processDefinitionId;
	private String processDefineName;
	private String processDefineKeyLike;
//	private String processInstanceId;
//	private String activityId;
	private String activityName;
	private String businessKey;
//	private String taskId;
	private String createTime;
	private String formKey;
	private String participant;
	private String startUserId;
	private String startUserName;
//	private String orgId;
	private String orgName;
	private String dueDate;
	private String processTitle;
	private Object actButton;
	private String beginTime;
	private String endTime;
	private String isStartNode;
//	@ApiModelProperty("流程实例创建查询起始时间")
	private String processCreateTimeBefore;
//	@ApiModelProperty("流程实例创建查询结束时间")
    private String processCreateTimeAfter;
	private String classifyId;

	/**
	 * 任务池状态，用于获取任务池
	 */
//	@ApiModelProperty("任务池状态，用于获取任务池")
	private String taskPoolStatus;

	/**
	 * 领取状态 1:已领取 2：未领取
	 */
	private Integer claimStatus;
	
	/**
     * 已办终止状态
     */
//	@ApiModelProperty("已办终止状态")
    private String isTerminateProcess;

    /**
     * 离岗移交表key id
     */
//	@ApiModelProperty("离岗移交表key id")
    private String leaveTransferId;

    // ------ 任务池执行记录start -------
    /**
     * 管理员id
     */
//	@ApiModelProperty("管理员id")
    private String sysId;

    /**
     * 任务池执行状态
     */
//	@ApiModelProperty("任务池执行状态")
    private String isAllot;

    /**
     * 进行中和结束状态
     */
//	@ApiModelProperty("进行中和结束状态")
    private String isEnd;

    /**
     * 未结和已结状态(包含待办)
     */
//	@ApiModelProperty("未结和已结状态(包含待办)")
    private String isEndIncludeRuTask;

	/**
     * 扩展数据
     */
//	@ApiModelProperty("扩展数据")
    private Map <String,String> taskExpandData;

    /**
     * 已办去重状态（1去重非1或不传为不去重）
     */
//	@ApiModelProperty("已办去重状态（1去重非1或不传为不去重）")
    private String singleHiTaskStatus;

	/**
     * 候选用户
     */
//	@ApiModelProperty("候选用户")
    private Set<String> users;

	/**
     * 待办固定扩展字段获取
     */
//	@ApiModelProperty("待办固定扩展字段获取")
    private String fixedTaskExpandDataStatus;

    /**
     * 区分系统管理员分配或释放的任务： 0表示正常领取释放的任务，1表示管理员分配释放的任务，默认0
     */
//	@ApiModelProperty(value = "区分系统管理员分配或释放的任务",example = "0表示正常领取释放的任务，1表示管理员分配释放的任务，默认0")
	private String sysCode;
    private Map formData;
    private String classifyName;
	private String businessKeyLike;
	private String processDefineNameLike;
	private String startTimeSort;
	private String claimTimeSort;
	private String endTimeSort;
	private String createTimeSort;
	private String unequalActivityId;
	private String unequalActivityName;
	private List<String> ignoreActivityId;
	private List<String> ignoreActivityName;
	/**
	 * 任务领取时间之前
	 *
	 */
//	@ApiModelProperty("任务领取时间之前")
	private Date claimTimeBefore;

	/**
	 * 任务领取时候
	 * @return
	 */
//	@ApiModelProperty("任务领取时候")
	private Date claimTimeAfter;

//	@ApiModelProperty("是否显示上一步信息")
	private String isShowPreTask;
	private String isShowCurrentTask;

//	@ApiModelProperty("任务创建查询起始时间")
	private String createTimeBefore;
//	@ApiModelProperty("任务创建查询结束时间")
	private String createTimeAfter;
//	@ApiModelProperty("任务结束时间的起始时间")
	private String endTimeBefore;
//	@ApiModelProperty("任务结束时间的终止时间")
	private String endTimeAfter;

	private List<String> assignees;

	private String isNotLimit;

	private List<String> processDefineKeys;

	/**
	 * 用来判断是否通过已阅未阅来判断是否释放任务
	 *
	 */
	private String isReadPass;
	/**
	 * 已阅未阅状态
	 *
	 */
	private String signStatus;

	private String orgCode;

	private String deptCode;

	private List<String> roleCodes;

	private PostInfo transferUser;

	private List<PostInfo> postInfos;

	private boolean queryAllTask;

	private String userName;
	


	private String startUserOrgCode;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public String getIsReadPass() {
		return isReadPass;
	}

	public void setIsReadPass(String isReadPass) {
		this.isReadPass = isReadPass;
	}

	public List<String> getProcessDefineKeys() {
		return processDefineKeys;
	}

	public void setProcessDefineKeys(List<String> processDefineKeys) {
		this.processDefineKeys = processDefineKeys;
	}

	public String getEndTimeBefore() {
		return endTimeBefore;
	}

	public void setEndTimeBefore(String endTimeBefore) {
		this.endTimeBefore = endTimeBefore;
	}

	public String getEndTimeAfter() {
		return endTimeAfter;
	}

	public void setEndTimeAfter(String endTimeAfter) {
		this.endTimeAfter = endTimeAfter;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String gettUserId() {
		return tUserId;
	}
	public void settUserId(String tUserId) {
		this.tUserId = tUserId;
	}
	public List<String> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Deprecated
	public String getProcessDefineId() {
		return processDefineId;
	}
	@Deprecated
	public void setProcessDefineId(String processDefineId) {
		this.processDefineId = processDefineId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public List<ValueType> getmPara() {
		return mPara;
	}
	public void setmPara(List<ValueType> mPara) {
		this.mPara = mPara;
	}
	public List<Map> getTaskInfo() {
		return taskInfo;
	}
	public void setTaskInfo(List<Map> taskInfo) {
		this.taskInfo = taskInfo;
	}
	public String getExecutionId() {
		return executionId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getProcessDefineKey() {
		return processDefineKey;
	}
	public void setProcessDefineKey(String processDefineKey) {
		this.processDefineKey = processDefineKey;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}


	public String getWfClassifyCode() {
		return wfClassifyCode;
	}

	public void setWfClassifyCode(String wfClassifyCode) {
		this.wfClassifyCode = wfClassifyCode;
	}

	public Map<String, String> getTaskExpandCondition() {
		return taskExpandCondition;
	}

	public void setTaskExpandCondition(Map<String, String> taskExpandCondition) {
		this.taskExpandCondition = taskExpandCondition;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getProcessDefineName() {
		return processDefineName;
	}
	public void setProcessDefineName(String processDefineName) {
		this.processDefineName = processDefineName;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	public String getParticipant() {
		return participant;
	}
	public void setParticipant(String participant) {
		this.participant = participant;
	}
	public String getStartUserId() {
		return startUserId;
	}
	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public String getUnequalActivityName() {
		return unequalActivityName;
	}

	public Integer getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(Integer claimStatus) {
		this.claimStatus = claimStatus;
	}

	public void setUnequalActivityName(String unequalActivityName) {
		this.unequalActivityName = unequalActivityName;
	}

	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getProcessTitle() {
		return processTitle;
	}
	public void setProcessTitle(String processTitle) {
		this.processTitle = processTitle;
	}
	public Object getActButton() {
		return actButton;
	}
	public void setActButton(Object actButton) {
		this.actButton = actButton;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getIsStartNode() {
		return isStartNode;
	}
	public void setIsStartNode(String isStartNode) {
		this.isStartNode = isStartNode;
	}


	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getProcessCreateTimeBefore() {
		return processCreateTimeBefore;
	}
	public void setProcessCreateTimeBefore(String processCreateTimeBefore) {
		this.processCreateTimeBefore = processCreateTimeBefore;
	}

	public String getIsTerminateProcess() {
		return isTerminateProcess;
	}
	public void setIsTerminateProcess(String isTerminateProcess) {
		this.isTerminateProcess = isTerminateProcess;
	}

	public Set<String> getToUsers() {
        return toUsers;
    }

    public void setToUsers(Set<String> toUsers) {
        this.toUsers = toUsers;
    }

    public Set<String> getFromUsers() {
        return fromUsers;
    }

    public void setFromUsers(Set<String> fromUsers) {
        this.fromUsers = fromUsers;
    }
	public String getProcessCreateTimeAfter() {
		return processCreateTimeAfter;
	}
	public void setProcessCreateTimeAfter(String processCreateTimeAfter) {
		this.processCreateTimeAfter = processCreateTimeAfter;
	}
	public String getLeaveTransferId() {
		return leaveTransferId;
	}
    public List<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<String> taskIds) {
        this.taskIds = taskIds;
    }
	public void setLeaveTransferId(String leaveTransferId) {
		this.leaveTransferId = leaveTransferId;
	}
	public String getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}
	public String getIsAllot() {
		return isAllot;
	}
	public void setIsAllot(String isAllot) {
		this.isAllot = isAllot;
	}
	public String getTaskPoolStatus() {
		return taskPoolStatus;
	}
	public void setTaskPoolStatus(String taskPoolStatus) {
		this.taskPoolStatus = taskPoolStatus;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public List<String> getTenantIds() {
		return tenantIds;
	}
	public void setTenantIds(List<String> tenantIds) {
		this.tenantIds = tenantIds;
	}
	
	public Map getFormData() {
		return formData;
	}

	public void setFormData(Map formData) {
		this.formData = formData;
	}

	public String getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}
	public String getIsEndIncludeRuTask() {
		return isEndIncludeRuTask;
	}
	public void setIsEndIncludeRuTask(String isEndIncludeRuTask) {
		this.isEndIncludeRuTask = isEndIncludeRuTask;
	}

	public Map<String, String> getTaskExpandData() {
		return taskExpandData;
	}
	public void setTaskExpandData(Map<String, String> taskExpandData) {
		this.taskExpandData = taskExpandData;
	}
	public String getSingleHiTaskStatus() {
		return singleHiTaskStatus;
	}
	public void setSingleHiTaskStatus(String singleHiTaskStatus) {
		this.singleHiTaskStatus = singleHiTaskStatus;
	}

	public void setLocalVariables(Map<String, Object> localVariables) {
		this.localVariables = localVariables;
	}

	public Map<String, Object> getLocalVariables() {
		return localVariables;
	}

	public Set<String> getUsers() {
		return users;
	}
	public void setUsers(Set<String> users) {
		this.users = users;
	}
	public String getFixedTaskExpandDataStatus() {
		return fixedTaskExpandDataStatus;
	}
	public void setFixedTaskExpandDataStatus(String fixedTaskExpandDataStatus) {
		this.fixedTaskExpandDataStatus = fixedTaskExpandDataStatus;
	}
	public String getClassifyName() {
		return classifyName;
	}
	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

    public String getBusinessKeyLike() {
        return businessKeyLike;
    }

    public void setBusinessKeyLike(String businessKeyLike) {
        this.businessKeyLike = businessKeyLike;
    }

	public  String getProcessDefineNameLike(){return processDefineNameLike;}

	public void setProcessDefineNameLike(String ProcessDefineNameLike) {
		this.processDefineNameLike = ProcessDefineNameLike;
	}

	public  String getProcessDefineKeyLike(){return processDefineKeyLike;}

	public void setProcessDefineKeyLike(String ProcessDefineKeyLike) {
		this.processDefineKeyLike = ProcessDefineKeyLike;
	}

	public Date getClaimTimeBefore() {
		return claimTimeBefore;
	}

	public void setClaimTimeBefore(Date claimTimeBefore) {
		this.claimTimeBefore = claimTimeBefore;
	}

	public Date getClaimTimeAfter() {
		return claimTimeAfter;
	}

	public void setClaimTimeAfter(Date claimTimeAfter) {
		this.claimTimeAfter = claimTimeAfter;
	}

    public String getIsShowPreTask() {
        return isShowPreTask;
    }

    public void setIsShowPreTask(String isShowPreTask) {
        this.isShowPreTask = isShowPreTask;
    }

	public String getIsShowCurrentTask() {
		return isShowCurrentTask;
	}

	public void setIsShowCurrentTask(String isShowCurrentTask) {
		this.isShowCurrentTask = isShowCurrentTask;
	}

	public List<String> getWfClassifyCodes() {
		return wfClassifyCodes;
	}

	public void setWfClassifyCodes(List<String> wfClassifyCodes) {
		this.wfClassifyCodes = wfClassifyCodes;
	}

	public String getStartTimeSort() {
		return startTimeSort;
	}

	public void setStartTimeSort(String startTimeSort) {
		this.startTimeSort = startTimeSort;
	}

	public String getClaimTimeSort() {
		return claimTimeSort;
	}

	public void setClaimTimeSort(String claimTimeSort) {
		this.claimTimeSort = claimTimeSort;
	}

	public String getEndTimeSort() {
		return endTimeSort;
	}

	public void setEndTimeSort(String endTimeSort) {
		this.endTimeSort = endTimeSort;
	}

	public String getCreateTimeSort() {
		return createTimeSort;
	}

	public void setCreateTimeSort(String createTimeSort) {
		this.createTimeSort = createTimeSort;
	}

	public String getCreateTimeBefore() {
		return createTimeBefore;
	}

	public void setCreateTimeBefore(String createTimeBefore) {
		this.createTimeBefore = createTimeBefore;
	}

	public String getCreateTimeAfter() {
		return createTimeAfter;
	}

	public void setCreateTimeAfter(String createTimeAfter) {
		this.createTimeAfter = createTimeAfter;
	}

	public String getUnequalActivityId() {
		return unequalActivityId;
	}

	public void setUnequalActivityId(String unequalActivityId) {
		this.unequalActivityId = unequalActivityId;
	}

	public List<String> getAssignees() {
		return assignees;
	}

	public void setAssignees(List<String> assignees) {
		this.assignees = assignees;
	}

	public String getIsNotLimit() {
		return isNotLimit;
	}

	public void setIsNotLimit(String isNotLimit) {
		this.isNotLimit = isNotLimit;
	}

	public List<String> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<String> orgList) {
		this.orgList = orgList;
	}

	public List<String> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<String> deptList) {
		this.deptList = deptList;
	}

	public List<PostInfo> getPostInfos() {
		return postInfos;
	}

	public void setPostInfos(List<PostInfo> postInfos) {
		this.postInfos = postInfos;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public List<String> getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(List<String> roleCodes) {
		this.roleCodes = roleCodes;
	}

	public boolean isQueryAllTask() {
		return queryAllTask;
	}

	public void setQueryAllTask(boolean queryAllTask) {
		this.queryAllTask = queryAllTask;
	}

	public PostInfo getTransferUser() {
		return transferUser;
	}

	public void setTransferUser(PostInfo transferUser) {
		this.transferUser = transferUser;
	}

	public String getFormScence() {
		return formScence;
	}

	public void setFormScence(String formScence) {
		this.formScence = formScence;
	}


	public String getStartUserOrgCode() {
		return startUserOrgCode;
	}

	public void setStartUserOrgCode(String startUserOrgCode) {
		this.startUserOrgCode = startUserOrgCode;
	}

	public List<String> getIgnoreActivityId() {
		return ignoreActivityId;
	}

	public void setIgnoreActivityId(List<String> ignoreActivityId) {
		this.ignoreActivityId = ignoreActivityId;
	}

	public List<String> getIgnoreActivityName() {
		return ignoreActivityName;
	}

	public void setIgnoreActivityName(List<String> ignoreActivityName) {
		this.ignoreActivityName = ignoreActivityName;
	}
}
