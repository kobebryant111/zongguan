package com.work.ppm.application.api.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmFlowFollowupDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.projectmanager.IPpmFlowFollowupService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 后督流程
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmFlowFollowupApi"},description="描述该类叫什么")
@RestController
@RequestMapping("ppmFlowFollowup")
@Slf4j
public class PpmFlowFollowupApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmFlowFollowupService ppmFlowFollowupService;


    /**
     * 查询详情
     * @param ppmFlowFollowupDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowid", value = "流程类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "truedealtime", value = "实际维护时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态（1：未开始，2：进行中，3：实施结束，4：验证结束）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "istimeout", value = "是否超时（0：未超时，1：超时）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "starttime", value = "开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "endtime", value = "结束时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "dealdeptid", value = "处理部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealerid", value = "处理人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remindtype", value = "通知", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isstart", value = "是否开始（1：需审批但未开始，2：需审批且已开始）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "affectqr", value = "本次更新影响情况确认 ", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "truestartdealtime", value = "实际维护开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "systemid", value = "关联系统", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmFlowFollowupDto> queryDetail(PpmFlowFollowupDto ppmFlowFollowupDto) {
        ApiResult<PpmFlowFollowupDto> result =new ApiResult<>();
        PpmFlowFollowupDto item = ppmFlowFollowupService.queryDetail(ppmFlowFollowupDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 发起后督流程
     * @param ppmFlowFollowupDto 对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping("initialeFlow")
    @ApiOperation(value = "发起后督流程", notes = "发起后督流程")
    public ApiResult initialeFlow(PpmFlowFollowupDto ppmFlowFollowupDto, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        ppmFlowFollowupService.initialeFlow(ppmFlowFollowupDto, user);
        result.success("发起成功");
        return result;
    }

}
