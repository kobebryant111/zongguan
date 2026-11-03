package com.tansun.wf.activiti.factory;


import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

import com.tansun.wf.activiti.manager.WfHiTaskPoolExecuteDataManager;

public class WfHiTaskPoolExecuteDataFactory implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return WfHiTaskPoolExecuteDataManager.class;
    }

    @Override
    public Session openSession() {
        return new WfHiTaskPoolExecuteDataManager();
    }
}
