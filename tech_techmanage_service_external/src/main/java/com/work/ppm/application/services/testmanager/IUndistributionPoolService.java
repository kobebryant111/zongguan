package com.work.ppm.application.services.testmanager;

import com.work.ppm.application.dto.testmanager.TestEmployeePoolDto;
import com.work.ppm.application.dto.testmanager.TestEmployeePoolExportDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 待分配测试人员池
 */
public interface IUndistributionPoolService {
    /**
     * 分页查询
     * @param testEmployeePoolDto
     * @param pager
     */
    void queryPager(TestEmployeePoolDto testEmployeePoolDto, Pager pager);

    /**
     * 获取所有的公司内负责人
     * @return
     */
    List<CommEmployee> getHeadOfCompanyList();

    /**
     * 未分配测试人员池数据导出
     * @param testEmployeePoolDto
     * @return
     */
    List<TestEmployeePoolExportDto> exportUndistriPoolExcel(TestEmployeePoolDto testEmployeePoolDto);

    /**
     * 测试人员出池
     * @param testEmployeePoolDto
     * @return
     */
    ApiResult dialogAddSubmit(TestEmployeePoolDto testEmployeePoolDto);

}
