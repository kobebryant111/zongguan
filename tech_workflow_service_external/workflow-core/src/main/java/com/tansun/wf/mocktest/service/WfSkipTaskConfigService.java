//package com.tansun.wf.mocktest.service;
//
//import com.tansun.wf.extend.db.WfBaseManager;
//import com.tansun.wf.extend.vo.WfResponseVO;
//import com.tansun.wf.mocktest.mapper.WfSkipTaskConfigMapper;
//import com.tansun.wf.mocktest.model.WfSkipTaskConfigEntity;
//import com.tansun.wf.mocktest.query.WfSkipTaskConfigQuery;
//import com.tansun.workflow.model.DebugInfo;
//import com.tansun.workflow.model.ProcessInfo;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//// camunda-TODO
//@Service
//public class WfSkipTaskConfigService extends WfBaseManager<WfSkipTaskConfigMapper, WfSkipTaskConfigEntity> {
//
//    @Autowired
//    private WfSkipTaskConfigMapper wfSkipTaskConfigMapper;
//
//    public void changeOpenStatus(List<String> ids, String isOpen) {
//        wfSkipTaskConfigMapper.changeOpenStatus(ids,isOpen);
//    }
//
//    public WfResponseVO saveConfig(WfSkipTaskConfigEntity entity) {
//        String tenantId = entity.getTenantId();
//        String processDefKey = entity.getProcessDefKey();
//        WfSkipTaskConfigQuery query = new WfSkipTaskConfigQuery();
//        query.tenantId(tenantId);
//        query.processDefKey(processDefKey);
//        if (null == entity.getId() && processDefKey!=null){
//            List<WfSkipTaskConfigEntity> list = this.findList(query);
//            if (CollectionUtils.isNotEmpty(list)) {
//                return WfResponseVO.errorResponse("流程键值【"+entity.getProcessDefKey()+"】已经配置" );
//            }
//        }
//        this.save(entity);
//        return WfResponseVO.successResponse("操作成功");
//    }
//
//
//    public void fillWorkDebugConfig(ProcessInfo processInfo) {
//        DebugInfo debugInfo = processInfo.getDebugInfo();
//        if (debugInfo != null && StringUtils.isNotBlank(debugInfo.getIsOpen())) {
//            return;
//        }
//
//        debugInfo = new DebugInfo();
//
//        WfSkipTaskConfigQuery query = new WfSkipTaskConfigQuery();
//        query.isGlobal("N");
//        query.tenantId(processInfo.getTenantId());
//        query.processDefKey(processInfo.getProcessDefineKey());
//
//        List<WfSkipTaskConfigEntity> entities = this.findList(query);
//        if (CollectionUtils.isNotEmpty(entities) && entities.get(0) != null) {
//            debugInfo.setTargetTaskAssignee(entities.get(0).getTaskAssignee());
//            debugInfo.setTargetTaskNodeId(entities.get(0).getTargetNodeId());
//            debugInfo.setIsOpen(entities.get(0).getIsOpen());
//            processInfo.setDebugInfo(debugInfo);
//            return;
//        }
//
//        WfSkipTaskConfigQuery global = new WfSkipTaskConfigQuery();
//        global.isGlobal("Y");
//        List<WfSkipTaskConfigEntity> entities1 = this.findList(global);
//        if (CollectionUtils.isNotEmpty(entities1) && entities1.get(0)!=null) {
//            debugInfo.setTargetTaskAssignee(entities1.get(0).getTaskAssignee());
//            debugInfo.setIsOpen(entities1.get(0).getIsOpen());
//            processInfo.setDebugInfo(debugInfo);
//        }
//    }
//}
