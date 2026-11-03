package com.tansun.workflow.model;

//import io.swagger.annotations.ApiModelProperty;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessInfo extends BaseInfo {

    private static final long serialVersionUID = 3046058476239646786L;
    /**
     * 流程名称
     */
//    @ApiModelProperty(value = "流程名称", example = "贷款流程")
    private String processName;
    /**
     * 流程定义ID
     */
//    @ApiModelProperty(value = "流程定义ID")
    private String processDefineId;
    /**
     * 流程定义标识
     */
//    @ApiModelProperty(value = "流程定义标识")
    private String processDefineKey;
    /**
     * 流程标题
     */
//    @ApiModelProperty(value = "流程标题")
    private String processTitle;
    /**
     * 业务主键
     */
//    @ApiModelProperty(value = "业务主键")
    private String businessKey;
    /**
     * 流程实列ID
     */
//    @ApiModelProperty(value = "流程实列ID")
    private String processInstanceId;
    /**
     * 状态,1：已结束流程，0：正在运行流程
     */
//    @ApiModelProperty(value = "状态", example = "1：已结束流程，0：正在运行流程")
    private String status;
    /**
     * 当前页码
     */
//    @ApiModelProperty(value = "当前页码")
    private String currentPage;
    /**
     * 每页数据大小
     */
//    @ApiModelProperty(value = "每页数据大小")
    private String pageSize;
    /**
     * 执行实列ID
     */
//    @ApiModelProperty(value = "执行实列ID")
    private String executionId;
    /**
     * 发起人
     */
    // 发起人
    private String startUserId;
    /**
     * 发起人中文名
     */
    // 发起人中文名
    private String startUserName;
    /**
     * 发起人同部门code
     */
//    @ApiModelProperty(value = "发起人同部门code")
    private String startDeptId;
    /**
     * 发起人同机构code
     */
//    @ApiModelProperty(value = "发起人同机构code")
    private String startOrgId;
    /**
     * 发起人同角色id
     */
//    @ApiModelProperty(value = "发起人同角色id")
    private String startRoleId;
    /**
     * 发起人上级机构同机构id
     */
//    @ApiModelProperty(value = "发起人上级机构同机构id")
    private String startParentOrgId;
    /**
     * 上一节点审批人同机构id
     */
//    @ApiModelProperty(value = "上一节点审批人同机构id")
    private String beforeNodeOrgId;
    /**
     * 上一节点审批人上级机构同机构id
     */
//    @ApiModelProperty(value = "上一节点审批人上级机构同机构id")
    private String beforeNodeParentOrgId;
    /**
     * 与上一个节点审批人下级机构同机构
     */
//    @ApiModelProperty("与上一个节点审批人下级机构同机构")
    private List<String> beforeNodeChildOrgId;
    /**
     * 与上一个节点审批人同部门
     */
//    @ApiModelProperty("与上一个节点审批人同部门")
    private String beforeNodeDeptId;
    /**
     * 与上一个节点审批人上级部门同部门
     */
//    @ApiModelProperty("与上一个节点审批人上级部门同部门")
    private String beforeNodeParentDeptId;
    /**
     * 与上一个节点审批人下级部门同部门
     */
//    @ApiModelProperty("与上一个节点审批人下级部门同部门")
    private List<String> beforeNodeChildDeptId;
    /**
     * 用户id
     */
//    @ApiModelProperty(value = "用户id")
    private String userId;
    /**
     * 流程参数
     */
//    @ApiModelProperty(value = "流程参数", dataType = "Map")
    private Map<String, Object> mPara;
    /**
     * 任务id
     */
//    @ApiModelProperty(value = "任务id")
    private String taskId;
    /**
     * 租户编号
     */
//    @ApiModelProperty(value = "租户编号", example = "liubei", required = true)
    private String tenantId;
    /**
     * 模型Id
     */
//    @ApiModelProperty(value = "模型Id")
    private String modelId;
    /**
     * 模型键值
     */
//    @ApiModelProperty(value = "模型键值")
    private String modelKey;

    // 模型key，模糊查询
    private String modelKeyLike;
    /**
     * 模型名称
     */
//    @ApiModelProperty(value = "模型名称")
    private String modelName;
    /**
     * 待办扩展数据
     */
//    @ApiModelProperty(value = "待办扩展数据")
    private Map<String, String> taskExpandData;
    /**
     * 下一步人
     */
//    @ApiModelProperty(value = "下一步人")
    private List<String> nextParticipates;

    /**
     * 下一个岗位
     */
    private List<PostInfo> nextPost;
    /**
     * 设置下一机构编号
     */
//    @ApiModelProperty(value = "设置下一机构编号")
    private String nextOrgId;
    /**
     * 批注
     */
//    @ApiModelProperty(value = "批注")
    private String comment;
    /**
     * 终止原因
     */
//    @ApiModelProperty(value = "终止原因")
    private String terminateReason;
    /**
     * 抄送用户
     */
