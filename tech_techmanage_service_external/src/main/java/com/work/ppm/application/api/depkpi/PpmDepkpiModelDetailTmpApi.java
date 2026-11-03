package com.work.ppm.application.api.depkpi;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiModelDetailTmpInfo;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDetailTmpDto;
import com.work.ppm.application.services.depkpi.IPpmDepkpiModelDetailTmpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 模板详情临时
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmDepkpiModelDetailTmpApi"})
@RestController
@RequestMapping("ppmDepkpiModelDetailTmp")
@Slf4j
public class PpmDepkpiModelDetailTmpApi extends BaseApi {
    /**
     * 模板详情临时
     */
    @Autowired
    private IPpmDepkpiModelDetailTmpService ppmDepkpiModelDetailTmpService;

    /**
     * 模板修改已分配指标
     *
     * @param ppmDepkpiModelDetailTmpDto 对象
     * @param request request
     * @return ApiResult
     */
    @PostMapping(value = "updateModelTarget")
    @ApiOperation(value = "模板修改已分配指标", notes = "模板修改已分配指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "modelid", value = "模板ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentid", value = "父级指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "basepoint", value = "基准分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "hasson", value = "是否有子节点：0:无子节点；1：有子节点；", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> updateModelTarget(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto,
                                               HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiModelDetailTmpService.updateModelTarget(ppmDepkpiModelDetailTmpDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else if(id==-1) {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("只能修改最下级！");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }


    /**
     * 模板已分配指标查询
     *
     * @param ppmDepkpiModelDetailTmpDto 对象
     * @return ApiResult 对象
     */
    @PostMapping(value = "queryModelTargetList")
    @ApiOperation(value = "模板已分配指标查询", notes = "模板已分配指标查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelid", value = "模板id", dataType = "Long", paramType = "query"),
    })
    public ApiResult queryModelTargetList(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto) {
        ApiResult<List> result = new ApiResult<>();
        List<PpmDepkpiModelDetailTmpInfo> items =
                ppmDepkpiModelDetailTmpService.queryDepModelTargetList(ppmDepkpiModelDetailTmpDto);
        result.setResults(items);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 模板分配指标
     * @param ppmDepkpiModelDetailTmpDto 对象
     * @param request                    request
     * @return ApiResult
     */
    @PostMapping(value = "modelAssignTarget")
    @ApiOperation(value = "模板分配指标", notes = "模板分配指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "modelid", value = "模板ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentid", value = "父级指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "basepoint", value = "基准分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "hasson", value = "是否有子节点：0:无子节点；1：有子节点；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "指标名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型：1：公共指标；2：自定义指标；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "制定部门ID，只有公共指标才有制定部门", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "evaltype", value = "评价方式：1：档位；2：任意分值；3：其他分值；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),
    })
    public ApiResult<Object> modelAssignTarget(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto,
                                               HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiModelDetailTmpService.modelAssignTarget(ppmDepkpiModelDetailTmpDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败");
        }
        return result;
    }

    /**
     * 模板删除已分配指标
     * @param ppmDepkpiModelDetailTmpDto 对象
     * @param request                    request
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteModelTarget")
    @ApiOperation(value = "模板删除已分配指标", notes = "模板删除已分配指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deleteModelTarget(PpmDepkpiModelDetailTmpDto ppmDepkpiModelDetailTmpDto,
                                       HttpServletRequest request) {
        ApiResult result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiModelDetailTmpService.deleteModelTarget(ppmDepkpiModelDetailTmpDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
