package com.tansun.wf.activiti.manager;


import org.camunda.bpm.engine.impl.db.DbEntity;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;

/**
 * 定时配置信息manager
 */
public class WfTimerConfigManager extends AbstractManager {
	
	public void deleteTimerConfigByCondition(DbEntity object) {
		getDbSqlSession().getSqlSession().delete("deleteTimerConfigByCondition", object);

    }
	public void update(DbEntity object) {
		getDbSqlSession().getSqlSession().update("updateTimerConfigNumber", object);

    }
}