//    @ApiModelProperty(value = "抄送用户")
    private List<String> ccUsers;
    /**
     * 抄送角色
     */
//    @ApiModelProperty(value = "抄送角色")
    private List<String> ccRoles;
    /**
     * 下个节点ID
     */
//    @ApiModelProperty(value = "下个节点ID")
    private String nextActivityId;
    /**
     * 回调Map
     */
//    @ApiModelProperty(value = "回调Map")
    private Map<String, Object> creditMap;
    /**
     * 表单数据
     */
//    @ApiModelProperty
    private Map<String, Object> formData;
    /**
     * 目标用户ID
     */
//    @ApiModelProperty(value = "目标用户ID")
    private String tUserId;
    /**
     * 变量
     */
//    @ApiModelProperty
    private Map<String, Object> variables;
    /**
     * 版本号
     */
//    @ApiModelProperty(value = "版本号")
    private Integer processDefineVersion;
    /**
     * 分类编号
     */
//    @ApiModelProperty
    private String classifyCode;
    /**
     * 分类id
     */
//    @ApiModelProperty(value = "分类id")
    private String classifyId;
    /**
     * 任务本地变量
     */
//    @ApiModelProperty(value = "任务本地变量")
    private Map localVariables;

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }
    /**
     * 编号
     */
//    @ApiModelProperty(value = "编号")
    private String code;
    /**
     * 并行多节点传参
     */
//    @ApiModelProperty(value = "并行多节点传参")
    private Map<String, Object> nextUsersMap;
    /**
     * "部署状态", 1:已部署 0:未部署
     */
//    @ApiModelProperty(value = "部署状态", example = "1:已部署 0:未部署")
    private String deploymentStatus;
    /**
     * 是否显示子流程的轨迹", "Y：显示 其他：不显示"
     */
//    @ApiModelProperty(value = "是否显示子流程的轨迹", example = "Y：显示 其他：不显示")
    private String isShowSubProcess;
    /**
     * 是否显示主流程的轨迹", "Y：显示 其他：不显示
     */
//    @ApiModelProperty(value = "是否显示主流程的轨迹", example = "Y：显示 其他：不显示")
    private String isShowSuperProcess;
    /**
     * 是否需要删除历史记录", "Y：删除 其他：不删除
     */
//    @ApiModelProperty(value = "是否需要删除历史记录", example = "Y：删除 其他：不删除")
    private String isDeleteHistory;
    /**
     * 是否需要完成的流程", Y：完成 其他：未完成
     */
//    @ApiModelProperty(value = "是否需要完成的流程", example = "Y：完成 其他：未完成")
    private String ignoreFinish;
    /**
     * 是否需要按照启动时间排序", desc：倒序 asc：正序
     */
//    @ApiModelProperty(value = "是否需要按照启动时间排序", example = "desc：倒序 asc：正序")
    private String startTimeSort;
    /**
     * 是否需要按照结束时间排序", "desc：倒序 asc：正序
     */
//    @ApiModelProperty(value = "是否需要按照结束时间排序", example = "desc：倒序 asc：正序")
    private String endTimeSort;
    /**
     * 发起人岗位列表
     */
//    @ApiModelProperty(value = "发起人岗位列表", example = "")
    private List<String> startRoleIdList;
    /**
     * 是否校验发起者岗位权限，Y:校验，N:不校验
     */
//    @ApiModelProperty(value = "是否校验发起者岗位权限", example = "Y:校验，N:不校验")
    private String isCheckStartUserPower;
    /**
     * 流程调式信息
     */
//    @ApiModelProperty(value = "流程调式信息", dataType = "DebugInfo")
    private DebugInfo debugInfo;
    /**
     * 父类流程实例id
     */
    private String superProcessInstanceId;
    /**
     * 子流程启动流程参数
     */
