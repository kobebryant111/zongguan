package com.work.ppm.application.api.testmanager;

import com.work.ppm.application.dto.testmanager.TestApectacularsDto;
import com.work.ppm.application.dto.testmanager.TestApectacularsInfoExportDto;
import com.work.ppm.application.services.testmanager.ITestApectacularsService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * packageName com.work.ppm.application.api.testmanager
 *
 * @version JDK 8
 * @className TestApectacularsApi
 * @date 2024/7/30
 * 测试管理-测试看板
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"TestApectaculars"},description="测试管理-测试看板")
@RestController
@RequestMapping("/testApectaculars")
@Slf4j
public class TestApectacularsApi {

    @Autowired
    private ITestApectacularsService iTestApectacularsService;

    /**
     * 测试看板分页查询
     *
     * @param testApectacularsDto 测试看板对象
     * @param pager                分页对象
     * @return ApiResult
     */
    @PostMapping(value = "/queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    public ApiResult queryPager(TestApectacularsDto testApectacularsDto, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        iTestApectacularsService.queryPager(testApectacularsDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }


    /**
     * 测试看板信息导出
     *
     * @param testApectacularsDto 测试看板对象
     * @param response 响应参数
     * @return ApiResult
     */
    @PostMapping(value = "/queryPagerExcel")
    @ApiOperation(value ="测试看板信息导出", notes = "测试看板信息导出")
    public void queryPagerExcel(@RequestBody TestApectacularsDto testApectacularsDto, HttpServletResponse response) {
        List<TestApectacularsInfoExportDto> reqs= this.iTestApectacularsService.queryPagerExcel(testApectacularsDto);
        if (!reqs.isEmpty()){
            ExcelUtil.exportExcel(reqs, TestApectacularsInfoExportDto.class, "测试看板信息表.xls", response);
        }
        log.info("导出成功。");
    }
}
