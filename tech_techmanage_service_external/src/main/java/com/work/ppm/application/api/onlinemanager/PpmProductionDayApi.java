package com.work.ppm.application.api.onlinemanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.onlinemanager.PpmProductionDayDto;
import com.work.ppm.application.services.onlinemanager.IPpmProductionDayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmProductionDayApi"},description="描述该类叫什么")
@RestController
@RequestMapping("ppmProductionDay")
@Slf4j
public class PpmProductionDayApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmProductionDayService ppmProductionDayService;

    /**
     * 分页
     * @param ppmProductionDayDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmProductionDayDto ppmProductionDayDto, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmProductionDayService.queryPager(ppmProductionDayDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmProductionDayDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           
    })
    public ApiResult<PpmProductionDayDto> queryDetail(PpmProductionDayDto ppmProductionDayDto) {
        ApiResult<PpmProductionDayDto> result =new ApiResult<>();
        PpmProductionDayDto item = ppmProductionDayService.queryDetail(ppmProductionDayDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
