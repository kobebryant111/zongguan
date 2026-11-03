package com.work.ppm.application.api.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiDepmodelDetailTmpDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepmodelDetailTmpService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
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
 * 部门模板操作接口
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmDepkpiDepmodelDetailTmpApi"})
@RestController
@RequestMapping("ppmDepkpiDepmodelDetailTmp")
@Slf4j
public class PpmDepkpiDepmodelDetailTmpApi extends BaseApi {
    /**
     * 部门模板临时表接口
     */
    @Autowired
    private IPpmDepkpiDepmodelDetailTmpService ppmDepkpiDepmodelDetailTmpService;

    /**
     * 部门已分配指标页查询
     *
     * @param ppmDepkpiDepmodelDetailTmpDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryDepTargetList")
    @ApiOperation(value = "部门已分配指标查询", notes = "部门已分配指标查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
    })
    public ApiResult<List<PpmDepkpiDepmodelDetailTmpDto>> queryDepTargetList(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto) {
        ApiResult<List<PpmDepkpiDepmodelDetailTmpDto>> result = new ApiResult<>();
        List<PpmDepkpiDepmodelDetailTmpDto> dtos = ppmDepkpiDepmodelDetailTmpService.getDepkpiDepmodelDetailTmpList
                (ppmDepkpiDepmodelDetailTmpDto);
        result.setResults(dtos);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 部门分配指标
     *
     * @param ppmDepkpiDepmodelDetailTmpDto 对象
     * @param request                       对象
     * @return ApiResult
     */
    @PostMapping(value = "depAssignTarget")
    @ApiOperation(value = "部门分配指标", notes = "部门分配指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentid", value = "父级指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "basepoint", value = "基准分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "hasson", value = "是否有子节点：0:无子节点；1：有子节点；", dataType = "long",
                    paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "指标名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型：1：公共指标；2：自定义指标；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "evaltype", value = "评价方式：1：档位；2：任意分值；3：其他分值；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> depAssignTarget(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto,
                                             HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiDepmodelDetailTmpService.addDepkpiDepmodelDetailTmpDto(ppmDepkpiDepmodelDetailTmpDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败");
        }
        return result;
    }

    /**
     * 部门修改已分配指标
     *
     * @param ppmDepkpiDepmodelDetailTmpDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "updateDepTarget")
    @ApiOperation(value = "部门修改已分配指标", notes = "部门修改已分配指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentid", value = "父级指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "basepoint", value = "基准分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "指标名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型：1：公共指标；2：自定义指标；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "evaltype", value = "评价方式：1：档位；2：任意分值；3：其他分值；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> updateDetail(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto) {
        ApiResult<Object> result = new ApiResult<>();
        int id = ppmDepkpiDepmodelDetailTmpService.updateDetail(ppmDepkpiDepmodelDetailTmpDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else if (id == -1) {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("只能修改最下级！");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除部门考核详情及其子集
     *
     * @param ppmDepkpiDepmodelDetailTmpDto 对象
     * @param request                       对象
     * @return ApiResult
     */
    @PostMapping(value = "deleteDepTarget")
    @ApiOperation(value = "部门删除已分配指标", notes = "部门删除已分配指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
    })
    public ApiResult<Object> deleteDepTarget(PpmDepkpiDepmodelDetailTmpDto ppmDepkpiDepmodelDetailTmpDto,
                                             HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiDepmodelDetailTmpService.deleteDepTarget(ppmDepkpiDepmodelDetailTmpDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
