package com.work.ppm.application.api.basismanager;

import com.work.ppm.application.dto.basismanager.PpmNewloginLogDto;
import com.work.ppm.application.services.basismanager.IPpmNewloginLogService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 登录日志
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmNewloginLogApi"})
@RestController
@RequestMapping("ppmNewloginLog")
@Slf4j
public class PpmNewloginLogApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmNewloginLogService ppmNewloginLogService;

    /**
     * 分页
     * @param ppmNewloginLogDto 对象
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
    public ApiResult queryPager(PpmNewloginLogDto ppmNewloginLogDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmNewloginLogService.queryPager(ppmNewloginLogDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmNewloginLogDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmNewloginLogDto> queryDetail(PpmNewloginLogDto ppmNewloginLogDto) {
        ApiResult<PpmNewloginLogDto> result =new ApiResult<>();
        PpmNewloginLogDto item = ppmNewloginLogService.queryDetail(ppmNewloginLogDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
