package com.tansun.wf.decision;


import com.alibaba.fastjson.JSON;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 流程实例数据库
 */
@SuppressWarnings("unchecked")
public class ExecutionDB {

	/**
	 * 涉及自动审批业务数据存储位置 用JSON字符串存储
	 */
	private Map<String,Object> DB;
	
	private DelegateExecution delegateExecution;
	
	private ExecutionEntity executionEntity;

	private TaskEntity taskEntity;

	
	/**
	 * 与规则交互的数据存储的key
	 */
    public final static String _RULE_DOMAIN_KEY = "_RULE_DOMAIN_KEY";
	
    public final static String _APPLY_DATA_BLOCK = "_APPLY_DATA_BLOCK";
    
    public final static String _COLLECT_DATA_BLOCK = "_COLLECT_DATA_BLOCK";
    
    public final static String _RULE_DATA_BLOCK = "_RULE_DATA_BLOCK";
    
	public ExecutionDB(DelegateExecution execution){
		String mapJosn = String.valueOf(execution.getVariable(_RULE_DOMAIN_KEY));
		this.delegateExecution = execution;
		initDB(mapJosn);
	}

	public ExecutionDB(TaskEntity taskEntity){
		String mapJosn = String.valueOf(taskEntity.getVariable(_RULE_DOMAIN_KEY));
		this.taskEntity = taskEntity;
		initDB(mapJosn);
	}
	
	public ExecutionDB(ExecutionEntity executionEntity) {
		String mapJosn = String.valueOf(executionEntity.getVariable(_RULE_DOMAIN_KEY));
		this.executionEntity = executionEntity;
		initDB(mapJosn);
	}

	public ExecutionDB(String DBJson) {
		initDB(DBJson);
	}
	/**
	 * 初始化DB
	 * @param mapJosn
	 */
	private void initDB(String mapJosn) {
		if(StringUtils.isBlank(mapJosn) || "null".equals(mapJosn)){
			mapJosn = "{}";
		}
		DB = JSON.parseObject(mapJosn, Map.class);
	}
	
    /**
     * 获取所有数据
     */
	public Map<String,Object> getAllData(){
		return DB;
	}
	
	/**
	 * 获取数据块
	 * @param block
	 * @return
	 */
	public Map<String,Object> getBlockData(String block){
		String mapJosn = String.valueOf(DB.get(block));
		if(StringUtils.isBlank(mapJosn) || "null".equals(mapJosn)){
			mapJosn = "{}";
		}
		return JSON.parseObject(mapJosn, Map.class);
	}
	
	/**
	 * 设置数据块,保证键值不能重复
	 * @param data
	 */
	public void setBlockDataWithKeyNoRepeat(String block, Map<String,Object> data) throws Exception{
		if(null == data){
			return ;
		}
		Map<String, Object> blockData = this.getBlockData(block);
		Set<String> keySet = blockData.keySet();
 		for (String key  : data.keySet()) {
			if(keySet.contains(key)){
//				throw new Exception("key ["+key+"] repeat in " + block);
			}else {
				blockData.put(key, data.get(key));
			}
		}
		DB.put(block, JSON.toJSONString(blockData));
		this.updateDBInExecution();
	}
	
	public void setBlockDataWithKeyNoRepeat(String block, String mapJson) throws Exception{
		Map<String,Object> data = JSON.parseObject(mapJson, Map.class);
		this.setBlockDataWithKeyNoRepeat(block, data);
	}
	
	
	/**
	 * 设置数据块,键值允许重复
	 * @param data
	 */
	public void setBlockDataIgnoreKeyRepeat(String block, Map<String,Object> data){
		if(null == data){
			return ;
		}
		Map<String, Object> blockData = this.getBlockData(block);
		blockData.putAll(data);
		DB.put(block, JSON.toJSONString(blockData));
		this.updateDBInExecution();
	}
	
	/**
	 * 判断该键值是否在数据块中存在
	 * @param block
	 * @param key
	 * @return
	 */
	public boolean isExistInBlock(String block,String key){
		Map<String, Object> blockData = this.getBlockData(block);
		return blockData.keySet().contains(key);
	}

	/**
	 * 将设置到某个数据块中
	 * @param block
	 * @param key
	 * @return value
	 */
	public void setInBlock(String block,String key,String value){
		Map<String, Object> blockData = this.getBlockData(block);
		blockData.put(key, value);
		this.setBlockDataIgnoreKeyRepeat(block, blockData);
	}

	private void updateDBInExecution(){
		if(this.executionEntity != null){
			this.executionEntity.setVariable(_RULE_DOMAIN_KEY, JSON.toJSONString(DB));
		}else if(this.delegateExecution != null){
			this.delegateExecution.setVariable(_RULE_DOMAIN_KEY, JSON.toJSONString(DB));
		}else if(this.taskEntity != null){
            this.taskEntity.setVariable(_RULE_DOMAIN_KEY, JSON.toJSONString(DB));
        }
	}

	public Map<String, Object> getDB() {
		return new HashMap<String,Object>(DB);
	}

	public String getJSONDB(){
        return JSON.toJSONString(new HashMap<String,Object>(DB));
    }
}
