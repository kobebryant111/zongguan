package com.work.ppm.application.services.othermanager;

import com.work.ppm.application.dto.projectmanager.DemandFileDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.othermanager.PpmDocumentCatalog;

/**
 * 文档目录
 */
public interface IPpmDocumentCatalogService {

    /**
     * 分页
     * @param ppmDocumentCatalog 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmDocumentCatalog ppmDocumentCatalog, Pager pager) ;

    /**
     * 查询详情
     * @param catalogNo 编号
     * @return PpmDocumentCatalog
     */
    PpmDocumentCatalog queryDetail(String catalogNo) ;

    /**
     * 更新
     * @param ppmDocumentCatalog 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmDocumentCatalog(PpmDocumentCatalog ppmDocumentCatalog) ;

    /**
     * 删除
     * @param ppmDocumentCatalog 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmDocumentCatalog(PpmDocumentCatalog ppmDocumentCatalog) ;

    /**
     * 保存文档
     * @param demandFileDto
     * @param demandno
     * @param userid
     */
    void saveDoucment(DemandFileDto demandFileDto, String demandno, String userid);
}
