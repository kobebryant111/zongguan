package com.tansun.workflow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tansun.workflow.model.*;
import com.tansun.workflow.utils.RestTemplateUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * http 远程调用类
 */
public class WorkFlowHttpClientService {

	private String address;
	private String username;
	private String password;
	private String token;

	static private RestTemplate restTemplate = new RestTemplate();

	public static Logger logger = Logger.getLogger(WorkFlowHttpClientService.class.toString());

	public WorkFlowHttpClientService() {
		super();
	}

	public void initToken(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", username);
		jsonObject.put("password", password);
		String result = RestTemplateUtils.post(address + "/auth/createToken", jsonObject.toJSONString());
		if(StringUtils.isEmpty(result)){
			throw new RuntimeException("工作流权限校验失败");
		}
		JSONObject tokenResult = JSON.parseObject(result);
		this.token = tokenResult.getString("access_token");
	}

	public WorkFlowHttpClientService(String address, String username, String password) {
		this.address = address;
		this.username = username;
		this.password = password;
		initToken();
	}

	private String parseString(String url, Object params) throws Exception {
        if (params == null) {
            throw new Exception("params is null");
        }
        String paramsJson = null;
        if (params instanceof String) {
            paramsJson = (String) params;
        } else {
            //加上SerializerFeature.DisableCircularReferenceDetect
            //防止出现fastjson出现$ref，如：{"$ref":"$.formData"}转换map后是null
            paramsJson = JSON.toJSONString(params, SerializerFeature.DisableCircularReferenceDetect);
        }
        logger.info("url is :" + url);
        logger.info("invoke params is :" + paramsJson);

		String result = post(url, paramsJson);
		logger.info("result content is :" + result);
		return result;
    }

