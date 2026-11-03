package com.work.ppm.application.services.flowmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.flowmanager.PpmFlowShouquan;

import java.util.List;

/**
 * 流程授权
 */
public interface IPpmFlowShouquanService {

    /**
     * 获取集合
     * @param ppmFlowShouquan 对象
     * @return List<PpmFlowShouquan>
     */
    List<PpmFlowShouquan> getPpmFlowShouquanList(PpmFlowShouquan ppmFlowShouquan) ;

    /**
     * 分页
     * @param ppmFlowShouquan 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmFlowShouquan ppmFlowShouquan, Pager pager) ;

    /**
     * 添加
     * @param ppmFlowShouquan 对象
     * @return int
     */
     @Deprecated
    int addPpmFlowShouquan(PpmFlowShouquan ppmFlowShouquan) ;

    /**
     * 查询详情
     * @param ppmFlowShouquan 对象
     * @return PpmFlowShouquan
     */
    @Deprecated
    PpmFlowShouquan queryDetail(PpmFlowShouquan ppmFlowShouquan) ;

    /**
     * 更新
     * @param ppmFlowShouquan 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmFlowShouquan(PpmFlowShouquan ppmFlowShouquan) ;

    /**
     * 删除
     * @param ppmFlowShouquan 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmFlowShouquan(PpmFlowShouquan ppmFlowShouquan) ;
}
