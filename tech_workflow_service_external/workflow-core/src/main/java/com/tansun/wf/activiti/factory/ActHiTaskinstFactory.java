//package com.tansun.wf.activiti.factory;
//
//import com.tansun.wf.activiti.manager.ActHiTaskinstManager;
//import org.camunda.bpm.engine.impl.interceptor.Session;
//import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
//
//public class ActHiTaskinstFactory implements SessionFactory{
//    @Override
//    public Class<?> getSessionType() {
//        return ActHiTaskinstManager.class;
//    }
//
//    @Override
//    public Session openSession() {
//        return new ActHiTaskinstManager();
//    }
//}
