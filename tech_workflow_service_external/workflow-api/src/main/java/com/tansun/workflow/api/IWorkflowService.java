package com.tansun.workflow.api;

import com.tansun.workflow.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Package com.tansun.wp.api 
 * @ClassName: IWorkflowService
 * @Description: 工作流服务接口 
 * @author liquanfang@tansun.com.cn
 * @Copyright  2016年8月16日
 * @version2.1 新增取回接口rebackTask;
 */
public interface IWorkflowService {

	/**
	 * 根据流程定义ID启动流程
	 * @param processInfo  流程条件信息
	 * @return ProcessBean 
	 */
	public ProcessBean startProcess(ProcessInfo processInfo)throws  Exception;
	
	/**
	 * 根据流程定义ID启动流程并提交任务
     * @param processInfo  流程条件信息
	 * @return ProcessBean
	 */
	public ProcessBean startAndSubmitProcess(ProcessInfo processInfo)throws  Exception;
	
    /**
     * 终止流程
     * @param
     */
    public   ProcessBean terminateProcess(ProcessInfo processInfo) throws  Exception;
	
	/**
	 * 挂起流程实列
	 * @param
	 */
	public   ProcessBean  suspendProcess(ProcessInfo processInfo)throws  Exception;
	
	/**
	 * 激活流程实列
	 * @param
	 */
	public   ProcessBean  activateProcess(ProcessInfo processInfo)throws  Exception;
	
	/**
	 * 查询业务流程轨迹
	 * @param processInfo
	 * @return List<TaskBean>    返回类型
	 */
	public TaskBean queryProcessTrack(ProcessInfo processInfo)throws  Exception;

	/**
	 * 标记任务，已阅、未阅
	 * @param taskInfo
	 * @return
	 */
	public TaskBean signTaskStatus(TaskInfo taskInfo);

	/**
	 * 移除任务已阅标记
	 * @param taskInfo
	 * @return
	 */
	public TaskBean removeTaskStatus(TaskInfo taskInfo);
	/**
	 * 查找用户的任务列表
	 * @param
	 * @return
	 * 
	 */
	public  TaskBean findTaskList(TaskInfo taskInfo)throws  Exception;
	
	/**
	 * 根据taskId查询任务详情(任务信息按钮信息等)
	 * @param taskInfo
	 * @return
	 * 
	 */
	public  TaskBean findTaskInfo(TaskInfo taskInfo)throws  Exception;
	/**
	 * 提交任务
	 * @param taskBean  当前任务信息
	 */
	public   TaskBean   submitTask(TaskBean taskBean)throws  Exception;
	
	
	/**
	 * 批量提交任务
	 * @param
	 */
	public List<TaskBean> batchSubmitTask(List<TaskBean> taskBeans)throws  Exception;

	/**
	 * 批量退回任务
	 * @param
	 */
	public BatchExecuteVo batchExecuteTask(Object o,BatchExecuteParam batchExecuteParam)throws  Exception;

    /**
     * 退回到指定节点
     * @param
     * @param
     * @throws Exception 
     */  
    public TaskBean backTask(TaskBean taskBean) throws Exception;
    
    /**
     * 退回到任务前一节点／收回
     * @param
     * @throws Exception
     */
    public  TaskBean  backPreTask(TaskBean taskBean) throws  Exception;
    
    /**
     * 取回
     * @param
     * @throws Exception
     */
    public  TaskBean  rebackTask(TaskBean taskBean) throws  Exception;
    
	/**
	 * 回退至第一个节点
	 */
	public TaskBean backToFirstUserTask(TaskBean taskBean) throws Exception;
	
    /**
	 * 释放任务
	 * @param taskInfo
	 */
	public   TaskBean   releaseTask(TaskInfo taskInfo)throws  Exception;
	
	 /** 
     * 转办任务
     * @param
     */
    public TaskBean transferTask(TaskInfo taskInfo)throws  Exception;

    /**
	 * 根据当前任务ID查找下一人工任务
	 * @param
	 * @return TaskBean 活动信息
	 */
    public  TaskBean   queryNextTaskList(TaskInfo taskInfo) throws Exception;


	
	
