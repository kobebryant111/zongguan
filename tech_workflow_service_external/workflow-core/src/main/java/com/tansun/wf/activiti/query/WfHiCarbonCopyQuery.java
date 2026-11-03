package com.tansun.wf.activiti.query;


import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import java.util.List;
import java.util.Map;

public class WfHiCarbonCopyQuery extends AbstractQuery<WfHiCarbonCopyQuery,Map> {


    private String userId;

    private String procInstId;

    private String taskId;

    // camunda-TODO
//    public WfHiCarbonCopyQuery(ManagementService managementService) {
//        super(managementService);
//    }

    @Override
    public long executeCount(CommandContext commandContext) {
        return (Long) commandContext.getDbSqlSession()
                .selectOne("selectWfHiCarbonCopyCountByQueryCriteria", this);
    }

    /**
     * Executes the actual query to retrieve the list of results.
     *
     * @param commandContext
     * @param page           used if the results must be paged. If null, no paging will be applied.
     */
    @Override
    public List<Map> executeList(CommandContext commandContext, Page page) {

        String databaseType = commandContext.getDbSqlSession().getDbSqlSessionFactory().getDatabaseType();
        this.setDatabaseType(databaseType);
        // camunda-TODO
        return commandContext.getDbSqlSession()
                .getSqlSession().selectList("selectWfHiCarbonCopyByQueryCriteria", this);
    }


    public WfHiCarbonCopyQuery procInstId(String procInstId) {
        this.procInstId = procInstId;
        return this;
    }
    
    public WfHiCarbonCopyQuery taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public WfHiCarbonCopyQuery userId(String userId) {
        this.userId = userId;
        return this;
    }
}