//    @ApiModelProperty("子流程启动流程参数")
    private List<ProcessInfo> subProcessInfos;
    /**
     * 接收节点上的接收消息
     */
    // 接收节点上的接收消息
    private String messageName;

    public String getDeploymentStatus() {
        return deploymentStatus;
    }

    public void setDeploymentStatus(String deploymentStatus) {
        this.deploymentStatus = deploymentStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public Map<String, Object> getFormData() {
        return formData;
    }

    public void setFormData(Map<String, Object> formData) {
        this.formData = formData;
    }

    public String getNextActivityId() {
        return nextActivityId;
    }

    public void setNextActivityId(String nextActivityId) {
        this.nextActivityId = nextActivityId;
    }

    public Map<String, Object> getCreditMap() {
        return creditMap;
    }

    public void setCreditMap(Map<String, Object> creditMap) {
        this.creditMap = creditMap;
    }

    public String gettUserId() {
        return tUserId;
    }

    public void settUserId(String tUserId) {
        this.tUserId = tUserId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Object> getmPara() {
        return mPara;
    }

    public void setmPara(Map<String, Object> mPara) {
        this.mPara = mPara;
    }

    public String getExecutionId() {
        return executionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getModelId() {
        return modelId;
    }

    public String getModelKey() {
        return modelKey;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getProcessTitle() {
        return processTitle;
    }

    public void setProcessTitle(String processTitle) {
        this.processTitle = processTitle;
    }

    public Map<String, String> getTaskExpandData() {
        return taskExpandData;
    }

    public void setTaskExpandData(Map<String, String> taskExpandData) {
        this.taskExpandData = taskExpandData;
    }

    public List<String> getNextParticipates() {
        return nextParticipates;
    }

    public void setNextParticipates(List<String> nextParticipates) {
        this.nextParticipates = nextParticipates;
    }

    public String getNextOrgId() {
        return nextOrgId;
    }

    public void setNextOrgId(String nextOrgId) {
        this.nextOrgId = nextOrgId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTerminateReason() {
        return terminateReason;
    }

    public void setTerminateReason(String terminateReason) {
        this.terminateReason = terminateReason;
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

    public Integer getProcessDefineVersion() {
        return processDefineVersion;
    }

    public void setProcessDefineVersion(Integer processDefineVersion) {
        this.processDefineVersion = processDefineVersion;
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

    public String getStartDeptId() {
        return startDeptId;
    }

    public void setStartDeptId(String startDeptId) {
        this.startDeptId = startDeptId;
    }

    public String getStartOrgId() {
        return startOrgId;
    }

    public void setStartOrgId(String startOrgId) {
        this.startOrgId = startOrgId;
    }

    public String getStartRoleId() {
        return startRoleId;
    }

    public void setStartRoleId(String startRoleId) {
        this.startRoleId = startRoleId;
    }

    public String getStartParentOrgId() {
        return startParentOrgId;
    }

    public void setStartParentOrgId(String startParentOrgId) {
        this.startParentOrgId = startParentOrgId;
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

    public Map<String, Object> getNextUsersMap() {
        return nextUsersMap;
    }

    public void setNextUsersMap(Map<String, Object> nextUsersMap) {
        this.nextUsersMap = nextUsersMap;
    }

    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
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

    public void addToMPara(String key, Object value) {
        if (value == null) {
            return;
        }
        if (mPara == null) {
            mPara = new HashMap();
        }
        mPara.put(key, value);

    }

    public String getIsShowSubProcess() {
        return isShowSubProcess;
    }

    public void setIsShowSubProcess(String isShowSubProcess) {
        this.isShowSubProcess = isShowSubProcess;
    }

    public String getIsShowSuperProcess() {
        return isShowSuperProcess;
    }

    public void setIsShowSuperProcess(String isShowSuperProcess) {
        this.isShowSuperProcess = isShowSuperProcess;
    }

    public String getIsDeleteHistory() {
        return isDeleteHistory;
    }

    public void setIsDeleteHistory(String isDeleteHistory) {
        this.isDeleteHistory = isDeleteHistory;
    }

    public String getIgnoreFinish() {
        return ignoreFinish;
    }

    public void setIgnoreFinish(String ignoreFinish) {
        this.ignoreFinish = ignoreFinish;
    }

    public String getStartTimeSort() {
        return startTimeSort;
    }

    public void setStartTimeSort(String startTimeSort) {
        this.startTimeSort = startTimeSort;
    }

    public String getEndTimeSort() {
        return endTimeSort;
    }

    public void setEndTimeSort(String endTimeSort) {
        this.endTimeSort = endTimeSort;
    }

    public List<String> getStartRoleIdList() {
        return startRoleIdList;
    }

    public void setStartRoleIdList(List<String> startRoleIdList) {
        this.startRoleIdList = startRoleIdList;
    }

    public String getIsCheckStartUserPower() {
        return isCheckStartUserPower;
    }

    public void setIsCheckStartUserPower(String isCheckStartUserPower) {
        this.isCheckStartUserPower = isCheckStartUserPower;
    }

    public DebugInfo getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(DebugInfo debugInfo) {
        this.debugInfo = debugInfo;
    }

    public String getSuperProcessInstanceId() {
        return superProcessInstanceId;
    }

    public void setSuperProcessInstanceId(String superProcessInstanceId) {
        this.superProcessInstanceId = superProcessInstanceId;
    }

    public List<ProcessInfo> getSubProcessInfos() {
        return subProcessInfos;
    }

    public void setSubProcessInfos(List<ProcessInfo> subProcessInfos) {
        this.subProcessInfos = subProcessInfos;
    }

    public Map getLocalVariables() {
        return localVariables;
    }

    public void setLocalVariables(Map localVariables) {
        this.localVariables = localVariables;
    }

    public List<PostInfo> getNextPost() {
        return nextPost;
    }

    public void setNextPost(List<PostInfo> nextPost) {
        this.nextPost = nextPost;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getModelKeyLike() {
        return modelKeyLike;
    }

    public void setModelKeyLike(String modelKeyLike) {
        this.modelKeyLike = modelKeyLike;
    }
}
