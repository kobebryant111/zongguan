package com.tansun.wf.activiti.query;


import java.util.List;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import com.tansun.wf.activiti.model.ClassifyEntity;

@SuppressWarnings({ "unused", "serial", "unchecked" })
public class WfClassifyCompletedQuery extends AbstractQuery<WfClassifyCompletedQuery,ClassifyEntity> {


    private String code;
    
    private String name;
    
    private String userId;
    
    private String isTerminateProcess;
    
    private String isEnd;
    
    private String singleHiTaskStatus;

    private String createTimeAfter;

    private String createTimeBefore;

    // camunda-TODO camunda中没有此构造器，需要的话所有Query都要改
//    public WfClassifyCompletedQuery(ManagementService managementService) {
//        super(managementService);
//    }

    @Override
    public long executeCount(CommandContext commandContext) {
        String databaseType = commandContext.getDbSqlSession().getDbSqlSessionFactory().getDatabaseType();
        this.setDatabaseType(databaseType);
        return (Long) commandContext.getDbSqlSession().getSqlSession()
                .selectOne("selectWfClassifyCompletedCountByQueryCriteria", this);
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
                .selectList("selectWfClassifyCompletedByQueryCriteria", this);
    }


    public WfClassifyCompletedQuery code(String code) {
        this.code = code;
        return this;
    }
    
    public WfClassifyCompletedQuery name(String name) {
        this.name = name;
        return this;
    }
    
    public WfClassifyCompletedQuery userId(String userId) {
        this.userId = userId;
        return this;
    }
    
    public WfClassifyCompletedQuery isTerminateProcess(String isTerminateProcess) {
        this.isTerminateProcess = isTerminateProcess;
        return this;
    }
    
    public WfClassifyCompletedQuery isEnd(String isEnd) {
        this.isEnd = isEnd;
        return this;
    }
    
    public WfClassifyCompletedQuery singleHiTaskStatus(String singleHiTaskStatus) {
        this.singleHiTaskStatus = singleHiTaskStatus;
        return this;
    }

    public WfClassifyCompletedQuery createTimeAfter(String createTimeAfter) {
        this.createTimeAfter = createTimeAfter;
        return this;
    }

    public WfClassifyCompletedQuery createTimeBefore(String createTimeBefore) {
        this.createTimeBefore = createTimeBefore;
        return this;
    }
}
