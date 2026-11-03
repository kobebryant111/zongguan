package com.work.ppm.application.api.integratedmanager;

import com.google.common.collect.Lists;
import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityDto;
import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityExcelDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.integratedmanager.IPpmZjAuthorityService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 闸机权限管理 API接口
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("ppmZjAuthority")
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmZjAuthorityApi"})
public class PpmZjAuthorityApi extends BaseApi {

    //闸机权限管理
    private final IPpmZjAuthorityService ppmZjAuthorityService;

    /**
     * 分页
     *
     * @param ppmZjAuthorityDto 对象
     * @param pager             分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmZjAuthorityDto ppmZjAuthorityDto, Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        ppmZjAuthorityService.queryPager(ppmZjAuthorityDto, pager, user);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     *
     * @param ppmZjAuthorityDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    public ApiResult<PpmZjAuthorityDto> queryDetail(PpmZjAuthorityDto ppmZjAuthorityDto) {
        ApiResult<PpmZjAuthorityDto> result = new ApiResult<>();
        PpmZjAuthorityDto item = ppmZjAuthorityService.queryDetail(ppmZjAuthorityDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 添加
     *
     * @param ppmZjAuthorityDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "add")
    @ApiOperation(value = "添加", notes = "添加")
    public ApiResult<Object> addPpmZjAuthority(PpmZjAuthorityDto ppmZjAuthorityDto) {
        ApiResult<Object> result = new ApiResult<>();
        int id = ppmZjAuthorityService.addPpmZjAuthority(ppmZjAuthorityDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败");
        }
        return result;
    }

    /**
     * 更新
     *
     * @param ppmZjAuthorityDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updatePpmZjAuthority(PpmZjAuthorityDto ppmZjAuthorityDto) {
        ApiResult<Object> result = new ApiResult<>();
        int id = ppmZjAuthorityService.updatePpmZjAuthority(ppmZjAuthorityDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 更新权限状态
     *
     * @param ppmZjAuthorityDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "changeFlag")
    @ApiOperation(value = "更新权限状态", notes = "更新权限状态")
    public ApiResult<Object> changeFlag(PpmZjAuthorityDto ppmZjAuthorityDto) {
        PpmZjAuthorityDto entry = ppmZjAuthorityService.queryDetail(ppmZjAuthorityDto);
        if (ObjectUtils.isEmpty(entry)) {
            throw new BizException(ApiCode.FAIL, "请求参数错误！");
        }
        if (Integer.valueOf(0).equals(entry.getFlag())) {
            throw new BizException(ApiCode.FAIL, "审批中的权限不允许修改状态！");
        }
        if (Integer.valueOf(-1).equals(entry.getFlag())) {
            // 判断权限是否过期
            if (entry.getEndDate().before(new Date())) {
                throw new BizException(ApiCode.FAIL, "当前权限已过期！");
            } else {
                entry.setFlag(1);
                ppmZjAuthorityService.addAuthToZjServer(Lists.newArrayList(entry));
            }
        } else {
            entry.setFlag(-1);
            ppmZjAuthorityService.delAuthToZjServer(Lists.newArrayList(entry));
        }
        ppmZjAuthorityService.updatePpmZjAuthority(entry);
        ApiResult<Object> result = new ApiResult<>();
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 删除
     *
     * @param ppmZjAuthorityDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    public ApiResult deletePpmZjAuthority(PpmZjAuthorityDto ppmZjAuthorityDto) {
        ApiResult result = new ApiResult<>();
        int id = ppmZjAuthorityService.deletePpmZjAuthority(ppmZjAuthorityDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 闸机权限导出
     *
     * @param zjAuthorityDto 对象
     * @param request        request
     * @param response       对象
     */
    @PostMapping("exportExcel")
    @ApiOperation(value = "闸机权限导出")
    public void exportExcel(PpmZjAuthorityDto zjAuthorityDto,
                            HttpServletRequest request, HttpServletResponse response) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        List<PpmZjAuthorityExcelDto> list = ppmZjAuthorityService.exportPpmZjAuthority(zjAuthorityDto, user);
        ExcelUtil.exportExcel(list, PpmZjAuthorityExcelDto.class, "闸机权限数据.xls", response);
        result.success("数据导出成功");
    }
}
