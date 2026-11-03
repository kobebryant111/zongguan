package com.tansun.workflow.api;

import com.tansun.workflow.model.ExternalTable;

public interface IWorkflowSynchroDataService {
	
	/**
	 * 获取角色菜单权限关联列表
	 * @param
	 * @return
	 */
	public ExternalTable findRoleMenuList();
	
	/**
	 * 获取用户角色关联列表
	 * @param
	 * @return
	 */
	public ExternalTable findUserRoleList();
	
	/**
	 * 获取用户列表
	 * @param
	 * @return
	 */
	public ExternalTable findUserList();
	
	/**
	 * 获取角色列表
	 * @param
	 * @return
	 */
	public ExternalTable findRoleList();
}
