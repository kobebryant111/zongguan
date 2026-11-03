//package com.tansun.wf.decision.handler;
//
//
//import com.alibaba.fastjson.JSON;
//import com.tansun.wf.decision.ExecutionDB;
//import com.tansun.wf.decision.service.ExternalService;
//import org.camunda.bpm.engine.delegate.event.ActivitiEvent;
//import org.camunda.bpm.engine.delegate.event.impl.ActivitiEntityEventImpl;
//import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.Map;
//
// camunda-TODO 如何定义handler
///**
// * 自动审批流程结束处理类
// */
//@Service("autoApprovalEndHandler")
//@Transactional(rollbackFor=Exception.class)
//public class AutoApprovalEndHandler implements EventHandler{
//
//	//表示该流程为测试流程
//	private static final String TEST_KEY = "_TEST";
//
//	//结束调用服务类
//	private static final String END_SERVICE = "_END_SERVICE";
//
//	@Autowired
//	private ExternalService externalService;
//
//	public void handler(ActivitiEvent activitievent) throws Exception{
//		ActivitiEntityEventImpl eventImpl=(ActivitiEntityEventImpl)activitievent;
//        ExecutionEntity executionEntity = (ExecutionEntity) eventImpl.getEntity();
//        ExecutionDB executionDB = new ExecutionDB(executionEntity);
//		Map<String, Object> applyData = executionDB.getBlockData(ExecutionDB._APPLY_DATA_BLOCK);
//		Map<String, Object> ruleData = executionDB.getBlockData(ExecutionDB._RULE_DATA_BLOCK);
//		Map<String, Object> data = new HashMap<String,Object>();
//		data.putAll(applyData);
//		data.putAll(ruleData);
//		data.putAll(getVariablesFromExecution(executionEntity));
//		if(!TEST_KEY.equals(executionEntity.getVariable(TEST_KEY))){ //非测试流程
//			distribute(data);
//		}
//	}
//
//	public void distribute(Map<String, Object> data) throws Exception{
//		String beanId = String.valueOf(data.get(END_SERVICE));
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("endServiceBeanId", beanId);
//		params.put("pushWorkflowResult", JSON.toJSONString(data));
//		externalService.pushWorkflowResult(params);
//	}
//
//	public Map<String,Object> getVariablesFromExecution(ExecutionEntity executionEntity){
//		Map<String,Object> variables = new HashMap<String,Object>();
//		variables.put("RJT", executionEntity.getVariable("RJT"));//人工审批处理意见
//		variables.put("processInstanceId", executionEntity.getProcessInstanceId());//流程实例Id
//		return variables;
//	}
//}
