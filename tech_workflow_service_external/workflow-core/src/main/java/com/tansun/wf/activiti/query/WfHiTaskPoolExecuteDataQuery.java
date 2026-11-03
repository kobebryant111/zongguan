package com.tansun.wf.activiti.query;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import com.tansun.wf.activiti.model.HiTaskPoolExecuteDataEntity;

@SuppressWarnings({ "serial", "unchecked", "unused" })
public class WfHiTaskPoolExecuteDataQuery extends AbstractQuery<WfHiTaskPoolExecuteDataQuery,Map<String,Object>> {

	private String taskId;
	private String sysId;
	private String userId;
	private String isAllot;
	private Date createDate;
	private String tenantId;
	private List<String> fieldMapping;
	private List<String> fieldCondition;

    @Override
    public long executeCount(CommandContext commandContext) {
        return (Long) commandContext.getDbSqlSession()
                .selectOne("selectWfHiTaskPoolExecuteDataCountByQueryCriteria", this);
    }

    /**
     * Executes the actual query to retrieve the list of results.
     *
     * @param commandContext
     * @param page           used if the results must be paged. If null, no paging will be applied.
     */
	@Override
    public List executeList(CommandContext commandContext, Page page) {
    	String databaseType = commandContext.getDbSqlSession().getDbSqlSessionFactory().getDatabaseType();
    	this.setDatabaseType(databaseType);
        return commandContext.getDbSqlSession()
                .selectList("selectWfHiTaskPoolExecuteDataByQueryCriteria", this);
    }

	public WfHiTaskPoolExecuteDataQuery taskId(String taskId){
		this.taskId = taskId;
		return this;
	}
	
	public WfHiTaskPoolExecuteDataQuery sysId(String sysId){
		this.sysId = sysId;
		return this;
	}
	
	public WfHiTaskPoolExecuteDataQuery userId(String userId){
		this.userId = userId;
		return this;
	}
	
	public WfHiTaskPoolExecuteDataQuery isAllot(String isAllot){
		this.isAllot = isAllot;
		return this;
	}
	
	public WfHiTaskPoolExecuteDataQuery tenantId(String tenantId){
		this.tenantId = tenantId;
		return this;
	}
	
	public WfHiTaskPoolExecuteDataQuery fieldMapping(List<String> fieldMapping){
		this.fieldMapping = fieldMapping;
		return this;
	}

	public WfHiTaskPoolExecuteDataQuery fieldCondition(List<String> fieldCondition){
		this.fieldCondition = fieldCondition;
		return this;
	}

}
