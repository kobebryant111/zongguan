package com.tansun.wf.activiti.query;


import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import java.util.List;
import java.util.Map;



/**
 * 历史任务查询
 */
@SuppressWarnings({"unused","rawtypes","unchecked", "serial"})
public class WfHiTaskQuery extends AbstractQuery<WfHiTaskQuery,Map> {

    /**
     * 流程实例id
     */
    private String processInstanceId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 任务名称(模糊查询)
     */
    private String taskNameLike;
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
     * 待办扩展字段查询条件(模糊查询)
     */
    private List<String> fieldCondition;

    /**
     * 活动类型，固定userTask
     */
    private String activityType;
    /**
     * 任务id
     */
    private String taskId;
    
    private String classifyCode;
    /**
     * 租户id
     */
    private String tenantId;
	
	private List<String> tenantIds;
	
    /**
     * 已办终止状态
     */
    private String isTerminateProcess;
    
    /**
     * 进行中和结束状态
     */
    private String isEnd;
	
	 /**
     * 未结和已结状态(包括待办)
     */
    private String isEndIncludeRuTask;
    
    /**
     * 已办去重状态（1去重非1或不传为不去重）
     */
    private String singleHiTaskStatus;
    
    /**
     * 流程实例创建查询起始时间
     */
    private String processCreateTimeBefore;
    
    /**
     * 流程实例创建查询结束时间
     */
    private String processCreateTimeAfter;

    /**
     * 业务主键
     */
    private String businessKey;

    /**
     * 业务主键模式
     */
    private String businessKeyLike;

    /**
     * 流程名称
     */
    private String processDefineName;

    /**
     * 流程名称模糊匹配
     */
    private String processDefineNameLike;
    /**
     * 流程键值匹配
     */
    private String processDefineKey;
    /**
     * 流程键值匹配匹配
     */
    private String processDefineKeyLike;

    /**
     * 流程是否领取
     */
    private Integer claimStatus;
    /**
     * 发起时间排序
     */
    private String startTimeSort;
    /**
     * 领取时间排序
     */
    private String claimTimeSort;
    /**
     * 结束时间排序
     */
    private String endTimeSort;
    /**
     * 任务创建查询起始时间
     */
    private String createTimeBefore;
    /**
     * 任务创建查询结束时间
     */
    private String createTimeAfter;
    /**
     * 任务结束查询的起始时间
     */
    private String endTimeBefore;
    /**
     * 任务结束查询的终止时间
     */
    private String endTimeAfter;

    private List<String> processDefineKeys;

    /**
     * 流程发起人
     */
    private String startUserId;
    private String startUserName;

    private String startUserOrgCode;

    private List<String> ignoreActivityId;
    private List<String> ignoreActivityName;
    private List<String> taskIds;

    @Override
    public long executeCount(CommandContext commandContext) {
        String databaseType = commandContext.getDbSqlSession().getDbSqlSessionFactory().getDatabaseType();
        this.setDatabaseType(databaseType);
        return (Long) commandContext.getDbSqlSession().selectOne("selectWfHiTasksCount", this);
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
        return commandContext.getDbSqlSession().selectList("selectWfHiTasks", this);
    }

    public WfHiTaskQuery fieldMapping(List<String> fieldMapping) {
        this.fieldMapping = fieldMapping;
        return this;
    }

    public WfHiTaskQuery fieldCondition(List<String> fieldCondition) {
        this.fieldCondition = fieldCondition;
        return this;
    }

