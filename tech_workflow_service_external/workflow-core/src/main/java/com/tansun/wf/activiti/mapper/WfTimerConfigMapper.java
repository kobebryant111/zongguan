package com.tansun.wf.activiti.mapper;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.impl.db.DbEntity;
import org.apache.ibatis.annotations.Param;

public interface WfTimerConfigMapper {

	 void insertTimerConfig(DbEntity object);
	 
	 List<HashMap> findTimerConfig(@Param(value = "status") String status);
}
