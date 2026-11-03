package com.work.ppm.application.api.processmanager;

import com.work.ppm.application.dto.processmanager.PpmNetworkStickDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.processmanager.IPpmNetworkStickService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 宁管部网点设备维护
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmNetworkStickApi"},description="宁管部网点设备维护")
@RestController
@RequestMapping("ppmNetworkStick")
@Slf4j
public class PpmNetworkStickApi extends BaseUploadApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmNetworkStickService ppmNetworkStickService;

    /**
     * 分页
     * @param ppmNetworkStickDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "发起人部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contact", value = "发起人联系方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createDate", value = "发起时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "processType", value = "流程类型", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "processTitle", value = "流程标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changeDevice", value = "变更设备名称", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "changeType", value = "变更类型（1：采购:，2：报废）", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "describe", value = "描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dataIfNormal", value = "各个数据是否正常（1：是，2：否）", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "monitoringConfirm", value = "监控变更确认AB岗", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "forConfirm", value = "出入库确认AB岗", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "cmdbConfirm", value = "CMDB确认AB岗", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changeDeviceData", value = "变更设备名称数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "instanceId", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "发起人ID", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmNetworkStickDto ppmNetworkStickDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmNetworkStickService.queryPager(ppmNetworkStickDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmNetworkStickDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "deptId", value = "发起人部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contact", value = "发起人联系方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createDate", value = "发起时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "processType", value = "流程类型", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "processTitle", value = "流程标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changeDevice", value = "变更设备名称", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "changeType", value = "变更类型（1：采购:，2：报废）", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "describe", value = "描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dataIfNormal", value = "各个数据是否正常（1：是，2：否）", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "monitoringConfirm", value = "监控变更确认AB岗", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "forConfirm", value = "出入库确认AB岗", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "cmdbConfirm", value = "CMDB确认AB岗", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changeDeviceData", value = "变更设备名称数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "instanceId", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "发起人ID", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmNetworkStickDto> queryDetail(PpmNetworkStickDto ppmNetworkStickDto) {
        ApiResult<PpmNetworkStickDto> result =new ApiResult<>();
        PpmNetworkStickDto item = ppmNetworkStickService.queryDetail(ppmNetworkStickDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 草稿
     * @param ppmNetworkStickDto 对象
     * @return ApiResult
     *
     */
    @PostMapping(value = "draft")
    @ApiOperation(value = "保存草稿", notes = "保存草稿")
    public ApiResult draft(@RequestBody PpmNetworkStickDto ppmNetworkStickDto, HttpServletRequest request) {
        ApiResult result = new ApiResult<>();
        CommOperatorDoLoginDto usr = super.getCurrentUser(request);
        ppmNetworkStickDto.setBadge(usr.getUserid());
        String item = ppmNetworkStickService.draft(ppmNetworkStickDto);
        result.success(item);
        return result;
    }

    /**
     * 发起申请
     * @param ppmNetworkStickDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "initiate")
    @ApiOperation(value = "发起申请", notes = "发起申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "发起人部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contact", value = "发起人联系方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createDate", value = "发起时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "processType", value = "流程类型", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "processTitle", value = "流程标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changeDevice", value = "变更设备名称", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "changeType", value = "变更类型（1：采购:，2：报废）", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "describe", value = "描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dataIfNormal", value = "各个数据是否正常（1：是，2：否）", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "monitoringConfirm", value = "监控变更确认AB岗", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "forConfirm", value = "出入库确认AB岗", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "cmdbConfirm", value = "CMDB确认AB岗", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "changeDeviceData", value = "变更设备名称数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "instanceId", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "发起人ID", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> initiatePpmNetworkStick(@RequestBody PpmNetworkStickDto ppmNetworkStickDto,
                                                   HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto usr = super.getCurrentUser(request);
        ppmNetworkStickDto.setBadge(usr.getUserid());
        String message = ppmNetworkStickService.initiatePpmNetworkStick(ppmNetworkStickDto);
        result.success(message);
        return result;
    }

    /**
     * 宁管部二级部经理审批
     * @param ppmNetworkStickDto  对象
     * @return ApiResult
     */
    @PostMapping("ministryManager")
    @ApiOperation(value = "宁管部二级部经理审批")
    public ApiResult ministryManagerExamination(@RequestBody PpmNetworkStickDto ppmNetworkStickDto,
                                                HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmNetworkStickDto.getBps().setUserId(user.getUserid());
        String message = ppmNetworkStickService.ministryManagerExamination(ppmNetworkStickDto);
        result.success(message);
        return result;
    }

    /**
     * 处理人审批
     * @param ppmNetworkStickDto 对象
     * @return ApiResult
     */
    @PostMapping("dealing")
    @ApiOperation(value = "处理人审批")
    public ApiResult dealingExamination(@RequestBody PpmNetworkStickDto ppmNetworkStickDto,
                                        HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmNetworkStickDto.getBps().setUserId(user.getUserid());
        String message = ppmNetworkStickService.dealingExamination(ppmNetworkStickDto);
        result.success(message);
        return result;
    }

    /**
     * 监控变更确认
     * @param ppmNetworkStickDto 对象
     * @return ApiResult
     */
    @PostMapping("monitorChange")
    @ApiOperation(value = "监控变更确认")
    public ApiResult monitorChangeConfirm(@RequestBody PpmNetworkStickDto ppmNetworkStickDto,
                                          HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmNetworkStickDto.getBps().setUserId(user.getUserid());
        String message = ppmNetworkStickService.monitorChangeConfirm(ppmNetworkStickDto);
        result.success(message);
        return result;
    }

    /**
     * 出入库确认
     * @param ppmNetworkStickDto 对象
     * @return ApiResult
     */
    @PostMapping("skinprinterLib")
    @ApiOperation(value = "出入库确认")
    public ApiResult skinprinterLibConfirm(@RequestBody PpmNetworkStickDto ppmNetworkStickDto,
                                           HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmNetworkStickDto.getBps().setUserId(user.getUserid());
        String message = ppmNetworkStickService.skinprinterLibConfirm(ppmNetworkStickDto);
        result.success(message);
        return result;
    }

    /**
     * CMDB录入确认
     * @param ppmNetworkStickDto 对象
     * @return ApiResult
     */
    @PostMapping("cmdbEntry")
    @ApiOperation(value = "CMDB录入确认")
    public ApiResult cmdbEntryConfirm(@RequestBody PpmNetworkStickDto ppmNetworkStickDto,
                                      HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmNetworkStickDto.getBps().setUserId(user.getUserid());
        String message = ppmNetworkStickService.cmdbEntryConfirm(ppmNetworkStickDto);
        result.success(message);
        return result;
    }

    /**
     * 获取当前登录人信息
     * @param request request
     * @return ApiResult
     */
    @GetMapping("getCurrentInfo")
    @ApiOperation(value = "获取当前登录人信息")
    public ApiResult<PpmNetworkStickDto> getCurrentInfo(HttpServletRequest request) {
        ApiResult<PpmNetworkStickDto> result = new ApiResult();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        PpmNetworkStickDto item = ppmNetworkStickService.getCurrentInfo(user.getUserid());
        result.setResults(item);
        result.success("查询成功");
        return result;
    }

    /**
     * 导出excel
     * @param ppmNetworkStickDto 对象
     * @param response response
     */
    @PostMapping("exportExcel")
    @ApiOperation(value = "导出excel")
    public void exportExcel(PpmNetworkStickDto ppmNetworkStickDto, HttpServletResponse response) {
        List<PpmNetworkStickDto> list = ppmNetworkStickService.exportExcel(ppmNetworkStickDto);
        ExcelUtil.exportExcel(list, PpmNetworkStickDto.class, "excel.xls", response);
    }
}