    public WfHiTaskQuery processInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }
    public WfHiTaskQuery userId(String userId) {
        this.userId = userId;
        return this;
    }
    public WfHiTaskQuery taskNameLike(String taskName) {
        if(StringUtils.isNotBlank(taskName)){
            this.taskNameLike = "%"+taskName+"%";
        }else{
            this.taskNameLike = taskName;
        }
        return this;
    }
    public WfHiTaskQuery processDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }
    public WfHiTaskQuery taskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
        return this;
    }

    public WfHiTaskQuery activityType(String activityType) {
        this.activityType = activityType;
        return this;
    }

    public WfHiTaskQuery taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }
    
    public WfHiTaskQuery classifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
        return this;
    }

    public WfHiTaskQuery tenantIds(List<String> tenantIds) {
        this.tenantIds = tenantIds;
        return this;
    }
    public WfHiTaskQuery tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }
    public WfHiTaskQuery processCreateTimeBefore(String processCreateTimeBefore) {
        this.processCreateTimeBefore = processCreateTimeBefore;
        return this;
    }

    public WfHiTaskQuery processCreateTimeAfter(String processCreateTimeAfter) {
        this.processCreateTimeAfter = processCreateTimeAfter;
        return this;
    }
	
	public WfHiTaskQuery isTerminateProcess(String isTerminateProcess) {
        this.isTerminateProcess = isTerminateProcess;
        return this;
    }
    
    public WfHiTaskQuery isEnd(String isEnd) {
        this.isEnd = isEnd;
        return this;
    }
    
    public WfHiTaskQuery isEndIncludeRuTask(String isEndIncludeRuTask) {
        this.isEndIncludeRuTask = isEndIncludeRuTask;
        return this;
    }
    
    public WfHiTaskQuery singleHiTaskStatus(String singleHiTaskStatus) {
        this.singleHiTaskStatus = singleHiTaskStatus;
        return this;
    }

    public WfHiTaskQuery businessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public WfHiTaskQuery businessKeyLike(String businessKeyLike) {
        this.businessKeyLike = businessKeyLike;
        return this;
    }

    public WfHiTaskQuery processDefineName(String processDefineName) {
        this.processDefineName=processDefineName;
        return this;
    }

    public WfHiTaskQuery processDefineNameLike(String processDefineNameLike) {
        this.processDefineNameLike=processDefineNameLike;
        return this;
    }

    public WfHiTaskQuery processDefineKey(String processDefineKey) {
        this.processDefineKey = processDefineKey;
        return this;
    }

    public WfHiTaskQuery processDefineKeyLike(String processDefineKeyLike) {
        this.processDefineKeyLike = processDefineKeyLike;
        return this;
    }

    public WfHiTaskQuery startTimeSort(String startTimeSort) {
        this.startTimeSort =startTimeSort;
        return this;
    }
    public WfHiTaskQuery claimTimeSort(String claimTimeSort) {
        this.claimTimeSort = claimTimeSort;
        return this;
    }
    public WfHiTaskQuery endTimeSort(String endTimeSort) {
        this.endTimeSort = endTimeSort;
        return this;
    }

    public WfHiTaskQuery createTimeBefore(String createTimeBefore) {
        this.createTimeBefore = createTimeBefore;
        return this;
    }

    public WfHiTaskQuery createTimeAfter(String createTimeAfter) {
        this.createTimeAfter = createTimeAfter;
        return this;
    }

    public WfHiTaskQuery endTimeBefore(String endTimeBefore) {
        this.endTimeBefore = endTimeBefore;
        return this;
    }

    public WfHiTaskQuery endTimeAfter(String endTimeAfter) {
        this.endTimeAfter = endTimeAfter;
        return this;
    }

    public WfHiTaskQuery processDefineKeys(List<String> processDefineKeys) {
        this.processDefineKeys = processDefineKeys;
        return this;
    }

    public WfHiTaskQuery startUserId(String startUserId) {
        this.startUserId = startUserId;
        return this;
    }

    public WfHiTaskQuery startUserName(String startUserName) {
        this.startUserName = startUserName;
        return this;
    }
    


    public WfHiTaskQuery startUserOrgCode(String startUserOrgCode) {
        this.startUserOrgCode = startUserOrgCode;
        return this;
    }

    public WfHiTaskQuery ignoreActivityId(List<String> ignoreActivityId) {
        this.ignoreActivityId = ignoreActivityId;
        return this;
    }

    public WfHiTaskQuery ignoreActivityName(List<String> ignoreActivityName) {
        this.ignoreActivityName = ignoreActivityName;
        return this;
    }

    public WfHiTaskQuery taskIds(List<String> taskIds) {
        this.taskIds = taskIds;
        return this;
    }


}
