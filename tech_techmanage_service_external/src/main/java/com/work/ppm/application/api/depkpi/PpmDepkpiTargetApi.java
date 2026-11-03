package com.work.ppm.application.api.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiTargetDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.depkpi.IPpmDepkpiTargetService;
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
 * 指标表操作
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmDepkpiTargetApi"})
@RestController
@RequestMapping("ppmDepkpiTarget")
@Slf4j
public class PpmDepkpiTargetApi extends BaseApi {

    /**
     * 指标配置增删改查
     */
    @Autowired
    private IPpmDepkpiTargetService ppmDepkpiTargetService;

    /**
     * 指标列表分页查询
     *
     * @param ppmDepkpiTargetDto 对象
     * @param pager              分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPagerList")
    @ApiOperation(value = "指标列表分页查询", notes = "指标列表分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "指标名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPagerList(PpmDepkpiTargetDto ppmDepkpiTargetDto, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        ppmDepkpiTargetService.queryPagerList(ppmDepkpiTargetDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 删除指标数据
     *
     * @param ppmDepkpiTargetDto 指标id
     * @return ApiResult
     */
    @DeleteMapping("delete")
    @ApiOperation(value = "删除", notes = "根据自增id删除数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
    })
    public ApiResult delete(PpmDepkpiTargetDto ppmDepkpiTargetDto, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int ids = ppmDepkpiTargetService.deletePpmDepkpiTarget(ppmDepkpiTargetDto, userid);
        if (ids > 0) {
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 修改指标
     *
     * @param ppmDepkpiTargetDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePpmDepkpiTarget")
    @ApiOperation(value = "修改指标", notes = "修改指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "指标名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型：1：公共指标；2：自定义指标；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "制定部门ID，只有公共指标才有制定部门", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "evaltype", value = "评价方式：1：档位；2：任意分值；3：其他分值；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> updatePpmDepkpiTarget(PpmDepkpiTargetDto ppmDepkpiTargetDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        ApiResult<Object> result = new ApiResult<>();
        int id = ppmDepkpiTargetService.updatePpmDepkpiTarget(ppmDepkpiTargetDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 新增指标
     *
     * @param ppmDepkpiTargetDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "addPpmDepkpiTarget")
    @ApiOperation(value = "新增指标", notes = "新增指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "指标名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型：1：公共指标；2：自定义指标；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "制定部门ID，只有公共指标才有制定部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "evaltype", value = "评价方式：1：档位；2：任意分值；3：其他分值；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> addPpmDepkpiTarget(PpmDepkpiTargetDto ppmDepkpiTargetDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        ApiResult<Object> result = new ApiResult<>();
        int id = ppmDepkpiTargetService.addPpmDepkpiTarget(ppmDepkpiTargetDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败");
        }
        return result;
    }

    /**
     * 指标列表查询
     *
     * @param ppmDepkpiTargetDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryList")
    @ApiOperation(value = "指标列表查询", notes = "指标列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "levelnum", value = "框架层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别", dataType = "long", paramType = "query"),
    })
    public ApiResult<List<PpmDepkpiTargetDto>> queryList(PpmDepkpiTargetDto ppmDepkpiTargetDto) {
        ApiResult<List<PpmDepkpiTargetDto>> result = new ApiResult<>();
        List<PpmDepkpiTargetDto> list = ppmDepkpiTargetService.getPpmDepkpiTargetList(ppmDepkpiTargetDto);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
