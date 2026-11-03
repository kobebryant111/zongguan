package com.tansun.wf.activiti.model;

import java.io.Serializable;
import java.util.List;

import com.tansun.workflow.model.PostInfo;

public class SelectUserMode implements Serializable {

	private static final long serialVersionUID = -868527363815353521L;
	
	/**
	 * 0:单人;1：单人或者多人;2:用户组
	 */
	private  String   type;

    /**
     * 处理人
     */
    private String assignee;
    
    /**
     * 处理人机构
     */
    private String  orgCode;
    /**
     * 处理人角色
     */
    
    private  String  roleCode;
    
    /**
         * 迭代变量名称
     */
    private  String iterateElement; 

    /**
     * 选人类型
     * byAssignment:通过处理人、候选人、候选角色、候选部门、候选机构页面配置生成选人
     * byLastTask: 由上一节点生成选人
     * byRule: 由规则生成选人
     */
    private String selectUserModel;

	private  List<String>  userList;
	
	private  List<String>  groupList;
	
	private  String   taskId;
	
	private  String processDefineKey;
	/**
	 * 是否选多人   true :会签,false :单签
	 */
	private  boolean     seleMulUserInd;
	
	/**
	 * 参与者列表
	 */
	private  List<PostInfo>  postInfos;
	/**
	 * 下一活动ID
	 */
	private  String   nextActId;
	
	/**
	 * 下一活动名称
	 */
	private  String   nextActName;
	
	private List<SelectUserMode> selectUserModes;
	private String isInSubProcess;


	//自定义规则
	private String customRule;
	// 流程上下文，与某节点：与选择的节点同样的选人方式、与选择节点的最后审批任务的处理人相同
	private String chooseTaskPoint;
	// 选择的节点id
	private List<String> taskPointInfo;

	private String startOrgId;
	private String startRoleId;
	private String startDeptId;
	private String startParentOrgId;	
	private  String  startUserId;

	private boolean allowPreAct;
	private String parRadioConfig;
	private String aboutData;
	private String aboutDataKey;


	public String getCustomRule() {
		return customRule;
	}

	public void setCustomRule(String customRule) {
		this.customRule = customRule;
	}

	public List<SelectUserMode> getSelectUserModes() {
		return selectUserModes;
	}


	public void setSelectUserModes(List<SelectUserMode> selectUserModes) {
		this.selectUserModes = selectUserModes;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public List<String> getUserList() {
		return userList;
	}


	public void setUserList(List<String> userList) {
		this.userList = userList;
	}


	public List<String> getGroupList() {
		return groupList;
	}


	public void setGroupList(List<String> groupList) {
		this.groupList = groupList;
	}


	public String getNextActId() {
		return nextActId;
	}


	public void setNextActId(String nextActId) {
		this.nextActId = nextActId;
	}


	public String getNextActName() {
		return nextActName;
	}


	public void setNextActName(String nextActName) {
		this.nextActName = nextActName;
	}


	public boolean isSeleMulUserInd() {
		return seleMulUserInd;
	}


	public void setSeleMulUserInd(boolean seleMulUserInd) {
		this.seleMulUserInd = seleMulUserInd;
	}


	public String getProcessDefineKey() {
		return processDefineKey;
	}


	public void setProcessDefineKey(String processDefineKey) {
		this.processDefineKey = processDefineKey;
	}


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

   

    public String getSelectUserModel() {
        return selectUserModel;
    }

    public void setSelectUserModel(String selectUserModel) {
        this.selectUserModel = selectUserModel;
    }

    public void setIsInSubProcess(String isInSubProcess) {
        this.isInSubProcess = isInSubProcess;
    }

    public String getIsInSubProcess() {
        return isInSubProcess;
    }

	
	public String getChooseTaskPoint() {
		return chooseTaskPoint;
	}

	public void setChooseTaskPoint(String chooseTaskPoint) {
		this.chooseTaskPoint = chooseTaskPoint;
	}

	
	public List<String> getTaskPointInfo() {
		return taskPointInfo;
	}

	public void setTaskPointInfo(List<String> taskPointInfo) {
		this.taskPointInfo = taskPointInfo;
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

	public String getStartDeptId() {
		return startDeptId;
	}

	public void setStartDeptId(String startDeptId) {
		this.startDeptId = startDeptId;
	}

	public String getStartParentOrgId() {
		return startParentOrgId;
	}

	public void setStartParentOrgId(String startParentOrgId) {
		this.startParentOrgId = startParentOrgId;
	}

	public List<PostInfo> getPostInfos() {
		return postInfos;
	}

	public void setPostInfos(List<PostInfo> postInfos) {
		this.postInfos = postInfos;
	}

	public String getIterateElement() {
		return iterateElement;
	}

	public void setIterateElement(String iterateElement) {
		this.iterateElement = iterateElement;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public boolean isAllowPreAct() {
		return allowPreAct;
	}

	public void setAllowPreAct(boolean allowPreAct) {
		this.allowPreAct = allowPreAct;
	}

	public String getParRadioConfig() {
		return parRadioConfig;
	}

	public void setParRadioConfig(String parRadioConfig) {
		this.parRadioConfig = parRadioConfig;
	}

	public String getAboutData() {
		return aboutData;
	}

	public void setAboutData(String aboutData) {
		this.aboutData = aboutData;
	}

	public String getAboutDataKey() {
		return aboutDataKey;
	}

	public void setAboutDataKey(String aboutDataKey) {
		this.aboutDataKey = aboutDataKey;
	}
}
