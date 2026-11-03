package com.tansun.wf.activiti.factory;


import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

import com.tansun.wf.activiti.manager.WfProcessConfigManager;

public class WfProcessConfigFactory implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return WfProcessConfigManager.class;
    }

    @Override
    public Session openSession() {
        return new WfProcessConfigManager();
    }
}
