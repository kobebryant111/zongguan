package com.tansun.wf.activiti.manager;


import com.tansun.wf.activiti.model.TaskExpandDataEntity;

import org.camunda.bpm.engine.impl.db.DbEntity;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;

/**
 * 待办扩展数据manager
 */
public class WfTaskExpandDataManager extends AbstractManager {

    public TaskExpandDataEntity selectByTaskId(String taskId) {
        return (TaskExpandDataEntity) getDbSqlSession().selectOne("selectByTaskId", taskId);
    }
    
    public void updateTaskExpandData(DbEntity object) {
		getDbSqlSession().getSqlSession().update("updateTaskExpandData", object);

    }
}
