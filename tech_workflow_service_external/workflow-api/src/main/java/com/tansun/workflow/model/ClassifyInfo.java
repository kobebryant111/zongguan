package com.tansun.workflow.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 流程分类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassifyInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3541437161510802586L;
	private String id;
	private String parent_id;		
	private String parent_ids;		
	private String name;		
	private String sort;		
	private String code;
	private String type;		
	private String USEABLE;		
	private String create_by;		
	private String create_date;
	private String update_by;		
	private String update_date;		
	private String remarks;		
	private String del_flag;
	private String tenantId;
	
	private String   currentPage;//当前页码
	private String   pageSize;//每页数据大小
	private String   total;


	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getParent_ids() {
		return parent_ids;
	}
	public void setParent_ids(String parent_ids) {
		this.parent_ids = parent_ids;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUSEABLE() {
		return USEABLE;
	}
	public void setUSEABLE(String uSEABLE) {
		USEABLE = uSEABLE;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}

	

}
