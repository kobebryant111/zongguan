package com.work.ppm.application.api.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiTitleDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiTitleInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.depkpi.IPpmDepkpiTitleService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
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
 * 考核标题增刪改查
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmDepkpiTitleApi"})
@RestController
@RequestMapping("ppmDepkpiTitle")
@Slf4j
public class PpmDepkpiTitleApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmDepkpiTitleService ppmDepkpiTitleService;

    /**
     * 考核标题列表查询
     *
     * @param ppmDepkpiTitleDto 对象
     * @return ApiResult 对象
     */
    @PostMapping(value = "queryTitleList")
    @ApiOperation(value = "考核标题列表查询", notes = "考核标题列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "所属部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "detailid", value = "明细id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "hasson", value = "是否有子节点", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型", dataType = "long", paramType = "query"),
    })
    public ApiResult<List<PpmDepkpiTitleDto>> queryTitleList(PpmDepkpiTitleDto ppmDepkpiTitleDto) {
        ApiResult<List<PpmDepkpiTitleDto>> result = new ApiResult<>();
        List<PpmDepkpiTitleDto> item = ppmDepkpiTitleService.queryTitleList(ppmDepkpiTitleDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 考核标题树查询
     * @param ppmDepkpiTitleDto 对象
     * @return ApiResult 对象
     */
    @PostMapping(value = "queryTitleTreeData")
    @ApiOperation(value = "考核标题查询", notes = "考核标题查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "所属部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "所属考核项ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "detailid", value = "明细ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型", dataType = "long", paramType = "query"),

    })
    public ApiResult<List<PpmDepkpiTitleInfoDto>> queryTitleTreeData(PpmDepkpiTitleDto ppmDepkpiTitleDto) {
        ApiResult<List<PpmDepkpiTitleInfoDto>> result = new ApiResult<>();
        List<PpmDepkpiTitleInfoDto> item = ppmDepkpiTitleService.queryTitleTreeData(ppmDepkpiTitleDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmDepkpiTitleDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "updateTitle")
    @ApiOperation(value = "修改标题", notes = "修改标题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "所属部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "所属考核项ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentid", value = "父级标题ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "hasson", value = "是否有子节点：0:无子节点；1：有子节点；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "evaltype", value = "评价方式：1：档位；2：任意分值；3：其他分值；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "basepoint", value = "基准分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> updateTitle(PpmDepkpiTitleDto ppmDepkpiTitleDto, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
       CommOperatorDoLoginDto user = super.getCurrentUser(request);
       String userid = user.getUserid();
        int id = ppmDepkpiTitleService.updatePpmDepkpiTitle(ppmDepkpiTitleDto, userid);
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
     * 删除
     * @param ppmDepkpiTitleDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deleteTitle")
    @ApiOperation(value = "刪除标题", notes = "刪除标题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deleteTitle(PpmDepkpiTitleDto ppmDepkpiTitleDto, HttpServletRequest request) {
        ApiResult result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiTitleService.updateIsdelById(ppmDepkpiTitleDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 新增标题
     * @param ppmDepkpiTitleDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "addTitle")
    @ApiOperation(value = "新增标题", notes = "新增标题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "所属部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "所属考核项ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "标题", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentid", value = "父级标题ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "hasson", value = "是否有子节点：0:无子节点；1：有子节点；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "evaltype", value = "评价方式：1：档位；2：任意分值；3：其他分值；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "basepoint", value = "基准分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "detailid", value = "部门指标分配明细id", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> addTitle(PpmDepkpiTitleDto ppmDepkpiTitleDto, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiTitleService.addPpmDepkpiTitle(ppmDepkpiTitleDto, userid);
        if (id == -1) {
            result.setMessage("已有考核内容，新增失败");
            result.setStatus(ApiCode.DELETE_FAIL);
        } else if (id == 1) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("新增失败");
        }
        return result;
    }
}
