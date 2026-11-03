package com.tansun.wf.decision.handler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tansun.wf.decision.ExecutionDB;
import com.tansun.wf.decision.model.DataSet;
import com.tansun.wf.decision.model.ParamVo;
import com.tansun.wf.decision.model.RuleResultEntity;
import com.tansun.wf.decision.model.RuleSystem;
import com.tansun.wf.decision.service.RuleService;
import com.tansun.workflow.model.ActExpandTracelog;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("callRuleServiceHandler")
@Transactional(rollbackFor=Exception.class)
public class CallRuleServiceHandler implements WorkflowBusinessHandler{
	
	private static final String PROJECT_NAME = "projectName";
	
	private static final String ENTER_POINT = "enterPoint";
	
	private static final String DATASETS = "dataSets";
	
	@Autowired
	private RuleService ruleService;
	
	
	
	@Override
	public Object dealBusiness(DelegateExecution execution, String invokeType, ActExpandTracelog log) throws Exception {
		JSONObject documentation = getRuleconfig(execution);
		String projectName = documentation.getString(PROJECT_NAME);
		String enterPoint = documentation.getString(ENTER_POINT);
        String dataSetJsonString = documentation.getString(DATASETS);
        List<DataSet> dataSets = JSONArray.parseArray(dataSetJsonString, DataSet.class);
		Set<String> inputKeys = getInputKeys(dataSets); //获取规则引擎入参
		Set<String> allKeys = getAllKeys(dataSets);
        Map<String, Object> params = readyRuleParams(execution, dataSets);//准备参数
        log.setInputParam(getInputParam(projectName,enterPoint,JSON.toJSONString(params)));
        String outPut = ruleService.invoke(projectName, enterPoint, params);
		log.setSyncResult(outPut);
		log.setAsyncResult(dataSetJsonString);//借用asyncResult,传递规则调用配置
		return  handlerRuleResult(outPut,inputKeys,allKeys,execution,log); //处理结果
	}

