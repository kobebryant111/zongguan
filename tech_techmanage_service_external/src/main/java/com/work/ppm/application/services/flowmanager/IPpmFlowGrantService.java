package com.work.ppm.application.services.flowmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.flowmanager.PpmFlowGrantDto;
import com.work.ppm.application.model.flowmanager.PpmFlowGrant;

/**
 * 流程授权
 */
public interface IPpmFlowGrantService {

    /**
     * 分页
     * @param ppmFlowGrant 对象
     * @param pager 对象
     */
    @Deprecated
    void queryPager(PpmFlowGrant ppmFlowGrant, Pager pager) ;

    /**
     * 添加
     * @param ppmFlowGrant 对象
     * @return int
     */
     @Deprecated
    int addPpmFlowGrant(PpmFlowGrant ppmFlowGrant) ;

    /**
     * 查询详情
     * @param ppmFlowGrant 对象
     * @return PpmFlowGrant
     */
    PpmFlowGrant queryDetail(PpmFlowGrantDto ppmFlowGrant) ;

    /**
     * 更新
     * @param ppmFlowGrant 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmFlowGrant(PpmFlowGrant ppmFlowGrant) ;

    /**
     * 删除
     * @param ppmFlowGrant 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmFlowGrant(PpmFlowGrant ppmFlowGrant) ;
}
