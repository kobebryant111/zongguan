package com.work.ppm.application.api.banktxmanager;

import com.work.ppm.application.dto.banktxmanager.IntegInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.banktxmanager.IntegInfo;
import com.work.ppm.application.services.banktxmanager.IEmpTxApproverDetailService;
import com.work.ppm.application.services.banktxmanager.IEmpTxUnrecogIntegService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 行员调休积分API接口
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"EmpTxApproverDetail"})
@RestController
@RequestMapping("/empTxApproverDetail")
@Slf4j
public class EmpTxApproverDetailApi extends BaseApi {

    /**
     * 待审核积分信息服务接口
     */
    @Autowired
    private IEmpTxApproverDetailService iEmpTxApproverDetailService ;

    /**
     * 分页查询
     * @param integInfoDto 对象
     * @param pager        分页对象
     * @param request      request
     * @return ApiResult
     */
    @PostMapping(value = "/queryPagerForApprover")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<Object> queryPagerForApprover(IntegInfoDto integInfoDto, Pager pager, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        iEmpTxApproverDetailService.queryPagerForApprover(integInfoDto , pager, user);

        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

}
