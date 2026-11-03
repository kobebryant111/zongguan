package com.tansun.wf.activiti.model;

import java.io.Serializable;

/**
 * 扩展选人设置
 */
//@Deprecated
public class AssignmentModel implements Serializable{
    /**
     * 由上一个节点指定机构
     */
    public static final String FROM_LAST_TASK_ORG_ID = "fromLastTaskOrgId";
    /**
     * 由上一个节点指定人员(varibale key)
     */
    public static final String LAST_USER = "nextUser";

    /**
     * 由上一个节点机构同机构(varibale key)
     */
    public static final String LAST_TASK_ORG_ID_KEY = "_LAST_TASK_ORG_ID";
    /**
     * 由上一个节点机构上级机构同机构(varibale key)
     */
    public static final String LAST_TASK_PARENT_ORG_ID_KEY = "_LAST_TASK_PARENT_ORG_ID";
    /**
     * 与上一个节点审批人下级机构同机构
     */
    public static final String LAST_TASK_CHILD_ORG_ID_KEY = "_LAST_TASK_CHILD_ORG_ID";
    /**
     * 与上一个节点审批人同部门
     */
    public static final String LAST_TASK_DEPT_ID_KEY = "_LAST_TASK_DEPT_ORG_ID";
    /**
     * 与上一个节点审批人上级部门同部门
     */
    public static final String LAST_TASK_PARENT_DEPT_ID_KEY = "_LAST_TASK_PARENT_DEPT_ID";
    /**
     * 与上一个节点审批人下级部门同部门
     */
    public static final String LAST_TASK_CHILD_DEPT_ID_KEY = "_LAST_TASK_CHILD_DEPT_ID";

    /**
     * 由上一个节点指定人员(varibale key)
     */
    public static final String LAST_USER_KEY = "_LAST_USER_KEY";

    /**
     * 上一节点指定结构在当前任务存储的键值(varibale key)
     */
    public static final String LAST_ORG_ID_SAVE_IN_TASK_SCOPE_KEY = "_LAST_ORG_ID_SAVE_IN_TASK_SCOPE";
    /**
     * 申请人部门code(varibale key)
     */
    public static final String START_USER_DEPT_ID_KEY = "startDeptId";
    /**
     * 申请人机构Id(varibale key)
     */
    public static final String START_USER_ORG_ID_KEY = "startOrgId";
    /**
     * 申请人角色Id(varibale key)
     */
    public static final String START_USER_ROLE_ID_KEY = "startRoleId";
    /**
     * 申请人上级机构Id(varibale key)
     */
    public static final String START_USER_PARENT_ORG_ID_KEY = "_START_USER_PARENT_ORG_ID";

    /**
     * 交接人
     */
    public static final String TRANSFER_USER = "_TRANSFER_USER";
    
    /**
     * 历史节点id
     */
    public static final String LAST_HI_TASK_ID = "_LAST_HI_TASK_ID";
    
    /**
     * 未移交前处理人/候选用户
     */
    public static final String TASK_BEFORE_TRANSFER_ASSIGNEE = "_TASK_BEFORE_TRANSFER_ASSIGNEE";
    
    /**
     * 新版离岗
     */
    public static final String DIMISSION_TASK_BEFORE_TRANSFER_ASSIGNEE = "_DIMISSION_TASK_BEFORE_TRANSFER_ASSIGNEE";
    
    /**
     * 回退人节点id
     */
    public static final String TASK_HI_REBACK_ACTID = "_TASK_HI_REBACK_ACTID";

    /**
     * 与某个节点相同模式 chooseTaskPoint:chooseTaskPoint
     */
    public static final Object CHOOSE_TASK_POINT = "chooseTaskPoint";

