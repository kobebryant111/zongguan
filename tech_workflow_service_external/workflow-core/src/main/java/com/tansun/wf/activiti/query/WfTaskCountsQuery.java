package com.tansun.wf.activiti.query;


import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;


/**
 * 历史任务查询
 */
@SuppressWarnings({"unused","rawtypes","unchecked", "serial"})
public class WfTaskCountsQuery extends AbstractQuery<WfTaskCountsQuery,Map> {

    /**
     * 任务id
     */
    private List<String> assignees;
    
    
    private   String   tenantId;

    @Override
    public long executeCount(CommandContext commandContext) {
        String databaseType = commandContext.getDbSqlSession().getDbSqlSessionFactory().getDatabaseType();
        this.setDatabaseType(databaseType);
        return (Long) commandContext.getDbSqlSession()
                    .selectOne("selectWfTasksCountTotalByUser", this);

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
                    .selectList("selectWfTasksCountByUser", this);

    }


    public WfTaskCountsQuery assignees(List<String> assignees) {
        this.assignees = assignees;
        return this;
    }
    
    public WfTaskCountsQuery tenantId(String tenantId) {
    	this.tenantId  = tenantId;
    	return  this;
    }
}
