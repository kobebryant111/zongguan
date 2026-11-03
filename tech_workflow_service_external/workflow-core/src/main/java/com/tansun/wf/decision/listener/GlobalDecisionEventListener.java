//package com.tansun.wf.decision.listener;
//
//// camunda-TODO 如何写事件监听器
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import com.tansun.workflow.model.ActExpandTracelog;
//import org.camunda.bpm.engine.delegate.event.ActivitiEvent;
//import org.camunda.bpm.engine.delegate.event.ActivitiEventListener;
//import org.camunda.bpm.engine.delegate.event.ActivitiEventType;
//import org.camunda.bpm.engine.delegate.event.impl.ActivitiEntityEventImpl;
//import org.camunda.bpm.engine.delegate.event.impl.ActivitiEventImpl;
//import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
//import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//
//import com.tansun.wf.decision.ExecutionDB;
//import com.tansun.wf.decision.handler.CollectServiceHandler;
//import com.tansun.wf.decision.handler.EventHandler;
//import com.tansun.wf.decision.handler.InvokeType;
//import com.tansun.wf.decision.service.ExternalService;
///**
// * 自动审批全局监听事件
// */
//public class GlobalDecisionEventListener implements ActivitiEventListener,ApplicationContextAware{
//
//	private ApplicationContext act;
//
//	private Map<String,String> handlers = new HashMap<String, String>();
//
//	private static final String AUTO_KEY = "_AUTO";
//
//
//	@Override
//	public boolean isFailOnException() {
//		return true;
//	}
//
//	@Override
//	public void onEvent(ActivitiEvent activitievent) {
//		String eventHandlerBeanId=handlers.get(activitievent.getType().name());
//		EventHandler eventHandler = null;
//		if(eventHandlerBeanId != null){
//			eventHandler = (EventHandler) act.getBean(eventHandlerBeanId);
//		}
//		try {
//			if(activitievent.getType() ==  ActivitiEventType.PROCESS_COMPLETED){
//				ActivitiEntityEventImpl eventImpl=(ActivitiEntityEventImpl)activitievent;
//				ExecutionEntity executionEntity = (ExecutionEntity) eventImpl.getEntity();
//				if(AUTO_KEY.equals(executionEntity.getVariable(AUTO_KEY))){ //如果是自动审批,进行回掉处理
//					eventHandler.handler(activitievent);
//				}
//			} else if(activitievent.getType() ==  ActivitiEventType.ACTIVITY_STARTED){
//				ActivitiEventImpl eventImpl=(ActivitiEventImpl)activitievent;
//				String instanceId = eventImpl.getProcessInstanceId();
//				if(instanceId.endsWith("-collect-retry")){ //征信数据源采集节点重试
//					String executionId = eventImpl.getExecutionId().replace("-collect-retry", "");
//					ExecutionEntity executionEntity = (ExecutionEntity) eventImpl.getEngineServices().getRuntimeService().createExecutionQuery().executionId(executionId).singleResult();
//					CollectServiceHandler collect = act.getBean(CollectServiceHandler.class);
//					collect.dealBusiness(executionEntity, InvokeType.COLLEC_DATA, new ActExpandTracelog());
//				}
//			} else if(activitievent.getType() == ActivitiEventType.TASK_COMPLETED){
//				ActivitiEntityEventImpl eventImpl =(ActivitiEntityEventImpl)activitievent;
//				TaskEntity task = (TaskEntity) eventImpl.getEntity();
//				if(AUTO_KEY.equals(task.getVariable(AUTO_KEY))){
//					//往日志轨迹插入人工审批记录
//					ExternalService externalService = (ExternalService) act.getBean(ExternalService.class);
//					ActExpandTracelog log = new ActExpandTracelog();
//					log.setId(UUID.randomUUID().toString());
//					log.setBussinessKey(task.getProcessInstance().getBusinessKey());
//					log.setProcessInstanceId(task.getProcessInstanceId());
//					log.setNodeId(task.getTaskDefinitionKey());
//					log.setNodeName(task.getName());
//					String timeStr = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss:SSS").format(new Date());
//					log.setInvokeTimes(timeStr);
//					log.setInvokeType("USER_TASK");
//					log.setIsSucess("N");
//					ExecutionDB executionDB = new ExecutionDB(task.getExecution());
//					Map<String, Object> applyData = executionDB.getBlockData(ExecutionDB._APPLY_DATA_BLOCK);
//					log.setLegalPersonNum(applyData.get("legalPerNum") +"");
//					log.setInputParam("");
//					log.setSyncResult("审批完成");
//					log.setAsyncResult("");
//					externalService.logTrace(log );
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void setApplicationContext(ApplicationContext act)
//			throws BeansException {
//		this.act = act;
//	}
//
//	public Map<String, String> getHandlers() {
//		return handlers;
//	}
//
//	public void setHandlers(Map<String, String> handlers) {
//		this.handlers = handlers;
//	}
//
//}