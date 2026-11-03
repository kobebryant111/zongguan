package com.tansun.workflow.api;

import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;

/**
 * 编辑器  如机构、部门、用户、角色列表等接口类
 * @author chenyinan
 */
public interface IWorkflowEditorService {

	String SUCCEED = "0";
	String FAILED = "1";

    /**
     * 获取机构列表
     *  入参
     *    1. orgCode 机构编号
     *    2. orgName 机构名称
     *    3. currentPage 当前页数
     *    4. pageSize 页数大小
     *
     *  返回值 orgList
     *    1.pId 父机构id
     *    2.id 当前机构id
     *    3.name 机构名称
     *    4.orgCode 机构编码
     *    5.orgName 机构名称
     */
	@Deprecated
	EditorBean findOrganizationList(EditorInfo editorInfo);

    /**
     * 获取部门列表
     *  入参
     *    1. deptCode 部门编号
     *    2. deptName 部门名称
     *    3. currentPage 当前页数
     *    4. pageSize 页数大小
     *
     *  返回值 deptList
	 *    1.pId 父部门id
     *    2.Id 部门id
	 *    3.name 部门名称
     *    4.deptCode 部门编号
     *    5.deptName 部门名称
     */
	@Deprecated
	EditorBean findDepartmentList(EditorInfo editorInfo);
	
	/**
	 * 获取用户列表
     *    1. deptCodes 部门编号
     *    2. orgCodes 机构编号
     *    3. roleCodes 角色编号
	 *    4. userName 用户名称
	 *    5. userLoginNames 用户登录名(list)
	 *    6. userLoginName 用户登录名
     *    7. currentPage 当前页数
     *    8. pageSize 页数大小
     *
     *  返回值 userList
     *    1.userId 部门id
     *    2.userName 部门编号
     *    3.userLoginName 部门名称
     *    4.orgName 部门名称
     *    5.deptName 部门名称
	 *    6.email 邮箱
	 */
	EditorBean findUsersList(EditorInfo editorInfo);

     /**
     * 获取角色列表
     *    1. roleCode 角色编号
     *    2. roleName 角色名称
     *    3. currentPage 当前页数
     *    4. pageSize 页数大小
     *
     *  返回值 roleList
     *    1.roleCode 角色编号
     *    2.roleName 角色名称
	 */
    @Deprecated
	EditorBean findRoleList(EditorInfo editorInfo);

	/**
	 * 参与者列表数据来源
	 */
	EditorBean findUserOrgRoleList(EditorInfo editorInfo);

	/**
	 * 根据userId查询该用户下的所有角色，机构，岗位信息
	 * @param editorInfo
	 * @return
	 */
	EditorBean findAllPostInfo(EditorInfo editorInfo);
}
