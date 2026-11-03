package com.work.ppm.application.api.kpi;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiItemSwitchItemDto;
import com.work.ppm.application.services.kpi.IPpmKpiItemSwitchItemService;
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
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiItemSwitchItemApi"},description="考核配置发起中心数据来源表")
@RestController
@RequestMapping("ppmKpiItemSwitchItem")
@Slf4j
public class PpmKpiItemSwitchItemApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKpiItemSwitchItemService ppmKpiItemSwitchItemService;

    /**
     * 分页
     * @param ppmKpiItemSwitchItemDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relroles", value = "相关角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isswitch", value = "是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itype", value = "考核开关类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "launchtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isvisible", value = "是否显示0：显示 1：不显示", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "itemtype", value = "配置项类型", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiItemSwitchItemService.queryPager(ppmKpiItemSwitchItemDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKpiItemSwitchItemDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relroles", value = "相关角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isswitch", value = "是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itype", value = "考核开关类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "launchtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isvisible", value = "是否显示0：显示 1：不显示", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "itemtype", value = "配置项类型", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmKpiItemSwitchItemDto> queryDetail(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto) {
        ApiResult<PpmKpiItemSwitchItemDto> result =new ApiResult<>();
        PpmKpiItemSwitchItemDto item = ppmKpiItemSwitchItemService.queryDetail(ppmKpiItemSwitchItemDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKpiItemSwitchItemDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relroles", value = "相关角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isswitch", value = "是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itype", value = "考核开关类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "launchtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isvisible", value = "是否显示0：显示 1：不显示", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "itemtype", value = "配置项类型", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmKpiItemSwitchItem(PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiItemSwitchItemService.updatePpmKpiItemSwitchItem(ppmKpiItemSwitchItemDto);
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
     * @param ppmKpiItemSwitchItemDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKpiItemSwitchItem (PpmKpiItemSwitchItemDto ppmKpiItemSwitchItemDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiItemSwitchItemService.deletePpmKpiItemSwitchItem(ppmKpiItemSwitchItemDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
