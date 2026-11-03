package com.work.ppm.application.api.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiContentDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiContentInfo;
import com.work.ppm.application.services.depkpi.IPpmDepkpiContentService;
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
 * 考核内容增刪改查
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmDepkpiContentApi"})
@RestController
@RequestMapping("ppmDepkpiContent")
@Slf4j
public class PpmDepkpiContentApi extends BaseApi {
    /**
     * 考核内容表接口
     */
    @Autowired
    private IPpmDepkpiContentService ppmDepkpiContentService;

    /**
     * 考核内容查询
     *
     * @param ppmDepkpiContentDto 对象
     * @return ApiResult 对象
     */
    @PostMapping(value = "queryContentList")
    @ApiOperation(value = "考核内容查询", notes = "考核内容查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "所属部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "titleid", value = "标题id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "detailid", value = "指标明细id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentTitleid", value = "标题id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentDetailid", value = "指标明细id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "考核内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否自评", dataType = "long", paramType = "query"),
    })
    public ApiResult queryContentList(PpmDepkpiContentDto ppmDepkpiContentDto) {
        ApiResult<List<PpmDepkpiContentInfo>> result = new ApiResult<>();
        List<PpmDepkpiContentInfo> item = ppmDepkpiContentService.queryContentList(ppmDepkpiContentDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 考核内容查询返回初评人还是终评人
     *
     * @param ppmDepkpiContentDto 对象
     * @return ApiResult 对象
     */
    @PostMapping(value = "getEvalContentList")
    @ApiOperation(value = "考核内容查询返回初评人还是终评人", notes = "考核内容查询返回初评人还是终评人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "所属部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "部门类别", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "titleid", value = "标题id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "detailid", value = "指标明细id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentTitleid", value = "标题id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentDetailid", value = "指标明细id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "考核内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dbuser", value = "代办人", dataType = "string", paramType = "query"),
    })
    public ApiResult<List<PpmDepkpiContentInfo>> getEvalContentList(PpmDepkpiContentDto ppmDepkpiContentDto,HttpServletRequest request) {
        ApiResult<List<PpmDepkpiContentInfo>> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        List<PpmDepkpiContentInfo> item = ppmDepkpiContentService.getEvalContentList(ppmDepkpiContentDto,userid);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 修改考核内容
     *
     * @param ppmDepkpiContentDto 对象
     * @param request             对象
     * @return ApiResult
     */
    @PostMapping(value = "updateContent")
    @ApiOperation(value = "修改考核内容", notes = "修改考核内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "detailid", value = "考核项明细ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "所属部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "所属考核项ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "titleid", value = "所属标题ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "basepoint", value = "基准分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "考核内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "standard", value = "评分标准", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startdate", value = "开始时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "enddate", value = "结束时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "evaltype", value = "评价方式：1：档位；2：任意分值；3：其他分值；",
                    dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要",
                    dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "selfevalres", value = "自评结果", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "selfevalstatus", value = "自评状态：0：未提交；1：已提交；",
                    dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "firstcheckres", value = "初评结果", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "firstcheckpoint", value = "初评得分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "firstcheckremark", value = "初评备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "firstcheckstatus", value = "初评提交状态：0：未提交；1：已提交；",
                    dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "secondcheckres", value = "终评结果", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheckpoint", value = "终评得分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "secondcheckremark", value = "终评备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheckstatus", value = "终评提交状态：0：未提交；1：已提交；",
                    dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> updateContent(PpmDepkpiContentDto ppmDepkpiContentDto, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiContentService.updatePpmDepkpiContent(ppmDepkpiContentDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     *
     * @param ppmDepkpiContentDto 对象
     * @return ApiResult
     * @request 对象
     */
    @DeleteMapping(value = "deleteContent")
    @ApiOperation(value = "删除考核内容", notes = "删除考核内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deleteContent(PpmDepkpiContentDto ppmDepkpiContentDto, HttpServletRequest request) {
        ApiResult result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiContentService.updateIsdelById(ppmDepkpiContentDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 新增考核内容
     *
     * @param ppmDepkpiContentDto 对象
     * @param request             对象
     * @return ApiResult
     * @request 对象
     */
    @PostMapping(value = "addContent")
    @ApiOperation(value = "新增考核内容", notes = "新增考核内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "titleid", value = "所属标题ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "basepoint", value = "基准分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "考核内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "standard", value = "评分标准", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "所属考核项id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型", dataType = "long", paramType = "query"),
    })
    public ApiResult<Object> addContent(PpmDepkpiContentDto ppmDepkpiContentDto, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiContentService.addPpmDepkpiContent(ppmDepkpiContentDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("新增失败");
        }
        return result;
    }

    /**
     * 评价
     *
     * @param ppmDepkpiContentDto 对象
     * @param request             对象
     * @return ApiResult
     * @request 对象
     */
    @PostMapping(value = "eval")
    @ApiOperation(value = "评价", notes = "评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "checkres", value = "评价结果", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "checkpoint", value = "评价得分", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "checkremark", value = "评价备注", dataType = "string", paramType = "query"),
    })
    public ApiResult<Object> eval(PpmDepkpiContentDto ppmDepkpiContentDto, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiContentService.eval(ppmDepkpiContentDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else if (id == 0) {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("获取人员信息失败");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("修改失败");
        }
        return result;
    }

    /**
     * 自评评价
     *
     * @param ppmDepkpiContentDto 对象
     * @param request             对象
     * @return ApiResult
     * @request 对象
     */
    @PostMapping(value = "selfeval")
    @ApiOperation(value = "自评提交", notes = "自评提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "selfevalres", value = "自评结果", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> selfeval(PpmDepkpiContentDto ppmDepkpiContentDto, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        int id = ppmDepkpiContentService.upSelfeval(ppmDepkpiContentDto, userid);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("修改失败");
        }
        return result;
    }
    /**
     * 考核内容代办查询
     *
     * @param ppmDepkpiContentDto 对象
     * @return ApiResult 对象
     */
    @PostMapping(value = "queryGgDbContentList")
    @ApiOperation(value = "考核内容代办查询", notes = "考核内容代办查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depid", value = "部门id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "String", paramType = "query"),
    })
    public ApiResult queryGgDbContentList(PpmDepkpiContentDto ppmDepkpiContentDto, HttpServletRequest request) {
        ApiResult<List<PpmDepkpiContentInfo>> result = new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        List<PpmDepkpiContentInfo> item = ppmDepkpiContentService.queryGgDbContentList(ppmDepkpiContentDto, user.getUserid());
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 考核汇总得分查询（代办--制定，页面）
     *
     * @param ppmDepkpiContentDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryZdySumpoint")
    @ApiOperation(value = "考核汇总得分查询（代办--制定，页面）", notes = "考核汇总得分查询（代办--制定，页面）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depid", value = "DEPID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "sign", dataType = "long", paramType = "query"),

    })
    public ApiResult querySumPointAndYargettype(PpmDepkpiContentDto ppmDepkpiContentDto,HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
       PpmDepkpiContentDto item = ppmDepkpiContentService.querySumPointAndTargettype(ppmDepkpiContentDto,user.getUserid());
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
