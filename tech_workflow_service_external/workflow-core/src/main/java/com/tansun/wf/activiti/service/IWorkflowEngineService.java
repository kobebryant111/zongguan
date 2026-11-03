package com.tansun.wf.activiti.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tansun.wf.activiti.exception.WorkflowException;
import com.tansun.wf.activiti.model.*;
import com.tansun.wf.activiti.util.Page;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import org.camunda.bpm.engine.task.Task;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 流程接口服务
 * @author admin
 *
 */
public interface IWorkflowEngineService {
	
	
	
//	/**
//	 * 根据当前任务ID查找下一人工任务
//	 * @param taskId  当前任务ID
//	 * @param valueType 判断条件参数
//	 * @return TaskDefinition 活动定义
//	 */
//    public  TaskDefinition   nextTaskDefinition(String  taskId,ValueType  valueType) throws WorkflowException;
    

	
    /**
     * 根据任务ID查找下一人工任务执行者
     * @param
     * @param
     * @return
     */
	public List<SelectUserMode> findCandidateGroups(TaskInfo taskInfo)  throws WorkflowException;

	/**
	 * 查找用户的任务列表
	 * @param userId 用户ID
	 * @return
	 */
	public List<WorkItem> findToClaimListByUserId(String userId);
	
	/**
	 * 查找用户的任务分页列表
	 * @param userId   用户ID
	 * @param pageNumber  页码
	 * @param pageSize  每页记录数
	 * @return
	 */
	public  Page<WorkItem> findAssignOrCandidate(String userId,int pageNumber,int pageSize);


	
	/**
	 * 根据流程定义key查找ID
	 * @param processDefinitionKey  流程定义Key
	 * @return
	 */
	public  String    findProcessDefinitionIdByKey(String  processDefinitionKey) throws WorkflowException;
	
	/**
	 * 根据流程定义文件Key启动流程

	 * @return
	 */
	public ProcessBean startProcessInstanceByKey(ProcessInfo processInfo);
	
	
	/**
	 * 改方法废弃，建议使用方法 {@link IWorkflowEngineService.startProcessInstanceByKey(String processDefinitionKey, String userId, String businessKey, Map<String, Object> variables)}
	 * 根据流程定义文件Key启动流程
	 * @param processDefinitionKey  流程定义文件Key
	 * @param userId  用户ID
	 * @param businessKey  业务Key
	 * @return
	 */
	@Deprecated
	public ProcessInst startProcessInstanceByKey(String processDefinitionKey,String  userId,String businessKey) throws  WorkflowException;
	
	
	/**
	 * 挂起流程实列
	 * @param processInstanceId  流程实列ID
	 */
	public   void  suspendProcessInstance(String processInstanceId );
	
	
	public   void   terminateProcess(String processInstanceId, Map<String, Object> variablesMap, String comment, String taskId, String userId);
	
	/**
	 * 激活流程实列
	 * @param processInstanceId  流程实列ID
	 */
	public   void  activateProcessInstanceById(String  processInstanceId);;
	/**
	 * 给定义流程表单赋值、启动流程
	 * @param processDefinitionKey 流程定义ID
	 * @param
	 * @return
	 */
	public  ProcessInst   submitStartFormData(String processDefinitionKey,Map<String,String> formProperties  ) throws  WorkflowException;
	
	/**
	 * 領取任務
	 * @param taskId
	 * @param userId
	 */
	public   void   claimTask(String taskId,String  userId);
	
	
	/**
	 * 释放任务
	 * @param taskId
	 */
	public void unClaimTask(String taskId);
	
	
	/**
	 * 提交表单数据，完成任务
	 * @param taskId   任务ID
	 * @param variables 表单变量
	 */
	public   void   submitTaskFormData(String  taskId, Map<String,String> variables);

	/**
	 * 取回流程
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            取回节点ID
	 * @throws Exception
	 */
	public void callBackTask(String taskId, String activityId)
			throws WorkflowException;
	
	 /** 
     * 转办流程 
     *  
     * @param
     */
    public void transferAssignee(TaskInfo taskInfo);
    
    
    /**
     * 完成指定任务
     * @param taskId  任务ID
     * @param variables 变量
     * @throws Exception
     */
    public  void  completeTask(String taskId, Map<String, Object> variables)  
            throws WorkflowException;
    
    
    /**
     * 根据任务ID获得流程定义表单信息
     * @param taskId  任务ID
     * @return
     */
    public   TaskFormDataVO   getTaskFormData(String  taskId);
    
    /** 
     * 审批通过(驳回直接跳回功能需后续扩展) 
     *  
     * @param taskId 
     *            当前任务ID 
     * @param variables 
     *            流程存储参数 
     * @throws Exception 
     */  
    public void passProcess(String taskId, Map<String, Object> variables);
    
    
    /**
     * 设置下个审批人
     * @param taskId
     * @param user
     */
    public void setApproveUser(String taskId,String user) ;

