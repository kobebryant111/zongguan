//package com.tansun.wf.decision.aspect;
//
//
//import com.alibaba.fastjson.JSON;
//import com.sun.xml.internal.bind.v2.TODO;
//import com.tansun.wf.decision.ExecutionDB;
//import com.tansun.wf.decision.model.AsyncReceiveResult;
//import com.tansun.wf.decision.model.BaseVo;
//import com.tansun.wf.decision.service.ExternalService;
//import com.tansun.workflow.model.ActExpandTracelog;
//import org.camunda.bpm.engine.RuntimeService;
//import org.camunda.bpm.engine.delegate.DelegateExecution;
//import org.camunda.bpm.engine.delegate.DelegateTask;
////import org.camunda.bpm.engine.delegate.event.impl.ActivitiEntityEventImpl;
//import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.camunda.bpm.model.bpmn.impl.instance.camunda.CamundaEntryImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//
//@Component
//@Aspect
//public class TraceLogAspectHandler {
//
//	public TraceLogAspectHandler(){
//		System.out.println();
//	}
//
//	@Autowired
//	private RuntimeService runtimeService;
//
//	@Autowired
//	private ExternalService externalService;
//
//	@Pointcut("execution(* com.tansun.wf.decision.handler.WorkflowBusinessHandler+.dealBusiness(..))")
//	private void dealBusiness(){}
//
//	@Pointcut("execution(* com.tansun.wf.decision.service.AsyncReceiveDataService.asyncRceiveData(..))")
//	private void asyncReceiveData(){}
//
//	@Pointcut("execution(* com.tansun.wf.decision.handler.AutoApprovalEndHandler.handler(..))")
//	private void handlerEnd(){}
//
//	@Pointcut("execution(* com.tansun.wf.decision.handler.TaskCreateServiceHandler.createTask(..))")
//	private void createTask(){}
//
//
//	//业务逻辑处理
//	@Around("dealBusiness()")
//	@Transactional(rollbackFor=Exception.class)
//	public void handlerDealBusiness(ProceedingJoinPoint pjp) throws Throwable{
//		DelegateExecution execution = getDelegateExecution(pjp);
//		String invokeType = getInvokeType(pjp);
//		ActExpandTracelog log = this.createCommonLog(execution);
//		log.setInvokeType(invokeType);
//        try {
//        	//执行
//            Object obj = pjp.proceed();
//            //记录出参
//            if(obj instanceof String){
//            	log.setSyncResult(obj == null?"":obj.toString());
//            }else{
//            	String outputValue = JSON.toJSONString(obj);
//            	log.setSyncResult(outputValue == null?"":outputValue);
//            }
//        } catch (Exception e) {
//            log.setIsSucess("N");
//    		log.setErrorContent(e.getMessage());
//    		log.setCause(e.getCause()== null?"":e.getCause().toString());
//            throw e;
//        }finally{
//        	//记录入参
//        	log.setInputParam(this.getBusinessInput(pjp));
//			if (log.getInvokeType().equals("CALL_RULE")) {//如果是规则，使用asyncResult传递 规则引擎入参配置
//				String config = ((ActExpandTracelog)pjp.getArgs()[2]).getAsyncResult();
//				log.setAsyncResult(config);
//			}
//        	externalService.logTrace(log);
//        }
//
//	}
//
//	private DelegateExecution getDelegateExecution(ProceedingJoinPoint pjp){
//		Object[] args = pjp.getArgs();
//		for (Object object : args) {
//			if(object instanceof DelegateExecution){
//				return (DelegateExecution) object;
//			}
//		}
//		return null;
//	}
//
//	public String getInvokeType(ProceedingJoinPoint pjp){
//		return String.valueOf(pjp.getArgs()[1]);
//	}
//
//	public String getBusinessInput(ProceedingJoinPoint pjp){
//
//		ActExpandTracelog log = (ActExpandTracelog)pjp.getArgs()[2];
//		if(log == null){
//			return null;
//		}else{
//			return log.getInputParam();
//		}
//	}
//
//
//	/**
//	 * 创建通用日志对象
//	 * @return
//	 */
//	private ActExpandTracelog createCommonLog(DelegateExecution execution){
//		ActExpandTracelog log = new ActExpandTracelog();
//		log.setId(UUID.randomUUID().toString());
//		log.setBussinessKey(execution.getProcessBusinessKey());
//		log.setProcessInstanceId(execution.getProcessInstanceId());
//		log.setNodeId(execution.getCurrentActivityId());
//		if(StringUtils.isBlank(execution.getCurrentActivityName())){
//			if(execution instanceof ExecutionEntity){
//				ExecutionEntity entity = (ExecutionEntity) execution;
//				String nodeName = (String) entity.getActivity().getProperty("name");
//				log.setNodeName(nodeName );
//			}
//
//
//		}else{
//			log.setNodeName(execution.getCurrentActivityName());
//		}
//		log.setInvokeTimes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
//		log.setIsSucess("Y");
//		Object legal = new ExecutionDB(execution).getBlockData(ExecutionDB._APPLY_DATA_BLOCK).get("legalPerNum");
//		log.setLegalPersonNum(String.valueOf(legal));
//		return log;
//	}
//
//	/**
//	 * 将异步接受的数据记录下来
//	 * @param pjp
//	 * @throws Throwable
//	 */
//	@Around("asyncReceiveData()")
//	@Transactional(rollbackFor=Exception.class)
//	public void handlerAsyncReceiveData(ProceedingJoinPoint pjp) throws Throwable {
//		AsyncReceiveResult result = (AsyncReceiveResult) pjp.getArgs()[0];
//		ActExpandTracelog log = new ActExpandTracelog();
//		log.setNodeId(result.getNodeId());
//		log.setProcessInstanceId(result.getProcessInstanceId());
//		Map<String, Object> data = result.getData();
//		log.setAsyncResult(JSON.toJSONString(data));
//		try {
//			pjp.proceed();
//		} catch (Exception e) {
//            log.setIsSucess("N");
//    		log.setErrorContent(e.getMessage());
//    		log.setCause(e.getCause()== null?"":e.getCause().toString());
//			throw e;
//		}finally{
//			externalService.logTrace(log);
//		}
//	}
//
//	//结束事件的日志记录
//	@Around("handlerEnd()")
//	@Transactional(rollbackFor=Exception.class)
//	public void handlerEndCall(ProceedingJoinPoint pjp) throws Throwable {
//		// camunda-TODO
////		ActivitiEntityEventImpl eventImpl=(ActivitiEntityEventImpl) pjp.getArgs()[0];
////		ExecutionEntity executionEntity = (ExecutionEntity) eventImpl.getEntity();
////		ActExpandTracelog log = this.createCommonLog(executionEntity);
////		结束标识
////		final String END_FLAG = "_END_FLAG";
////		Object endFlag = executionEntity.getVariable(END_FLAG);
////		if(endFlag != null){ //正确结束、错误结束，不在重试
////			return ;
////		}
////		ExecutionDB executionDB = new ExecutionDB(executionEntity);
////		Map<String, Object> applyData = executionDB.getBlockData(ExecutionDB._APPLY_DATA_BLOCK);
////		Map<String, Object> ruleData = executionDB.getBlockData(ExecutionDB._RULE_DATA_BLOCK);
////		Map<String, Object> data = new HashMap<String,Object>();
////		data.putAll(applyData);
////		data.putAll(ruleData);
////		log.setInputParam(JSON.toJSONString(data));
////		log.setNodeId("end");
////		log.setNodeName("结束");
////		try {
////			pjp.proceed();
////			executionEntity.setVariable(END_FLAG,"CORRECT");
////		}  catch (Exception e) {
////            log.setIsSucess("N");
////    		log.setErrorContent(e.getMessage());
////    		log.setCause(e.getCause()== null?"":e.getCause().toString());
////    		executionEntity.setVariable(END_FLAG,"ERROR");
////            throw e;
////        }finally{
////        	log.setSyncResult(JSON.toJSONString(ruleData));
////        	记录入参
////        	externalService.logTrace(log);
////        }
//	}
//
//	/**
//	 * 创建任务
//	 * @param pjp
//	 * @throws Exception
//	 */
//	@Around("createTask()")
//	public void handlerCreateTask(ProceedingJoinPoint pjp) throws Throwable {
//		DelegateTask task = (DelegateTask) pjp.getArgs()[0];
//
//		ActExpandTracelog log = new ActExpandTracelog();
//		log.setId(UUID.randomUUID().toString());
//		log.setBussinessKey(task.getExecution().getProcessBusinessKey());
//		log.setProcessInstanceId(task.getExecution().getProcessInstanceId());
//		log.setNodeId(task.getTaskDefinitionKey());
//		log.setNodeName(task.getName());
//		log.setInvokeTimes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
//		log.setInvokeType((String) pjp.getArgs()[1]);
//		log.setIsSucess("Y");
//		Object legal = new ExecutionDB(task.getExecution()).getBlockData(ExecutionDB._APPLY_DATA_BLOCK).get("legalPerNum");
//		log.setLegalPersonNum(String.valueOf(legal));
//		try {
//			BaseVo obj = (BaseVo) pjp.proceed();
//			if(BaseVo.SUCCESS_CODE.equals(obj.getReturnCode())){
//				log.setIsSucess("Y");
//				log.setSyncResult("任务创建成功,任务id:" + task.getId());
//			}else{
//				log.setIsSucess("N");
//				log.setSyncResult(obj.getReturnMsg());
//			}
//
//		}  catch (Exception e) {
//            log.setIsSucess("N");
//    		log.setErrorContent(e.getMessage());
//    		log.setCause(e.getCause()== null?"":e.getCause().toString());
//            throw e;
//        }finally{
//        	//记录入参
//        	externalService.logTrace(log);
//        }
//	}
//}
