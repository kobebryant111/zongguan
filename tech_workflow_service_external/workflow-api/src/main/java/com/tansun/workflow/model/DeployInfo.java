package com.tansun.workflow.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.Serializable;

public class DeployInfo implements Serializable {

	private static final long serialVersionUID = 3046058476239646786L;

	private String fileName;
	
	private InputStream inputStream;
	
	private  String name;
	
	private  String tenantId;

	private String key;
	
	private String category;
	
	private String duplicateFilter = "N";


	public String getName() {
		return name;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getCategory() {
		return category;
	}

	public String getFileName() {
		return fileName;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDuplicateFilter() {
		return duplicateFilter;
	}

	public void setDuplicateFilter(String duplicateFilter) {
		this.duplicateFilter = duplicateFilter;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
