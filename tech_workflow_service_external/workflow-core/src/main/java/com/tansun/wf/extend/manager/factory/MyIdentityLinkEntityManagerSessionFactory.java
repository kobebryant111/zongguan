//package com.tansun.wf.extend.manager.factory;
//
//import com.tansun.wf.extend.manager.MyIdentityLinkEntityManager;
//import org.camunda.bpm.engine.impl.interceptor.Session;
//import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
//import org.camunda.bpm.engine.impl.persistence.entity.IdentityLinkEntityManager;
//import org.camunda.bpm.engine.impl.persistence.entity.IdentityLinkManager;
//
// camunda-TODO
//public class MyIdentityLinkEntityManagerSessionFactory implements SessionFactory {
//
//    @Override
//    public Class<?> getSessionType() {
//        return IdentityLinkManager.class;
//    }
//
//    @Override
//    public Session openSession() {
//        return new MyIdentityLinkEntityManager();
//    }
//
//}
//
