package com.work.ppm.application.api.processmanager;

import com.work.ppm.application.dto.processmanager.PpmNingGuanFlowQueryDto;
import com.work.ppm.application.services.processmanager.IPpmNingGuanFlowService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 宁管流程
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"ppmNingGuanFlow"})
@RestController
@RequestMapping("ppmNingGuanFlow")
@Slf4j
public class PpmNingGuanFlowApi extends BaseApi {

    /**
     * 宁管流程查询
     */
    @Autowired
    private IPpmNingGuanFlowService ppmNingGuanFlowService;

    /**
     * 分页查询
     *
     * @param ppmNingGuanFlowQueryDto 对象
     * @param pager                   分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public ApiResult queryPager(PpmNingGuanFlowQueryDto ppmNingGuanFlowQueryDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        ppmNingGuanFlowService.queryPager(ppmNingGuanFlowQueryDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 导出excel
     *
     * @param ppmNingGuanFlowQueryDto 对象
     * @param response  对象
     */
    @PostMapping(value = "exportExcel")
    @ApiOperation(value = "导出excel", notes = "导出excel")
    public void exportExcel(PpmNingGuanFlowQueryDto ppmNingGuanFlowQueryDto, HttpServletResponse response) {
        ppmNingGuanFlowService.exportExcel(ppmNingGuanFlowQueryDto, response);
    }
}
