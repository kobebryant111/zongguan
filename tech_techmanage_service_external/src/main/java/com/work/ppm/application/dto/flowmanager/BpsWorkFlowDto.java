package com.work.ppm.application.dto.flowmanager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * bps
 *
 */
@ApiModel("BpsWorkFlowDto")
public class BpsWorkFlowDto {
    /**
     * 流程实例名称
     */
    @ApiModelProperty(value = " 流程实例名称", name = "processInstName")
    private String processInstName;
    /**
     * 流程定义名称 (com.work.ppm.bps.glzxzdgz)
     */
    @ApiModelProperty(value = " 流程定义名称 (com.work.ppm.bps.glzxzdgz)", name = "processDefName")
    private String processDefName;
    /**
     * 流程id
     */
    @ApiModelProperty(value = " 流程id", name = "processInstID")
    private String processInstID;
    /**
     * 流程id
     */
    @ApiModelProperty(value = " 流程id", name = "processInstId")
    private String processInstId;
    /**
     * 任务ID
     */
    @ApiModelProperty(value = " 任务ID", name = "taskID")
    private String taskID;
    /**
     * 任务ID
     */
    @ApiModelProperty(value = " 任务ID", name = "taskId")
    private String taskId;

    /**
     * 工作项id
     */
    @ApiModelProperty(value = " 工作项id", name = "workItemID")
    private String workItemID;
    /**
     * 工作项id
     */
    @ApiModelProperty(value = " 工作项id", name = "workItemId")
    private String workItemId;
    /**
     * 审批节点描述
     */
    @ApiModelProperty(value = " 审批节点描述", name = "activityInstName")
    private String activityInstName;

    @ApiModelProperty(value = "用户名称", name = "activityName")
    private String activityName;

    @ApiModelProperty(value = "用户名称", name = "assignee")
    private String assignee;
    /**
     * 用户id
     */
    @ApiModelProperty(value = " 用户id", name = "userId")
    private String userId;
    /**
     * 部门编码
     */
    @ApiModelProperty(value = " 部门编码", name = "depId")
    private String depId;
    /**
     * 相关数据map
     * users 下一个节点处理人
     * stuatus -1 通过
     */
    @ApiModelProperty(value = " 相关数据map，users 下一个节点处理人,stuatus -1 通过", name = "relaData")
    private Map<String, String> relaData;
    /**
     * 目标活动定义ID(下一个活动节点id) 例如：manualActivity5
     */
    @ApiModelProperty(value = " 目标活动定义ID(下一个活动节点id)", name = "destActDefID")
    private String destActDefID;
    /**
     * 目标活动定义ID(下一个活动节点id) 例如：manualActivity5
     */
    @ApiModelProperty(value = " 目标活动定义ID(下一个活动节点id)", name = "destActDefId")
    private String destActDefId;
    /**
     * 目标活动定义ID(下一个活动节点id) 例如：manualActivity5
     */
    @ApiModelProperty(value = " 目标活动定义ID(下一个活动节点id)", name = "activityDefID")
    private String activityDefID;
    /**
     * 目标活动定义ID(下一个活动节点id) 例如：manualActivity5
     */
    @ApiModelProperty(value = " 目标活动定义ID(下一个活动节点id)", name = "activityDefId")
    private String activityDefId;
    /**
     * 流程描述 例如：发起申请 节点
     */
    @ApiModelProperty(value = " 流程描述 例如：发起申请 节点", name = "processInstDesc")
    private String processInstDesc;
    /**
     * 当前活动实例ID 编号
     */
    @ApiModelProperty(value = "  当前活动实例ID", name = "activityInstID")
    private String activityInstID;
    /**
     * 当前活动实例ID编号
     */
    @ApiModelProperty(value = "  当前活动实例ID", name = "activityInstId")
    private String activityInstId;