	private String getInputParam(String projectName, String enterPoint,
			String paramsJson) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put(PROJECT_NAME, projectName);
		map.put(ENTER_POINT, enterPoint);
		map.put("params", JSON.parseObject(paramsJson));
		return JSON.toJSONString(map);
	}

	/**
	 * 准备调用规则的参数
	 * @param execution
	 * @param dataSets
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> readyRuleParams(DelegateExecution execution, List<DataSet> dataSets) throws Exception {
		//将数据集转换为模板                  
		//"root": {                  
		//		"caipan": "1",    
		//		"shixin": "2",    
		//		"xiangao": "3",   
		//		"zhixing": "0"    
		//	} 
		//"input": {                  
		//		"caipan": "1",    
		//		"shixin": "2",    
		//		"xiangao": "3",   
		//		"zhixing": "0"    
		//	} 

        ExecutionDB executionDB = new ExecutionDB(execution);
        Map<String,Object> collectData = executionDB.getBlockData(ExecutionDB._COLLECT_DATA_BLOCK);  //数据源数据
        Map<String, Object> applyData = executionDB.getBlockData(ExecutionDB._APPLY_DATA_BLOCK);     //发起流程时传入的数据
        Map<String, Object> ruleOutputResult = new ExecutionDB(execution).getBlockData(ExecutionDB._RULE_DATA_BLOCK);   //规则计算后返回的数据

        Map<String,Object> params = new HashMap<String,Object>();
        for (DataSet dataSet : dataSets) {
            //寻找参数key
            Map<String,Object> ruleParams = dataSet.getDefaultRuleParams();

			//从申请数据取值
			ruleService.fillParamsValue(dataSet,applyData, ruleParams);
			
			//从数据源返回的和提交的数据取值
			ruleService.fillParamsValue(dataSet,collectData, ruleParams);


            //从上一个规则返回的数据取值
//            ruleService.fillParamsValue(dataSet,ruleOutputResult, ruleParams);

            params.put(dataSet.getDataSetKey(), ruleParams);
		}
        return params;
	}


    /**
	 * 获取规则引擎的入参
	 * @return
	 * @throws Exception
     * @param dataSets
	 */
	private Set<String> getInputKeys(List<DataSet> dataSets) throws Exception {

		Set<String> checkRepeat = new HashSet<String>();
		Set<String> inputKeys = new HashSet<String>();
		for (DataSet dataSet : dataSets) {
			List<ParamVo> params = dataSet.getParams();
			for (ParamVo param : params) {
				if("out".equals(String.valueOf(param.getParamFor()))){
					continue;
				}
				String key = String.valueOf(param.getParamKey());
				inputKeys.add(key);
				checkRepeat.add(dataSet.getDataSetKey() +"."+key);
			}
		}
		
		//不同数据集参数重复校验
		Set<String> checkRepeat1 = new HashSet<String>(checkRepeat);
		for (String key : checkRepeat) {
			for (String key1 : checkRepeat1) {
				if(key.equals(key1)){ //自己等于自己
					continue;
				}else{ 
					if(key.split("\\.")[1].equals(key1.split("\\.")[1])){
						throw new Exception("key ["+key+"],[" + key1+"] repeat !!!");
					}
				}
			}
			
		}
		return inputKeys;
	}
	/**
	 * 获取all key
	 * @param dataSets
	 * @return
	 * @throws Exception
	 */
	private Set<String> getAllKeys(List<DataSet> dataSets) throws Exception {

		Set<String> allKeys = new HashSet<String>();
		for (DataSet dataSet : dataSets) {
			List<ParamVo> params = dataSet.getParams();
			for (ParamVo param : params) {
				if("all".equals(String.valueOf(param.getParamFor()))){
					String key = String.valueOf(param.getParamKey());
					allKeys.add(key);
				}
				
			}
		}
		return allKeys;
	}


    //{
	//	  "projectName": "ProjectKey1",
	//	  "enterPoint": "enterKey11",
	//	  "dataSets": [
	//	    {
	//	      "dataSetName": "dataSetName11",
	//	      "dataSetKey": "root",
	//	      "params": [
	//	        {
	//	          "paramName": "paramName1",
	//	          "paramKey": "paramKey1"
	//	        }
	//	      ]
	//	    }
	//	  ]
	//	}
	private JSONObject getRuleconfig(DelegateExecution execution){
		// camunda-TODO
//		BpmnModelInstance bpmnModel = execution.getProcessEngineServices().getRepositoryService().getBpmnModelInstance(execution.getProcessDefinitionId());
//		FlowElement flowElement = bpmnModel.getModelElementById(execution.getCurrentActivityId());
//		Map<String, List<ExtensionElement>> elements = flowElement.getExtensionElements();
		JSONObject ruleconfig = null;
//		if(elements != null){
//			List<ExtensionElement> extensionElements = elements.get("ruleconfig");
//			if(extensionElements != null && extensionElements.size() >0){
//				String json = extensionElements.get(0).getElementText();
//
//				ruleconfig = JSONObject.parseObject(json);
//			}
//		}
		return ruleconfig;
	}
	
	private String handlerRuleResult(String outPut, Set<String> inputKeys,Set<String>allKeys, DelegateExecution execution, ActExpandTracelog log) throws Exception {
		RuleResultEntity ruleResult = new RuleResultEntity(outPut);
		RuleSystem ruleSystem = ruleResult.getRuleSystem();
		
		log.setSyncResult(outPut);
		Map<String, Object> calcResult = ruleResult.getCalcResult();
		if(ruleResult.isSuccess()){//调用成功
			//获取规则计算的结果
			Map<String, Object> outputResult = filterInputKey(calcResult,inputKeys,allKeys);
			
			ExecutionDB executionDB = new ExecutionDB(execution);
			Map<String, Object> block = executionDB.getBlockData(ExecutionDB._RULE_DATA_BLOCK);
			for (String key : outputResult.keySet()) {
				String value = String.valueOf(outputResult.get(key));
				String nodeId = execution.getCurrentActivityId();
				
				//加上工作流节点ID,保证每个调用规则的值都存在
				block.put(nodeId + "_" + key, value);
				
				//相同的key的值做覆盖
				block.put(key, value); 
			}
			executionDB.setBlockDataIgnoreKeyRepeat(ExecutionDB._RULE_DATA_BLOCK, block);
			execution.setVariables(outputResult);
//			execution.setVariablesLocal(filterVariable(outputResult));//将计算结果设置到流程变量中
//			execution.setVariablesLocal(outputResult);//将计算结果设置到流程变量中
		}else{
			log.setIsSucess("N");
			throw new Exception(ruleSystem.getException());
		}
		return outPut;
	}

	/**
	 * 过滤规则入参，剩下的都是规则出参
	 * @param calcResult 计算结果
	 * @param inputKeys 
	 * @return	outputResult 规则出参
	 */
	private Map<String,Object> filterInputKey(Map<String,Object> calcResult, Set<String> inputKeys, Set<String> allKeys) {
		HashMap<String, Object> outputResult = new HashMap<String,Object>();
		for (String key : calcResult.keySet()) {
			Map<String,Object> subContent = JSON.parseObject(String.valueOf(calcResult.get(key)), Map.class);
			for (String subKey : subContent.keySet()) {
                if(subContent.get(subKey) instanceof BigDecimal){
                    outputResult.put(subKey,((BigDecimal) subContent.get(subKey)).doubleValue());
                }else{
                    outputResult.put(subKey, subContent.get(subKey));
                }
//				if(!inputKeys.contains(subKey) || allKeys.contains(subKey)){
//					outputResult.put(subKey, subContent.get(subKey));
//				}
			}
		}
		return outputResult;
	}
	
	/**
	 * 从流程变量中获取法人编号
	 */
	private String getLegalPerNum(DelegateExecution execution){
		ExecutionDB executionDB = new ExecutionDB(execution);
		Map<String,Object> collectData = executionDB.getBlockData(ExecutionDB._APPLY_DATA_BLOCK);
		return collectData.get("legalPerNum") + "";
	}

}
