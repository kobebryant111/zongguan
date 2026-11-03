package com.tansun.wf.activiti.query;


import com.tansun.wf.platform.model.AgentTrust;
import com.tansun.workflow.model.PostInfo;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.impl.AbstractQuery;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 运行中的任务查询
 */
public class WfOrgTaskQuery extends AbstractQuery<WfOrgTaskQuery,Map> {
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
     * 是否领取标识
     */
    private Integer claimStatus;

    /**
     * 任务处理人
     */
    private String assignee;

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

    private List<PostInfo> postInfos;

    private String startUserId;
    private String startUserName;

    private boolean queryAllTask;

    


    // 发起人机构id
    private String  startUserOrgCode;

  @Override
    public long executeCount(CommandContext commandContext) {
        String databaseType = commandContext.getDbSqlSession().getDbSqlSessionFactory().getDatabaseType();
        this.setDatabaseType(databaseType);
        if (is365Query){
            return (Long) commandContext.getDbSqlSession()
                    .selectOne("selectWfTasksCountByQueryCriteria", this);
        }else{
            return (Long) commandContext.getDbSqlSession()
                    .selectOne("selectWfOrgTasksCount", this);
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
                    .selectList("selectWfOrgTasks", this);
        }

    }

    public WfOrgTaskQuery fieldMapping(List<String> fieldMapping) {
        this.fieldMapping = fieldMapping;
        return this;
    }

    public WfOrgTaskQuery fieldCondition(List<String> fieldCondition) {
        this.fieldCondition = fieldCondition;
        return this;
    }

    public WfOrgTaskQuery assignee(String assignee){
        this.assignee=assignee;
        return this;
    }

    public WfOrgTaskQuery processInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }
    public WfOrgTaskQuery userId(String userId) {
        this.userId = userId;
        return this;
    }

    public WfOrgTaskQuery claimStatus(Integer claimStatus){
        this.claimStatus=claimStatus;
        return this;
    }

    public WfOrgTaskQuery taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }
    public WfOrgTaskQuery taskNameLike(String taskName) {
        if(StringUtils.isNotBlank(taskName)){
            this.taskNameLike = "%"+taskName+"%";
        }else{
            this.taskNameLike = taskName;
        }
        return this;
    }
    public WfOrgTaskQuery processDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }
    public WfOrgTaskQuery taskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
        return this;
    }
    public WfOrgTaskQuery classifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
        return this;
    }
    public WfOrgTaskQuery tenantIds(List<String> tenantIds) {
        this.tenantIds = tenantIds;
        return this;
    }
    public WfOrgTaskQuery tenantId(String tenantId) {
        this.tenantId = tenantId;

        return this;
    }

    public WfOrgTaskQuery roleList(List<String> roleList) {
        this.roleList = roleList;
        return this;
    }

    public WfOrgTaskQuery orgList(List<String> orgList) {
        this.orgList = orgList;
        return this;
    }
    
    public WfOrgTaskQuery processCreateTimeBefore(String processCreateTimeBefore) {
        this.processCreateTimeBefore = processCreateTimeBefore;
        return this;
    }

    public WfOrgTaskQuery processCreateTimeAfter(String processCreateTimeAfter) {
        this.processCreateTimeAfter = processCreateTimeAfter;
        return this;
    }
    public WfOrgTaskQuery taskPoolStatus(String taskPoolStatus) {
        this.taskPoolStatus = taskPoolStatus;
        return this;
    }
    
    public WfOrgTaskQuery isAllot(String isAllot) {
        this.isAllot = isAllot;
        return this;
    }

    public WfOrgTaskQuery claimTimeAfter(Date claimTimeAfter) {
        this.claimTimeAfter = claimTimeAfter;
        return this;


    }

    public WfOrgTaskQuery claimTimeBefore(Date claimTimeBefore) {
        this.claimTimeBefore = claimTimeBefore;
        return this;
    }

    public WfOrgTaskQuery businessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;

    }

    public WfOrgTaskQuery businessKeyLike(String businessKeyLike) {
        this.businessKeyLike = businessKeyLike;
        return this;
    }

    public WfOrgTaskQuery processDefineName(String processDefineName) {
        this.processDefineName = processDefineName;
        return this;
    }

    public WfOrgTaskQuery processDefineNameLike(String processDefineNameLike) {
        this.processDefineNameLike = processDefineNameLike;
        return this;
    }

    public WfOrgTaskQuery processDefineKey(String processDefineKey) {
        this.processDefineKey = processDefineKey;
        return this;
    }



    public WfOrgTaskQuery processDefineKeyLike(String processDefineKeyLike) {
        this.processDefineKeyLike = processDefineKeyLike;
        return this;
    }
    public WfOrgTaskQuery createTimeSort(String createTimeSort) {
        this.createTimeSort =createTimeSort;
        return this;
    }
    public WfOrgTaskQuery createTimeBefore(String createTimeBefore) {
        this.createTimeBefore = createTimeBefore;
        return this;
    }

    public WfOrgTaskQuery createTimeAfter(String createTimeAfter) {
        this.createTimeAfter = createTimeAfter;
        return this;
    }

    public void is365Query(boolean use365TaskListQuery) {
        this.is365Query = use365TaskListQuery;
    }

    public WfOrgTaskQuery unequalActivityId(String unequalActivityId) {
        this.unequalActivityId = unequalActivityId;
        return this;
    }

    public WfOrgTaskQuery unequalActivityName(String unequalActivityName) {
        this.unequalActivityName = unequalActivityName;
        return this;
    }
	
    public WfOrgTaskQuery deptList(List<String> deptList) {
        this.deptList = deptList;
        return this;
    }

    public WfOrgTaskQuery processDefineKeys(List<String> processDefineKeys) {
        this.processDefineKeys = processDefineKeys;
        return this;
    }

    public WfOrgTaskQuery postInfos(List<PostInfo> postInfos) {
        this.postInfos = postInfos;
        return this;
    }

    public WfOrgTaskQuery startUserId(String startUserId) {
        this.startUserId = startUserId;
        return this;
    }

    public WfOrgTaskQuery startUserName(String startUserName) {
        this.startUserName = startUserName;
        return this;
    }

    public WfOrgTaskQuery queryAllTask(boolean queryAllTask) {
        this.queryAllTask = queryAllTask;
        return this;
    }
    


    public  WfOrgTaskQuery  startUserOrgCode(String startUserOrgCode) {
    	this.startUserOrgCode = startUserOrgCode;
    	return  this;
    }

    




}
