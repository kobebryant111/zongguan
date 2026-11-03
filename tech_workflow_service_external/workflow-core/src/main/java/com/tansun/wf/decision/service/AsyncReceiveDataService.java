package com.tansun.wf.decision.service;


import com.alibaba.fastjson.JSON;
import com.tansun.wf.decision.ExecutionDB;
import com.tansun.wf.decision.model.AsyncReceiveResult;
import org.camunda.bpm.engine.OptimisticLockingException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AsyncReceiveDataService {
	
	private static final Logger logger = LoggerFactory.getLogger(AsyncReceiveDataService.class);
	
	@Autowired
	private RuntimeService runtimeService;

	
	public Object asyncRceiveData(AsyncReceiveResult result) throws Exception {
		int checkCommitCount = 500;
		int count = checkCommitCount;
		int checkCommitWaitMills = 500;
		
		if(result == null){
			throw new Exception("async call error! result is null");
		}
		
		if("N".equals(result.getIsSuccess())){ //回调失败
			throw new Exception(result.getErrorContent());
		}
		
		String executionId = result.getExecutionId();
		String nodeId = result.getNodeId();
		//判断是否为征信平台回调重试
//		if(!CollectServiceHandler.asyncFlags.contains(executionId + ":" + nodeId)){
//			//已经回调
//			System.out.println("已经回调 "  + executionId + ":" +nodeId);
//			return true;
//		}
		
		while (!isCommit(executionId, nodeId) && checkCommitCount > 0) { //没有提交，等待150ms, 等待8次
			Thread.sleep(checkCommitWaitMills);
			checkCommitCount--;
		}
		if(checkCommitCount <0){
			logger.error("回掉失败  : executionId is {}, nodeId is {}",executionId,nodeId);
		}else{
			if(checkCommitCount <8){
				logger.info("{}次确认查询后,流程数据已经入库",count - checkCommitWaitMills);
			}
		}
		
		String DBJson = String.valueOf(runtimeService.getVariable(result.getExecutionId(), ExecutionDB._RULE_DOMAIN_KEY));
		ExecutionDB executionDB = new ExecutionDB(DBJson);
		executionDB.setBlockDataWithKeyNoRepeat(ExecutionDB._COLLECT_DATA_BLOCK, result.getData());
		Map<String, Object> processVariables = new HashMap<String,Object>();
		processVariables.put(ExecutionDB._RULE_DOMAIN_KEY, JSON.toJSONString(executionDB.getDB()));
		//触发流程
		//2017-09-14 18:02:20 加锁，防止并发回调出现事务问题
        try{
            runtimeService.signal(executionId);
            // camunda-TODO
        }catch (OptimisticLockingException e) {
            int tryCount = 500;
            siganlFailedAndTry(tryCount,executionId);
        }

		
		return JSON.toJSONString(result.getData());
	}

	public void siganlFailedAndTry(int tryCount, String executionId) {
		logger.info("executionId:" + executionId + ",siganlFailedAndTry : " + tryCount);
		try {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			runtimeService.signal(executionId);
			// camunda-TODO
		} catch (OptimisticLockingException e) {
			tryCount = tryCount - 1;
			if (tryCount < 0) {
				return;
			}else{
				siganlFailedAndTry(tryCount, executionId);
			}

		}
	}
	/**
	 * 确保数据已经入库,才唤醒流程操作
	 * @param executionId
	 * @param nodeId
	 * @return
	 */
	public boolean isCommit(String executionId,String nodeId){
		String sql = "SELECT ID_, PROC_INST_ID_,ACT_ID_ FROM act_ru_execution WHERE ID_ = #{id} LIMIT 0,1";
		Execution execution = runtimeService.createNativeExecutionQuery().sql(sql).parameter("id", executionId).singleResult();
		// camunda-TODO
		if(execution == null || !execution.getId().equals(nodeId)){
			return false;
		}else{
			return true;
		}
	}
	
}
