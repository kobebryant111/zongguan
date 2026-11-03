package com.work.ppm.application.api.othermanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.othermanager.PpmQuestionOption;
import com.work.ppm.application.services.othermanager.IPpmQuestionOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 题库类型从表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmQuestionOptionApi"}, description = "题库类型从表")
@RestController
@RequestMapping("ppmQuestionOption")
@Slf4j
public class PpmQuestionOptionApi {
    /**
     * 题库类型从表 接口
     */
    @Autowired
    private IPpmQuestionOptionService ppmQuestionOptionService;

    /**
     * 分页
     * @param ppmQuestionOption 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="queryPager", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "questionno", value = "问题序号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "optioncode", value = "选项序号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "optioncontent", value = "选项内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除，0未删除，1已删除", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(PpmQuestionOption ppmQuestionOption,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmQuestionOptionService.queryPager(ppmQuestionOption,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmQuestionOption 对象
     * @return ApiResult<PpmQuestionOption>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "questionno", value = "问题序号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "optioncode", value = "选项序号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "optioncontent", value = "选项内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除，0未删除，1已删除", dataType = "string", paramType = "query"),

    })
    public ApiResult<PpmQuestionOption> queryDetail(PpmQuestionOption ppmQuestionOption) {
        ApiResult<PpmQuestionOption> result =new ApiResult<>();
        PpmQuestionOption item = ppmQuestionOptionService.queryDetail(ppmQuestionOption);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmQuestionOption 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "updatePojo", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "questionno", value = "问题序号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "optioncode", value = "选项序号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "optioncontent", value = "选项内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除，0未删除，1已删除", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> updatePpmQuestionOption(PpmQuestionOption ppmQuestionOption) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmQuestionOptionService.updatePpmQuestionOption(ppmQuestionOption);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmQuestionOption 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "delete", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmQuestionOption (PpmQuestionOption ppmQuestionOption) {
        ApiResult result =new ApiResult<>();
        int id = ppmQuestionOptionService.deletePpmQuestionOption(ppmQuestionOption);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("操作成功");
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
