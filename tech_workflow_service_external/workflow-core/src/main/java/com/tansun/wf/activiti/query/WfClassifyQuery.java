package com.tansun.wf.activiti.query;


import java.util.List;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import com.tansun.wf.activiti.model.ClassifyEntity;

@SuppressWarnings({ "serial", "unchecked" })
public class WfClassifyQuery extends AbstractQuery<WfClassifyQuery,ClassifyEntity> {

	private String code;
    
    private String name;
    
    private String id;
    
    protected String databaseType;
    
	private String parentId;		
	private String parentIds;		
	private String sort;		
	private String type;		
	private String USEABLE;			
	private String remarks;		
	private String delFlag;
	private String tenantId;

    @Override
    public long executeCount(CommandContext commandContext) {

        return (Long) commandContext.getDbSqlSession()
                .selectOne("selectWfClassifyCountByQueryCriteria", this);
    }

    /**
     * Executes the actual query to retrieve the list of results.
     *
     * @param commandContext
     * @param page           used if the results must be paged. If null, no paging will be applied.
     */
    @Override
    public List<ClassifyEntity> executeList(CommandContext commandContext, Page page) {
    	String databaseType = commandContext.getDbSqlSession().getDbSqlSessionFactory().getDatabaseType();
    	this.setDatabaseType(databaseType);
        return commandContext.getDbSqlSession().getSqlSession()
                .selectList("selectWfClassifyByQueryCriteria", this);


    }


    public WfClassifyQuery code(String code) {
        this.code = code;
        return this;
    }
    
    public WfClassifyQuery name(String name) {
        this.name = name;
        return this;
    }
    
    public WfClassifyQuery parentIds(String parentIds) {
    	this.parentIds = parentIds;
        return this;
    }
    
    public WfClassifyQuery databaseType(String databaseType) {
        this.databaseType = databaseType;
        return this;
    }
    
    public WfClassifyQuery id(String id) {
    	this.id = id;
        return this;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

    
}
