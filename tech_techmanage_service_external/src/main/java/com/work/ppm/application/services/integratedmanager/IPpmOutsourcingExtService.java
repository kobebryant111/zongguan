package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingExtInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingExt;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingExtInfo;
import com.work.ppm.commons.pager.Pager;

/**
 * 外包人员扩展
 */
public interface IPpmOutsourcingExtService {

    /**
     * 分页
     * @param ppmOutsourcingExt 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmOutsourcingExt ppmOutsourcingExt, Pager pager) ;

    /**
     * 添加
     * @param ppmOutsourcingExt 对象
     * @return int
     */
    int addPpmOutsourcingExt(PpmOutsourcingExt ppmOutsourcingExt) ;

    /**
     * 查询详情
     * @param ppmOutsourcingExt 对象
     * @return PpmOutsourcingExt
     */
    PpmOutsourcingExt queryDetail(PpmOutsourcingExt ppmOutsourcingExt) ;

    /**
     * 更新
     * @param ppmOutsourcingExt 对象
     * @return Integer
     */
    Integer updatePpmOutsourcingExt(PpmOutsourcingExt ppmOutsourcingExt) ;

    /**
     * 删除
     * @param ppmOutsourcingExt 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmOutsourcingExt(PpmOutsourcingExt ppmOutsourcingExt) ;

    /**
     * 查询详情数据带中文标识
     * @param PpmOutsourcingExtInfo
     * @return PpmOutsourcingExtInfo
     */
    PpmOutsourcingExtInfo queryDetailAll(PpmOutsourcingExtInfo PpmOutsourcingExtInfo);

    /**
     * 更新外包拓展数据
     * @param ppmOutsourcingExtInfo
     * @return Integer
     */
    Integer updatePpmOutsourcingExt(PpmOutsourcingExtInfoDto ppmOutsourcingExtInfo);
}
