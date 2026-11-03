package com.tansun.workflow.model;

public class ExternalUser extends BaseBean{
	private String id;       // id
	private String loginName;// 账号
	private String name;     // 用户名称（如:张三，李四 用于展示）
	private String loginFlag;	 // 是否可用 1是 0否
	private String flag; 	 // 是否删除 1是0否
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLoginFlag() {
		return loginFlag;
	}
	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}
	
	
}