    /**
     * 与选择的节点处理人相同 chooseTaskPoint:chooseHiUser
     */
    public static final String CHOOSE_HI_USER = "chooseHiUser";

//    /**
//     * 处理人
//     */
//    private String assignee;
//
//    /**
//     * 候选用户
//     */
//    private List<String> candidateUsers;
//
//    /**
//     * 候选角色
//     */
//    private List<String> candidateRoles;
//
//    /**
//     * 候选角色名称（只用于显示）
//     */
//    private List<String> candidateRoleNames;
//
//    /**
//     * 候选部门
//     */
//    private List<String> candidateDepts;
//
//    /**
//     * 候选部门名称（只用于显示）
//     */
//    private List<String> candidateDeptNames;
//
//    /**
//     * 候选机构
//     */
//    private List<String> candidateOrgs;
//
//    /**
//     * 候选机构（只用于显示）
//     */
//    private List<String> candidateOrgNames;
//
//    /**
//     * 下一节点处理人
//     */
//    private List<String> nextParticipates;
//
//    /**
//     * 规则入参键值对
//     */
//    private Map<String,Object> ruleParams;
//
//    /**
//     * 候选机构与候选角色的交集
//     */
//    private CandidateOrgsAndRoles candidateOrgsAndRoles;
//
//    /**
//     * 选人类型
//     * byAssignment:通过处理人、候选人、候选角色、候选部门、候选机构页面配置生成选人
//     * byLastTask: 由上一节点生成选人
//     * byRule: 由规则生成选人
//     */
//    private String selectUserModel;
//
//    /**
//     * 处理人配置类型（页面返显）
//     */
//    private String assigneeType;
//
//    /**
//     * 机构选择类型
//     */
//    private String orgSelectType;
//
//    /**
//     * 由上一个节点指定选人（逗号分隔的数组）
//     */
//    private String lastUsers;
//
//    /**
//     * 规则工程编号
//     */
//    private String ruleProjectCode;
//
//    /**
//     * 规则入口点编号
//     */
//    private String ruleEnterPoint;
//
//    /**
//     * 规则调用涉及的数据模型
//     */
//    private List<DataSet> ruleDataSets;
//
//    /**
//     * 上下文模式
//     */
//    private String founderSame;
//	/**
//	 * 流程定义ID
//	 */
//    private String procDefId;
//    /**
//	 * 上级机构ID
//	 */
//    private String parentOrgId;
    
//    /**
//     * 是否通过配置生成选人
//     * @return
//     */
//    public boolean isByAssignment(){
//        return "byAssignment".equals(selectUserModel);
//    }
//
//    /**
//     * 是否通过上一节点生成选人
//     * @return
//     */
//    public boolean isByLastTask(){
//        return "byLastTask".equals(selectUserModel);
//    }
//
//    /**
//     * 是否通过规则选人
//     * @return
//     */
//    public boolean isByRule(){
//        return "byRule".equals(selectUserModel);
//    }
//
//    public List<String> getCandidateOrgs() {
//        return candidateOrgs;
//    }
//
//    public void setCandidateOrgs(List<String> candidateOrgs) {
//        this.candidateOrgs = candidateOrgs;
//    }
//
//    public CandidateOrgsAndRoles getCandidateOrgsAndRoles() {
//        return candidateOrgsAndRoles;
//    }
//
//    public void setCandidateOrgsAndRoles(CandidateOrgsAndRoles candidateOrgsAndRoles) {
//        this.candidateOrgsAndRoles = candidateOrgsAndRoles;
//    }
//
//    public List<String> getCandidateRoles() {
//        return candidateRoles;
//    }
//
//    public void setCandidateRoles(List<String> candidateRoles) {
//        this.candidateRoles = candidateRoles;
//    }
//
//    public String getOrgSelectType() {
//        return orgSelectType;
//    }
//
//    public void setOrgSelectType(String orgSelectType) {
//        this.orgSelectType = orgSelectType;
//    }
//
//    public String getAssignee() {
//        return assignee;
//    }
//
//    public void setAssignee(String assignee) {
//        this.assignee = assignee;
//    }
//
//    public String getSelectUserModel() {
//        return selectUserModel;
//    }
//
//    public void setSelectUserModel(String selectUserModel) {
//        this.selectUserModel = selectUserModel;
//    }
//
//    public String getAssigneeType() {
//        return assigneeType;
//    }
//
//    public void setAssigneeType(String assigneeType) {
//        this.assigneeType = assigneeType;
//    }
//
//    public List<String> getCandidateUsers() {
//        return candidateUsers;
//    }
//
//    public void setCandidateUsers(List<String> candidateUsers) {
//        this.candidateUsers = candidateUsers;
//    }
//
//    public List<String> getCandidateDepts() {
//        return candidateDepts;
//    }
//
//    public void setCandidateDepts(List<String> candidateDepts) {
//        this.candidateDepts = candidateDepts;
//    }
//
//    public List<String> getCandidateRoleNames() {
//        return candidateRoleNames;
//    }
//
//    public void setCandidateRoleNames(List<String> candidateRoleNames) {
//        this.candidateRoleNames = candidateRoleNames;
//    }
//
//    public List<String> getCandidateDeptNames() {
//        return candidateDeptNames;
//    }
//
//    public void setCandidateDeptNames(List<String> candidateDeptNames) {
//        this.candidateDeptNames = candidateDeptNames;
//    }
//
//    public List<String> getCandidateOrgNames() {
//        return candidateOrgNames;
//    }
//
//    public void setCandidateOrgNames(List<String> candidateOrgNames) {
//        this.candidateOrgNames = candidateOrgNames;
//    }
//
//    public List<String> getNextParticipates() {
//        return nextParticipates;
//    }
//
//    public void setNextParticipates(List<String> nextParticipates) {
//        this.nextParticipates = nextParticipates;
//    }
//
//    public String getLastUsers() {
//        return lastUsers;
//    }
//
//    public void setLastUsers(String lastUsers) {
//        this.lastUsers = lastUsers;
//    }
//
//    public boolean lastUsersIsNotEmpty() {
//        return StringUtils.isNotEmpty(lastUsers);
//    }
//
//    public String getRuleProjectCode() {
//        return ruleProjectCode;
//    }
//
//    public void setRuleProjectCode(String ruleProjectCode) {
//        this.ruleProjectCode = ruleProjectCode;
//    }
//
//    public String getRuleEnterPoint() {
//        return ruleEnterPoint;
//    }
//
//    public void setRuleEnterPoint(String ruleEnterPoint) {
//        this.ruleEnterPoint = ruleEnterPoint;
//    }
//
//    public List<DataSet> getRuleDataSets() {
//        return ruleDataSets;
//    }
//
//    public void setRuleDataSets(List<DataSet> ruleDataSets) {
//        this.ruleDataSets = ruleDataSets;
//    }
//
//    public Map<String, Object> getRuleParams() {
//        return ruleParams;
//    }
//
//    public void setRuleParams(Map<String, Object> ruleParams) {
//        this.ruleParams = ruleParams;
//    }
//
//	public String getFounderSame() {
//		return founderSame;
//	}
//
//	public void setFounderSame(String founderSame) {
//		this.founderSame = founderSame;
//	}
//
//	public String getProcDefId() {
//		return procDefId;
//	}
//
//	public void setProcDefId(String procDefId) {
//		this.procDefId = procDefId;
//	}
//
//	public String getParentOrgId() {
//		return parentOrgId;
//	}
//
//	public void setParentOrgId(String parentOrgId) {
//		this.parentOrgId = parentOrgId;
//	}


    
}