	// ======================= 模型操作 ========================
	
	/**
	 * 获取模型列表
	 * @param processInfo
	 * @return
	 */
	public ProcessBean modelList(ProcessInfo processInfo);
	
	/**
	 * json byte -> json -> xml -> xml bytes
	 * 根据模型部署流程
	 * @param processDefinitionId
	 * @return
	 */
	public ProcessBean deployByDefinitionId(String processDefinitionId) throws Exception;

	/**
	 * 创建模型
	 * @param
	 * @return
	 */
	public ProcessBean createModel(String key, String name, String description, String tenantId);


	/**
	 * 删除模型
	 * @param modelId
	 * @return
	 */
	public ProcessBean deleteModel(String modelId, String key);
	
	
	public ProcessBean deleteModelList(List<String>  deploymentIds);
	
	/**
	 * model文件导出,导出类型有json,bpmn格式
	 * processBean={fileName,bytes}
	 */
	public ProcessBean exportModel(String modelId,String type);
  
	
	
	// ======================= 流程定义操作 ========================
	/**
	 * 获取流程定义列表
	 * 
	 * @param processInfo
	 * @return
	 */
	public ProcessBean processDefinitionList(ProcessInfo processInfo);

	/**
     * 获取分类及其子分类的所有已发布流程创建信息
     * @param processInfo
     * @return
     */
    public ProcessBean findProcessByCode(ProcessInfo processInfo);

	/**
	 * 部署流程
	 * @param deployInfo
	 * @return
	 */
	public ProcessBean deploy(DeployInfo deployInfo);
	
	/**
	 * 获取流程运行的实时图片
	 * @param processDefineId
	 * @return 
	 */
	public InputStream getProcessImage(String processDefineId);

	/**
	 * 获取流程定义文件
	 * @param processDefineId
	 * @return 
	 */
	public InputStream getProcessXml(String processDefineId);
	
	/**
	 * 根据流程定义创建模型
	 * @param ids
	 * @return
	 */
	public ProcessBean createModelByProcessDefinition(String[] ids);
	
	
	/**
	 * 复制流程定义
	 * @param targetDefinitionId 目标流程定义id
	 * @param newKey 新的键值
	 * @return newName 新的名字
	 */
	public ProcessBean copyProcessDefinition(String targetDefinitionId, String newKey, String newName);
	/**
	 * 修改任务的处理人
	 */
	public ProcessBean changeAssigee(String processDefinitionId, String activityId, String assignee);
	
	//============================== 流程实例服务=================
	/**
	 * 运行中的流程查询
	 * @param processInfo
	 * @return
	 */
	public ProcessBean processInstanceList(ProcessInfo processInfo);
	
	/**
	 * 获取已经结束的流程列表
	 * @param processInfo
	 * @return
	 */
	public ProcessBean getCompletedProcess(ProcessInfo processInfo);
	
	
	
	//============================== service task 服务===========
	/**
	 * 重试流程中出异常的serviceTask节点
	 *
	 * @param taskBean @return
	 */
	public TaskBean serviceTaskRetry(TaskBean taskBean);
	
	
	//============================== task 服务 ==================
	/**
	 * 查询侯选组组的任务
	 * @param info
	 * @return
	 */
//	public TaskBean findCandidateGroupTasks(TaskInfo info);
	/**
	 * 返回会签任务的审批状态
	 * @param taskInfo
	 */
	TaskBean getMultiStatus(TaskInfo taskInfo);

	/**
	 * 根据流程定义id获取任务节点信息
	 * @param processDefinitionId
	 * @return
	 */
	public TaskBean getTaskDefinitions(String processDefinitionId);

    /**
     *自由流转到某个任务节点--自由流(根据taskList、nextTaskId、userId)
     * @param taskBean
     * @return
     */
    public TaskBean jumpFlowTask(TaskBean taskBean) ;

	/**
	 * 根据流程实例id查询任务
	 * @param processInstanceId
	 * @return
	 */
	public TaskBean getTasksByProcessInstanceId(String processInstanceId) throws Exception;

