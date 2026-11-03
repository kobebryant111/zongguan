package com.work.ppm.application.api.processmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.processmanager.PpmChagedeviceMasterDto;
import com.work.ppm.application.services.processmanager.IPpmChagedeviceMasterService;
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
 * 变更设备名称主表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmChagedeviceMasterApi"},description="变更设备名称主表")
@RestController
@RequestMapping("ppmChagedeviceMaster")
@Slf4j
public class PpmChagedeviceMasterApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmChagedeviceMasterService ppmChagedeviceMasterService;

    /**
     * 分页
     * @param ppmChagedeviceMasterDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "自增ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "devices", value = "设备名称", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmChagedeviceMasterDto ppmChagedeviceMasterDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmChagedeviceMasterService.queryPager(ppmChagedeviceMasterDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmChagedeviceMasterDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "自增ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "devices", value = "设备名称", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmChagedeviceMasterDto> queryDetail(PpmChagedeviceMasterDto ppmChagedeviceMasterDto) {
        ApiResult<PpmChagedeviceMasterDto> result =new ApiResult<>();
        PpmChagedeviceMasterDto item = ppmChagedeviceMasterService.queryDetail(ppmChagedeviceMasterDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmChagedeviceMasterDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "自增ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "devices", value = "设备名称", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmChagedeviceMaster(PpmChagedeviceMasterDto ppmChagedeviceMasterDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmChagedeviceMasterService.updatePpmChagedeviceMaster(ppmChagedeviceMasterDto);
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
     * @param ppmChagedeviceMasterDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmChagedeviceMaster (PpmChagedeviceMasterDto ppmChagedeviceMasterDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmChagedeviceMasterService.deletePpmChagedeviceMaster(ppmChagedeviceMasterDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 获取设备名称变更集合
     * @return ApiResult
     */
    @GetMapping("queryList")
    @ApiOperation(value = "获取设备名称变更集合")
    public ApiResult<List<PpmChagedeviceMasterDto>> queryList() {
        ApiResult<List<PpmChagedeviceMasterDto>> result = new ApiResult<>();
        List<PpmChagedeviceMasterDto> list = ppmChagedeviceMasterService.queryList();
        result.setResults(list);
        result.success("查询成功");
        return result;
    }
}
