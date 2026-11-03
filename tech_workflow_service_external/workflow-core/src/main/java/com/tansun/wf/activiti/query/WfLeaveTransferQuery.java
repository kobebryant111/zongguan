package com.tansun.wf.activiti.query;


import com.tansun.wf.activiti.model.LeaveTransferEntity;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import java.util.Date;
import java.util.List;

@SuppressWarnings({ "unused", "serial", "unchecked" })
public class WfLeaveTransferQuery extends AbstractQuery<WfLeaveTransferQuery,LeaveTransferEntity> {


    
	private String userId;
    
	private String id;
    
	private Date time;
    
	private String classifyId;
	
	private String procInstId;
    
	private String startStatus;
	
	private String transferId;

    private String status;

    private String setStatus;

    private String[] ids;

    @Override
    public long executeCount(CommandContext commandContext) {
        return (Long) commandContext.getDbSqlSession()
                .selectOne("selectWfLeaveTransferCountByQueryCriteria", this);
    }

    /**
     * Executes the actual query to retrieve the list of results.
     *
     * @param commandContext
     * @param page           used if the results must be paged. If null, no paging will be applied.
     */
	@Override
    public List<LeaveTransferEntity> executeList(CommandContext commandContext, Page page) {
        String databaseType = commandContext.getDbSqlSession().getDbSqlSessionFactory().getDatabaseType();
        this.setDatabaseType(databaseType);
        return commandContext.getDbSqlSession().getSqlSession()
                .selectList("selectWfLeaveTransferByQueryCriteria", this);
    }

    public WfLeaveTransferQuery userId(String userId) {
        this.userId = userId;
        return this;
    }
    
    public WfLeaveTransferQuery id(String id) {
    	this.id = id;
        return this;
    }
    
    public WfLeaveTransferQuery time(Date time) {
    	this.time = time;
        return this;
    }
    
    public WfLeaveTransferQuery classifyId(String classifyId) {
    	this.classifyId = classifyId;
        return this;
    }
    
    public WfLeaveTransferQuery procInstId(String procInstId) {
    	this.procInstId = procInstId;
        return this;
    }
    
    public WfLeaveTransferQuery startStatus(String startStatus) {
    	this.startStatus = startStatus;
        return this;
    }
    
    public WfLeaveTransferQuery transferId(String transferId) {
        this.transferId = transferId;
        return this;
    }

    public WfLeaveTransferQuery status(String status) {
        this.status = status;
        return  this;
    }

    public WfLeaveTransferQuery setStatus(String setStatus) {
        this.setStatus = setStatus;
        return this;
    }

    public WfLeaveTransferQuery ids(String[] ids) {
        this.ids = ids;
        return this;
    }
}
