package com.work.ppm.application.api.systemmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.application.dto.systemmanager.ImMenusDto;
import com.work.ppm.application.model.systemmanager.ImMenus;
import com.work.ppm.application.services.systemmanager.IImMenusService;
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
 * 菜单
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"ImMenusApi"}, description = "菜单")
@RestController
@RequestMapping("imMenus")
@Slf4j
public class ImMenusApi {
    /**
     * 菜单
     */
    @Autowired
    private IImMenusService imMenusService;

    /**
     * 分页
     * @param imMenus 对象
     * @return ApiResult<List>
     */
    @GetMapping(value = "loadMenu")
    @ApiOperation(value ="loadMenu", notes = "菜单树")
    public ApiResult<List> queryMenuTree(ImMenus imMenus) {
        ApiResult<List> result =new ApiResult<>();
        List<ImMenusDto> items = imMenusService.queryMenusTree(imMenus);
        result.setResults(items);
        result.success("获取数据成功");
        return result;
    }

    /**
     * 查询详情
     * @param imMenus      对象
     * @return ApiResult<ImMenus>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "type", value = "菜单类型，-1为树根不可删除，0为菜单权限，2为2级子菜单权限,3为子菜单", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "code", value = "权限CODE", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "parentid", value = "父ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "routeid", value = "前端路由ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "url", value = "URL", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "readme", value = "备注说明", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "菜单名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ordernum", value = "排序(序号)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "modifydate", value = "修改时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "mid", value = "主键ID，UUID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "icon", value = "图标CODE", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<ImMenus> queryDetail(ImMenus imMenus) {
        ApiResult<ImMenus> result =new ApiResult<>();
        ImMenus item = imMenusService.queryDetail(imMenus);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param imMenus 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "update", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "菜单类型，-1为树根不可删除，0为菜单权限，1为子菜单权限", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "code", value = "权限CODE", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "parentid", value = "父ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "routeid", value = "前端路由ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "url", value = "URL", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "readme", value = "备注说明", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "菜单名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ordernum", value = "排序(序号)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "modifydate", value = "修改时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "mid", value = "主键ID，UUID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "icon", value = "图标CODE", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updateImMenus(ImMenus imMenus) {
        ApiResult<Object> result =new ApiResult<>();
        int id = imMenusService.updateImMenus(imMenus);
        if(id == 1){
            result.success("添加成功");
        } else if(id == 2) {
            result.success("更新成功");
        }else
         {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     * @param imMenus 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "delete", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mid", value = "主键ID，UUID", dataType = "string", paramType = "query"),
    })
    public ApiResult deleteImMenus (ImMenus imMenus) {
        ApiResult result =new ApiResult<>();
        int id = imMenusService.deleteImMenus(imMenus);
        if(id > 0){
            result.success("已删除");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 分页
     * @param imMenus 对象
     * @return ApiResult<List>
     */
    @GetMapping(value = "loadOperaMenu")
    @ApiOperation(value ="loadOperaMenu", notes = "菜单树")
    public ApiResult<List> queryOperaMenuTree(ImMenus imMenus) {
        ApiResult<List> result =new ApiResult<>();
        List<ImMenusDto> items = imMenusService.queryOperaMenusTree(imMenus);
        result.setResults(items);
        result.success("获取数据成功");
        return result;
    }
}
