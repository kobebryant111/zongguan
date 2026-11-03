package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmRlwbhtInfoDto;
import com.work.ppm.application.services.integratedmanager.IPpmRlwbhtInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmRlwbhtInfoApi"},description="人力合同信息表")
@RestController
@RequestMapping("ppmRlwbhtInfo")
@Slf4j
public class PpmRlwbhtInfoApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmRlwbhtInfoService ppmRlwbhtInfoService;

    /**
     * 分页
     * @param ppmRlwbhtInfoDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgcode", value = "公司ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htmc", value = "合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htid", value = "合同ID", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmRlwbhtInfoDto ppmRlwbhtInfoDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmRlwbhtInfoService.queryPager(ppmRlwbhtInfoDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmRlwbhtInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgcode", value = "公司ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htmc", value = "合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htid", value = "合同ID", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmRlwbhtInfoDto> queryDetail(PpmRlwbhtInfoDto ppmRlwbhtInfoDto) {
        ApiResult<PpmRlwbhtInfoDto> result =new ApiResult<>();
        PpmRlwbhtInfoDto item = ppmRlwbhtInfoService.queryDetail(ppmRlwbhtInfoDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmRlwbhtInfoDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgcode", value = "公司ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htmc", value = "合同名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "htid", value = "合同ID", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmRlwbhtInfo(PpmRlwbhtInfoDto ppmRlwbhtInfoDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmRlwbhtInfoService.updatePpmRlwbhtInfo(ppmRlwbhtInfoDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmRlwbhtInfoDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmRlwbhtInfo (PpmRlwbhtInfoDto ppmRlwbhtInfoDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmRlwbhtInfoService.deletePpmRlwbhtInfo(ppmRlwbhtInfoDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
    /**
     * 查询人力合同下拉框
     * @param info 对象
     * @return ApiResult<List<PpmRlwbhtInfoDto>>
     */
    @GetMapping("queryOrInsertPpmRlwbhtInfo")
    @ApiOperation("查询合同下拉框")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgcode", value = "供应商ID", dataType = "string", paramType = "query" ,required=true),
    })
    public ApiResult<List<PpmRlwbhtInfoDto>> queryOrInsertPpmRlwbhtInfo(PpmRlwbhtInfoDto info) {
        ApiResult<List<PpmRlwbhtInfoDto>> result =new ApiResult<>();
        List<PpmRlwbhtInfoDto> dtoList = ppmRlwbhtInfoService.queryOrInsertPpmRlwbhtInfo(info);
        result.setResults(dtoList);
        result.success("查询成功");
        return result;
    }


}
