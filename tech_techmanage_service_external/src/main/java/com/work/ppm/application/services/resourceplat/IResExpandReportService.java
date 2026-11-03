package com.work.ppm.application.services.resourceplat;

import javax.servlet.http.HttpServletResponse;

/**
 * 资源扩容业务处理
 */
public interface IResExpandReportService {
    /**
     * 导出资源申请详情
     * @param id 申请ID
     * @param  response response
     */
    void exportResAuditReport(String id, HttpServletResponse response);

}
