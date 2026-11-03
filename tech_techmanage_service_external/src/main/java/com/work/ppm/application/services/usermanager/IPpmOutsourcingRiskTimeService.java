package com.work.ppm.application.services.usermanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.usermanager.PpmOutsourcingRiskTime;

/**
 * 外包风控排查时间接口
 */
public interface IPpmOutsourcingRiskTimeService {

    /**
     * 分页
     * @param ppmOutsourcingRiskTime 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmOutsourcingRiskTime ppmOutsourcingRiskTime, Pager pager) ;

    /**
     * 添加
     * @param ppmOutsourcingRiskTime 对象
     * @return int
     */
    int addPpmOutsourcingRiskTime(PpmOutsourcingRiskTime ppmOutsourcingRiskTime) ;

    /**
     * 查询详情
     * @param ppmOutsourcingRiskTime 对象
     * @return PpmOutsourcingRiskTime
     */
    @Deprecated
    PpmOutsourcingRiskTime queryDetail(PpmOutsourcingRiskTime ppmOutsourcingRiskTime) ;

    /**
     * 更新
     * @param ppmOutsourcingRiskTime 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmOutsourcingRiskTime(PpmOutsourcingRiskTime ppmOutsourcingRiskTime) ;

    /**
     * 删除
     * @param ppmOutsourcingRiskTime 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmOutsourcingRiskTime(PpmOutsourcingRiskTime ppmOutsourcingRiskTime) ;
}
