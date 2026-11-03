package com.tansun.wf.activiti.manager;


import com.tansun.wf.activiti.model.HiTaskExpandDataEntity;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;

/**
 * 待办扩展数据manager
 */
public class WfHiTaskExpandDataManager extends AbstractManager {
    public HiTaskExpandDataEntity selectByHiTaskId(String taskId) {
        return (HiTaskExpandDataEntity) getDbSqlSession().selectOne("selectByHiTaskId", taskId);
    }
}
