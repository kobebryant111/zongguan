package com.tansun.wf.activiti.factory;


import com.tansun.wf.activiti.manager.WfTaskExpandDataManager;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

public class WfTaskExpandDataFactory implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return WfTaskExpandDataManager.class;
    }

    @Override
    public Session openSession() {
        return new WfTaskExpandDataManager();
    }
}
