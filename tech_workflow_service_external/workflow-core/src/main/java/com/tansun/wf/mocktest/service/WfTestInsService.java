//package com.tansun.wf.mocktest.service;
//
//import com.tansun.wf.extend.db.WfBaseManager;
//import com.tansun.wf.extend.vo.WfResponseVO;
//import com.tansun.wf.mocktest.mapper.WfTestInsMapper;
//import com.tansun.wf.mocktest.model.WfTestInsEntity;
//import com.tansun.wf.mocktest.model.WfTestPlanEntity;
//import com.tansun.wf.mocktest.query.WfTestPlanQuery;
//import org.springframework.stereotype.Service;
//
// camunda-TODO
//import java.util.List;
//
//@Service
//public class WfTestInsService extends WfBaseManager<WfTestInsMapper, WfTestInsEntity> {
//
//    public WfResponseVO addIns(WfTestInsEntity entity) {
//        this.insert(entity);
//        return WfResponseVO.successResponse("操作成功");
//    }
//
//    public WfResponseVO deleteIns(WfTestInsEntity entity) {
//        this.delete(entity);
//        return WfResponseVO.successResponse("操作成功");
//    }
//
//    public List<WfTestPlanEntity> findList(WfTestPlanQuery query) {
//        return this.findList(query);
//    }
//}
