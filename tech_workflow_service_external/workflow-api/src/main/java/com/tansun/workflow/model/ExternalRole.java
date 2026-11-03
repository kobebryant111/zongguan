package com.tansun.workflow.model;

public class ExternalRole extends BaseBean {
	
	private String id;// 角色id
	private String name; 	// 角色名称
	private String useable; 		//是否是可用/ 是1/否0
	private String flag;    // 是否已删0否1是

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}
