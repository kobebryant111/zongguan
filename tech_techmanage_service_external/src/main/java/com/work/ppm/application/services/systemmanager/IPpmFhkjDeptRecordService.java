package com.work.ppm.application.services.systemmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.systemmanager.PpmFhkjDeptRecord;

/**
 * 分行科技配置管理
 */
public interface IPpmFhkjDeptRecordService {

    /**
     * 分页
     * @param ppmFhkjDeptRecord 对象
     * @param pager
     */
    @Deprecated
    void queryPager(PpmFhkjDeptRecord ppmFhkjDeptRecord, Pager pager) ;

    /**
     * 查询详情
     * @param ppmFhkjDeptRecord 对象
     * @return PpmFhkjDeptRecord
     */
    @Deprecated
    PpmFhkjDeptRecord queryDetail(PpmFhkjDeptRecord ppmFhkjDeptRecord) ;

    /**
     * 更新
     * @param ppmFhkjDeptRecord 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmFhkjDeptRecord(PpmFhkjDeptRecord ppmFhkjDeptRecord) ;

    /**
     * 删除
     * @param ppmFhkjDeptRecord 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmFhkjDeptRecord(PpmFhkjDeptRecord ppmFhkjDeptRecord) ;
}
