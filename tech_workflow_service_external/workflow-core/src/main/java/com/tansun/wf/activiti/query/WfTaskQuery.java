package com.tansun.wf.activiti.query;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

/**
 * 运行中的任务查询
 */
public class WfTaskQuery extends AbstractQuery<WfTaskQuery,Map> {

    /**
     * 流程实例id
     */
    private String processInstanceId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 任务id
     */
    private String taskId;
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
    
    private String classifyCode;
    
 
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 租户id
     */
    private List<String> tenantIds;
    /**
     * 角色
     */
    private List<String> roleList;

    /**
     * 机构
     */
    private List<String> orgList;
    
    
    private  List<String> deptList;
    
    /**
     * 流程实例创建查询起始时间
     */
    private String processCreateTimeBefore;
    
    /**
     * 流程实例创建查询结束时间
     */
    private String processCreateTimeAfter;
    
    /**
	 * 任务池状态，用于获取任务池
	 */
	private String taskPoolStatus;
	
	/**
	 * 分配状态1分配0释放
	 */
	private String isAllot;
    private Date claimTimeAfter;

    private Date claimTimeBefore;

    /**
     * 业务主键
     */
    private String businessKey;

    /**
     * 业务主键模糊匹配D
     */
    private String businessKeyLike;

    /**
     * 业务主键模糊匹配
     */
    private  String processDefineName;

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
     * 任务创建查询起始时间
     */
    private String createTimeBefore;
    /**
     * 任务创建查询结束时间
     */
    private String createTimeAfter;
    /**
     * 创建时间排序
     */
    private String createTimeSort;

    /**
     * 是否使用旧逻辑查询 , 5.3.0 版本之前为旧逻辑
     */
    private boolean is365Query;

    private String unequalActivityId;

    private String unequalActivityName;

    private List<String> processDefineKeys;

    @Override
    public long executeCount(CommandContext commandContext) {
        String databaseType = commandContext.getDbSqlSession().getDbSqlSessionFactory().getDatabaseType();
        this.setDatabaseType(databaseType);
        if (is365Query){
            return (Long) commandContext.getDbSqlSession()
                    .selectOne("selectWfTasksCountByQueryCriteria", this);
        }else{
            return (Long) commandContext.getDbSqlSession()
                    .selectOne("selectWfTasksCount", this);
        }

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
        if (is365Query){
            return commandContext.getDbSqlSession()
                    .selectList("selectWfTasksByQueryCriteria", this);
        }else{
            return commandContext.getDbSqlSession()
                    .selectList("selectWfTasks", this);
        }

    }

    public WfTaskQuery fieldMapping(List<String> fieldMapping) {
        this.fieldMapping = fieldMapping;
        return this;
    }

    public WfTaskQuery fieldCondition(List<String> fieldCondition) {
        this.fieldCondition = fieldCondition;
        return this;
    }

    public WfTaskQuery processInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }
    public WfTaskQuery userId(String userId) {
        this.userId = userId;
        return this;
    }
    public WfTaskQuery taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }
    public WfTaskQuery taskNameLike(String taskName) {
        if(StringUtils.isNotBlank(taskName)){
            this.taskNameLike = "%"+taskName+"%";
        }else{
            this.taskNameLike = taskName;
        }
        return this;
    }
    public WfTaskQuery processDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }
    public WfTaskQuery taskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
        return this;
    }
    public WfTaskQuery classifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
        return this;
    }
    public WfTaskQuery tenantIds(List<String> tenantIds) {
        this.tenantIds = tenantIds;
        return this;
    }
    public WfTaskQuery tenantId(String tenantId) {
        this.tenantId = tenantId;

        return this;
    }

    public WfTaskQuery roleList(List<String> roleList) {
        this.roleList = roleList;
        return this;
    }

    public WfTaskQuery orgList(List<String> orgList) {
        this.orgList = orgList;
        return this;
    }
    
    public WfTaskQuery processCreateTimeBefore(String processCreateTimeBefore) {
        this.processCreateTimeBefore = processCreateTimeBefore;
        return this;
    }

    public WfTaskQuery processCreateTimeAfter(String processCreateTimeAfter) {
        this.processCreateTimeAfter = processCreateTimeAfter;
        return this;
    }
    public WfTaskQuery taskPoolStatus(String taskPoolStatus) {
        this.taskPoolStatus = taskPoolStatus;
        return this;
    }
    
    public WfTaskQuery isAllot(String isAllot) {
        this.isAllot = isAllot;
        return this;
    }

    public WfTaskQuery claimTimeAfter(Date claimTimeAfter) {
        this.claimTimeAfter = claimTimeAfter;
        return this;


    }

    public WfTaskQuery claimTimeBefore(Date claimTimeBefore) {
        this.claimTimeBefore = claimTimeBefore;
        return this;
    }

    public WfTaskQuery businessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;

    }

    public WfTaskQuery businessKeyLike(String businessKeyLike) {
        this.businessKeyLike = businessKeyLike;
        return this;
    }

    public WfTaskQuery processDefineName(String processDefineName) {
        this.processDefineName = processDefineName;
        return this;
    }

    public WfTaskQuery processDefineNameLike(String processDefineNameLike) {
        this.processDefineNameLike = processDefineNameLike;
        return this;
    }

    public WfTaskQuery processDefineKey(String processDefineKey) {
        this.processDefineKey = processDefineKey;
        return this;
    }

    public WfTaskQuery processDefineKeyLike(String processDefineKeyLike) {
        this.processDefineKeyLike = processDefineKeyLike;
        return this;
    }
    public WfTaskQuery createTimeSort(String createTimeSort) {
        this.createTimeSort =createTimeSort;
        return this;
    }
    public WfTaskQuery createTimeBefore(String createTimeBefore) {
        this.createTimeBefore = createTimeBefore;
        return this;
    }

    public WfTaskQuery createTimeAfter(String createTimeAfter) {
        this.createTimeAfter = createTimeAfter;
        return this;
    }

    public void is365Query(boolean use365TaskListQuery) {
        this.is365Query = use365TaskListQuery;
    }

    public WfTaskQuery unequalActivityId(String unequalActivityId) {
        this.unequalActivityId = unequalActivityId;
        return this;
    }

    public WfTaskQuery unequalActivityName(String unequalActivityName) {
        this.unequalActivityName = unequalActivityName;
        return this;
    }

    public WfTaskQuery processDefineKeys(List<String> processDefineKeys) {
        this.processDefineKeys = processDefineKeys;
        return this;
    }

	public List<String> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<String> deptList) {
		this.deptList = deptList;
	}
	
    public WfTaskQuery deptList(List<String> deptList) {
        this.deptList = deptList;
        return this;
    }
    
    

}
