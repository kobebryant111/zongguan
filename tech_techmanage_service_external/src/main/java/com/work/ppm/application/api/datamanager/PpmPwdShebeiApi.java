package com.work.ppm.application.api.datamanager;

import com.work.ppm.application.dto.datamanager.PpmPwdShebeiDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.datamanager.IPpmPwdShebeiService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 密管设备
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmPwdShebeiApi"},description="密管设备")
@RestController
@RequestMapping("ppmPwdShebei")
@Slf4j
public class PpmPwdShebeiApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmPwdShebeiService ppmPwdShebeiService;

    /**
     * 分页
     * @param ppmPwdShebeiDto 对象
     * @param pager 分页对象
     * @param request  对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "systemname", value = "系统", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "usertype", value = "用户类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ip", value = "设备IP", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pwd", value = "密码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "loginuser", value = "密管登陆用户", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endtime", value = "到期时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applyer", value = "申请人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "servename", value = "应用名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealcode", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "starttime", value = "开始使用时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "checkindate", value = "归还日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "hascheckin", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remindstatus", value = "通知状态", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmPwdShebeiDto ppmPwdShebeiDto,
                                Pager pager, HttpServletRequest request) {
        ApiResult<List> result =new ApiResult<>();
        CommOperatorDoLoginDto user = getCurrentUser(request);
        ppmPwdShebeiDto.setApplyer(user.getUserid());
        ppmPwdShebeiService.queryPager(ppmPwdShebeiDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 删除设备
     * @param id 自增编号
     * @return ApiResult
     */
    @DeleteMapping("deleteShebei")
    @ApiOperation(value = "删除设备", notes = "根据设备id查询信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "long", paramType = "query", required = true),
    })
    public ApiResult deleteShebei(Long id) {
        ApiResult result = new ApiResult();
        int row = ppmPwdShebeiService.deleteShebei(id);
        if(row > 0) {
            result.success("删除成功");
        } else {
            result.failure("删除失败");
        }
        return result;
    }

    /**
     * 获取设备列表
     * @param ppmPwdShebeiDto 对象
     * @param request http请求参数
     * @return ApiResult
     */
    @GetMapping("getPwdList")
    @ApiOperation(value = "获取设备列表", notes = "获取设备列表")
    public ApiResult getPwdList( PpmPwdShebeiDto ppmPwdShebeiDto, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        List<PpmPwdShebeiDto> list = ppmPwdShebeiService.getPwdList(ppmPwdShebeiDto);
        result.setResults(list);
        result.success("查询成功");
        return result;
    }

    /**
     * 导出
     * @param ppmPwdShebeiDto 密管设备对象
     * @param response 对象
     * @return void
     */
    @PostMapping("exportExcel")
    @ApiOperation(value = "导出")
    public void exportExcel(PpmPwdShebeiDto ppmPwdShebeiDto, HttpServletResponse response) {
        List<PpmPwdShebeiDto> list = ppmPwdShebeiService.getPwdList(ppmPwdShebeiDto);
        ExcelUtil.exportExcel(list, PpmPwdShebeiDto.class,"xxx.xls", response);
    }
}
