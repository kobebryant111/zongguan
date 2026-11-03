package com.tansun.wf.activiti.query;


import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import java.util.List;
import java.util.Map;


/**
 * 历史任务查询
 */
@SuppressWarnings({"unused","rawtypes","unchecked", "serial"})
public class WfHiTaskCountsQuery extends AbstractQuery<WfHiTaskCountsQuery,Map> {

    /**
     * 处理人id
     */
    private List<String> assignees;
    /**
     * 已办去重状态（1去重非1或不传为不去重）
     */
    private String singleHiTaskStatus;
    /**
     * 未结和已结状态(包括待办)
     */
    private String isEndIncludeRuTask;
    
    private  String  tenantId;

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
                    .selectList("selectWfHiTaskCountByUser", this);

    }

    public WfHiTaskCountsQuery assignees(List<String> assignees) {
        this.assignees = assignees;
        return this;
    }
    public WfHiTaskCountsQuery singleHiTaskStatus(String singleHiTaskStatus) {
        this.singleHiTaskStatus = singleHiTaskStatus;
        return this;
    }
    public WfHiTaskCountsQuery isEndIncludeRuTask(String isEndIncludeRuTask) {
        this.isEndIncludeRuTask = isEndIncludeRuTask;
        return this;
    }
    
    public WfHiTaskCountsQuery tenantId(String tenantId) {
    	this.tenantId = tenantId;
    	return  this;
    }
    
    
}
