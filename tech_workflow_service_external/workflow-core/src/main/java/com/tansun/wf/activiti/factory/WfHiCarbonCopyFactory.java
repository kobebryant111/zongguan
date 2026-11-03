package com.tansun.wf.activiti.factory;


import com.tansun.wf.activiti.manager.WfHiCarbonCopyManager;
import com.tansun.wf.activiti.model.HiCarbonCopyEntity;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

public class WfHiCarbonCopyFactory implements SessionFactory {

    /**
     * 添加批量插入操作
     */
    static {
        // camunda-TODO
//        EntityDependencyOrder.INSERT_ORDER.add(HiCarbonCopyEntity.class);
    }

    @Override
    public Class<?> getSessionType() {
        return WfHiCarbonCopyManager.class;
    }

    @Override
    public Session openSession() {
        return new WfHiCarbonCopyManager();
    }
}
