package com.tansun.wf.extend.db;

import java.io.Serializable;

/**
 * 工作流基础实体类
 */
public abstract class WfBaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
