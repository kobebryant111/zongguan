package com.tansun.wf.activiti.factory;


import com.tansun.wf.activiti.manager.WfHiTaskExpandDataManager;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

public class WfHiTaskExpandDataFactory implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return WfHiTaskExpandDataManager.class;
    }

    @Override
    public Session openSession() {
        return new WfHiTaskExpandDataManager();
    }
}
