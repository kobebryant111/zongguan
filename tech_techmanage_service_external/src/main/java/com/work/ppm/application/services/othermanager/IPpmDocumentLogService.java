package com.work.ppm.application.services.othermanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.othermanager.PpmDocumentLog;

/**
 * 文档日志
 */
public interface IPpmDocumentLogService {

    /**
     * 分页
     * @param ppmDocumentLog 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmDocumentLog ppmDocumentLog, Pager pager) ;

    /**
     * 添加
     * @param ppmDocumentLog 对象
     * @return int
     */
    int addPpmDocumentLog(PpmDocumentLog ppmDocumentLog) ;

    /**
     * 查询详情
     * @param ppmDocumentLog 对象
     * @return PpmDocumentLog
     */
    @Deprecated
    PpmDocumentLog queryDetail(PpmDocumentLog ppmDocumentLog) ;

    /**
     * 更新
     * @param ppmDocumentLog 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmDocumentLog(PpmDocumentLog ppmDocumentLog) ;

    /**
     * 删除
     * @param ppmDocumentLog 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmDocumentLog(PpmDocumentLog ppmDocumentLog) ;
}
