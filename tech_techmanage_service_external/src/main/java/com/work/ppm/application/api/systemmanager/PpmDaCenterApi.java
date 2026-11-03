package com.work.ppm.application.api.systemmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.application.dto.systemmanager.PpmDaCenterDto;
import com.work.ppm.application.services.systemmanager.IPpmDaCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 中心
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmDaCenterApi"},description="中心")
@RestController
@RequestMapping("ppmDaCenter")
@Slf4j
public class PpmDaCenterApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmDaCenterService ppmDaCenterService;

    /**
     * 查询中心集合
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryList")
    @ApiOperation(value = "查询中心集合", notes = "查询中心集合")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "centertype", value = "部门类型 01.管理中心 02.研发中心 03.数据中心 04.测试中心", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "centername", value = "中心名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regusrid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "regtime", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "resuserid", value = "负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "resname", value = "负责人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "ord", value = "排序列", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "部门类型1.管理中心 2.研发中心 3.数据中心 4.测试中心", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "zxjgsusrid", value = "中心架构师人员ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "zxjgsusername", value = "中心架构师人员NAME", dataType = "string", paramType = "query"),
    })
    public ApiResult<List<PpmDaCenterDto>> queryList() {
        ApiResult<List<PpmDaCenterDto>> result =new ApiResult<>();
        List<PpmDaCenterDto> item = ppmDaCenterService.getList();
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 根据中心id查询部门
     * @param centerId 中心id
     * @return ApiResult
     */
    @GetMapping(value = "queryDeptList")
    @ApiOperation(value = "根据中心id查询部门", notes = "根据中心id查询部门")
    @ApiImplicitParam(name = "centerId", value = "主键", dataType = "String", paramType = "query")
    public ApiResult<List<PpmDaCenterDto>> queryDeptList(String centerId) {
        ApiResult<List<PpmDaCenterDto>> result =new ApiResult<>();
        List<PpmDaCenterDto> item = ppmDaCenterService.queryDeptList(centerId);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 根据多个中心id查询部门
     * @param centerIds 中心ids
     * @return ApiResult
     */
    @GetMapping(value = "queryDeptListBycenterIds")
    @ApiOperation(value = "根据多个中心id查询部门", notes = "根据多个中心id查询部门")
    @ApiImplicitParam(name = "centerIds", value = "主键", dataType = "String", paramType = "query")
    public ApiResult<List<PpmDaCenterDto>> queryDeptListBycenterIds(String centerIds) {
        ApiResult<List<PpmDaCenterDto>> result =new ApiResult<>();
        List<PpmDaCenterDto> item = ppmDaCenterService.queryDeptListBycenterIds(centerIds);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 根据部门id查询中心
     * @param depId 部门id
     * @return ApiResult
     */
    @GetMapping(value = "queryCenter")
    @ApiOperation(value = "根据部门id查询中心", notes = "根据部门id查询中心")
    @ApiImplicitParam(name = "depId", value = "部门id", dataType = "integer", paramType = "query")
    public ApiResult<PpmDaCenterDto> queryCenter(String depId) {
        ApiResult<PpmDaCenterDto> result = new ApiResult<>();
        PpmDaCenterDto item = ppmDaCenterService.queryCenter(depId);
        result.setResults(item);
        result.success("查询成功");
        return result;
    }
}
