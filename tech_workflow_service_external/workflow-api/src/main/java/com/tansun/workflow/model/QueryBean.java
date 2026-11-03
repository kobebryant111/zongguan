package com.tansun.workflow.model;

import java.util.List;
import java.util.Map;

public class QueryBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  List<Map<String,Object>>  resList;

	public List<Map<String, Object>> getResList() {
		return resList;
	}

	public void setResList(List<Map<String, Object>> resList) {
		this.resList = resList;
	}

	
	

}
