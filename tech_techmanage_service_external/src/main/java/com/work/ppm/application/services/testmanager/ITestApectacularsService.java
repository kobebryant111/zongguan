package com.work.ppm.application.services.testmanager;

import com.work.ppm.application.dto.testmanager.TestApectacularsDto;
import com.work.ppm.application.dto.testmanager.TestApectacularsInfoExportDto;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 测试看板
 */
public interface ITestApectacularsService {
    /**
     * 分页查询
     * @param testApectacularsDto
     * @param pager
     */
    void queryPager(TestApectacularsDto testApectacularsDto, Pager pager);

    /**
     * 测试看板数据导出
     * @param testApectacularsDto
     * @return
     */
    List<TestApectacularsInfoExportDto> queryPagerExcel(TestApectacularsDto testApectacularsDto);
}