	/**
	 * 根据流程实例id获取所有的下一步审批任务
	 * @param taskInfo
	 * @return
	 * @throws
	 */
	public TaskBean getAllNextTaskInfo(TaskInfo taskInfo);
	
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
	
	/**
	 * 获取任务池执行记录
	 * @param taskInfo
	 * @return
	 */
	public TaskBean findHiTaskPoolExecuteDataList(TaskInfo taskInfo);
	
	/**
	 * 暂存/修改扩展字段数据
	 * @param taskInfo
	 * @return
	 */
	public TaskBean temporarySaveTaskExpandData(TaskInfo taskInfo);

	/**
	 * 修改业务主键
	 * @param processInfo
	 * @return
	 */
	public ProcessBean updateBusinessKey(ProcessInfo processInfo);

	
	/**
	 * 获取当前节点用户信息
	 * @param taskInfo
	 * @return
	 */
	public TaskBean findTaskUsers(TaskInfo taskInfo);

	public ProcessBean deleteProcessInstance(ProcessInfo info);

    TaskBean getTaskDefinitionsByKey(TaskInfo taskInfo);

	/**
	 * 根据流程定义key和第一个节点的id获取属性信息
	 * @param taskInfo
	 * @return
	 */
	public TaskBean getFirstTaskDefine(TaskInfo taskInfo);

    /**
     * 批量发起流程，支持批量回滚
     * @param processInfoList
     * @return
     */
	public List<ProcessBean> batchStartProcess(List<ProcessInfo> processInfoList) throws Exception;

    /**
     * 批量发起并提交流程，支持批量回滚
     * @param processInfoList
     * @return
     */
	public List<ProcessBean> batchStartAndSubmitProcess(List<ProcessInfo> processInfoList) throws Exception;



	/**
	 * 批量退回流程
	 * @param taskBeanList
	 * @return
	 */
	public List<TaskBean> batchBackTask(List<TaskBean>  taskBeanList) throws Exception;

	/**
	 * 流程结束后获取流程变量信息
	 * @param processInfo
	 * @return
	 */
	public TaskBean findFinishedVariable(ProcessInfo processInfo);

    /**
     * 查询流程分类列表
     */
    public List<ClassifyInfo> findClassifyList(ClassifyInfo classifyInfo);

	/**
	 * 离岗移交
	 * @param wfLeaveTransfer
	 * @return
	 */
	public TaskBean saveLeaveTransfer(WfLeaveTransfer wfLeaveTransfer);

	/**
	 * 离岗移交终止
	 * @param wfLeaveTransfer
	 * @return
	 */
	public TaskBean stopLeaveTransfer(WfLeaveTransfer wfLeaveTransfer);

	public ProcessBean copyModel(ModelInfo modelInfo) throws Exception;

	public ProcessBean getExpandPlanByProcessInfo(ProcessInfo processInfo);

	public TaskBean findTaskCounts(TaskInfo taskInfo);

    public TaskBean getSignTaskStatus(TaskInfo taskInfo);

	TaskBean findOrgTaskList(TaskInfo taskInfo);

	TaskBean findOrgHiTaskList(TaskInfo taskInfo);

    ProcessBean messageConfirmProcessInstanceId(ProcessInfo processInfo);
	/**
	 * 统计机构下待办任务数
	 * @param params
	 * @return
	 */
	QueryBean findOrgTaskCount(Map<String,Object> params);

	/**
	 * 查询服务节点中的异常节点
	 */
		Map<String, Object> findErrorServiceTask(Map<String,Object> map);

	/**
	 * 服务节点重试
	 */
	TaskBean retryServiceTask(TaskInfo taskInfo);

	TaskBean reAssignmentTask(TaskInfo taskInfo);

	TaskBean claimWorkInfo(TaskInfo taskInfo);

	/**
	 * 根据userId查询待办任务接口
	 * @param taskInfo
	 * @return
	 */
	TaskBean findTodoList(TaskInfo taskInfo);

    ProcessBean setProcessVariable(ProcessInfo processInfo);
	ProcessBean batchTerminateProcess(List<ProcessInfo> info);
}
