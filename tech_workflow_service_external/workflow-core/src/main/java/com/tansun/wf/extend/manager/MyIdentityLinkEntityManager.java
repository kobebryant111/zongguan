//package com.tansun.wf.extend.manager;
//
//import org.camunda.bpm.engine.impl.context.Context;
//import org.camunda.bpm.engine.impl.persistence.entity.IdentityLinkEntity;
//import org.camunda.bpm.engine.impl.persistence.entity.IdentityLinkEntityManager;
//
//import java.util.List;
//
///**
// * 自定义IdentityLinkEntityManager
// */
//// camunda-TODO
//public class MyIdentityLinkEntityManager  extends IdentityLinkEntityManager {
//
//
//    /**
//     * 修复循环删除问题
//     * @param taskId
//     */
//    @Override
//    public void deleteIdentityLinksByTaskId(String taskId) {
//
//        getDbSqlSession().delete("deleteIdentityByTaskId", taskId);
//
//    }
//
//    /**
//     * 修复循环删除问题
//     * @param processInstanceId
//     * @return
//     */
//    @Override
//    public void deleteIdentityLinksByProcInstance(String processInstanceId) {
//
//        // Identity links from db
//        List<IdentityLinkEntity> identityLinks = findIdentityLinksByProcessInstanceId(processInstanceId);
//        // Delete
//        getDbSqlSession().delete("deleteIdentityByProcInstId", processInstanceId);
//
//        // Identity links from cache, if not already deleted
//        List<IdentityLinkEntity> identityLinksFromCache = Context.getCommandContext().getDbSqlSession().findInCache(IdentityLinkEntity.class);
//        boolean alreadyDeleted = false;
//        for (IdentityLinkEntity identityLinkEntity : identityLinksFromCache) {
//            if (processInstanceId.equals(identityLinkEntity.getProcessInstanceId())) {
//                alreadyDeleted = false;
//                for(IdentityLinkEntity deleted : identityLinks) {
//                    if(deleted.getId() != null && deleted.getId().equals(identityLinkEntity.getId())) {
//                        alreadyDeleted = true;
//                        break;
//                    }
//                }
//
//                if(!alreadyDeleted) {
//                    deleteIdentityLink(identityLinkEntity, false);
//                }
//            }
//        }
//    }
//}
