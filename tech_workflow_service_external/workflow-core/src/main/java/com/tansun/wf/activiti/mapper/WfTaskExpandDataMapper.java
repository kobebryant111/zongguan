package com.tansun.wf.activiti.mapper;

import org.camunda.bpm.engine.impl.db.DbEntity;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WfTaskExpandDataMapper {

    public void insertTaskExpandData(DbEntity object);

    public List<Map> userTaskCount(@Param("userId") String userId);
}
