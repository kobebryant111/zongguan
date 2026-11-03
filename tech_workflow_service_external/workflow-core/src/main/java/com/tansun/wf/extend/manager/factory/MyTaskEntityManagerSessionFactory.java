package com.tansun.wf.extend.manager.factory;

import com.tansun.wf.extend.manager.MyTaskManager;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
import org.camunda.bpm.engine.impl.persistence.entity.TaskManager;

/**
 * @author jali
 * @create 2019-12-06 9:43
 */
public class MyTaskEntityManagerSessionFactory implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return TaskManager.class;
    }

    @Override
    public Session openSession() {
        return new MyTaskManager();
    }
}
