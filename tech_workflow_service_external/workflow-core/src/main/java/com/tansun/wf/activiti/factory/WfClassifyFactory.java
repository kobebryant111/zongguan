//package com.tansun.wf.activiti.factory;
//
//
//import org.camunda.bpm.engine.impl.interceptor.Session;
//import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
//
//import com.tansun.wf.activiti.manager.WfClassifyManager;
//
//public class WfClassifyFactory implements SessionFactory {
//
//    @Override
//    public Class<?> getSessionType() {
//        return WfClassifyManager.class;
//    }
//
//    @Override
//    public Session openSession() {
//        return new WfClassifyManager();
//    }
//}
