package com.work.ppm.application.api.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepmodelDetailTmpService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiModelService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 模板操作
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmDepkpiModelApi"})
@RestController
@RequestMapping("ppmDepkpiModel")
@Slf4j
public class PpmDepkpiModelApi extends BaseApi {

    /**
     * 操作模板增删改查
     */
    @Autowired
    private IPpmDepkpiModelService ppmDepkpiModelService;

    /**
     * 操作模板详情增删改查
     */
    @Autowired
    private IPpmDepkpiDepmodelDetailTmpService ppmDepkpiDepmodelDetailTmpService;

    /**
     * 更新模板
     *
     * @param ppmDepkpiModelDto 对象
     * @param request           request
     * @return ApiResult
     */
    @PostMapping(value = "updateModel")
    @ApiOperation(value = "模板修改", notes = "模板修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "模板名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "commitflag", value = "提交标志：0：未提交；1：已提交；", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> updateModel(PpmDepkpiModelDto ppmDepkpiModelDto, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiModelService.updateModel(ppmDepkpiModelDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除(删除模板)
     *
     * @param ppmDepkpiModelDto 对象
     * @param request           request
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteModel")
    @ApiOperation(value = "模板删除", notes = "模板删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "delete"),

    })
    public ApiResult deleteModel(PpmDepkpiModelDto ppmDepkpiModelDto, HttpServletRequest request) {
        ApiResult result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int sid = ppmDepkpiModelService.deleteModel(ppmDepkpiModelDto, userid);
        if (sid > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 插入数据（添加模板）
     *
     * @param ppmDepkpiModelDto 对象
     * @param request           request
     * @return ApiResult
     */
    @PostMapping(value = "addModel")
    @ApiOperation(value = "模板新增", notes = "模板新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "模板名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "commitflag", value = "提交标志：0：未提交；1：已提交；", dataType = "long", paramType = "query"),

    })
    public ApiResult addModel(PpmDepkpiModelDto ppmDepkpiModelDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        ApiResult result = new ApiResult<>();
        int id = ppmDepkpiModelService.addModel(ppmDepkpiModelDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("新增失败");
        }
        return result;
    }

    /**
     * 分页、模板查询
     *
     * @param ppmDepkpiModelDto 对象
     * @param pager             分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryModelPagerList")
    @ApiOperation(value = "模板列表分页查询", notes = "模板列表分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "模板名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "commitflag", value = "提交标志：0：未提交；1：已提交；", dataType = "long", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),

    })
    public ApiResult queryModelPagerList(PpmDepkpiModelDto ppmDepkpiModelDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        ppmDepkpiModelDto.setIsdel(0L);
        ppmDepkpiModelDto.setHasson(0L);
        ppmDepkpiModelService.queryPager(ppmDepkpiModelDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 模板列表查询
     *
     * @param ppmDepkpiModelDto 对象
     * @return ApiResult 对象
     */
    @PostMapping(value = "queryModelList")
    @ApiOperation(value = "模板列表查询", notes = "模板列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sign", value = "所属组织级别", dataType = "Long", paramType = "query"),
    })
    public ApiResult queryModelList(PpmDepkpiModelDto ppmDepkpiModelDto) {
        ApiResult<List> result = new ApiResult<>();
        List<PpmDepkpiModelDto> items = ppmDepkpiModelService.queryModelList(ppmDepkpiModelDto);
        result.setResults(items);
        result.setTotal(items == null ? 0 : items.size());
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
