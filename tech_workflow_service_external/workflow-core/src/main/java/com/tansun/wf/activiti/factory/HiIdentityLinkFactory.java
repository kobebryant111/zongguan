//package com.tansun.wf.activiti.factory;
//
//import com.tansun.wf.activiti.manager.HiIdentityLinkManager;
//import org.camunda.bpm.engine.impl.interceptor.Session;
//import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
//
//public class HiIdentityLinkFactory implements SessionFactory {
//
//    @Override
//    public Class<?> getSessionType() {
//        return HiIdentityLinkManager.class;
//    }
//
//    @Override
//    public Session openSession() {
//        return new HiIdentityLinkManager();
//    }
//}
