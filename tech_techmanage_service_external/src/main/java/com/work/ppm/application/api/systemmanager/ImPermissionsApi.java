package com.work.ppm.application.api.systemmanager;

import com.work.ppm.application.dto.systemmanager.ImPermissionsDto;
import com.work.ppm.application.model.systemmanager.ImPermissions;
import com.work.ppm.application.services.systemmanager.IImPermissionsService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单角色关联
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"ImPermissionsApi"}, description = "菜单角色关联")
@RestController
@RequestMapping("imPermissions")
@Slf4j
public class ImPermissionsApi {
    /**
     * 菜单角色关联
     */
    @Autowired
    private IImPermissionsService imPermissionsService;

    /**
     * 查询详情
     * @param imPermissions 对象
     * @return ApiResult<List<ImPermissions>>
     */
    @GetMapping(value = "list")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rid", value = "角色ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mid", value = "菜单ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			
    })
    public ApiResult<List<ImPermissions>> queryRidForList(ImPermissions imPermissions) {
        ApiResult<List<ImPermissions>> result =new ApiResult<>();
        if(!StringUtil.isStringNotEmpty(imPermissions.getRid())) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            result.setMessage("请选择角色");
            return result;
        }
        List<ImPermissions> item = imPermissionsService.queryRidForList(imPermissions.getRid());
        result.success("获取成功");
        result.setResults(item);
        return result;
    }

    /**
     * 添加用户菜单
     * @param imPermissions 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "add")
    @ApiOperation(value = "add", notes = "添加用户菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rid", value = "角色ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mid", value = "菜单ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			
    })
    public ApiResult<Object> addImPermissions(ImPermissionsDto imPermissions) {
        ApiResult<Object> result =new ApiResult<>();
        if(!StringUtil.isNotEmpty(imPermissions.getMids())) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            result.setMessage("请选择菜单权限");
            return result;
        }
        int id = imPermissionsService.addImPermissions(imPermissions);
        if(id > 0){
            result.success("添加成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }


    /**
     * 添加用户菜单
     * @param imPermissions 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "addOperaImPermissions")
    @ApiOperation(value = "addOperaImPermissions", notes = "添加角色操作权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rid", value = "角色ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mid", value = "菜单ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
    })
    public ApiResult<Object> addOperaPermissions(ImPermissionsDto imPermissions) {
        ApiResult<Object> result =new ApiResult<>();
        if(!StringUtil.isNotEmpty(imPermissions.getMids())) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            result.setMessage("请选择操作按钮");
            return result;
        }
        int i = imPermissionsService.addOperaPermissions(imPermissions);
        if(i > 0){
            result.success("添加成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }




    /**
     * 查询详情
     * @param imPermissions 对象
     * @return ApiResult<List<ImPermissions>>
     */
    @GetMapping(value = "queryOperaList")
    @ApiOperation(value = "queryOperaList", notes = "查询相关角色下的操作按钮")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rid", value = "角色ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mid", value = "菜单ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),

    })
    public ApiResult<List<ImPermissions>> queryOperaList(ImPermissions imPermissions) {
        ApiResult<List<ImPermissions>> result =new ApiResult<>();
        if(!StringUtil.isStringNotEmpty(imPermissions.getRid())) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            result.setMessage("请选择角色");
            return result;
        }
        List<ImPermissions> item = imPermissionsService.queryOperaList(imPermissions.getRid());
        result.success("获取成功");
        result.setResults(item);
        return result;
    }


}
