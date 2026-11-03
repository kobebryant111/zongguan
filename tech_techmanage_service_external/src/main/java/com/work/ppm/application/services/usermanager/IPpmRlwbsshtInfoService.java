package com.work.ppm.application.services.usermanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.usermanager.PpmRlwbsshtInfo;

/**
 * 人力合同
 */
public interface IPpmRlwbsshtInfoService {

    /**
     * 分页
     * @param ppmRlwbsshtInfo 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmRlwbsshtInfo ppmRlwbsshtInfo, Pager pager) ;

    /**
     * 添加
     * @param ppmRlwbsshtInfo 对象
     * @return int
     */
    int addPpmRlwbsshtInfo(PpmRlwbsshtInfo ppmRlwbsshtInfo) ;

    /**
     * 查询详情
     * @param ppmRlwbsshtInfo 对象
     * @return PpmRlwbsshtInfo
     */
    PpmRlwbsshtInfo queryDetail(PpmRlwbsshtInfo ppmRlwbsshtInfo) ;

    /**
     * 更新
     * @param ppmRlwbsshtInfo 对象
     * @return Integer
     */
    Integer updatePpmRlwbsshtInfo(PpmRlwbsshtInfo ppmRlwbsshtInfo) ;

    /**
     * 删除
     * @param ppmRlwbsshtInfo 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmRlwbsshtInfo(PpmRlwbsshtInfo ppmRlwbsshtInfo) ;
}