    /** 
     * 退回到指定节点
     */
    @SuppressWarnings("rawtypes")
	public void backTask(TaskBean taskBean);

    public TaskBean rebackTask(TaskBean taskBean) throws WorkflowException;

    

     /**
      * 查找可退回节点信息
      * @param taskId  任务ID
      * @return
      * @throws WorkflowException
      */
    public List<ActivityInfo> findBackAvtivityInfo(String taskId) throws WorkflowException ;
    
    /**
     * 根据流程实列ID查找任务执行信息
     * @param processInstanceId 流程实列ID
     * @return
     */
    public   List<TaskTraceInfo>     findTaskListByProcessInstanceId(String   processInstanceId)throws WorkflowException;
    
    
    /**
     * 根据流程实列ID 查找流程实列
     * @param processInstanceId 流程实列ID
     * @return
     */
    public   ProcessInst    findProcessInstByProcessInstanceId(String  processInstanceId);
    
    /** 
     * 根据任务ID获取对应的流程实例 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    public ProcessInst findProcessInstByTaskId(String taskId)  
            throws WorkflowException;
    
    /**
     * 查找下一个任务节点列表信息
     * @param taskInfo
     * @return
     * @throws WorkflowException
     */
    public  TaskBean   queryNextTaskList(TaskInfo taskInfo) throws WorkflowException;

    
    /**
     * 提交任务
     * taskId 任务ID
     * variables 任务流程变量
     * nextTaskUsers 下一任务执行人的相关信息
     */
    public void submitTask(Task task, TaskBean taskBean);
    
    
    /**
     * 根据任务查询信息查找已办列表
     * @param taskInfo 任务查询信息
     * @return
     * @throws WorkflowException
     */
    public  TaskBean  findCompletedTaskList(TaskInfo taskInfo);
    
    
    
    /**
     *  根据任务查询信息查找待办列表
     * @param taskInfo 任务查询信息
     * @return
     * @throws WorkflowException
     */
    public  TaskBean  findPendTaskList(TaskInfo  taskInfo);
    
    
    
    /**
     * 查找流程信息
     * @param processInfo  流程信息
     * @return
     */
    public  ProcessBean   findProcessListByInfo( ProcessInfo  processInfo)  ;
    
    
    
    /**
     * 设定执行对象的流程变量
     * @param executionId 执行对象ID
     * @param variables
     */
    public   void   setVariables(String executionId,Map<String,Object> variables);

    /**
    * 自由流转(根据taskList、nextTaskId、userId)
    * @param
    * @param
    * @throws Exception
	* @return
    */
	public TaskBean jumpFlowTask(TaskBean taskBean) ;

	/**
	 * 加签
	 * @param taskBean
	 * @return 
	 */
     public TaskBean addGroupUser(TaskBean taskBean)throws  WorkflowException;


	TaskBean getMultiStatus(TaskInfo taskInfo);

	/**
 	 * 减签
 	 * @param taskBean
 	 * @return 
 	 */
     public TaskBean delGroupUser(TaskBean taskBean) throws WorkflowException;
     
     /**
      * 获取所有流程分类的待办数据
      * @param
      * @return
      */
     public List<TaskBean> getAllClassifyPendTasks(TaskInfo taskInfo);
     
     /**
      * 获取所有流程分类的已办数据
      * @param
      * @return
      */
     public List<TaskBean> getAllClassifyCompletedTasks(TaskInfo taskInfo);
     
     /**
 	 * 获取按钮信息
 	 * @param taskInfo
 	 * @return
 	 */
     public TaskBean findButtonPower(TaskInfo taskInfo);
     
     /**
 	 * 获取流程回调类
 	 * @param processBean
 	 * @return
 	 */
 	 public ProcessBean findProcessHandleClass(ProcessBean processBean);
     
     /**
      * 获取业务处理类类名
      * @param taskInfo
      * @return
      * @throws JsonProcessingException
      * @throws IOException
      */
     public TaskBean findClassName(TaskInfo taskInfo);

     /**
      * 根据流程实例id获取该流程实例已办任务列表
      * @param processInfo
      * @return
      */
     public TaskBean findHiTaskByProcessInstanceId(ProcessInfo processInfo);

     /**
 	 * 获取流程变量
 	 * @param processInfo
 	 * @return
 	 */
 	 public TaskBean findProcessVariable(ProcessInfo processInfo);

	public List<Map> getTaskList(TaskInfo taskInfo,String processInstanceId) throws WorkflowException;

	public TaskBean getFirstTaskDefine(TaskInfo taskInfo);

	/**
	 * 流程结束后获取流程变量信息
	 * @param processInfo
	 * @return
	 */
	public TaskBean findFinishedVariable(ProcessInfo processInfo);

	TaskBean findPendOrgTaskList(TaskInfo taskInfo);
}
