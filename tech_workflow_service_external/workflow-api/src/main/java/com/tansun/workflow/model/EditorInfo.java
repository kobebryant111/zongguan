package com.tansun.workflow.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EditorInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tenantId;//租户id
	private String deptId;//部门id
	private String deptCode;//部门编号
	private List<String> deptCodes;//部门编号
	private String deptName;//部门名称
	private String orgName;//机构名称
	private String orgId;//机构id
	private String orgGrade;//机构等级
	private String orgCode;//机构编号
	private List<String> orgCodes;//机构编号
	private String roleId;//角色id
	private String roleName;//角色名称
	private String roleCode;//角色编号
    private List<String> roleCodes;//角色编号
    private List<String> userLoginNames;//用户登录名
	private String userId;//用户id
	private String userCode;//用户编号
	private String userName;//用户名称
	private String userLoginName;//用户登陆名称
	private String userNameLike;//用户名称模糊查询
	private String userLoginNameLike;//用户登陆名称模糊查询
	//设置默认分页数，1，1000 ，如果每页数超过1000，业务不合理。
	private int   currentPage =1;//当前页码
	private int   pageSize = 1000;//每页数据大小
	private String   designKey;//流程设计key
	private String   designName;//流程设计name
	private String   delInd;//删除标志
	private String   state;//状态标志，0 不过滤，1 生效， 2 失效
	private Map<String,Object> editorVariables;//issue #228 贵州银行，任务池查询用户需要传递TOKEN，STRIPE_TYPE
	private String customRule;//自定义规则
	private String processInstanceId;//流程实例ID
	private String groupId;//其他用户类型的id
	private String type;
	
	private List<PostInfo>  postInfos;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getCustomRule() {
		return customRule;
	}

	public void setCustomRule(String customRule) {
		this.customRule = customRule;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgGrade() {
		return orgGrade;
	}
	public void setOrgGrade(String orgGrade) {
		this.orgGrade = orgGrade;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

    public List<String> getDeptCodes() {
        return deptCodes;
    }

    public void setDeptCodes(List<String> deptCodes) {
        this.deptCodes = deptCodes;
    }

    public List<String> getOrgCodes() {
        return orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDesignKey() {
        return designKey;
    }

    public void setDesignKey(String designKey) {
        this.designKey = designKey;
    }

    public String getDesignName() {
        return designName;
    }

    public void setDesignName(String designName) {
        this.designName = designName;
    }

	public List<String> getUserLoginNames() {
		return userLoginNames;
	}

	public void setUserLoginNames(List<String> userLoginNames) {
		this.userLoginNames = userLoginNames;
	}

	public String getUserNameLike() {
		return userNameLike;
	}

	public void setUserNameLike(String userNameLike) {
		this.userNameLike = userNameLike;
	}

	public String getUserLoginNameLike() {
		return userLoginNameLike;
	}

	public void setUserLoginNameLike(String userLoginNameLike) {
		this.userLoginNameLike = userLoginNameLike;
	}

	public String getDelInd() {
		return delInd;
	}

	public void setDelInd(String delInd) {
		this.delInd = delInd;
	}

	public Map<String, Object> getEditorVariables() {
		return editorVariables;
	}

	public void setEditorVariables(Map<String, Object> editorVariables) {
		this.editorVariables = editorVariables;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<PostInfo> getPostInfos() {
		return postInfos;
	}

	public void setPostInfos(List<PostInfo> postInfos) {
		this.postInfos = postInfos;
	}
	
	
}
