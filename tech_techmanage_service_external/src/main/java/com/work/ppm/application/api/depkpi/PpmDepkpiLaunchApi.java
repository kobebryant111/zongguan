package com.work.ppm.application.api.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiLaunchDto;
import com.work.ppm.application.services.depkpi.IPpmDepkpiLuanchService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 发起中心
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmDepkpiLaunchApi"})
@RestController
@RequestMapping("ppmDepkpiLaunch")
@Slf4j
public class PpmDepkpiLaunchApi {
    /**
     * 部门详情
     */
    @Autowired
    private IPpmDepkpiLuanchService iPpmDepkpiLuanchService;

    /**
     * 发起
     * @return ApiResult 对象
     */
    @PostMapping(value = "launch")
    @ApiOperation(value = "发起", notes = "发起")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "考核内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "部门考核类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
    })
    public ApiResult luanch(PpmDepkpiLaunchDto ppmDepkpiLuanchDto) {
        ApiResult<Object> result = new ApiResult<>();
        int id = iPpmDepkpiLuanchService.luanch(ppmDepkpiLuanchDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("插入数据失败");
        }
        return result;
    }

}
