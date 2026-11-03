package com.tansun.wf.platform.mapper;

import org.apache.ibatis.annotations.Param;
import org.camunda.bpm.engine.impl.db.DbEntity;

import java.util.HashMap;
import java.util.List;

public interface WfTimerConfigMapper {

	 void insertTimerConfig(DbEntity object);
	 
	 List<HashMap> findTimerConfig(@Param(value = "status") String status);
}
