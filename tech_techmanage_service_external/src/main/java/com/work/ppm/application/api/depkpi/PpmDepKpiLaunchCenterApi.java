package com.work.ppm.application.api.depkpi;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.application.dto.depkpi.PpmDepKpiLaunchCenterDto;
import com.work.ppm.application.services.depkpi.IPpmDepKpiLaunchCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmDepKpiLaunchCenterApi"})
@RestController
@RequestMapping("ppmDepKpiLaunchCenter")
@Slf4j
public class PpmDepKpiLaunchCenterApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmDepKpiLaunchCenterService ppmDepKpiLaunchCenterService;

    /**
     * 更新
     * @param ppmDepKpiLaunchCenterDto 对象
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
            @ApiImplicitParam(name = "launchtime", value = "发起时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchurl", value = "发起路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchtype", value = "发起方式", dataType = "long", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmDepKpiLaunchCenter(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmDepKpiLaunchCenterService.updatePpmDepKpiLaunchCenter(ppmDepKpiLaunchCenterDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }


    /**
     * 根据条件查询数据
     * @param ppmDepKpiLaunchCenterDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "getPpmDepKpiDepRelList")
    @ApiOperation(value = "根据条件查询数据", notes = "根据条件查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "考核内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isswitch", value = "自动发起是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "部门考核类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "autolaunchtime", value = "自动发起时间MM-DD", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchtime", value = "发起时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchurl", value = "发起路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchtype", value = "发起方式", dataType = "long", paramType = "query"),

    })
    public ApiResult<List<PpmDepKpiLaunchCenterDto>> getPpmDepKpiDepRelList(
            PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto) {
        ApiResult<List<PpmDepKpiLaunchCenterDto>> result =new ApiResult<>();
        List<PpmDepKpiLaunchCenterDto> dtos = ppmDepKpiLaunchCenterService.
                getPpmDepKpiLaunchCenterList(ppmDepKpiLaunchCenterDto);
        result.setResults(dtos);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 生成数据
     * @param ppmDepKpiLaunchCenterDto 对象
     * @param request   对象
     * @return ApiResult
     */
    @PostMapping(value = "createPpmDepKpiLaunchCenter")
    @ApiOperation(value = "生成数据", notes = "生成数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "考核内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isswitch", value = "自动发起是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "部门考核类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "autolaunchtime", value = "自动发起时间MM-DD", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchtime", value = "发起时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "周期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchurl", value = "发起路径", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "launchtype", value = "发起方式", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> createPpmDepKpiLaunchCenter(PpmDepKpiLaunchCenterDto ppmDepKpiLaunchCenterDto,
                                                         HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int res = ppmDepKpiLaunchCenterService.createPpmDepKpiLaunchCenter(ppmDepKpiLaunchCenterDto,user.getUserid());
        if(res >=0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("发起失败");
        }
        return result;
    }
}
