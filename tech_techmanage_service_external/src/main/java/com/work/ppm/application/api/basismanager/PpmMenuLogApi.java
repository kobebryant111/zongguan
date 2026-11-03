package com.work.ppm.application.api.basismanager;

import com.work.ppm.application.dto.basismanager.PpmMenuLogDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.basismanager.IPpmMenuLogService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 菜单日志记录
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmMenuLogApi"})
@RestController
@RequestMapping("ppmMenuLog")
@Slf4j
public class PpmMenuLogApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmMenuLogService ppmMenuLogService;

    /**
     * 分页
     * @param ppmMenuLogDto 对象
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
    public ApiResult queryPager(PpmMenuLogDto ppmMenuLogDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmMenuLogService.queryPager(ppmMenuLogDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }
    /**
     * 添加菜单日志
     * @param ppmMenuLogDto 对象
     * @return ApiResult 对象
     */
    @PostMapping(value = "addMenuLog")
    @ApiOperation(value = "添加菜单日志", notes = "添加菜单日志")
    public ApiResult addMenuLog(@RequestBody PpmMenuLogDto ppmMenuLogDto,  HttpServletRequest request) {
        ApiResult result =new ApiResult<>();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        ppmMenuLogDto.setIp(IpUtil.getIpAddress(request));
        ppmMenuLogDto.setUserId(user.getUserid());
        ppmMenuLogDto.setUserName(user.getOperatorname());
        ppmMenuLogService.addPpmMenuLog(ppmMenuLogDto);
        result.success("添加成功");
        return result;
    }

    /**
     * 查询详情
     * @param ppmMenuLogDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmMenuLogDto> queryDetail(PpmMenuLogDto ppmMenuLogDto) {
        ApiResult<PpmMenuLogDto> result =new ApiResult<>();
        PpmMenuLogDto item = ppmMenuLogService.queryDetail(ppmMenuLogDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
