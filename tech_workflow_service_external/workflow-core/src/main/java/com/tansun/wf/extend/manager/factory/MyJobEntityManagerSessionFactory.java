//package com.tansun.wf.extend.manager.factory;
//
//import com.tansun.wf.extend.manager.MyJobEntityManager;
//import org.camunda.bpm.engine.impl.interceptor.Session;
//import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
//import org.camunda.bpm.engine.impl.persistence.entity.JobEntityManager;
//
//// camunda-TODO
//public class MyJobEntityManagerSessionFactory implements SessionFactory {
//
//    @Override
//    public Class<?> getSessionType() {
//        return JobEntityManager.class;
//    }
//
//    @Override
//    public Session openSession() {
//        return new MyJobEntityManager();
//    }
//
//}
//
