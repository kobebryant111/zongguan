package com.tansun.wf.activiti.factory;

import com.tansun.wf.activiti.manager.WfTaskExpandPlanManager;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

public class WfTaskExpandPlanFactory implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return WfTaskExpandPlanManager.class;
    }

    @Override
    public Session openSession() {
        return new WfTaskExpandPlanManager();
    }
}
