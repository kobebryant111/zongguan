//package com.tansun.wf.extend.manager;
//
//import com.tansun.wf.extend.event.CustomEvent;
//import org.camunda.bpm.engine.delegate.event.ActivitiEventType;
//import org.camunda.bpm.engine.delegate.event.impl.ActivitiEntityEventImpl;
//import org.camunda.bpm.engine.impl.context.Context;
//import org.camunda.bpm.engine.impl.history.DefaultHistoryManager;
//import org.camunda.bpm.engine.impl.history.HistoryLevel;
//import org.camunda.bpm.engine.impl.identity.Authentication;
//import org.camunda.bpm.engine.impl.persistence.entity.*;
//import org.camunda.bpm.engine.impl.variable.NullType;
//import org.camunda.bpm.engine.task.Event;
//
//// camunda-TODO
//public class MyDefaultHistoryManager extends Defaultmanager DefaultHistoryManager {
//    public MyDefaultHistoryManager() {
//        super();
//    }
//    @Override
//    public void createIdentityLinkComment(String taskId, String userId, String groupId, String type, boolean create) {
//        createIdentityLinkComment(taskId, userId, groupId, type, create, false);
//    }
//
//    @Override
//    public void createIdentityLinkComment(String taskId, String userId, String groupId, String type, boolean create, boolean forceNullUserId) {
//        if(isHistoryEnabled()) {
//            String authenticatedUserId = Authentication.getAuthenticatedUserId();
//            CommentEntity comment = new CommentEntity();
//            comment.setUserId(authenticatedUserId);
//            comment.setType(CommentEntity.TYPE_EVENT);
//            comment.setTime(Context.getProcessEngineConfiguration().getClock().getCurrentTime());
//            comment.setTaskId(taskId);
//            if (userId!=null || forceNullUserId) {
//                if(create) {
//                    comment.setAction(Event.ACTION_ADD_USER_LINK);
//                } else {
//                    comment.setAction(Event.ACTION_DELETE_USER_LINK);
//                }
//                comment.setMessage(new String[]{userId, type});
//            } else {
//                if(create) {
//                    comment.setAction(Event.ACTION_ADD_GROUP_LINK);
//                } else {
//                    comment.setAction(Event.ACTION_DELETE_GROUP_LINK);
//                }
//                comment.setMessage(new String[]{groupId, type});
//            }
//            if (!"event".equals(comment.getType())){
//                getSession(CommentEntityManager.class).insert(comment);
//            }
//        }
//    }
//
//    @Override
//    public void recordIdentityLinkCreated(IdentityLinkEntity identityLink) {
//        // It makes no sense storing historic counterpart for an identity-link that is related
//        // to a process-definition only as this is never kept in history
//        if (isHistoryLevelAtLeast(HistoryLevel.AUDIT) && (identityLink.getProcessInstanceId() != null || identityLink.getTaskId() != null)) {
//            HistoricIdentityLinkEntity historicIdentityLinkEntity = new HistoricIdentityLinkEntity(identityLink);
////            getDbSqlSession().insert(historicIdentityLinkEntity);
//        }
//    }
//
//    @Override
//    public void recordTaskEnd(String taskId, String deleteReason) {
//        super.recordTaskEnd(taskId, deleteReason);
//        if (Context.getCommandContext().getEventDispatcher().isEnabled()) {
//            HistoricTaskInstanceEntity inCache = Context.getCommandContext().getDbSqlSession().findInCache(HistoricTaskInstanceEntity.class, taskId);
//            CustomEvent customEvent = new CustomEvent();
//            customEvent.setEventName(CustomEvent.RECORD_TASK_END_EVENT);
//            customEvent.setEventEntity(inCache);
//
//            ActivitiEntityEventImpl taskEnd = new ActivitiEntityEventImpl(customEvent, ActivitiEventType.CUSTOM);
//            Context.getCommandContext().getEventDispatcher().dispatchEvent(taskEnd);
//        }
//    }
//
//    @Override
//    public void recordVariableCreate(VariableInstanceEntity variable) {
//        if (variable.getType() instanceof NullType){
//            return;
//        }
//        super.recordVariableCreate(variable);
//    }
//}
