//package com.tansun.wf.mocktest.service;
//
//import com.github.pagehelper.PageInfo;
//import com.tansun.wf.extend.db.WfBaseManager;
//import com.tansun.wf.extend.vo.WfResponseVO;
//import com.tansun.wf.mocktest.mapper.WfTestPlanMapper;
//import com.tansun.wf.mocktest.model.WfSkipTaskConfigEntity;
//import com.tansun.wf.mocktest.model.WfTestInsEntity;
//import com.tansun.wf.mocktest.model.WfTestPlanEntity;
//import com.tansun.wf.mocktest.query.WfTestPlanQuery;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
////@Service
//// camunda-TODO
//public class WfTestPlanService extends WfBaseManager<WfTestPlanMapper, WfTestPlanEntity> {
//
//    public WfResponseVO savePlan(WfTestPlanEntity entity) {
//        if (null ==entity.getPlanCode() && null ==entity.getPlanCode()){
//         String pCode =  entity.getPlanCode();
//         String pName = entity.getPlanName();
//         WfTestPlanQuery wfTestPlanQuery = new WfTestPlanQuery();
//         wfTestPlanQuery.planCode(pCode);
//         wfTestPlanQuery.planName(pName);
//            List<WfTestPlanEntity> list = this.findList(wfTestPlanQuery);
//            if (CollectionUtils.isNotEmpty(list)) {
//                return WfResponseVO.errorResponse("测试码值【"+entity.getPlanCode()+"】已经配置" );
//            }
//        }
//        this.save(entity);
//        return WfResponseVO.successResponse("操作成功");
//    }
//
//    public PageInfo<WfTestPlanEntity> getPageList(WfTestPlanQuery query,PageInfo pageInfo) {
//        return this.findPage(query, pageInfo);
//    }
//}
