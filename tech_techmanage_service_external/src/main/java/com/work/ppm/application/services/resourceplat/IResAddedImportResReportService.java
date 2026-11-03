package com.work.ppm.application.services.resourceplat;

import com.work.ppm.application.model.resourceplat.ResAddedItem;

/**
 * 资源新增流转完成导入资源报表
 *
 */
public interface IResAddedImportResReportService {

    /**
     * 根据id将对应的数据导入报表中
     *
     * @param id           资源新增id
     * @param resAddedItem 资源新增对象
     */
    void importReport(String id, ResAddedItem resAddedItem);
}
