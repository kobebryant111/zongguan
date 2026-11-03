package com.tansun.wf.activiti.query;


import com.tansun.wf.activiti.model.TaskExpandPlanEntity;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.ManagementServiceImpl;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import java.util.List;

public class WfExpandPlanQuery extends AbstractQuery<WfExpandPlanQuery,TaskExpandPlanEntity> {

    private String wfClassId;

    private String wfClassCode;

    private String fieldKey;
    
    
    public  WfExpandPlanQuery() {
      
    }
    public  WfExpandPlanQuery(ManagementService  managementService) {
    	this.setCommandExecutor(((ManagementServiceImpl)managementService).getCommandExecutor());
    	
    }
    
    @Override
    public long executeCount(CommandContext commandContext) {
        return (Long) commandContext.getDbSqlSession()
                .selectOne("selectWfTaskExpandPlanCountByQueryCriteria", this);
    }

    /**
     * Executes the actual query to retrieve the list of results.
     *
     * @param commandContext
     * @param page           used if the results must be paged. If null, no paging will be applied.
     */
    @Override
    public List<TaskExpandPlanEntity> executeList(CommandContext commandContext, Page page) {
        String databaseType = commandContext.getDbSqlSession().getDbSqlSessionFactory().getDatabaseType();
        this.setDatabaseType(databaseType);
        return commandContext.getDbSqlSession().getSqlSession()
                .selectList("selectWfTaskExpandPlanByQueryCriteria", this);
    }



    public WfExpandPlanQuery wfClassId(String wfClassId) {
        this.wfClassId = wfClassId;
        return this;
    }

    public WfExpandPlanQuery wfClassCode(String wfClassCode) {
        this.wfClassCode = wfClassCode;
        return this;
    }
    public WfExpandPlanQuery fieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
        return this;
    }
}
