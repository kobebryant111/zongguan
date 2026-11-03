package com.tansun.wf.platform.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tansun.wf.extend.db.WfBaseEntity;

import java.util.Date;

/**
 * 租户Entity
 * @author zhuofushen
 */

public class WfTenantEntity extends WfBaseEntity {

	private static final long serialVersionUID = 6295775736043286016L;
	private String tenantId;
	private String name;
	private String createBy;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	private String updateBy;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	private String remarks;
	private String delFlag;


    public WfTenantEntity() {
        super();
    }

	public String getTenantId() {
		return tenantId;
	}


	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCreateBy() {
		return createBy;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}


	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	
}