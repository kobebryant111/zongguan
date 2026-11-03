package com.tansun.wf.activiti.query;


import java.util.List;

import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import com.tansun.wf.activiti.model.ClassifyEntity;

@SuppressWarnings({ "unused", "serial", "unchecked" })
public class WfClassifyPendQuery extends AbstractQuery<WfClassifyPendQuery,ClassifyEntity> {


    private String code;
    
    private String name;
    
    private String userId;
    
    private String taskPoolStatus;

    private String createTimeAfter;

    private String createTimeBefore;

    // camunda-TODO
//    public WfClassifyPendQuery(ManagementService managementService) {
//        super(managementService);
//    }

    @Override
    public long executeCount(CommandContext commandContext) {
        String databaseType = commandContext.getDbSqlSession().getDbSqlSessionFactory().getDatabaseType();
        this.setDatabaseType(databaseType);
        return (Long) commandContext.getDbSqlSession()
                .selectOne("selectWfClassifyPendCountByQueryCriteria", this);
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
                .selectList("selectWfClassifyPendByQueryCriteria", this);
    }


    public WfClassifyPendQuery code(String code) {
        this.code = code;
        return this;
    }
    
    public WfClassifyPendQuery name(String name) {
        this.name = name;
        return this;
    }
    
    public WfClassifyPendQuery userId(String userId) {
        this.userId = userId;
        return this;
    }
    
    public WfClassifyPendQuery taskPoolStatus(String taskPoolStatus) {
        this.taskPoolStatus = taskPoolStatus;
        return this;
    }

    public WfClassifyPendQuery createTimeAfter(String createTimeAfter) {
        this.createTimeAfter = createTimeAfter;
        return this;
    }

    public WfClassifyPendQuery createTimeBefore(String createTimeBefore) {
        this.createTimeBefore = createTimeBefore;
        return this;
    }
}
