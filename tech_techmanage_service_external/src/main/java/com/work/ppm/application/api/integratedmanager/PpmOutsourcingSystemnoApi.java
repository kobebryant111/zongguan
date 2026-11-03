package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingSystemnoDto;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingSystemnoService;
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
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcingSystemnoApi"},description="外包历史系统")
@RestController
@RequestMapping("ppmOutsourcingSystemno")
@Slf4j
public class PpmOutsourcingSystemnoApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmOutsourcingSystemnoService ppmOutsourcingSystemnoService;

    /**
     * 分页
     * @param ppmOutsourcingSystemnoDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "time", value = "时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemnos", value = "系统ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemname", value = "系统名称", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmOutsourcingSystemnoService.queryPager(ppmOutsourcingSystemnoDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmOutsourcingSystemnoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "time", value = "时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemnos", value = "系统ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemname", value = "系统名称", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmOutsourcingSystemnoDto> queryDetail(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto) {
        ApiResult<PpmOutsourcingSystemnoDto> result =new ApiResult<>();
        PpmOutsourcingSystemnoDto item = ppmOutsourcingSystemnoService.queryDetail(ppmOutsourcingSystemnoDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmOutsourcingSystemnoDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "time", value = "时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemnos", value = "系统ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemname", value = "系统名称", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmOutsourcingSystemno(PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOutsourcingSystemnoService.updatePpmOutsourcingSystemno(ppmOutsourcingSystemnoDto);
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
     * @param ppmOutsourcingSystemnoDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmOutsourcingSystemno (PpmOutsourcingSystemnoDto ppmOutsourcingSystemnoDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmOutsourcingSystemnoService.deletePpmOutsourcingSystemno(ppmOutsourcingSystemnoDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
