package com.tansun.wf.activiti.manager;


import org.camunda.bpm.engine.impl.db.DbEntity;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;

/**
 * HiTaskPoolExecuteData
 */
public class WfHiTaskPoolExecuteDataManager extends AbstractManager {

	public void insertHiTaskPoolExecute(DbEntity object) {
		getDbSqlSession().getSqlSession().insert("insertHiTaskPoolExecuteData",object);
    }
}
