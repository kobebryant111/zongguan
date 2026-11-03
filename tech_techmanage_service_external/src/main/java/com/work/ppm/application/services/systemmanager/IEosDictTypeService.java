package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.model.systemmanager.EosDictType;
import com.work.ppm.commons.pager.Pager;

/**
 * 业务字典主表
 */
public interface IEosDictTypeService {

    /**
     * 分页
     * @param eosDictType 对象
     * @param pager
     */
    @Deprecated
    void queryPager(EosDictType eosDictType, Pager pager) ;

    /**
     * 查询详情
     * @param eosDictType 对象
     * @return EosDictType
     */
    @Deprecated
    EosDictType queryDetail(EosDictType eosDictType) ;

    /**
     * 更新
     * @param eosDictType 对象
     * @return Integer
     */
    @Deprecated
    Integer updateEosDictType(EosDictType eosDictType) ;

    /**
     * 删除
     * @param eosDictType 对象
     * @return Integer
     */
    @Deprecated
    Integer deleteEosDictType(EosDictType eosDictType) ;
}