    /**
     * 撤回的workItemId
     */
    @ApiModelProperty(value = "需要撤回的workItemId", name = "drawbackId")
    private String drawbackId;
    // =========== 以下是日志部分 ==================
    /**
     * 审核结论(01：通过，02：退回，03：授权，04：撤回，05：调度)
     */
    @ApiModelProperty(value = "审核结论(01：通过，02：退回，03：授权，04：撤回，05：调度)", name = "conclusion")
    private String conclusion;
    /**
     * 审核描述
     */
    @ApiModelProperty(value = " 审核描述", name = "suggest")
    private String suggest;
    /**
     * 审批节点描述 例如：发起申请 节点
     */
    @ApiModelProperty(value = " 审批节点描述", name = "applydesc")
    private String applydesc;
    // =========== 以上是日志部分 ==================
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号", name = "projectNo")
    private String projectNo;
    //============== 授权 部分 ====================
    /**
     * 指派的人员id（多个以“@“分割）
     */
    @ApiModelProperty(value = "指派的人员id（多个以“@“分割）", name = "personId")
    private String personId;
    /**
     * 指派的人员name（多个以“@“分割）
     */
    @ApiModelProperty(value = "指派的人员name（多个以“@“分割）", name = "personName")
    private String personName;
    //============== 授权 部分 ====================
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", name = "userName")
    private String userName;
    /**
     * 流程结束的地址
     */
    @ApiModelProperty(value = "流程结束的地址", name = "flowUrl")
    private String flowUrl;
    /**
     * 是否写入日志
     */
    @JsonIgnore
    private Boolean toDB = true;

    public Boolean getToDB() {
        return toDB;
    }

    public void setToDB(Boolean toDB) {
        this.toDB = toDB;
    }

    public Boolean getFalg() {
        return falg;
    }

    public void setFalg(Boolean falg) {
        this.falg = falg;
    }

    /**
     * 当返回结果为布尔时，可使用此属性
     */
    @ApiModelProperty(value = "当返回结果为布尔时，可使用此属性", name = "falg")
    private Boolean falg;


    public String getProcessInstName() {
        return processInstName;
    }

    public void setProcessInstName(String processInstName) {
        this.processInstName = processInstName;
    }

    public String getProcessDefName() {
        return processDefName;
    }

    public void setProcessDefName(String processDefName) {
        this.processDefName = processDefName;
    }

    public String getProcessInstID() {
        return processInstID;
    }

    public void setProcessInstID(String processInstID) {
        this.processInstID = processInstID;
    }

    public String getProcessInstId() {
        return StringUtil.isNotBlank(processInstId) ? processInstId : processInstID;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskId() {
        return StringUtil.isNotBlank(taskId) ? taskId : taskID;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getWorkItemID() {
        return workItemID;
    }

    public void setWorkItemID(String workItemID) {
        this.workItemID = workItemID;
    }

    public String getWorkItemId() {
        return StringUtil.isNotBlank(workItemId) ? workItemId : workItemID;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    public String getDrawbackId() {
        return drawbackId;
    }

    public void setDrawbackId(String drawbackId) {
        this.drawbackId = drawbackId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public Map<String, String> getRelaData() {
        return relaData;
    }

    public void setRelaData(Map<String, String> relaData) {
        this.relaData = relaData;
    }

    public String getDestActDefID() {
        return destActDefID;
    }

    public void setDestActDefID(String destActDefID) {
        this.destActDefID = destActDefID;
    }

    public String getDestActDefId() {
        return StringUtil.isNotBlank(destActDefId) ? destActDefId : destActDefID;
    }

    public String getActivityDefID() {
        return activityDefID;
    }

    public void setActivityDefID(String activityDefID) {
        this.activityDefID = activityDefID;
    }

    public String getActivityDefId() {
        return StringUtil.isNotBlank(activityDefId) ? activityDefId : activityDefID;
    }

    public void setActivityDefId(String activityDefId) {
        this.activityDefId = activityDefId;
    }

    public void setDestActDefId(String destActDefId) {
        this.destActDefId = destActDefId;
    }

    public String getProcessInstDesc() {
        return processInstDesc;
    }

    public void setProcessInstDesc(String processInstDesc) {
        this.processInstDesc = processInstDesc;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getApplydesc() {
        return applydesc;
    }

    public void setApplydesc(String applydesc) {
        this.applydesc = applydesc;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFlowUrl() {
        return flowUrl;
    }

    public void setFlowUrl(String flowUrl) {
        this.flowUrl = flowUrl;
    }

    public String getActivityInstName() {
        return activityInstName;
    }

    public void setActivityInstName(String activityInstName) {
        this.activityInstName = activityInstName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getActivityInstID() {
        return activityInstID;
    }

    public void setActivityInstID(String activityInstID) {
        this.activityInstID = activityInstID;
    }

    public String getActivityInstId() {
        return StringUtil.isNotBlank(activityInstID) ? activityInstID : activityInstId;
    }

    public void setActivityInstId(String activityInstId) {
        this.activityInstId = activityInstId;
    }
}
