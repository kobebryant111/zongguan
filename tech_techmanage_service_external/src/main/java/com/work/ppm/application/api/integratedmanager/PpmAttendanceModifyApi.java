package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmAttendanceModifyDto;
import com.work.ppm.application.services.integratedmanager.IPpmAttendanceModifyService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 考勤修改流程
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmAttendanceModifyApi"},description="考勤修改流程")
@RestController
@RequestMapping("ppmAttendanceModify")
@Slf4j
public class PpmAttendanceModifyApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmAttendanceModifyService ppmAttendanceModifyService;

    /**
     * 分页
     * @param ppmAttendanceModifyDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "自增ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "instanceId", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depId", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "发起人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "reasonDes", value = "修改原因描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contentDes", value = "修改内容描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addDate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除(0:未删除，1：删除)", dataType = "long", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmAttendanceModifyDto ppmAttendanceModifyDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmAttendanceModifyService.queryPager(ppmAttendanceModifyDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmAttendanceModifyDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "自增ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "instanceId", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depId", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "发起人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "reasonDes", value = "修改原因描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "contentDes", value = "修改内容描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addDate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除(0:未删除，1：删除)", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "发起人名称", dataType = "string", paramType = "query"),
    })
    public ApiResult<PpmAttendanceModifyDto> queryDetail(PpmAttendanceModifyDto ppmAttendanceModifyDto) {
        ApiResult<PpmAttendanceModifyDto> result =new ApiResult<>();
        PpmAttendanceModifyDto item = ppmAttendanceModifyService.queryDetail(ppmAttendanceModifyDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 保存
     * @param ppmAttendanceModifyDto 对象
     * @return  ApiResult
     */
    @PostMapping("save")
    @ApiOperation(value = "保存", notes = "请参照分页方法元素填充")
    public ApiResult<String> saveFlow(@RequestBody PpmAttendanceModifyDto ppmAttendanceModifyDto) {
        ApiResult<String> result;
        result  = ppmAttendanceModifyService.saveFlow(ppmAttendanceModifyDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 流转-发起申请
     * @param ppmAttendanceModifyDto 对象
     * @return ApiResult
     */
    @PostMapping("liuzhuan")
    @ApiOperation(value = "流转-发起申请", notes = "请参照分页方法元素填充")
    public ApiResult liuZhuan(@RequestBody PpmAttendanceModifyDto ppmAttendanceModifyDto) {
        ApiResult<String> result =new ApiResult<>();
        String mge = ppmAttendanceModifyService.liuZhuan(ppmAttendanceModifyDto);
        result.setMessage(mge);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 二级部主持经理审核
     * @param ppmAttendanceModifyDto 对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping("hostManagerReview")
    @ApiOperation(value = "二级部主持经理审核", notes = "请参照分页方法元素填充")
    public ApiResult hostManagerReview(@RequestBody PpmAttendanceModifyDto ppmAttendanceModifyDto,
                                       HttpServletRequest request) {
        ApiResult<String> result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmAttendanceModifyDto.getBps().setUserId(user.getUserid());
        String mge = ppmAttendanceModifyService.hostManagerReview(ppmAttendanceModifyDto);
        result.setMessage(mge);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 分管总经理审批
     * @param ppmAttendanceModifyDto 对象
     * @return ApiResult
     */
    @PostMapping("generalMnager")
    @ApiOperation(value = "分管总经理审批", notes = "请参照分页方法元素填充")
    public ApiResult generalMnager(@RequestBody PpmAttendanceModifyDto ppmAttendanceModifyDto, HttpServletRequest request) {
        ApiResult<String> result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmAttendanceModifyDto.getBps().setUserId(user.getUserid());
        String mge = ppmAttendanceModifyService.generalMnager(ppmAttendanceModifyDto);
        result.setMessage(mge);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 研发管理部确认
     * @param ppmAttendanceModifyDto 对象
     * @return ApiResult
     */
    @PostMapping("generalDept")
    @ApiOperation(value = "研发管理部确认", notes = "请参照分页方法元素填充")
    public ApiResult generalDept(@RequestBody PpmAttendanceModifyDto ppmAttendanceModifyDto, HttpServletRequest request) {
        ApiResult<String> result =new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmAttendanceModifyDto.getBps().setUserId(user.getUserid());
        String mge = ppmAttendanceModifyService.generalDept(ppmAttendanceModifyDto);
        result.setMessage(mge);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 导出
     * @param ppmAttendanceModifyDto 对象
     * @param response 对象
     */
    @PostMapping("exportExcel")
    @ApiOperation(value = "导出", notes = "导出")
    public void exportExcel(PpmAttendanceModifyDto ppmAttendanceModifyDto,
                                HttpServletResponse response) {
        List<PpmAttendanceModifyDto> mge = ppmAttendanceModifyService.exportExcel(ppmAttendanceModifyDto);
        ExcelUtil.exportExcel(mge, PpmAttendanceModifyDto.class,"aaa.xls", response);
    }
}
