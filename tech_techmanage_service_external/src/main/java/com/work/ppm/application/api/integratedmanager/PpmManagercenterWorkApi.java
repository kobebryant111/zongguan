package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.services.integratedmanager.IPpmManagercenterWorkService;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 要是督办审批
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmManagercenterWorkApi"}, description="要是督办审批")
@RestController
@RequestMapping("ppmManagercenterWork")
@Slf4j
public class PpmManagercenterWorkApi extends BaseApi {
	/**
	 * 接口
	 */
    @Autowired
    private IPpmManagercenterWorkService ppmManagercenterWorkService;


	/**
	 * 申请结项
	 * @param bps bps
	 * @return ApiResult
	 */
	@PostMapping("sqApprove")
	public ApiResult sqApprove(BpsWorkFlowDto bps) {
		ApiResult result = new ApiResult();
//		CommOperatorDoLoginDto userInfo = super.getCurrentUser(request);
//		bps.setUserId("182430");
		String processMsg = ppmManagercenterWorkService.sqApprove(bps);
		result.success(processMsg);
		return result;
	}

	/**
	 * 督办审批
	 * @param bps  对象
	 * @param request request
	 * @return ApiResult
	 */
	@PostMapping("dbApprove")
	public ApiResult dbApprove(BpsWorkFlowDto bps, HttpServletRequest request) {
		ApiResult result = new ApiResult();
//		CommOperatorDoLoginDto userInfo = super.getCurrentUser(request);
//		bps.setUserId("182430");
		String processMsg = ppmManagercenterWorkService.dbApprove(bps);
		result.success(processMsg);
		return result;
	}

	/**
	 * 总经理审批
	 * @param bps  对象
	 * @param request request
	 * @return ApiResult
	 */
	@PostMapping("zjlApprove")
	public ApiResult zjlApprove(BpsWorkFlowDto bps, HttpServletRequest request) {
		ApiResult result = new ApiResult();
//		CommOperatorDoLoginDto userInfo = super.getCurrentUser(request);
		bps.setUserId("030029");
		String processMsg = ppmManagercenterWorkService.zjlApprove(bps);
		result.success(processMsg);
		return result;
	}


}
