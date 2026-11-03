package com.work.ppm.application.services.resourceplat;

import com.work.ppm.application.model.resourceplat.ResAdded;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 资源新增总办技评报表导出
 * service
 * @date 2021/11/04
 */
public interface IResAddedReportService {
    /**
     * 生成报表
     * @param wb excel工作簿
     * @param resAdd 实体
     */
    void generateReport(HSSFWorkbook wb, ResAdded resAdd);
}
