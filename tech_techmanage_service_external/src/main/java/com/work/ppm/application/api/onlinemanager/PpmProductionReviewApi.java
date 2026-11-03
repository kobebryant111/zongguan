package com.work.ppm.application.api.onlinemanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.application.dto.onlinemanager.PpmProductionReviewDto;
import com.work.ppm.application.services.onlinemanager.IPpmProductionReviewService;
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
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmProductionReviewApi"},description="描述该类叫什么")
@RestController
@RequestMapping("ppmProductionReview")
@Slf4j
public class PpmProductionReviewApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmProductionReviewService ppmProductionReviewService;

    /**
     * 查询详情
     * @param ppmProductionReviewDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetailByInstanceid")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "productionno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "onlineno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "chgdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "reviewres", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "xmpj", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pjcont", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gbfs", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tsqksm", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jlpjcont", value = "项目经理评价", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "xmjlpj", value = "对项目经理总体评价", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isyqyz", value = "延期验证   1：是   2：否", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jhyzdate", value = "计划验证时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "planonline", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isqbsx", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmProductionReviewDto> queryDetailByInstanceid(PpmProductionReviewDto ppmProductionReviewDto) {
        ApiResult<PpmProductionReviewDto> result = new ApiResult<>();
        PpmProductionReviewDto item = ppmProductionReviewService.queryDetailByInstanceid(ppmProductionReviewDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

	/**
	 * 分行意向上线验证发起
	 * @param ppmProductionReviewDto 对象
	 * @param request 请求
	 * @return ApiResult
	 */
	@PostMapping(value = "addPpmProductionReviewFlow")
	@ApiOperation(value = "分行意向上线验证发起", notes = "分行意向上线验证发起")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "productionno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "onlineno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "chgdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "reviewres", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "xmpj", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pjcont", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gbfs", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tsqksm", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jlpjcont", value = "项目经理评价", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "xmjlpj", value = "对项目经理总体评价", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isyqyz", value = "延期验证   1：是   2：否", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jhyzdate", value = "计划验证时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "planonline", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isqbsx", value = "", dataType = "string", paramType = "query"),

	})
	public ApiResult<String> addPpmProductionReviewFlow(@RequestBody PpmProductionReviewDto ppmProductionReviewDto,
														HttpServletRequest request) {
		ApiResult<String> result = new ApiResult<>();

		BpsWorkFlowDto bps = ppmProductionReviewDto.getBps();
		if (null == bps) {
			bps=new BpsWorkFlowDto();
		}

		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		bps.setUserId(user.getUserid());

		int success = ppmProductionReviewService.addPpmProductionReviewFlow(ppmProductionReviewDto, "182402");
		if(success > 0) {
			result.success("流程发起成功！");
			result.setStatus(ApiCode.SUCCESS);
		} else {
			result.success("流程发起失败！");
			result.setStatus(ApiCode.BPS_WORKFLOW_FAIL);
		}

		return result;
	}

    /**
     * 分行意向上线验证业务经理审批
     * @param ppmProductionReviewDto 对象
	 * @param request 请求
     * @return ApiResult
     */
    @PostMapping(value = "approveReviewByBusjl")
    @ApiOperation(value = "分行意向上线验证业务经理审批", notes = "分行意向上线验证业务经理审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "productionno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "onlineno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "projectname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "chgdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "reviewres", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "xmpj", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pjcont", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gbfs", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tsqksm", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jlpjcont", value = "项目经理评价", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "xmjlpj", value = "对项目经理总体评价", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isyqyz", value = "延期验证   1：是   2：否", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jhyzdate", value = "计划验证时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "planonline", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isqbsx", value = "", dataType = "string", paramType = "query"),

    })
    public ApiResult<String> approveReviewByBusjl(@RequestBody PpmProductionReviewDto ppmProductionReviewDto,
												  HttpServletRequest request) {
		ApiResult<String> result = new ApiResult<>();

		BpsWorkFlowDto bps = ppmProductionReviewDto.getBps();
		if (null == bps) {
			bps=new BpsWorkFlowDto();
		}

		CommOperatorDoLoginDto user =  super.getCurrentUser(request);
		bps.setUserId(user.getUserid());

		String msg = ppmProductionReviewService.approveReviewByBusjl(ppmProductionReviewDto, bps);
		result.success(msg);
        return result;
    }

	/**
	 * 手机端延迟上线时只更新数据
	 * @param ppmProductionReviewDto 对象
	 * @return   ApiResult
	 */
	@PostMapping("modifyMobile")
	@ApiOperation(value = "手机端延迟上线时只更新数据", notes ="手机端延迟上线调用接口" )
    public ApiResult updateMoblie(@RequestBody PpmProductionReviewDto ppmProductionReviewDto) {
		ApiResult result = new ApiResult();
		ppmProductionReviewService.updateMoblie(ppmProductionReviewDto);
		result.success("更新成功");
		return result;
	}
}