	public String post(String url, String paramsJson) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
		httpHeaders.set("token",this.token);
		HttpEntity requestEntity = new HttpEntity(paramsJson, httpHeaders);;
		String result = null;
		try {
			result = restTemplate.postForObject(url, requestEntity, String.class);
		} catch (RestClientException e) {
			if (e instanceof HttpClientErrorException){
				// token过期，重新获取
				initToken();
				httpHeaders.set("token",this.token);
				return restTemplate.postForObject(url, new HttpEntity(paramsJson, httpHeaders), String.class);
			}if (e instanceof HttpServerErrorException){
				throw new RuntimeException(((HttpServerErrorException) e).getResponseBodyAsString());
			}else {
				throw new RuntimeException(e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 通用远程调用请求
	 * @return
	 */
	private <T> T doPost(String url, Object params, Class<T> resultType) throws Exception {
        String result = parseString(url,params);
		if (String.class == resultType) {
			return (T) result;
		} else {
			T t = JSON.parseObject(result, resultType);
			return t;
		}
	}

    /**
     * List<TaskBean>
     * @param resultType TaskBean.class
     */
	private <T> List<T> doPostArray(String url, Object params, Class<T> resultType) throws Exception {
        String result = parseString(url,params);
        List<T> t = JSON.parseArray(result, resultType);
        return t;
	}


	/**
	 * 创建模型通过modelInfo
	 *
	 */
	public ProcessBean createModelByInfo(ModelInfo modelInfo) throws Exception {
		String url = address + "/workflow/json/model/createModelByInfo";
		return doPost(url, modelInfo, ProcessBean.class);
	}

	/**
	 * 启动流程
	 *
	 */
	public ProcessBean startProcess(ProcessInfo info) throws Exception {
		String url = address + "/workflow/json/processInstance/startProcess";
		return doPost(url, info, ProcessBean.class);
	}

	


	/**
	 * 启动流程并且提交
	 */
	public ProcessBean startAndSubmitProcess(ProcessInfo info) throws Exception {
		String url = address + "/workflow/json/processInstance/startAndSubmitProcess";
		return doPost(url, info, ProcessBean.class);
	}

	/**
	 * 根据流程实例ID终止流程
	 */
	public ProcessBean terminateProcess(ProcessInfo info) throws Exception {
		String url = address + "/workflow/json/processInstance/terminateProcess";
		return doPost(url,info,ProcessBean.class);
	}


	/**
	 * 挂起流程实列
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public ProcessBean suspendProcess(ProcessInfo info) throws Exception {
		String url = address + "/workflow/json/processInstance/suspendProcess";
		return doPost(url,info,ProcessBean.class);
	}




	/**
	 * 激活流程实列
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public ProcessBean activateProcess(ProcessInfo info) throws Exception {
		String url = address + "/workflow/json/processInstance/activateProcess";
		return doPost(url,info,ProcessBean.class);
	}

	/**
	 * 查询业务流程轨迹
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public TaskBean queryProcessTrack(ProcessInfo info) throws Exception {
		String url = address + "/workflow/json/processInstance/queryProcessTrack";
		return doPost(url,info,TaskBean.class);
	}

	/**
	 * 获取运行中的流程
	 */
	public ProcessBean processInstanceList(ProcessInfo info) throws Exception {
		String url = address + "/workflow/json/processInstance/processInstanceList";
		return doPost(url,info,ProcessBean.class);
	}
	
	/**
     * 流程定义列表
     *
     * @return
     */
    public ProcessBean processList(ProcessInfo info) throws Exception {
    	String url = address + "/workflow/json/processDefinition/definitionList";
    	return doPost(url,info,ProcessBean.class);
    }
	/************************************task****************************/

	/**
	 * 提交任务
	 */
	public TaskBean submitTask(TaskBean taskBean)throws Exception {
		String url = address + "/workflow/json/task/submitTask";
		return doPost(url,taskBean,TaskBean.class);
	}

	/**
	 * 退回到任务前一节点／收回
	 */
	public TaskBean backPreTask(TaskBean taskBean) throws Exception {
		String url = address + "/workflow/json/task/backPreTask";
		return doPost(url,taskBean,TaskBean.class);
	}

	/**
	 * 退回到指定节点
	 */
	public TaskBean backTask(TaskBean taskBean) throws Exception {
		String url = address + "/workflow/json/task/backTask";
		return doPost(url,taskBean,TaskBean.class);
	}

	/**
	 * 退回到第一个用户审批节点
	 */
	public   TaskBean backToFirstUserTask(TaskBean taskBean) throws Exception {
		String url = address + "/workflow/json/task/backToFirstUserTask";
		return doPost(url,taskBean,TaskBean.class);
	}

	/**
	 * 获取任务
	 * @param taskInfo
	 */
	public   TaskBean getTask(TaskInfo taskInfo) throws Exception {
		String url = address + "/workflow/json/task/getTask";
		return doPost(url,taskInfo,TaskBean.class);
	}

	/**
	 * 释放任务
	 * @param taskInfo
	 */

	public   TaskBean releaseTask(TaskInfo taskInfo) throws Exception {
		String url = address + "/workflow/json/task/releaseTask";
		return doPost(url,taskInfo,TaskBean.class);
	}

	/**
	 * 查询任务列表
	 * 3 = 已办
	 * 非3 = 待办
	 */
	public TaskBean findTaskList(TaskInfo info) throws Exception {
		String url = address + "/workflow/json/task/taskList";
		return doPost(url,info,TaskBean.class);
	}

	/**
	 * 根据taskId查询任务详情(任务信息按钮信息等)
	 */
	@Deprecated
	public TaskBean findTaskInfo(TaskInfo info) throws Exception {
		String url = address + "/workflow/json/task/findTaskInfo";
		return doPost(url,info,TaskBean.class);
	}

	/**
	 * 自由流转
	 */
	public TaskBean goFlowTask(TaskBean taskBean) throws Exception{
		String url = address + "/workflow/json/task/goFlowTask";
		return doPost(url,taskBean,TaskBean.class);
	}

	/**
	 * 自由流转(根据taskList、nextTaskId、userId)
	 */
	public TaskBean jumpFlowTask(TaskBean taskBean) throws Exception{
		String url = address + "/workflow/json/task/jumpFlowTask";
		return doPost(url,taskBean,TaskBean.class);
	}

	/**
	 * 加签
	 */
	public TaskBean addGroupUser(TaskBean taskBean) throws Exception{
		String url = address + "/workflow/json/task/addGroupUser";
		return doPost(url,taskBean,TaskBean.class);
	}

	/**
	 * 减签
	 */
	public TaskBean delGroupUser(TaskBean taskBean) throws Exception{
		String url = address + "/workflow/json/task/delGroupUser";
		return  doPost(url,taskBean,TaskBean.class);
	}


	/**
	 * json byte -> json -> xml -> xml bytes
	 * 根据模型部署流程
	 * @param modelId
	 * @return
	 */
	public ProcessBean deployeBymodel(String modelId)throws Exception {
		String url = address + "/workflow/json/task/deployeBymodel";
		return doPost(url,modelId,ProcessBean.class);
	}

	/**
	 * 获取流程变量
	 */
	public TaskBean findProcessVariable(ProcessInfo processInfo) throws Exception{
		String url = address + "/workflow/json/task/findProcessVariable";
		return doPost(url,processInfo,TaskBean.class);
	}
	/**
	 * 获取按钮信息
	 * @param taskInfo
	 * @return
	 */
	public TaskBean findButtonPower(TaskInfo taskInfo) throws Exception{
		String url = address + "/workflow/json/task/findButtonPower";
		return doPost(url,taskInfo,TaskBean.class);
	}


	/**
	 * 取回任务
	 * @param taskBean
	 * @return
	 */
	public TaskBean rebackTask(TaskBean taskBean) throws Exception {
		String url = address + "/workflow/json/task/rebackTask";
		return doPost(url,taskBean,TaskBean.class);
	}

	/**
	 * 抄送任务 查询
	 */
	public TaskBean findCarbonCopyTasks(TaskInfo taskInfo) throws Exception {
		String url = address + "/workflow/json/task/findCarbonCopyTasks";
		return doPost(url,taskInfo,TaskBean.class);
	}

    /**
     * 根据流程实例id获取该流程实例已办任务列表
     */
    public TaskBean findHiTaskByProcessInstanceId(ProcessInfo processInfo) throws Exception {
        String url = address + "/workflow/json/task/findHiTaskByProcessInstanceId";
        return doPost(url,processInfo,TaskBean.class);
    }
    
    /**
     * 
     * @param taskInfo
     * @return
     * @throws Exception 
     */
    public TaskBean findHiTaskPoolExecuteDataList(TaskInfo taskInfo) throws Exception{
    	String url = address + "/workflow/json/task/findHiTaskPoolExecuteDataList";
        return doPost(url,taskInfo,TaskBean.class);
    }

    
    /**
     * 流程定义列表
     */
    public ProcessBean processDefinitionList(ProcessInfo processInfo) throws Exception {
        String url = address + "/workflow/json/processDefinition/definitionList";
        return doPost(url,processInfo,ProcessBean.class);
    }

	/**
	 * 重试流程中出异常的serviceTask节点
	 */
	public TaskBean serviceTaskRetry(TaskBean taskBean)throws Exception {
		String url = address + "/workflow/json/task/serviceTaskRetry";
		return doPost(url,taskBean,TaskBean.class);
	}

	/**
	 * 获取流程实例下的任务
	 */
	public TaskBean getTasksByProcessInstanceId(String processInstanceId) throws Exception {
		String url = address + "/workflow/json/task/getTasksByProcessInstanceId";
		return doPost(url,processInstanceId,TaskBean.class);
	}

	public TaskBean userTaskCount(TaskInfo taskInfo) throws Exception {
		String url = address + "/workflow/json/task/userTaskCount";
		return doPost(url,taskInfo,TaskBean.class);
	}

	/**
	 * 模型列表
	 */
	public ProcessBean modelList(ProcessInfo processInfo) throws Exception {
		String url = address + "/workflow/json/model/modelList";
		return doPost(url,processInfo,ProcessBean.class);
	}


	/**
	 * 获取下一步所有任务
	 */
	public TaskBean getAllNextTaskInfo(TaskInfo taskInfo) throws Exception {
		String url = address + "/workflow/json/task/getAllNextTaskInfo";
		return doPost(url,taskInfo,TaskBean.class);
	}

	/**
	 * 删除整个流程实例，包括历史数据
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public ProcessBean deleteProcessInstance(ProcessInfo info) throws Exception {
		String url = address + "/workflow/json/processInstance/deleteProcessInstance";
		return doPost(url,info,ProcessBean.class);
	}

	/**
	 * 根据流程定义id获取任务节点信息
	 * @param processDefinitionId
	 * @return
	 */
	public TaskBean getTaskDefinitions(String processDefinitionId) throws Exception {
		String url = address + "/workflow/json/task/getTaskDefinitions";
		return doPost(url,processDefinitionId,TaskBean.class);
	}

	/**
	 * 根据流程定义key获取任务节点信息
	 * @param taskInfo
	 * @return
	 */
	public TaskBean getTaskDefinitionsByKey(TaskInfo taskInfo) throws Exception {
		String url = address + "/workflow/json/task/getTaskDefinitionsByKey";
		return doPost(url,taskInfo,TaskBean.class);
	}

	/**
	 * 先查分类树,并获取每个分类下的已办扩展表头
	 * @return
	 */
	public List<TaskBean> getAllClassifyCompletedTasks(TaskInfo taskInfo) throws Exception {
		String url = address + "/workflow/json/task/getAllClassifyCompletedTasks";
		return doPostArray(url, taskInfo, TaskBean.class);
	}

	/**
	 * 先查分类树,并获取每个分类下的待办扩展表头
	 * @return
	 */
	public List<TaskBean> getAllClassifyPendTasks(TaskInfo taskInfo) throws Exception {
		String url = address + "/workflow/json/task/getAllClassifyPendTasks";
		return doPostArray(url, taskInfo, TaskBean.class);
	}

	/**
	 * 批量发起流程，支持批量回滚
	 */
	public List<ProcessBean> batchStartProcess(List<ProcessInfo> processInfoList) throws  Exception{
		String url = address + "/workflow/json/processInstance/batchStartProcess";
		return doPostArray(url,processInfoList,ProcessBean.class);
	}

	/**
	 * 批量发起流程，支持批量回滚
	 */
	public List<ProcessBean> batchStartAndSubmitProcess(List<ProcessInfo> processInfoList) throws  Exception{
		String url = address + "/workflow/json/processInstance/batchStartAndSubmitProcess";
		return doPostArray(url,processInfoList,ProcessBean.class);
	}

    /**
     * 根据流程定义key和第一个节点的id获取属性信息
     * @param taskInfo
     * @return
     */
    public TaskBean getFirstTaskDefine(TaskInfo taskInfo) throws Exception {
        String url = address + "/workflow/json/task/getFirstTaskDefine";
        return doPost(url,taskInfo,TaskBean.class);
    }

	/**
	 * 根据传入批量执行的对象进行批量执行
	 * @return
	 */
	public List batchExecuteTask(BatchExecuteParam batchExecuteParam) throws Exception {
		String url = address + "/workflow/json/processInstance/batchExecuteTask";
		return doPost(url,batchExecuteParam,List.class);
	}



	/**
     * 批量提交任务
     * @param taskBeans 当前任务信息
     */
    public List<TaskBean> batchSubmitTask(List<TaskBean> taskBeans) throws Exception {
        String url = address + "/workflow/json/processInstance/batchSubmitTask";
        return doPostArray(url,taskBeans,TaskBean.class);
    }

    /**
     * 获取业务处理类
     *
     * @param taskInfo
     * @return
     */
    public TaskBean findClassName(TaskInfo taskInfo) throws Exception {
        String url = address + "/workflow/json/task/findClassName";
        return doPost(url,taskInfo,TaskBean.class);
    }

    /**
     * 获取分类及其子分类的所有已发布流程创建信息
     * @param processInfo
     * @return
     */
    public ProcessBean findProcessByCode(ProcessInfo processInfo) throws Exception  {
        String url = address + "/workflow/json/processDefinition/findProcessByCode";
        return doPost(url,processInfo,ProcessBean.class);
    }

    /**
     * 标记任务，已阅、未阅
     * @param taskInfo
     * @return
     */
    public TaskBean signTaskStatus(TaskInfo taskInfo) throws Exception {
        String url = address + "/workflow/json/task/signTaskStatus";
        return doPost(url,taskInfo,TaskBean.class);
    }

	/**
	 * 移除任务已阅标记
	 * @param taskInfo
	 * @return
	 */
	public TaskBean removeTaskStatus(TaskInfo taskInfo) throws Exception {
		String url = address + "/workflow/json/task/removeTaskStatus";
		return doPost(url,taskInfo,TaskBean.class);
	}


    /**
     * 获取任务，已阅、未阅状态
     * @param taskInfo
     * @return
     */
    public TaskBean getSignTaskStatus(TaskInfo taskInfo) throws Exception {
        String url = address + "/workflow/json/task/getSignTaskStatus";
        return doPost(url,taskInfo,TaskBean.class);
    }

	/**
	 * 转办任务
	 * @param taskInfo
	 * @return
	 * @throws Exception
	 */
	public TaskBean transferTask(TaskInfo taskInfo) throws Exception {
		String url = address + "/workflow/json/task/transferTask";
		return doPost(url,taskInfo,TaskBean.class);
	}

	/**
	 * 返回会签任务的审批状态
	 * @param taskInfo
	 */
	public TaskBean getMultiStatus(TaskInfo taskInfo) throws Exception {
		String url = address + "/workflow/json/task/getMultiStatus";
		return doPost(url,taskInfo,TaskBean.class);
	}

	/**
	 * 流程结束后获取流程变量信息
	 * @param processInfo
	 * @return
	 */
	public TaskBean findFinishedVariable(ProcessInfo processInfo) throws Exception{
		String url = address + "/workflow/json/task/findFinishedVariable";
		return doPost(url,processInfo,TaskBean.class);
	}

    /**
     * 查询流程分类列表
     */
    public List<ClassifyInfo> findClassifyList(ClassifyInfo classifyInfo) throws Exception{
        String url = address + "/workflow/json/task/findClassifyList";
        return doPostArray(url,classifyInfo,ClassifyInfo.class);
    }


	/**
	 * 离岗移交
	 * @param wfLeaveTransfer
	 * @return
	 */
	public TaskBean saveLeaveTransfer(WfLeaveTransfer wfLeaveTransfer) throws Exception{
		String url = address + "/workflow/json/LeaveTransfer/saveLeaveTransfer";
		return doPost(url,wfLeaveTransfer,TaskBean.class);
	}

	/**
	 * 离岗移交终止
	 * @param wfLeaveTransfer
	 * @return
	 */
	public TaskBean stopLeaveTransfer(WfLeaveTransfer wfLeaveTransfer) throws Exception{
		String url = address + "/workflow/json/LeaveTransfer/stopLeaveTransfer";
		return doPost(url,wfLeaveTransfer,TaskBean.class);
	}

	/**
	 * 添加一条离岗移交配置
	 * @param leaveTransfer
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public TaskBean addLeaveTransferConfig(LeaveTransfer leaveTransfer) throws Exception{
		String url = address + "/workflow/json/task/addLeaveTransferConfig";
		return doPost(url,leaveTransfer,TaskBean.class);
	}

	/**
	 * 获取流程回调类
	 * @param processBean
	 * @return
	 */
	public ProcessBean findProcessHandleClass(ProcessBean processBean) throws Exception{
		String url = address + "/workflow/json/processInstance/findProcessHandleClass";
		return doPost(url,processBean,ProcessBean.class);
	}


	/**
	 * 复制流程模型
	 * @param modelInfo
	 */
	@ResponseBody
	public ProcessBean copyModel(ModelInfo modelInfo) throws Exception {
		String url = address + "/workflow/json/model/copyModel";
		return doPost(url,modelInfo,ProcessBean.class);
	}

	/**
	 * 根据流程信息获取待办已办扩展配置
	 * @param processInfo
	 */
	@ResponseBody
	public ProcessBean getExpandPlanByProcessInfo(ProcessInfo processInfo) throws Exception {
		String url = address + "/workflow/json/process/getExpandPlanByProcessInfo";
		return doPost(url,processInfo,ProcessBean.class);
	}

	/**
	 * 根据任务来可跳转节点信息
	 * @param taskInfo
	 */
	@ResponseBody
	public TaskBean getSkipTaskNodes(TaskInfo taskInfo) throws Exception{
		String url = address + "/workflow/json/task/getSkipTaskNodes";
		return doPost(url,taskInfo,TaskBean.class);
	}

	/**
	 * 根据任务来可跳转节点信息
	 * @param taskInfo
	 */
	@ResponseBody
	public TaskBean findTaskCounts(TaskInfo taskInfo) throws Exception{
		String url = address + "/workflow/json/task/findTaskCounts";
		return doPost(url,taskInfo,TaskBean.class);
	}

	/**
	 * 查询待办任务
	 * @param taskInfo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	public TaskBean findPendOrgTaskList(TaskInfo taskInfo) throws Exception{
		String url = address + "/workflow/json/task/findPendOrgTaskList";
		return doPost(url,taskInfo,TaskBean.class);
	}

	/**
	 * 根据当前任务ID查找下一人工任务
	 */
	@ResponseBody
	public TaskBean queryNextTaskList(TaskInfo taskInfo) throws Exception{
		String url = address + "/workflow/json/task/queryNextTaskList";
		return doPost(url,taskInfo,TaskBean.class);
	}

	/**
	 * 部署流程
	 */
	@ResponseBody
	public ProcessBean deploy(DeployInfo deployInfo) throws Exception{
		String url = address + "/workflow/json/task/deploy";
		return doPost(url,deployInfo,ProcessBean.class);
	}

	/**
	 * 创建流程图
	 */
	public ProcessBean createModel(ModelInfo modelInfo) throws Exception{
		String url = address + "/workflow/json/task/createModel";
		return doPost(url,modelInfo,ProcessBean.class);
	}

	/**
	 * 删除流程图
	 */
	public ProcessBean deleteModel(ModelInfo modelInfo) throws Exception{
		String url = address + "/workflow/json/task/deleteModel";
		return doPost(url,modelInfo,ProcessBean.class);
	}

	/**
	 * 获取已完成的流程实例
	 */
	public ProcessBean getCompletedProcess(ProcessInfo processInfo) throws Exception{
		String url = address + "/workflow/json/processInstance/getCompletedProcess";
		return doPost(url,processInfo,ProcessBean.class);
	}

	/**
	 * 获取已完成的流程实例
	 */
	public QueryBean findOrgTaskCount(TaskInfo taskInfo) throws Exception{
		String url = address + "/workflow/json/task/findOrgTaskCount";
		return doPost(url,taskInfo,QueryBean.class);
	}

	public TaskBean retryServiceTask(TaskInfo taskInfo) throws Exception{
		String url = address + "/workflow/json/task/retryServiceTask";
		return doPost(url,taskInfo,TaskBean.class);
	}


	public TaskBean reAssignmentTask(TaskInfo taskInfo) throws Exception{
		String url = address + "/workflow/json/task/reAssignmentTask";
		return doPost(url,taskInfo,TaskBean.class);
	}

	public TaskBean claimWorkInfo(TaskInfo taskInfo) throws Exception{
		String url = address + "/workflow/json/task/claimWorkInfo";
		return doPost(url,taskInfo,TaskBean.class);
	}

	public ProcessBean batchterminateProcess(List<ProcessInfo> info) throws Exception{
		String url = address + "/workflow/json/task/claimWorkInfo";
		return doPost(url,info,ProcessBean.class);
	}

	/**
	 * 根据userId获取所有的待办任务
	 * @param taskInfo
	 * @return
	 * @throws Exception
	 */
	public TaskBean findTodoList(TaskInfo taskInfo) throws Exception{
		String url = address + "/workflow/json/task/findTodoList";
		return doPost(url,taskInfo,TaskBean.class);
	}

	/**
	 * 设置流程变量
	 * @param processInfo
	 * @return
	 */
	public ProcessBean setProcessVariable(ProcessInfo processInfo) throws Exception{
		String url = address + "/workflow/json/processInstance/setProcessVariable";
		return doPost(url,processInfo,ProcessBean.class);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
