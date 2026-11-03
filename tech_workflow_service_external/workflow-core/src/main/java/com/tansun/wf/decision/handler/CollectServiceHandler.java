package com.tansun.wf.decision.handler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tansun.wf.decision.ExecutionDB;
import com.tansun.wf.decision.model.ThirdInterface;
import com.tansun.wf.decision.model.ThirdPartyDataSourceConfigModel;
import com.tansun.wf.decision.model.TirdPartyParam;
import com.tansun.wf.decision.service.ExternalService;
import com.tansun.wf.decision.service.StandardDataSourceConfigService;
import com.tansun.workflow.model.ActExpandTracelog;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("collectServiceHandler")
@Transactional(rollbackFor=Exception.class)
public class CollectServiceHandler implements WorkflowBusinessHandler{

	private static final String EXECUTION_ID = "executionId";
	
	private static Logger logger = LoggerFactory.getLogger(CollectServiceHandler.class);


	@Autowired
	private StandardDataSourceConfigService standardDataSouceParser;
	
	@Autowired
	private ExternalService externalService;
	
	private static ExecutorService executorService = Executors.newFixedThreadPool(20);
	
	/**
	 * 采集数据
	 */
	@Override
	public Object dealBusiness(DelegateExecution execution, String invokeType, ActExpandTracelog log) throws Exception {
		ThirdPartyDataSourceConfigModel interfaceConfig = getInterfaceConfig(execution);
		String interfaceKey = interfaceConfig.getDataSource();
		
		List<TirdPartyParam> params = interfaceConfig.getParams();
		
		if(StringUtils.isBlank(interfaceKey)){
			throw new Exception("{id:"+execution.getCurrentActivityId()+",name "+execution.getCurrentActivityName()+"}  , interfaceKey is null !!!");
		}
		
		if(params == null){
            params = new ArrayList<TirdPartyParam>();
		}
		
		//req 拼装接口调用请求参数
		Map<String,Object> applyData = new ExecutionDB(execution).getBlockData(ExecutionDB._APPLY_DATA_BLOCK);
		Map<String,Object> collectData = new ExecutionDB(execution).getBlockData(ExecutionDB._COLLECT_DATA_BLOCK);
		
		//collectData 可以覆盖 applyData
		Map<String,Object> data = new HashMap<String,Object>();
		data.putAll(applyData);
		data.putAll(collectData);
		
		Map<String,Object> req = new HashMap<String,Object>();
		for (int i = 0; i < params.size(); i++) {
			String standardKey = params.get(i).getStandardKey();
			String changeKey = params.get(i).getChangeKey();
			String fixedValue = params.get(i).getFixedValue();
			//只要fixedValue不为空，就使用该值
			if(StringUtils.isNotBlank(fixedValue)){
				req.put(changeKey, fixedValue);
			}else{
				req.put(changeKey, data.get(standardKey));
			}
		}
		
		ThirdInterface third = new ThirdInterface();
		third.setInterfaceKey(interfaceKey); //数据源编号

		Map<String,String> channelData = new HashMap<String,String>();
		channelData.put(EXECUTION_ID, execution.getId());
		channelData.put("processInstanceId", execution.getProcessInstanceId());
		channelData.put("nodeId", execution.getCurrentActivityId());
		channelData.put("nodeName", execution.getCurrentActivityName());
		channelData.put("channelType", "process");
		third.setChannelData(channelData);
		third.setReq(req);
		//调用接口
		log.setInputParam(JSON.toJSONString(third));
		third = externalService.invokeThirdInterface(third);
		log.setExpandId(third.getId());

		return handlerResult(execution, third);
	}


	/**
	 * 处理采集节点返回的数据
	 * @param execution
	 * @param result
	 * @return
	 * @throws Exception
	 */
	private Map handlerResult(DelegateExecution execution,ThirdInterface result) throws Exception{
		if(result!=null && result.getSyncDataJson() != null && !"null".equals(result.getSyncDataJson())){ //同步交易
			String dataJson = result.getSyncDataJson();
			ExecutionDB executionDB = new ExecutionDB(execution);
			executionDB.setBlockDataWithKeyNoRepeat(ExecutionDB._COLLECT_DATA_BLOCK, result.getSyncDataJson());
			execution.setVariables(JSON.parseObject(dataJson,Map.class));
			passReceiveTask(execution);
			return JSON.parseObject(StringUtils.isBlank(dataJson)?"{}":dataJson, Map.class);
		}
		return new HashMap();
	}
	
