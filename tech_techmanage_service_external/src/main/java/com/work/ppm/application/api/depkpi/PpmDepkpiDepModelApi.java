package com.work.ppm.application.api.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiDepModelDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepModelService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 部门模板分配处理操作
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmDepkpiDepModelApi"})
@RestController
@RequestMapping("ppmDepkpiDepModel")
@Slf4j
public class PpmDepkpiDepModelApi extends BaseApi {
    /**
     * 部门模板分配表接口
     */
    @Autowired
    private IPpmDepkpiDepModelService ppmDepkpiDepModelService;

    /**
     * 分页
     *
     * @param ppmDepkpiDepModelDto 对象
     * @param pager                分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryDepModelPagerList")
    @ApiOperation(value = "部门模板分配分页查询", notes = "部门模板分配分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "modelid", value = "模板ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心id", dataType = "long", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmDepkpiDepModelDto ppmDepkpiDepModelDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        ppmDepkpiDepModelService.queryList(ppmDepkpiDepModelDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 分页
     *
     * @param ppmDepkpiDepModelDto 对象
     * @param request              对象
     * @return ApiResult
     * @request 对象
     */
    @PostMapping(value = "changeDepModel")
    @ApiOperation(value = "部门更换模板", notes = "部门更换模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "modelid", value = "模板ID", dataType = "long", paramType = "query"),

    })
    public ApiResult changeDepModel(PpmDepkpiDepModelDto ppmDepkpiDepModelDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
       ApiResult<List> result = new ApiResult<>();
        int id = ppmDepkpiDepModelService.updateDepModel(ppmDepkpiDepModelDto, "182402");
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更换失败");
        }
        return result;
    }
}
