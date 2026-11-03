package com.tansun.workflow.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditorBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6245008777685141034L;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 用户登陆名称
	 */
	private String userLoginName;
	
	/**
	 * 部门id
	 */
	private String deptId;
	/**
	 * 部门code
	 */
	private String deptCode;

	/**
	 * 部门名称
	 */
	private String deptName;
	
	/**
	 * 机构id
	 */
	private String orgId;
	/**
	 * 机构code
	 */
	private String orgCode;

	/**
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 机构等级
	 */
	private String orgGrade;
	
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 角色编号
	 */
	private String roleCode;

	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 角色列表
	 */
	private List<Map<String,Object>> roleList;
	
	/**
	 * 机构列表
	 */
	private List<Map<String,Object>> orgList;
	
	/**
	 * 部门列表
	 */
	private List<Map<String,Object>> deptList;
	
	/**
	 * 用户列表
	 */
	private List<Map<String,Object>> userList;

	/**
	 * 用户所拥有的角色，机构，岗位等信息
	 */
	private List<PostInfo> postInfos;
	
	/**
	 * 总条数
	 */
	private long total;
	
	/**
	 * 当前页数
	 */
	private int currentPageIndex;
	/**
	 * 是否有下一页
	 */
	private boolean hasNextPage;

	private List<UserOrgRoleInfo> userOrgRoleTree;

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	private List<Map<String, Object>> resultList;
	
	private Object resultObject;
    private List designList;

    public EditorBean() {
		super();
	}

	public EditorBean(String retCode, String retMsg) {
		super(retCode, retMsg);
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

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public List<Map<String, Object>> getUserList() {
		return userList;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public List<Map<String, Object>> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Map<String, Object>> roleList) {
		this.roleList = roleList;
	}

	public List<Map<String, Object>> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Map<String, Object>> orgList) {
		this.orgList = orgList;
	}

	public List<Map<String, Object>> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Map<String, Object>> deptList) {
		this.deptList = deptList;
	}

	public void setUserList(List<Map<String, Object>> userList) {
		this.userList = userList;
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

    public void setDesignList(List designList) {
        this.designList = designList;
    }

    public List getDesignList() {
        return designList;
    }

	public List<UserOrgRoleInfo> getUserOrgRoleTree() {
		return userOrgRoleTree;
	}

	public void setUserOrgRoleTree(List<UserOrgRoleInfo> userOrgRoleTree) {
		this.userOrgRoleTree = userOrgRoleTree;
	}

	public List<PostInfo> getPostInfos() {
		return postInfos;
	}

	public void setPostInfos(List<PostInfo> postInfos) {
		this.postInfos = postInfos;
	}
}
