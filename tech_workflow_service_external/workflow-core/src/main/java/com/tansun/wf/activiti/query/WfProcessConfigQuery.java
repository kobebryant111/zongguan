package com.tansun.wf.activiti.query;


import java.util.List;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import com.tansun.wf.activiti.model.ProcessConfig;

public class WfProcessConfigQuery extends AbstractQuery<WfProcessConfigQuery,ProcessConfig> {


    private String procDefId;
    
    private String actId;
    
    private String processStatus;

    @Override
    public long executeCount(CommandContext commandContext) {
        return (Long) commandContext.getDbSqlSession()
                .selectOne("selectWfProcessConfigCountByQueryCriteria", this);
    }

    /**
     * Executes the actual query to retrieve the list of results.
     *
     * @param commandContext
     * @param page           used if the results must be paged. If null, no paging will be applied.
     */
    @Override
    public List<ProcessConfig> executeList(CommandContext commandContext, Page page) {
        String databaseType = commandContext.getDbSqlSession().getDbSqlSessionFactory().getDatabaseType();
        this.setDatabaseType(databaseType);
        return commandContext.getDbSqlSession().getSqlSession()
                .selectList("selectWfProcessConfigByQueryCriteria", this);
    }


    public WfProcessConfigQuery procDefId(String procDefId) {
        this.procDefId = procDefId;
        return this;
    }
    
    public WfProcessConfigQuery actId(String actId) {
        this.actId = actId;
        return this;
    }
    
    public WfProcessConfigQuery processStatus(String processStatus) {
        this.processStatus = processStatus;
        return this;
    }
}
