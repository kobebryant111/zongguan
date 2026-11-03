package com.work.ppm.application.api.banktxmanager;

import cn.hutool.http.server.HttpServerRequest;
import com.work.ppm.application.dto.banktxmanager.PpmAprivalInfo;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.banktxmanager.IntegInfo;
import com.work.ppm.application.model.banktxmanager.PpmAprovalListDto;
import com.work.ppm.application.model.banktxmanager.PpmIntegeListDto;
import com.work.ppm.application.model.banktxmanager.PpmKepAprovalDto;
import com.work.ppm.application.services.banktxmanager.PpmEmpTxInfoImpl;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
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
import java.util.List;

/**
 * 员工调休
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmEmpTxInfoApi"})
@RestController
@RequestMapping("/detailsLeave")
@Slf4j
public class PpmEmpTxInfoApi extends BaseApi {
    @Autowired
    private PpmEmpTxInfoImpl ppmEmpTxInfo;

    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "approvalStatus", value = "审批状态", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<Object> queryPager(@RequestBody PpmAprivalInfo ppmAprivalInfo,@RequestBody Pager pager) {
        ApiResult<Object> result = new ApiResult<>();
        ppmEmpTxInfo.queryPager(ppmAprivalInfo,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }
    @PostMapping(value = "submitApproval")
    @ApiOperation(value = "提交/保存草稿", notes = "提交/保存草稿")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "approvalStatus", value = "审批状态", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> submitApproval(@RequestBody PpmKepAprovalDto ppmKepAprovalDto, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmEmpTxInfo.submitApproval(ppmKepAprovalDto,user);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "deleteApproval")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "approvalStatus", value = "审批状态", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> deleteApproval(@RequestBody PpmKepAprovalDto ppmKepAprovalDto) {
        ApiResult<Object> result = new ApiResult<>();
        ppmEmpTxInfo.deleteApproval(ppmKepAprovalDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
    @PostMapping(value = "userApproval")
    @ApiOperation(value = "使用调休天数", notes = "使用调休天数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "approvalStatus", value = "审批状态", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> userApproval(@RequestBody PpmKepAprovalDto ppmKepAprovalDto) {
        ApiResult<Object> result = new ApiResult<>();
        double restNo = ppmEmpTxInfo.userApproval(ppmKepAprovalDto);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(restNo);
        return result;
    }
    @PostMapping(value = "pointsQueryPaper")
    @ApiOperation(value = "使用调休天数", notes = "使用调休天数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "approvalStatus", value = "审批状态", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> pointsQueryPaper(@RequestBody PpmAprivalInfo ppmAprivalInfo,@RequestBody Pager pager, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmEmpTxInfo.pointsQueryPaper(ppmAprivalInfo,pager,user);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(pager.getList());
        result.setTotal(pager.getTotal());
        return result;
    }

    @PostMapping(value = "confirmPointsChoice")
    @ApiOperation(value = "使用调休天数", notes = "使用调休天数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "approvalStatus", value = "审批状态", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> confirmPointsChoice(@RequestBody PpmIntegeListDto ppmIntegeListDto) {
        ApiResult<Object> result = new ApiResult<>();
        ppmEmpTxInfo.confirmPointsChoice(ppmIntegeListDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "leaveQueryPager")
    @ApiOperation(value = "查询调休审批", notes = "查询调休审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "approvalStatus", value = "审批状态", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> leaveQueryPager(@RequestBody PpmAprivalInfo ppmAprivalInfo,@RequestBody Pager pager, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmEmpTxInfo.leaveQueryPager(ppmAprivalInfo,pager,user);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(pager.getList());
        result.setTotal(pager.getTotal());
        return result;
    }
    @PostMapping(value = "confirmLeaveChoice")
    @ApiOperation(value = "使用调休天数", notes = "使用调休天数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "approvalStatus", value = "审批状态", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> confirmLeaveChoice(@RequestBody PpmAprovalListDto ppmAprovalListDto) {
        ApiResult<Object> result = new ApiResult<>();
        ppmEmpTxInfo.confirmLeaveChoice(ppmAprovalListDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
