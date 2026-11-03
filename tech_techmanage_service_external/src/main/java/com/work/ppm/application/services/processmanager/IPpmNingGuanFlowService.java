package com.work.ppm.application.services.processmanager;

import com.work.ppm.application.dto.processmanager.PpmNingGuanFlowQueryDto;
import com.work.ppm.commons.pager.Pager;

import javax.servlet.http.HttpServletResponse;

/**
 * 宁管部网点设备变更维护
 *
 */
public interface IPpmNingGuanFlowService {

    /**
     * 分页
     *
     * @param ppmNingGuanFlowQueryDto 对象
     * @param pager                   分页对象
     */
    void queryPager(PpmNingGuanFlowQueryDto ppmNingGuanFlowQueryDto, Pager pager);

    /**
     * 分页
     *
     * @param ppmNingGuanFlowQueryDto 对象
     */
    void exportExcel(PpmNingGuanFlowQueryDto ppmNingGuanFlowQueryDto, HttpServletResponse response);

}
