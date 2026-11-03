package com.work.ppm.application.api.flowmanager;


import com.work.ppm.application.dto.flowmanager.PpmRelateuserDto;
import com.work.ppm.application.services.flowmanager.IPpmRelateuserService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
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
 * 外包人员账号关联
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmRelateuserApi"},description="外包人员账号关联")
@RestController
@RequestMapping("ppmRelateuser")
@Slf4j
public class PpmRelateuserApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmRelateuserService ppmRelateuserService;

    /**
     * 分页
     * @param ppmRelateuserDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "userno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relateUserno", value = "", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmRelateuserDto ppmRelateuserDto, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmRelateuserService.queryPager(ppmRelateuserDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmRelateuserDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "userno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relateUserno", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmRelateuserDto> queryDetail(PpmRelateuserDto ppmRelateuserDto) {
        ApiResult<PpmRelateuserDto> result =new ApiResult<>();
        PpmRelateuserDto item = ppmRelateuserService.queryDetail(ppmRelateuserDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmRelateuserDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "userno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relateUserno", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmRelateuser(PpmRelateuserDto ppmRelateuserDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmRelateuserService.updatePpmRelateuser(ppmRelateuserDto);
        if(id == 1){
            result.success("更新成功");
        } else if(id == 2) {
            result.success("添加成功");
        } else {
            result.failure("执行失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmRelateuserDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmRelateuser (PpmRelateuserDto ppmRelateuserDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmRelateuserService.deletePpmRelateuser(ppmRelateuserDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
