//package com.tansun.wf.activiti.factory;
//
//
//import com.tansun.wf.activiti.manager.ActRuTaskinstManager;
//import org.camunda.bpm.engine.impl.interceptor.Session;
//import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
//
///**
// * @author: 卓福深 zhuofushen@tansun.com.cn
// * @类描述 <pre></pre>
// * @创建时间 2020/12/30
// * @版本 v1.01
// * @修改记录 <pre>
// * 版本 修改人 修改时间 修改内容描述
// * ----------------------------------------------
// * ----------------------------------------------
// * </pre>
// */
//public class ActRuTaskFactory implements SessionFactory {
//    @Override
//    public Class<?> getSessionType() {
//        return ActRuTaskinstManager.class;
//    }
//
//    @Override
//    public Session openSession() {
//        return new ActRuTaskinstManager();
//    }
//}
