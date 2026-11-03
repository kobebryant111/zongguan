package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.application.dto.integratedmanager.PpmDistributionMasterDto;
import com.work.ppm.application.services.integratedmanager.IPpmDistributionMasterService;
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

/**
 * 入职部门分配主表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmDistributionMasterApi"},description="入职部门分配主表")
@RestController
@RequestMapping("ppmDistributionMaster")
@Slf4j
public class PpmDistributionMasterApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmDistributionMasterService ppmDistributionMasterService;


    /**
     * 查询详情
     * @param ppmDistributionMasterDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "自增ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "initiateName", value = "发起人名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "initiateId", value = "发起人工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addDate", value = "发起时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除(0:正常，1：删除)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceId", value = "流程编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "batchNo", value = "批次号", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmDistributionMasterDto> queryDetail(PpmDistributionMasterDto ppmDistributionMasterDto) {
        ApiResult<PpmDistributionMasterDto> result =new ApiResult<>();
        PpmDistributionMasterDto item = ppmDistributionMasterService.queryDetail(ppmDistributionMasterDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 流程发起
     * @param ppmDistributionMasterDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "liuzhuan")
    @ApiOperation(value = "流程发起", notes = "流程发起")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "自增ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "initiateName", value = "发起人名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "initiateId", value = "发起人工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addDate", value = "发起时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除(0:正常，1：删除)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceId", value = "流程编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "resumeIds", value = "简历id，多个使用“,” 分割", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> liuzhuan(@RequestBody PpmDistributionMasterDto ppmDistributionMasterDto) {
        ApiResult<Object> result = new ApiResult<>();
        String msg = ppmDistributionMasterService.liuzhuan(ppmDistributionMasterDto);
        result.success(msg);
        return result;
    }

    /**
     * 处理人审批
     * @param ppmDistributionMasterDto 对象
     * @return ApiResult
     */
    @PostMapping("processApproval")
    @ApiOperation(value = "处理人审批", notes = "页面元素请参考流转方法")
    public ApiResult processApproval(@RequestBody PpmDistributionMasterDto ppmDistributionMasterDto,
                                     HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmDistributionMasterDto.getBps().setUserId(user.getUserid());
        String msg = ppmDistributionMasterService.processApproval(ppmDistributionMasterDto);
        result.success(msg);
        return result;
    }
}
