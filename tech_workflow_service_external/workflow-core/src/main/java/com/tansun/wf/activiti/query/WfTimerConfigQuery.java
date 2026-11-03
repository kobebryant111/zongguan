package com.tansun.wf.activiti.query;


import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

/**
 * 定时配置信息
 */
public class WfTimerConfigQuery extends AbstractQuery<WfTimerConfigQuery,Map> {

    /**
     * 流程实例id
     */
    private String processInstanceId;
    /**
     * 流程定义Id
     */
    private String processDefinitionId;
    /**
     * 任务定义Key
     */
    private String taskDefinitionKey;
    /**
     * 待办扩展字段映射
     */
    private List<String> fieldMapping;
    
    /**
     * 租户id
     */
    private String tenantId;

    @Override
    public long executeCount(CommandContext commandContext) {
    	return (Long) commandContext.getDbSqlSession()
                .selectOne("selectWfTimerConfigCount", this);
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
				.selectList("selectWfTimerConfig", this);
        		
    }

    public WfTimerConfigQuery fieldMapping(List<String> fieldMapping) {
        this.fieldMapping = fieldMapping;
        return this;
    }


    public WfTimerConfigQuery processInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public WfTimerConfigQuery processDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }
    public WfTimerConfigQuery taskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
        return this;
    }

    public WfTimerConfigQuery tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }
}
