package com.tansun.wf.decision.model;


import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 规则引擎返回结果
 *
 */
@SuppressWarnings("unchecked")
public class RuleResultEntity {

	private static final String SYSTEM = "System";
	
	private Map<String,Object> content;
	
	//规则引擎返回值   System属性
	private RuleSystem ruleSystem;
	
	public RuleResultEntity(String resultJson){
		content = JSON.parseObject(resultJson, Map.class);
		ruleSystem = JSON.parseObject(String.valueOf(content.get(SYSTEM)), RuleSystem.class);
		
	}
	
	/**
	 * 获取规则计算的返回值，去除System属性
	 * @return
	 */
	public Map<String,Object> getCalcResult(){
		Map<String,Object> calcResult = new HashMap<String,Object>();
		for (String key : content.keySet()) {
			if(!key.equals(SYSTEM)){
				calcResult.put(key, content.get(key));
			}
		}
		return calcResult;
	}
	
	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}

	public RuleSystem getRuleSystem() {
		return ruleSystem;
	}

	public void setRuleSystem(RuleSystem ruleSystem) {
		this.ruleSystem = ruleSystem;
	}

	public boolean isSuccess(){
		return "1".equals(ruleSystem.getIsSuccess());
	}
}

