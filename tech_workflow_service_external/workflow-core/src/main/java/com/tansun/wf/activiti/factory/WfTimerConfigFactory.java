package com.tansun.wf.activiti.factory;


import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

import com.tansun.wf.activiti.manager.WfTimerConfigManager;

public class WfTimerConfigFactory implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return WfTimerConfigManager.class;
    }

    @Override
    public Session openSession() {
        return new WfTimerConfigManager();
    }
}
