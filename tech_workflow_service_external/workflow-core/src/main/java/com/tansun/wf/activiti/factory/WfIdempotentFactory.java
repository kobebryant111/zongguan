package com.tansun.wf.activiti.factory;


import com.tansun.wf.activiti.manager.WfIdempotentManager;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

public class WfIdempotentFactory implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return WfIdempotentManager.class;
    }

    @Override
    public Session openSession() {
        return new WfIdempotentManager();
    }
}
