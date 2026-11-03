package com.work.ppm.application.services.flowmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.flowmanager.PpmFlowShouquanLog;

/**
 * 流程授权日志
 */
public interface IPpmFlowShouquanLogService {

    /**
     * 分页
     * @param ppmFlowShouquanLog 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmFlowShouquanLog ppmFlowShouquanLog, Pager pager) ;

    /**
     * 添加
     * @param ppmFlowShouquanLog 对象
     * @return int
     */
    int addPpmFlowShouquanLog(PpmFlowShouquanLog ppmFlowShouquanLog) ;

    /**
     * 查询详情
     * @param ppmFlowShouquanLog 对象
     * @return PpmFlowShouquanLog
     */
    @Deprecated
    PpmFlowShouquanLog queryDetail(PpmFlowShouquanLog ppmFlowShouquanLog) ;

    /**
     * 更新
     * @param ppmFlowShouquanLog 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmFlowShouquanLog(PpmFlowShouquanLog ppmFlowShouquanLog) ;

    /**
     * 删除
     * @param ppmFlowShouquanLog 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmFlowShouquanLog(PpmFlowShouquanLog ppmFlowShouquanLog) ;
}
