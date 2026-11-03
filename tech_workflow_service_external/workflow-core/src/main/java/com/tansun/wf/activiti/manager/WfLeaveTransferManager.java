package com.tansun.wf.activiti.manager;


import org.camunda.bpm.engine.impl.db.DbEntity;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;

/**
 * LeaveTransfer
 */
public class WfLeaveTransferManager extends AbstractManager {

	public void updateLeaveTransferStatus(DbEntity object) {
		getDbSqlSession().getSqlSession().update("updateLeaveTransferStatus", object);

    }
	
	public void updateLeaveTransferStartStatus(DbEntity object) {
		getDbSqlSession().getSqlSession().update("updateLeaveTransferAutoStart", object);
	}
}