	/**
	 * 同步返回,流转至下一步
	 * @param execution
	 */
	private void passReceiveTask(final DelegateExecution execution){
/*		ExecutionEntity executionEntity = (ExecutionEntity) execution;
		executionEntity.setTransition(null);
		execution.getEngineServices().getRuntimeService().signal(execution.getId());*/
//		NotifyThread thread = new NotifyThread(execution.getId(), execution.getCurrentActivityId(), execution.getEngineServices().getRuntimeService());
//		executorService.submit(thread);
		// camunda-TODO
		final RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				NotifyThread thread = new NotifyThread(execution.getId(), execution.getCurrentActivityId(), runtimeService);
				executorService.submit(thread);
			}
		});
	}
	
	/**
	 * 获取第三方接口配置
	 * @param execution
	 * @return
	 */
	private ThirdPartyDataSourceConfigModel getInterfaceConfig(DelegateExecution execution){
		// camunda-TODO
//		BpmnModel bpmnModel = execution.getEngineServices().getRepositoryService().getBpmnModel(execution.getProcessDefinitionId());
//		FlowElement flowElement = bpmnModel.getMainProcess().getFlowElement(execution.getCurrentActivityId());
//		Map<String, List<ExtensionElement>> elements = flowElement.getExtensionElements();
        ThirdPartyDataSourceConfigModel config= null;
//		if(elements != null){
//			List<ExtensionElement> extensionElements = elements.get("extensiondatasource");
//			if(extensionElements != null && extensionElements.size() >0){
//				String json = extensionElements.get(0).getElementText();
//				config = JSONObject.parseObject(json,ThirdPartyDataSourceConfigModel.class);
//			}
//		}
		return config;
	}

	/**
	 * 同步请求，创建一个线程执行signal操作
	 */
	class NotifyThread implements Runnable{
		private RuntimeService  runtimeService;
		private String executionId;
		private String activityId;
		
		NotifyThread (String exectionId,String activityId,RuntimeService  runtimeService){
			this.executionId = exectionId;
			this.activityId = activityId;
			this.runtimeService = runtimeService;
		}
		
		@Override
		public void run() {
			logger.info("afterCommit signal executionId : " + executionId + ", activityId" + activityId);
			runtimeService.signal(executionId);
//			try {
//				int checkCommitCount = 2000;
//				int checkCommitWaitMills = 50;
//				Thread.sleep(checkCommitWaitMills);
//				while (!isCommit(executionId, activityId) && checkCommitCount >0) { //没有提交，等待50ms
//					logger.info("checkCommitCount ：" + checkCommitCount );
//					Thread.sleep(checkCommitWaitMills);
//					checkCommitCount--;
//				}
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
//
//		/**
//		 * 确保数据已经入库,才唤醒流程操作
//		 * @param executionId
//		 * @param nodeId
//		 * @return
//		 */
//		public boolean isCommit(String executionId,String nodeId){
//			String sql = "SELECT ID_, PROC_INST_ID_,ACT_ID_ FROM act_ru_execution WHERE ID_ = #{id} and ACT_ID_ =#{actId}";
//			Execution execution = runtimeService.createNativeExecutionQuery().sql(sql).parameter("id", executionId).parameter("actId",nodeId).singleResult();
//			if(execution == null || !execution.getActivityId().equals(nodeId)){
//				return false;
//			}else{
//                try{
//                    runtimeService.signal(executionId);
//                }catch (ActivitiOptimisticLockingException e) {
//                    int tryCount = 500;
//                    siganlFailedAndTry(tryCount,executionId);
//                }
//				return true;
//			}
//		}
//
//		public void siganlFailedAndTry(int tryCount, String executionId) {
//			logger.info("executionId:" + executionId + ",siganlFailedAndTry : " + tryCount);
//			try {
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				runtimeService.signal(executionId);
//			} catch (ActivitiOptimisticLockingException e) {
//				tryCount = tryCount - 1;
//				if (tryCount < 0) {
//					return;
//				}else{
//					siganlFailedAndTry(tryCount, executionId);
//				}
//
//			}
//		}
		
	}


}