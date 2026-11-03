package com.tansun.workflow.service;

import com.tansun.workflow.api.IWorkflowService;
import com.tansun.workflow.model.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkFlowClientService {

	private IWorkflowService workflowService;
	/**
	 * 根据流程定义ID启动流程
	 * @param processInfo  流程条件信息
	 * @return ProcessBean 
	 */
	public ProcessBean startProcess(ProcessInfo processInfo) throws  Exception{
		return workflowService.startProcess(processInfo);
	}

	/**
	 * 根据流程定义ID启动流程并提交任务
     * @param processInfo  流程条件信息
	 * @return ProcessBean
	 */
	public ProcessBean startAndSubmitProcess(ProcessInfo processInfo)throws  Exception{
		return workflowService.startAndSubmitProcess(processInfo);
	}
	
    /**
     * 终止流程
     */
    public   ProcessBean terminateProcess(ProcessInfo processInfo) throws  Exception{
    	return  workflowService.terminateProcess(processInfo);

    }
	
	/**
	 * 挂起流程实列
	 */
	public   ProcessBean  suspendProcess(ProcessInfo processInfo)throws  Exception{
		return  workflowService.suspendProcess(processInfo);
	}
	
	/**
	 * 激活流程实列
	 */
	public   ProcessBean  activateProcess(ProcessInfo processInfo)throws  Exception{
		return workflowService.activateProcess(processInfo);

	}
	
	/**
	 * 查询业务流程轨迹
	 * @param processInfo
	 * @return List<TaskBean>    返回类型
	 */
	public TaskBean queryProcessTrack(ProcessInfo processInfo)throws  Exception{
		return workflowService.queryProcessTrack(processInfo);
	}
	
	/**
	 * 标记任务，已阅、未阅
	 * @param taskInfo
	 * @return
	 */
	public TaskBean signTaskStatus(TaskInfo taskInfo){
        return workflowService.signTaskStatus(taskInfo);
    }
	/**
	 * 移除任务已阅标记
	 * @param taskInfo
	 * @return
	 */
	public TaskBean removeTaskStatus(TaskInfo taskInfo){
		return workflowService.removeTaskStatus(taskInfo);
	}
	
	/**
	 * 查找用户的任务列表
	 * @return
	 * 
	 */
	public TaskBean findTaskList(TaskInfo taskInfo)throws  Exception{
		return  workflowService.findTaskList(taskInfo);
	}
	
	/**
	 * 根据taskId查询任务详情(任务信息按钮信息等)
	 * @param taskInfo 
	 * @return
	 * 
	 */
	public TaskBean findTaskInfo(TaskInfo taskInfo)throws  Exception{
		return  workflowService.findTaskInfo(taskInfo);
	}
	/**
	 * 提交任务
	 * @param taskBean  当前任务信息
	 */
	public TaskBean submitTask(TaskBean taskBean)throws  Exception{
		return  workflowService.submitTask(taskBean);
	}
	/**
	 * 批量提交任务
	 */
	public List<TaskBean> batchSubmitTask(List<TaskBean> taskBeans)throws  Exception{
		return  workflowService.batchSubmitTask(taskBeans);
	}
	/**
	 * 批量撤回任务
	 */
	public BatchExecuteVo batchExecuteTask(BatchExecuteParam batchExecuteParam)throws  Exception{
		return  workflowService.batchExecuteTask(workflowService,batchExecuteParam);
	}

	/** 
     * 退回到指定节点
     * @param taskBean 当前任务信息
     * @throws Exception 
     */  
    public TaskBean backTask(TaskBean taskBean) throws Exception{
    	return  workflowService.backTask(taskBean);
    }
    /**
     * 退回到任务前一节点／收回
     * @param taskBean  当前任务信息
     * @throws Exception
     */
    public  TaskBean  backPreTask(TaskBean taskBean) throws  Exception{
		return workflowService.backPreTask(taskBean);

    }
    /**
     * 取回任务节点
     * @param taskBean  取回任务信息
     * @throws Exception
     */
    public  TaskBean  rebackTask(TaskBean taskBean) throws  Exception{
		return workflowService.rebackTask(taskBean);

    }

	/**
	 * 回退至第一个节点
	 * @param taskBean
	 * @return
	 * @throws Exception
	 */
    public TaskBean backToFirstUserTask(TaskBean taskBean) throws Exception{
    	return workflowService.backToFirstUserTask(taskBean);
	}
	
    /**
	 * 释放任务
	 * @param taskInfo
	 */
	public   TaskBean   releaseTask(TaskInfo taskInfo) throws  Exception{
		return  workflowService.releaseTask(taskInfo);
	}
	
	 /** 
     * 转办任务
     */
    public TaskBean transferTask(TaskInfo taskInfo) throws  Exception{
		return workflowService.transferTask(taskInfo);
    }
    
	/**
	 * 根据当前任务ID查找下一人工任务
	 * @param taskInfo  当前任务信息
	 * @return TaskBean 活动信息
	 */
    public TaskBean queryNextTaskList(TaskInfo taskInfo) throws Exception{
    	return workflowService.queryNextTaskList(taskInfo);
    }
	

	
	
	// ======================= 模型操作 ========================
	
	/**
	 * 获取模型列表
	 * @param processInfo
	 * @return
	 */
	
	public ProcessBean modelList(ProcessInfo processInfo) {
		return workflowService.modelList(processInfo);
	}
	
	/**
	 * json byte -> json -> xml -> xml bytes
	 * 根据模型部署流程
	 * @param processDefinitionId
	 * @return
	 */
	public ProcessBean deployByDefinitionId(String processDefinitionId) throws Exception {
		return workflowService.deployByDefinitionId(processDefinitionId);
	}


	/**
	 * 创建模型
	 * @return
	 */
	public ProcessBean createModel(ModelInfo modelInfo) {
		return workflowService.createModel(modelInfo.getKey(), modelInfo.getName(), "", modelInfo.getTenantId());
	}





	/**
	 * 删除模型
	 * @return
	 */
	public ProcessBean deleteModel(String key,String tenantId) {
		return workflowService.deleteModel(key,tenantId);
	}
	/**
	 * model文件导出,导出类型有json,bpmn格式
	 * processBean={fileName,bytes}
	 */
	
	public ProcessBean exportModel(String modelId,String type){
		return workflowService.exportModel(modelId, type);
	}
	
	
	
	// ======================= 流程定义操作 ========================
	/**
	 * 获取流程定义列表
	 * 
	 * @param processInfo
	 * @return
	 */
	
	public ProcessBean processDefinitionList(ProcessInfo processInfo) {
		return workflowService.processDefinitionList(processInfo);
	}
	
	/**
	 * 部署流程
	 * @param deployInfo
	 * @return
	 */
	public ProcessBean deploy(DeployInfo deployInfo){
		return workflowService.deploy(deployInfo);
	}
	
	/**
	 * 获取流程运行的实时图片
	 * @param processDefineId
	 * @return 
	 */
	
	public InputStream getProcessImage(String processDefineId) {
		return workflowService.getProcessImage(processDefineId);
	}

	/**
	 * 获取流程定义文件
	 * @param processDefineId
	 * @return 
	 */
	
	public InputStream getProcessXml(String processDefineId) {
		return workflowService.getProcessXml(processDefineId);
	}
	
	/**
	 * 根据流程定义创建模型
	 * @param ids
	 * @return
	 */
	public ProcessBean createModelByProcessDefinition(String[] ids) {
		return workflowService.createModelByProcessDefinition(ids);
	}
	
	
	
	//============================== 流程实例服务=================
	/**
	 * 运行中的流程查询
	 * @param processInfo
	 * @return
	 */
	
	public ProcessBean processInstanceList(ProcessInfo processInfo) {
		return workflowService.processInstanceList(processInfo);
	}
	
	/**
	 * 获取已经结束的流程列表
	 * @param processInfo
	 * @return
	 */
	public ProcessBean getCompletedProcess(ProcessInfo processInfo) {
		return workflowService.getCompletedProcess(processInfo);
	}
	
	//============================== task 服务 ==================

	/**
	 * 根据流程定义id获取任务节点信息
	 * @param processDefinitionId
	 * @return
	 */
	public TaskBean getTaskDefinitions(String processDefinitionId){
		return workflowService.getTaskDefinitions(processDefinitionId);
	}

    /**
     * 自由流转(根据taskList、nextTaskId、userId)
     * @param taskBean
     * @return
     * @throws Exception
     */
    public TaskBean jumpFlowTask(TaskBean taskBean) throws Exception{
        return  workflowService.jumpFlowTask(taskBean);
    }

    /**
	/**
	 * 根据流程实例id查询任务
	 * @param processInstanceId
	 * @return
	 * @throws Exception
	 */
	public TaskBean getTasksByProcessInstanceId(String processInstanceId) throws Exception{
		return workflowService.getTasksByProcessInstanceId(processInstanceId);
	}
	/**
	 * 返回会签任务的审批状态
	 * @param taskInfo
	 */
	public TaskBean getMultiStatus(TaskInfo taskInfo)  {
		return this.workflowService.getMultiStatus(taskInfo);
	}

	/**
	 * 根据流程实例id获取所有的下一步审批任务
	 * @param taskInfo
	 * @return
	 * @throws
	 */
	public TaskBean getAllNextTaskInfo(TaskInfo taskInfo) throws Exception{
		return  workflowService.getAllNextTaskInfo(taskInfo);
	}

	public IWorkflowService getWorkflowService() {
		return workflowService;
	}

	public void setWorkflowService(IWorkflowService workflowService) {
		this.workflowService = workflowService;
	}
	
	/**
     * 获取所有流程分类的待办数据
     * @return
     */
	public List<TaskBean> getAllClassifyPendTasks(TaskInfo taskInfo){
		return workflowService.getAllClassifyPendTasks(taskInfo);
	}
	
	/**
     * 获取所有流程分类的已办数据
     * @return
     */
	public List<TaskBean> getAllClassifyCompletedTasks(TaskInfo taskInfo){
		return workflowService.getAllClassifyCompletedTasks(taskInfo);
	}
	
	/**
	 * 获取按钮信息
	 * @param taskInfo
	 * @return
	 */
	public TaskBean findButtonPower(TaskInfo taskInfo){
		return workflowService.findButtonPower(taskInfo);
	}

	/**
	 * 获取流程变量
	 * @param processInfo
	 * @return
	 */
	public TaskBean findProcessVariable(ProcessInfo processInfo){
		return workflowService.findProcessVariable(processInfo);
	}

	/**
     * 根据流程实例id获取该流程实例已办任务列表
     * @param processInfo
     * @return
     */
	public TaskBean findHiTaskByProcessInstanceId(ProcessInfo processInfo){
		return workflowService.findHiTaskByProcessInstanceId(processInfo);
	}

	/**
	 * 删除整个流程实例，包括待办已办审批历史等所有数据
	 * @param info
	 * @return
	 */
	public ProcessBean deleteProcessInstance(ProcessInfo info) {
		return workflowService.deleteProcessInstance(info);
	}

	/**
	 * 根据流程定义key获取任务节点信息
	 * @param taskInfo
	 * @return
	 */
	public TaskBean getTaskDefinitionsByKey(TaskInfo taskInfo) {
		return workflowService.getTaskDefinitionsByKey(taskInfo);
	}

	/**
	 * 根据流程定义key和第一个节点的id获取属性信息
	 * @param taskInfo
	 * @return
	 */
	public TaskBean getFirstTaskDefine(TaskInfo taskInfo) {
		return workflowService.getFirstTaskDefine(taskInfo);
	}

    /**
     * 批量发起流程，支持批量回滚
     */
    public List<ProcessBean> batchStartProcess(List<ProcessInfo> processInfoList) throws  Exception{
        return  workflowService.batchStartProcess(processInfoList);
    }

    /**
     * 批量发起流程，支持批量回滚
     */
    public List<ProcessBean> batchStartAndSubmitProcess(List<ProcessInfo> processInfoList) throws  Exception{
        return  workflowService.batchStartAndSubmitProcess(processInfoList);
    }

	/**
	 * 批量退回流程
	 */
	public List<TaskBean> batchBackTask(List<TaskBean> taskBeanList) throws  Exception{
		return  workflowService.batchBackTask(taskBeanList);
	}

	/**
	 * 流程结束后获取流程变量信息
	 * @param processInfo
	 */
	public TaskBean findFinishedVariable(ProcessInfo processInfo){
		return workflowService.findFinishedVariable(processInfo);
	}

	/**
	 * 查询流程分类列表
	 * @param classifyInfo
	 */
	public List<ClassifyInfo>  findClassifyList(ClassifyInfo classifyInfo){
		return workflowService.findClassifyList(classifyInfo);
	}

	/**
	 * 复制流程模型
	 * @param modelInfo
	 */
	public ProcessBean copyModel(ModelInfo modelInfo) throws Exception {
		return workflowService.copyModel(modelInfo);
	}

	/**
	 * 获取任务，已阅、未阅状态
	 * @param taskInfo
	 * @return
	 */
	public TaskBean getSignTaskStatus(TaskInfo taskInfo) {
		return workflowService.getSignTaskStatus(taskInfo);
	}
	
	public  TaskBean  findPendOrgTaskList(TaskInfo taskInfo) {
	   return  workflowService.findOrgTaskList(taskInfo);
	}

	/**
	 * 发送消息给接收节点，接收节点接收到后往下继续执行
	 * @param processInfo
	 * @return
	 */
	public ProcessBean messageConfirmProcessInstanceId(ProcessInfo processInfo){
		return workflowService.messageConfirmProcessInstanceId(processInfo);
	}

	public QueryBean findOrgTaskCount(TaskInfo taskInfo){
		 Map<String,Object>  params = new HashMap<>();
		 params.put("tenantId", taskInfo.getTenantId());
		 return  this.workflowService.findOrgTaskCount(params);
	}
	
	public  ProcessBean  deleteModel(List<String> deploymentIds) {
		return  this.workflowService.deleteModelList(deploymentIds);
	}

	public Map<String, Object> findErrorServiceTask(Map<String,Object> map) {
		return this.workflowService.findErrorServiceTask(map);
	}

	public TaskBean retryServiceTask(TaskInfo taskInfo) {
		return this.workflowService.retryServiceTask(taskInfo);
	}

    public TaskBean reAssignmentTask(TaskInfo taskInfo) {
		return this.workflowService.reAssignmentTask(taskInfo);
    }

	public TaskBean claimWorkInfo(TaskInfo taskInfo) {
		return this.workflowService.claimWorkInfo(taskInfo);
	}

	public TaskBean findTodoList(TaskInfo taskInfo) {
		return this.workflowService.findTodoList(taskInfo);
	}

	public ProcessBean batchTerminateProcess(List<ProcessInfo> info) {
		return this.workflowService.batchTerminateProcess(info);
	}

	/**
	 * 设置流程变量
	 * @param processInfo
	 * @return
	 */
	public ProcessBean setProcessVariable(ProcessInfo processInfo){
		return workflowService.setProcessVariable(processInfo);
	}
}
