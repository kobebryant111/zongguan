package com.work.ppm.application.api.processmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.processmanager.PpmNewBranchBankAcceptanceDto;
import com.work.ppm.application.services.processmanager.IPpmNewBranchBankAcceptanceService;
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
 * 新支行交付流程
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmNewBranchBankAcceptanceApi"})
@RestController
@RequestMapping("ppmNewBranchBankAcceptance")
@Slf4j
public class PpmNewBranchBankAcceptanceApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmNewBranchBankAcceptanceService ppmNewBranchBankAcceptanceService;

    /**
     * 分页
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @param pager                         分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "instanceId", value = "流程ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "发起人ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptId", value = "发起人部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contact", value = "发起人联系方式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createDate", value = "发起时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "processType", value = "流程类型", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "processTitle", value = "流程标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "processDescribe", value = "流程说明", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "netcoolManagerId", value = "NETCOOL监控责任人",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "netcoolDecision", value = "NETCOOL监控是否上线(0-否，1-是)",
                    dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "upsManagerId", value = "UPS监控责任人",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "upsDecision", value = "UPS监控是否上线(0-否，1-是)",
                    dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "ackManagerId", value = "ACK策略责任人",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ackDecision", value = "ACK策略是否上线(0-否，1-是)",
                    dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "发起人名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "currentId", value = "当前处理人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastMofiyDate", value = "最后一个更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "状态", dataType = "long", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        ppmNewBranchBankAcceptanceService.queryPager(ppmNewBranchBankAcceptanceDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     *
     * @param ppmNewBranchBankAcceptanceDto dto对象
     * @param request                       request对象
     * @return ApiResult
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instanceId", value = "流程ID", dataType = "string", paramType = "query")
    })
    public ApiResult<PpmNewBranchBankAcceptanceDto> queryDetail(
            PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto,
            HttpServletRequest request) {
        ApiResult<PpmNewBranchBankAcceptanceDto> result = new ApiResult<>();
        CommOperatorDoLoginDto usr = super.getCurrentUser(request);
        ppmNewBranchBankAcceptanceDto.setBadge(usr.getUserid());
        PpmNewBranchBankAcceptanceDto item = ppmNewBranchBankAcceptanceService.
                queryDetail(ppmNewBranchBankAcceptanceDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 发起申请
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @param request                       对象
     * @return ApiResult
     */
    @PostMapping(value = "initiate")
    @ApiOperation(value = "发起申请", notes = "发起申请")
    public ApiResult<PpmNewBranchBankAcceptanceDto> initiate(
            @RequestBody PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto,
            HttpServletRequest request) {
        ApiResult<PpmNewBranchBankAcceptanceDto> result = new ApiResult<>();
        CommOperatorDoLoginDto usr = super.getCurrentUser(request);
        ppmNewBranchBankAcceptanceDto.setBadge(usr.getUserid());
        String message = ppmNewBranchBankAcceptanceService.initiate(ppmNewBranchBankAcceptanceDto);
        result.setMessage(message);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 保存草稿
     *
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @param request                       对象
     * @return ApiResult
     */
    @PostMapping(value = "saveDraft")
    @ApiOperation(value = "保存草稿", notes = "保存草稿")
    public ApiResult<PpmNewBranchBankAcceptanceDto> saveDraft(
            @RequestBody PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto,
            HttpServletRequest request) {
        ApiResult<PpmNewBranchBankAcceptanceDto> result = new ApiResult<>();
        CommOperatorDoLoginDto usr = super.getCurrentUser(request);
        ppmNewBranchBankAcceptanceDto.setBadge(usr.getUserid());
        String message = ppmNewBranchBankAcceptanceService.draft(ppmNewBranchBankAcceptanceDto);
        result.setMessage(message);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 流转
     * @param ppmNewBranchBankAcceptanceDto 对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "submitFlow")
    @ApiOperation(value = "流转", notes = "流转")
    public ApiResult<Object> submitFlow(@RequestBody PpmNewBranchBankAcceptanceDto ppmNewBranchBankAcceptanceDto,
                                        HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmNewBranchBankAcceptanceDto.getBps().setUserId(user.getUserid());
        String message = ppmNewBranchBankAcceptanceService.doFlow(ppmNewBranchBankAcceptanceDto);
        result.setMessage(message);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询当前用户信息
     *
     * @param request servlet对象
     * @return 用户信息
     */
    @GetMapping(value = "getCurrentInfo")
    @ApiOperation(value = "查询当前用户信息", notes = "流转")
    public ApiResult<Object> getCurrentInfo(HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        PpmNewBranchBankAcceptanceDto dto = ppmNewBranchBankAcceptanceService.getCurrentInfo(user.getUserid());
        result.setResults(dto);
        result.success("查询成功");
        return result;
    }

}
