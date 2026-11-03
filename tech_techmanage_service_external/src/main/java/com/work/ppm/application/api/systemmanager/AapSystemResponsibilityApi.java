package com.work.ppm.application.api.systemmanager;

import com.work.ppm.application.dto.systemmanager.AapSystemResponsibilityDto;
import com.work.ppm.application.services.systemmanager.IAapSystemResponsibilityService;
import com.work.ppm.commons.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统负责人
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"AapSystemResponsibility"}, description = "系统负责人")
@RestController
@RequestMapping("aapSystemResponsibility")
@Slf4j
public class AapSystemResponsibilityApi {
    /**
     * 系统负责人
     */
    @Autowired
    private IAapSystemResponsibilityService aapSystemResponsibilityService;

    /**
     * 获取系统AB岗负责人
     * @param sysId 系统编号
     * @return ApiResult
     */
    @GetMapping("getSystemHead")
    @ApiOperation(value = "获取系统负责人", notes = "根据sysId查询")
    @ApiImplicitParam(name = "sysId", value = "系统编号", dataType = "string", paramType = "query")
    public ApiResult<List<AapSystemResponsibilityDto>> getSystemHead(@RequestParam String sysId) {
        ApiResult<List<AapSystemResponsibilityDto>> result = new ApiResult();
        List<AapSystemResponsibilityDto> item = aapSystemResponsibilityService.querySystemHeadAb(sysId);
        result.setResults(item);
        result.success("查询成功");
        return result;
    }
}
