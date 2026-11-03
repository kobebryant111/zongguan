package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.integratedmanager.PpmManagercenterWork;

/**
 * 要是督办审批
 */
public interface IPpmManagercenterWorkService {
    /**
     * 启动流程
     * @return String
     */
    String startWorkFlow() ;

    /**
     * 查询详情
     * @param ppmManagercenterWork 对象
     * @return PpmManagercenterWork
     */
    @Deprecated
    PpmManagercenterWork queryDetail(PpmManagercenterWork ppmManagercenterWork) ;

    /**
     * 更新
     * @param ppmManagercenterWork 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmManagercenterWork(PpmManagercenterWork ppmManagercenterWork) ;

    /**
     * 删除
     * @param ppmManagercenterWork 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmManagercenterWork(PpmManagercenterWork ppmManagercenterWork) ;

    /**
     * 督办审批
     * @param bps 对象
     * @return  String
     */
    String dbApprove(BpsWorkFlowDto bps) ;

    /**
     * 总经理审批
     * @param bps 对象
     * @return String
     */
    String zjlApprove(BpsWorkFlowDto bps) ;

    /**
     * 申领结项
     * @param bps 对象
     * @return String
     */
    String sqApprove(BpsWorkFlowDto bps) ;
}
