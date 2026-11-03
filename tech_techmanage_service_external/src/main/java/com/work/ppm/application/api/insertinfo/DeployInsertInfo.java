package com.work.ppm.application.api.insertinfo;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.model.interpoints.CommInterPointsDto;
import com.work.ppm.application.services.insertinfo.IDeployInsertInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 配置管理
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"interPoints"}, description = "配置管理")
@RestController
@RequestMapping("interPoints")
@Slf4j
@Validated
public class DeployInsertInfo {

    @Autowired
    private IDeployInsertInfoService deployInsertInfoService;


    /**
     * 查询评分项
     * @param commInterPointsDto 查询条件实体类
     * @return ApiResult<List> 返回实体类
     */
    @PostMapping(value = "insertInfo")
    @ApiOperation(value = "查询评分项", notes = "查询评分项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectScale", value = "项目规模", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "interviewRole", value = "面试角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "investOptions", value = "考察项", dataType = "string", paramType = "query"),
    })
    public ApiResult<List> insertInfo(@RequestBody CommInterPointsDto commInterPointsDto) {
        ApiResult<List> result = new ApiResult<>();
        List<CommInterPointsDto> list = deployInsertInfoService.insertInfo(commInterPointsDto);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(list);
        return result;
    }


    /**
     * 修改评分项
     * @param commInterPointsDto 修改评分项条件实体类
     * @return ApiResult 返回实体类
     */
    @PostMapping(value = "keepIhfo")
    @ApiOperation(value = "查询评分项", notes = "查询评分项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkId", value = "主键", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectScale", value = "项目规模", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "interviewRole", value = "面试角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "investOptions", value = "考察项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scoreA", value = "A", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "scoreB", value = "B", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "scoreC", value = "C", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "scoreD", value = "D", dataType = "int", paramType = "query"),
    })
    public ApiResult keepIhfo(@RequestBody CommInterPointsDto commInterPointsDto) {
        ApiResult result = new ApiResult<>();
        deployInsertInfoService.keepIhfo(commInterPointsDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 删除评分项
     * @param commInterPointsDto 删除评分项条件实体类
     * @return ApiResult 返回实体类
     */
    @PostMapping(value = "deleteIhfo")
    @ApiOperation(value = "更新评分项", notes = "查询评分项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pkId", value = "主键", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectScale", value = "项目规模", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "interviewRole", value = "面试角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "investOptions", value = "考察项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scoreA", value = "A", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "scoreB", value = "B", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "scoreC", value = "C", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "scoreD", value = "D", dataType = "int", paramType = "query"),
    })
    public ApiResult deleteIhfo(@RequestBody CommInterPointsDto commInterPointsDto) {
        ApiResult result = new ApiResult<>();
        deployInsertInfoService.deleteIhfo(commInterPointsDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
