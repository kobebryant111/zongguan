package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBlacklistDto;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBlacklistService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 外包黑名单
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmOutsourcingBlacklistApi"},description="外包黑名单")
@RestController
@RequestMapping("ppmOutsourcingBlacklist")
@Slf4j
public class PpmOutsourcingBlacklistApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmOutsourcingBlacklistService ppmOutsourcingBlacklistService;

    /**
     * 分页
     * @param ppmOutsourcingBlacklistDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "icard", value = "身份证", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depId", value = "二级部", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgCode", value = "供应商", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "officeDate", value = "离职时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "why", value = "原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addDate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "addUserId", value = "创建人工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addUserName", value = "创建人名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除（0:正常，1：删除）", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmOutsourcingBlacklistService.queryPager(ppmOutsourcingBlacklistDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 检查是否黑名单
     * @param ppmOutsourcingBlacklistDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "checkedIsBlack")
    @ApiOperation(value = "检查是否黑名单", notes = "根据身份证查询")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "icard", value = "身份证", dataType = "string", paramType = "query", required = true),
			@ApiImplicitParam(name = "depId", value = "二级部", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgCode", value = "供应商", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "officeDate", value = "离职时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "why", value = "原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addDate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "addUserId", value = "创建人工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addUserName", value = "创建人名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除（0:正常，1：删除）", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			
    })
    public ApiResult<Boolean> checkedIsBlack(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto) {
        ApiResult<Boolean> result =new ApiResult<>();
        PpmOutsourcingBlacklistDto item = ppmOutsourcingBlacklistService.queryDetail(ppmOutsourcingBlacklistDto);
        result.setStatus(ApiCode.SUCCESS);
        if(null != item) {
            result.setResults(true);
            result.setMessage("已被列入黑名单");
        } else {
            result.setResults(false);
            result.setMessage("未查询到黑名单");
        }

        return result;
    }

    /**
     * 更新
     * @param ppmOutsourcingBlacklistDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "icard", value = "身份证", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depId", value = "二级部", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orgCode", value = "供应商", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "officeDate", value = "离职时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "why", value = "原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addDate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "addUserId", value = "创建人工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addUserName", value = "创建人名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除（0:正常，1：删除）", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "自增编号", dataType = "long", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmOutsourcingBlacklist(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmOutsourcingBlacklistService.updatePpmOutsourcingBlacklist(ppmOutsourcingBlacklistDto);
        if(id == 1){
            result.success("添加成功");
        }else if(id == 2) {
            result.success("更新成功");
        } else{
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("操作失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmOutsourcingBlacklistDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmOutsourcingBlacklist (PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmOutsourcingBlacklistService.deletePpmOutsourcingBlacklist(ppmOutsourcingBlacklistDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 导出
     * @param ppmOutsourcingBlacklistDto 对象
     * @param response response
     */
    @PostMapping("exportExcel")
    @ApiOperation(value = "导出", notes = "导出")
    public void exportExcel(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto, HttpServletResponse response) {
        ApiResult result =new ApiResult<>();
        List<PpmOutsourcingBlacklistDto> list = ppmOutsourcingBlacklistService.getList(ppmOutsourcingBlacklistDto);
        ExcelUtil.exportExcel(list,PpmOutsourcingBlacklistDto.class,"aa.xls", response);
        result.success("导入成功");
    }
}
