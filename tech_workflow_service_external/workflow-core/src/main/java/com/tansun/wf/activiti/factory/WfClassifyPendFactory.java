package com.tansun.wf.activiti.factory;


import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

import com.tansun.wf.activiti.manager.WfClassifyPendManager;

public class WfClassifyPendFactory implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return WfClassifyPendManager.class;
    }

    @Override
    public Session openSession() {
        return new WfClassifyPendManager();
    }
}
