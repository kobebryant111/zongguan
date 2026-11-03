package com.work.ppm.application.api.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDetailDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.depkpi.IPpmDepkpiModelDetailService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 模板详情
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmDepkpiModelDetailApi"})
@RestController
@RequestMapping("ppmDepkpiModelDetail")
@Slf4j
public class PpmDepkpiModelDetailApi extends BaseApi {
    /**
     * 模板详情表接口
     */
    @Autowired
    private IPpmDepkpiModelDetailService ppmDepkpiModelDetailService;

    /**
     * 模板提交
     *
     * @param ppmDepkpiModelDetailDto 对象
     * @param request                 request
     * @return ApiResult
     */
    @PostMapping(value = "commitModelTarget")
    @ApiOperation(value = "模板提交", notes = "模板提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelid", value = "模板ID", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> commitModelTarget(PpmDepkpiModelDetailDto ppmDepkpiModelDetailDto,
                                               HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiModelDetailService.commitModelTarget(ppmDepkpiModelDetailDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else if (id == -1) {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("基准分配置不正确！");
        } else if(id == 0){
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("子集指标类型有为空的配置");
        }else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("提交失败");
        }
        return result;
    }

}
