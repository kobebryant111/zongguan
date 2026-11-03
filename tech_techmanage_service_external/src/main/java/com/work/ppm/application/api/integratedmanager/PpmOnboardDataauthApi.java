package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.application.dto.integratedmanager.PpmOnboardDataauthDto;
import com.work.ppm.application.services.integratedmanager.IPpmOnboardDataauthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 入职部门分配数据权限
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOnboardDataauthApi"},description="入职部门分配数据权限")
@RestController
@RequestMapping("ppmOnboardDataauth")
@Slf4j
public class PpmOnboardDataauthApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmOnboardDataauthService ppmOnboardDataauthService;


    /**
     * 查询详情
     * @param ppmOnboardDataauthDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "自增编号", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "centerId", value = "中心ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "userId", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userName", value = "名称", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmOnboardDataauthDto> queryDetail(PpmOnboardDataauthDto ppmOnboardDataauthDto) {
        ApiResult<PpmOnboardDataauthDto> result =new ApiResult<>();
        PpmOnboardDataauthDto item = ppmOnboardDataauthService.queryDetail(ppmOnboardDataauthDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmOnboardDataauthDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "自增编号", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "centerId", value = "中心ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "userId", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userName", value = "名称", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmOnboardDataauth(@RequestBody  List<PpmOnboardDataauthDto> ppmOnboardDataauthDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOnboardDataauthService.updatePpmOnboardDataauth(ppmOnboardDataauthDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 获取查询权限列表
     * @param request request
     * @return ApiResult
     */
    @GetMapping("getViewAuth")
    @ApiOperation(value = "获取查询权限列表", notes = "获取查询权限列表")
    public ApiResult<List<PpmOnboardDataauthDto>> getViewAuth(HttpServletRequest request) {
        ApiResult<List<PpmOnboardDataauthDto>> result = new ApiResult();
        List<PpmOnboardDataauthDto> list = ppmOnboardDataauthService.checkedAuth(null);
        result.setResults(list);
        result.success("获取成功");
        return result;
    }
}
