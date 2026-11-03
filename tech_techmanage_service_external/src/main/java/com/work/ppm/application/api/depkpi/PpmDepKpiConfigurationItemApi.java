package com.work.ppm.application.api.depkpi;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.depkpi.PpmDepKpiConfigurationItemDto;
import com.work.ppm.application.services.depkpi.IPpmDepKpiConfigurationItemService;
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
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmDepKpiConfigurationItemApi"}, description = "描述该类叫什么")
@RestController
@RequestMapping("ppmDepKpiConfigurationItem")
@Slf4j
public class PpmDepKpiConfigurationItemApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmDepKpiConfigurationItemService ppmDepKpiConfigurationItemService;

    /**
     * 分页
     *
     * @param ppmDepKpiConfigurationItemDto 对象
     * @param pager                         分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "考核内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isswitch", value = "自动发起是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "部门考核类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "autolaunchtime", value = "自动发起时间MM-DD", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchtime", value = "发起时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchurl", value = "发起路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchtype", value = "发起方式", dataType = "long", paramType = "query"),


            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        ppmDepKpiConfigurationItemService.queryPager(ppmDepKpiConfigurationItemDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     *
     * @param ppmDepKpiConfigurationItemDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "考核内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isswitch", value = "自动发起是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "部门考核类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "autolaunchtime", value = "自动发起时间MM-DD", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchtime", value = "发起时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchurl", value = "发起路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchtype", value = "发起方式", dataType = "long", paramType = "query"),


    })
    public ApiResult<PpmDepKpiConfigurationItemDto> queryDetail(
            PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto) {
        ApiResult<PpmDepKpiConfigurationItemDto> result = new ApiResult<>();
        PpmDepKpiConfigurationItemDto item = ppmDepKpiConfigurationItemService.
                queryDetail(ppmDepKpiConfigurationItemDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     *
     * @param ppmDepKpiConfigurationItemDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "考核内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isswitch", value = "自动发起是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "部门考核类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "autolaunchtime", value = "自动发起时间MM-DD", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchtime", value = "发起时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchurl", value = "发起路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchtype", value = "发起方式", dataType = "long", paramType = "query"),


    })
    public ApiResult<Object> updatePpmDepKpiConfigurationItem(
            PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto) {
        ApiResult<Object> result = new ApiResult<>();
        int id = ppmDepKpiConfigurationItemService.updatePpmDepKpiConfigurationItem(ppmDepKpiConfigurationItemDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     *
     * @param ppmDepKpiConfigurationItemDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmDepKpiConfigurationItem(PpmDepKpiConfigurationItemDto ppmDepKpiConfigurationItemDto) {
        ApiResult result = new ApiResult<>();
        int id = ppmDepKpiConfigurationItemService.deletePpmDepKpiConfigurationItem(ppmDepKpiConfigurationItemDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
