package com.tansun.wf.platform.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tansun.wf.extend.db.WfBaseMapper;
import com.tansun.wf.platform.model.WfAgentEntity;

@Repository
public interface WfAgentMapper extends WfBaseMapper<WfAgentEntity> {
	
	
	 public  List<Map<String,Object>>  findTrustProcess(Map<String,Object> paramMap);
	 
	 
	 public  List<Map<String,Object>>  findTrustProcessTask(Map<String,Object> paramMap);
	 
	 public  List<Map<String,Object>>  findTrust(Map<String,Object> paramMap);
	 
	 
	 public List<WfAgentEntity>  findEffectAgent(Map<String,Object> paramMap);
	 
	 public List<WfAgentEntity>  findAgent(Map<String,Object> paramMap);

}
