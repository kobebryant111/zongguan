package com.tansun.wf.activiti.model;

import org.camunda.bpm.engine.impl.db.DbEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskExpandDataEntity implements Serializable ,DbEntity {

    private String id;
    private String taskExpandPlanId;		// 代办拓展方案ID
    private String processInstanceId;		// 流程实例ID
    private String processDefineId;		// 流程定义ID
    private String taskId;		// 待办任务ID
    private String taskNodeId;		// 待办任务节点ID
    private String tenantId;
    private String field01;		// 拓展字段01
    private String field02;		// 拓展字段02
    private String field03;		// 拓展字段03
    private String field04;		// 拓展字段04
    private String field05;		// 拓展字段05
    private String field06;		// 拓展字段06
    private String field07;		// 拓展字段07
    private String field08;		// 拓展字段08
    private String field09;		// 拓展字段09
    private String field10;		// 拓展字段10
    private String field11;		// 拓展字段11
    private String field12;		// 拓展字段12
    private String field13;		// 拓展字段13
    private String field14;		// 拓展字段14
    private String field15;		// 拓展字段15
    private String field16;		// 拓展字段16
    private String field17;		// 拓展字段17
    private String field18;		// 拓展字段18
    private String field19;		// 拓展字段19
    private String field20;		// 拓展字段20

    private String processDefKey ; //流程定义键值
    private String businessKey ; //业务主键
    private String processClassifyId ; //流程分类Id
    private String processName ; //流程名称
    private String processClassifyCode ; //流程分类编码
    private String superProcessInstanceId ; //父流程实例Id
    private Date processStartTime ; //流程发起时间
    private Date processEndTime ; //流程结束时间，实现逻辑待定
    private String startUserId ; //发起人用户
    private String startUserName ; //发起人用户中文名
    private String startUserDeptCode ; //发起人部门编码
    private String startUserRoleCode ; //发起人角色编码
    private String startUserParentOrgCode ; //发起人父级机构编码
    private String startUserOrgCode ; //发起人机构编码
    private String taskName ; //任务名称
    private String taskType ; //任务类型
    private String isStartNode ; //是否为发起节点
    private Date taskCreateTime ; //任务创建时间
    private Date taskEndTime ; //任务结束时间
    private String formScene ; //表单场景
    private String actButton ; //操作权限,字段长度确认
    private String isMulti ; //是否会签
    private String selectUserModel ; //处理人模式
    private String candidateRoles ; //候选角色编码
    private String candidateDepts ; //候选部门编码
    private String candidateOrgs ; //候选机构编码
    private String assigneeRoleCode ; //处理人角色编码
    private String assigneeRoleName ; //处理人角色名称
    private String assigneeDeptCode ; //处理人部门编码
    private String assigneeDeptName ; //处理人部门名称
    private String assigneeOrgCode ; //处理人机构编码
    private String assigneeOrgName ; //处理人机构名称

    private String opionKey ; //流程操作键值，例如Submit
    private String opion ; //流程操作名称，例如提交
    private String opinion ; //流程操作业务名称,例如同意
    private String commentMsg; //审批意见详情


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
        Map<String, Object> persistentState = new HashMap<String, Object>();
        persistentState.put("taskExpandPlanId", taskExpandPlanId);
        persistentState.put("processInstanceId", taskExpandPlanId);
        return persistentState;
    }

    public String getTaskExpandPlanId() {
        return taskExpandPlanId;
    }

    public void setTaskExpandPlanId(String taskExpandPlanId) {
        this.taskExpandPlanId = taskExpandPlanId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefineId() {
        return processDefineId;
    }

    public void setProcessDefineId(String processDefineId) {
        this.processDefineId = processDefineId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskNodeId() {
        return taskNodeId;
    }

    public void setTaskNodeId(String taskNodeId) {
        this.taskNodeId = taskNodeId;
    }

    public String getField01() {
        return field01;
    }

    public void setField01(String field01) {
        this.field01 = field01;
    }

    public String getField02() {
        return field02;
    }

    public void setField02(String field02) {
        this.field02 = field02;
    }

    public String getField03() {
        return field03;
    }

    public void setField03(String field03) {
        this.field03 = field03;
    }

    public String getField04() {
        return field04;
    }

    public void setField04(String field04) {
        this.field04 = field04;
    }

    public String getField05() {
        return field05;
    }

    public void setField05(String field05) {
        this.field05 = field05;
    }

    public String getField06() {
        return field06;
    }

    public void setField06(String field06) {
        this.field06 = field06;
    }

    public String getField07() {
        return field07;
    }

    public void setField07(String field07) {
        this.field07 = field07;
    }

    public String getField08() {
        return field08;
    }

    public void setField08(String field08) {
        this.field08 = field08;
    }

    public String getField09() {
        return field09;
    }

    public void setField09(String field09) {
        this.field09 = field09;
    }

    public String getField10() {
        return field10;
    }

    public void setField10(String field10) {
        this.field10 = field10;
    }

    public String getField11() {
        return field11;
    }

    public void setField11(String field11) {
        this.field11 = field11;
    }

    public String getField12() {
        return field12;
    }

    public void setField12(String field12) {
        this.field12 = field12;
    }

    public String getField13() {
        return field13;
    }

    public void setField13(String field13) {
        this.field13 = field13;
    }

    public String getField14() {
        return field14;
    }

    public void setField14(String field14) {
        this.field14 = field14;
    }

    public String getField15() {
        return field15;
    }

    public void setField15(String field15) {
        this.field15 = field15;
    }

    public String getField16() {
        return field16;
    }

    public void setField16(String field16) {
        this.field16 = field16;
    }

    public String getField17() {
        return field17;
    }

    public void setField17(String field17) {
        this.field17 = field17;
    }

    public String getField18() {
        return field18;
    }

    public void setField18(String field18) {
        this.field18 = field18;
    }

    public String getField19() {
        return field19;
    }

    public void setField19(String field19) {
        this.field19 = field19;
    }

    public String getField20() {
        return field20;
    }

    public void setField20(String field20) {
        this.field20 = field20;
    }

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

    public String getCommentMsg() {
        return commentMsg;
    }

    public void setCommentMsg(String commentMsg) {
        this.commentMsg = commentMsg;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getOpion() {
        return opion;
    }

    public void setOpion(String opion) {
        this.opion = opion;
    }

    public String getOpionKey() {
        return opionKey;
    }

    public void setOpionKey(String opionKey) {
        this.opionKey = opionKey;
    }


    public String getAssigneeOrgName() {
        return assigneeOrgName;
    }

    public void setAssigneeOrgName(String assigneeOrgName) {
        this.assigneeOrgName = assigneeOrgName;
    }

    public String getAssigneeOrgCode() {
        return assigneeOrgCode;
    }

    public void setAssigneeOrgCode(String assigneeOrgCode) {
        this.assigneeOrgCode = assigneeOrgCode;
    }

    public String getAssigneeDeptName() {
        return assigneeDeptName;
    }

    public void setAssigneeDeptName(String assigneeDeptName) {
        this.assigneeDeptName = assigneeDeptName;
    }

    public String getAssigneeDeptCode() {
        return assigneeDeptCode;
    }

    public void setAssigneeDeptCode(String assigneeDeptCode) {
        this.assigneeDeptCode = assigneeDeptCode;
    }

    public String getAssigneeRoleName() {
        return assigneeRoleName;
    }

    public void setAssigneeRoleName(String assigneeRoleName) {
        this.assigneeRoleName = assigneeRoleName;
    }

    public String getAssigneeRoleCode() {
        return assigneeRoleCode;
    }

    public void setAssigneeRoleCode(String assigneeRoleCode) {
        this.assigneeRoleCode = assigneeRoleCode;
    }

    public String getCandidateOrgs() {
        return candidateOrgs;
    }

    public void setCandidateOrgs(String candidateOrgs) {
        this.candidateOrgs = candidateOrgs;
    }

    public String getCandidateDepts() {
        return candidateDepts;
    }

    public void setCandidateDepts(String candidateDepts) {
        this.candidateDepts = candidateDepts;
    }

    public String getCandidateRoles() {
        return candidateRoles;
    }

    public void setCandidateRoles(String candidateRoles) {
        this.candidateRoles = candidateRoles;
    }

    public String getSelectUserModel() {
        return selectUserModel;
    }

    public void setSelectUserModel(String selectUserModel) {
        this.selectUserModel = selectUserModel;
    }

    public String getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(String isMulti) {
        this.isMulti = isMulti;
    }

    public String getActButton() {
        return actButton;
    }

    public void setActButton(String actButton) {
        this.actButton = actButton;
    }

    public String getFormScene() {
        return formScene;
    }

    public void setFormScene(String formScene) {
        this.formScene = formScene;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public Date getTaskCreateTime() {
        return taskCreateTime;
    }

    public void setTaskCreateTime(Date taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }

    public String getIsStartNode() {
        return isStartNode;
    }

    public void setIsStartNode(String isStartNode) {
        this.isStartNode = isStartNode;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStartUserOrgCode() {
        return startUserOrgCode;
    }

    public void setStartUserOrgCode(String startUserOrgCode) {
        this.startUserOrgCode = startUserOrgCode;
    }

    public String getStartUserParentOrgCode() {
        return startUserParentOrgCode;
    }

    public void setStartUserParentOrgCode(String startUserParentOrgCode) {
        this.startUserParentOrgCode = startUserParentOrgCode;
    }

    public String getStartUserRoleCode() {
        return startUserRoleCode;
    }

    public void setStartUserRoleCode(String startUserRoleCode) {
        this.startUserRoleCode = startUserRoleCode;
    }

    public String getStartUserDeptCode() {
        return startUserDeptCode;
    }

    public void setStartUserDeptCode(String startUserDeptCode) {
        this.startUserDeptCode = startUserDeptCode;
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

    public Date getProcessEndTime() {
        return processEndTime;
    }

    public void setProcessEndTime(Date processEndTime) {
        this.processEndTime = processEndTime;
    }

    public Date getProcessStartTime() {
        return processStartTime;
    }

    public void setProcessStartTime(Date processStartTime) {
        this.processStartTime = processStartTime;
    }

    public String getSuperProcessInstanceId() {
        return superProcessInstanceId;
    }

    public void setSuperProcessInstanceId(String superProcessInstanceId) {
        this.superProcessInstanceId = superProcessInstanceId;
    }

    public String getProcessClassifyCode() {
        return processClassifyCode;
    }

    public void setProcessClassifyCode(String processClassifyCode) {
        this.processClassifyCode = processClassifyCode;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessClassifyId() {
        return processClassifyId;
    }

    public void setProcessClassifyId(String processClassifyId) {
        this.processClassifyId = processClassifyId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getProcessDefKey() {
        return processDefKey;
    }

    public void setProcessDefKey(String processDefKey) {
        this.processDefKey = processDefKey;
    }
}
