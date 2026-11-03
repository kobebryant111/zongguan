//package com.tansun.wf.extend.event;
//
//
///**
// * 自定义事件类型
// */
//// camunda-TODO
//public class CustomEvent {
//
//    /**
//     * recordTaskEnd 事件
//     */
//    public static String RECORD_TASK_END_EVENT = "recordTaskEnd";
//
//    /**
//     * 事件名称
//     */
//    private String eventName;
//
//    /**
//     * 事件携带的实体对象
//     */
//    private Object eventEntity;
//
//    /**
//     * 判断自定义消息的是否与预期相同
//     * @param event
//     * @param eventName
//     * @return
//     */
//    public static boolean isEqualsEvent(ActivitiEvent event, String eventName) {
//        if (! (event instanceof ActivitiEntityEventImpl)){
//            return false;
//        }
//
//        ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl) event;
//        if (eventImpl.getEntity() instanceof CustomEvent) {
//            CustomEvent customEvent = (CustomEvent) eventImpl.getEntity();
//            if (customEvent.getEventName().equals(eventName)) {
//                return true;
//            }else{
//                return false;
//            }
//        }else {
//            return false;
//        }
//    }
//
//    public static <T> T getEntityFromEvent(Class<T> entityClass, ActivitiEvent event) {
//        if (event instanceof ActivitiEntityEventImpl && ((ActivitiEntityEventImpl) event).getEntity() instanceof CustomEvent) {
//            CustomEvent customEvent = (CustomEvent) ((ActivitiEntityEventImpl) event).getEntity();
//            if (customEvent == null || entityClass == null) {
//                return null;
//            }
//            Object eventEntity = customEvent.getEventEntity();
//            if (eventEntity == null) {
//                return null;
//            }
//
//            if (eventEntity.getClass().getName().equals(entityClass.getName())){
//                return (T)eventEntity;
//            }
//
//            return null;
//        }else {
//            return  null;
//        }
//    }
//
//
//    public void setEventName(String eventName) {
//        this.eventName = eventName;
//    }
//
//    public String getEventName() {
//        return eventName;
//    }
//
//    public void setEventEntity(Object eventEntity) {
//        this.eventEntity = eventEntity;
//    }
//
//    public Object getEventEntity() {
//        return eventEntity;
//    }
//}
