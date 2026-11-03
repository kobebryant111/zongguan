package com.work.ppm.application.api.processmanager;

import com.work.ppm.application.dto.processmanager.PpmChagedeviceChildrenDto;
import com.work.ppm.application.services.processmanager.IPpmChagedeviceChildrenService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 变更设备名称子表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmChagedeviceChildrenApi"},description="变更设备名称子表")
@RestController
@RequestMapping("ppmChagedeviceChildren")
@Slf4j
public class PpmChagedeviceChildrenApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmChagedeviceChildrenService ppmChagedeviceChildrenService;


    /**
     * 查询主表id查询集合
     * @param ppmChagedeviceChildrenDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryList")
    @ApiOperation(value = "查询主表id查询集合", notes = "查询主表id查询集合")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "mid", value = "主表ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "itemName", value = "选项名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemType", value = "选项类型", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "notifications", value = "是否必填（0：是，1：否）", dataType = "integer", paramType = "query"),
			
    })
    public ApiResult<List<PpmChagedeviceChildrenDto>> queryList(PpmChagedeviceChildrenDto ppmChagedeviceChildrenDto) {
        ApiResult<List<PpmChagedeviceChildrenDto>> result =new ApiResult<>();
        List<PpmChagedeviceChildrenDto> item = ppmChagedeviceChildrenService.queryList(ppmChagedeviceChildrenDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmChagedeviceChildrenDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "mid", value = "主表ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "itemName", value = "选项名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itemType", value = "选项类型", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "notifications", value = "是否必填（0：是，1：否）", dataType = "integer", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmChagedeviceChildren(PpmChagedeviceChildrenDto ppmChagedeviceChildrenDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmChagedeviceChildrenService.updatePpmChagedeviceChildren(ppmChagedeviceChildrenDto);
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
     * @param ppmChagedeviceChildrenDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmChagedeviceChildren (PpmChagedeviceChildrenDto ppmChagedeviceChildrenDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmChagedeviceChildrenService.deletePpmChagedeviceChildren(ppmChagedeviceChildrenDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
