//package com.tansun.wf.extend.manager;
//
//import org.camunda.bpm.engine.impl.persistence.entity.JobEntityManager;
//import org.camunda.bpm.engine.impl.persistence.entity.MessageEntity;
//
//// camunda-TODO
//public class MyJobEntityManager extends JobEntityManager {
//
//    /**
//     * 修改重试默认值
//     * @param message
//     */
//    @Override
//    public void send(MessageEntity message) {
//        //失败任务重试次数可配置
//        message.setRetries(3);
//        super.send(message);
//    }
//}
