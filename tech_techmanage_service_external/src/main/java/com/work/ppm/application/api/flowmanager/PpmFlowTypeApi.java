package com.work.ppm.application.api.flowmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.application.dto.flowmanager.PpmFlowTypeDto;
import com.work.ppm.application.model.flowmanager.PpmFlowType;
import com.work.ppm.application.services.flowmanager.IPpmFlowTypeService;
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
 * 流程类型
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmFlowTypeApi"}, description = "流程类型")
@RestController
@RequestMapping("ppmFlowType")
@Slf4j
public class PpmFlowTypeApi {
    /**
     * 流程类型接口
     */
    @Autowired
    private IPpmFlowTypeService ppmFlowTypeService;

    /**
     * 获取流程树
     * @param ppmFlowType 对象
     * @return ApiResult<List>
     */
    @GetMapping(value = "loadFlow")
    @ApiOperation(value ="获取流程树", notes = "获取流程树")
    public ApiResult<List> queryFlowTree(PpmFlowType ppmFlowType) {
        ApiResult<List> result =new ApiResult<>();
        List<PpmFlowTypeDto> items = ppmFlowTypeService.queryFlowTree(ppmFlowType);
        result.setResults(items);
        result.success("获取数据成功");
        return result;
    }

    /**
     * 获取所有流程类型
     * @return ApiResult<List<PpmFlowTypeDto>>
     */
    @GetMapping("getTypeAlls")
    @ApiOperation(value = "获取所有流程类型")
    public ApiResult<List<PpmFlowTypeDto>> getListAll() {
        ApiResult<List<PpmFlowTypeDto>> result =new ApiResult<>();
        List<PpmFlowTypeDto> items = ppmFlowTypeService.getListAll();
        result.setResults(items);
        result.success("获取数据成功");
        return result;
    }

    /**
     * 查询详情
     * @param ppmFlowType 对象
     * @return ApiResult<PpmFlowType>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "flowname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowdraftno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "parentid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmFlowType> queryDetail(PpmFlowType ppmFlowType) {
        ApiResult<PpmFlowType> result =new ApiResult<>();
        PpmFlowType item = ppmFlowTypeService.queryDetail(ppmFlowType);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmFlowType 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "flowname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowdraftno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "parentid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmFlowType(PpmFlowType ppmFlowType) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmFlowTypeService.updatePpmFlowType(ppmFlowType);
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
     * @param ppmFlowType 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Integer", paramType = "query"),

    })
    public ApiResult deletePpmFlowType (PpmFlowType ppmFlowType) {
        ApiResult result =new ApiResult<>();
        ppmFlowType.setIsdel(1);
        int id = ppmFlowTypeService.updatePpmFlowType(ppmFlowType);
        if(id > 0){
            result.success("刪除成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 获取两级流程树
     * @param ppmFlowType 对象
     * @return ApiResult<List<PpmFlowTypeDto>>
     */
    @GetMapping(value = "loadTwoLevelFlow")
    @ApiOperation(value ="获取两级流程树", notes = "获取两级流程树")
    public ApiResult<List<PpmFlowTypeDto>> queryTwoLevelFlowTree(PpmFlowType ppmFlowType) {
        ApiResult<List<PpmFlowTypeDto>> result =new ApiResult<>();
        List<PpmFlowTypeDto> items = ppmFlowTypeService.queryTwoLevelFlowTree(ppmFlowType);
        result.setResults(items);
        result.success("获取数据成功");
        return result;
    }
}
