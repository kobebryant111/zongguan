package com.tansun.wf.activiti.factory;


import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

import com.tansun.wf.activiti.manager.WfLeaveTransferManager;

public class WfLeaveTransferFactory implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return WfLeaveTransferManager.class;
    }

    @Override
    public Session openSession() {
        return new WfLeaveTransferManager();
    }
}
