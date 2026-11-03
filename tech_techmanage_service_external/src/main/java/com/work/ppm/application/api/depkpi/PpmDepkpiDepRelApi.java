package com.work.ppm.application.api.depkpi;

import com.work.ppm.application.dto.depkpi.EosDepKpiGrade;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepRelInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepRelDto;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepRelService;
import com.work.ppm.commons.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 部门考核参数设置
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmDepkpiDepRelApi"})
@RestController
@RequestMapping("ppmDepkpiDepRel")
@Slf4j
public class PpmDepkpiDepRelApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmDepkpiDepRelService ppmDepkpiDepRelService;


    /**
     * 插入数据
     * @param ppmDepkpiDepRelDto 对象
     * @param request   对象
     * @return ApiResult
     */
    @PostMapping(value = "insertPpmDepKpiDepRels")
    @ApiOperation(value = "插入数据", notes = "插入数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "director", value = "主持工作经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depcharge", value = "部门分管领导//分管总经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centercharge", value = "中心总经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "1:部门 2：中心 3：一级部 ", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "firstcheck", value = "初审/初评人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheck", value = "终审/终评人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "marker", value = "考核制定人", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> insertPpmDepKpiDepRels(PpmDepkpiDepRelDto ppmDepkpiDepRelDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int res = ppmDepkpiDepRelService.insertPpmDepKpiDepRels(ppmDepkpiDepRelDto,user.getUserid());
        if(res >=0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("发起失败");
        }
        return result;
    }


    /**
     * 部门考核关系导出
     * @param ppmKpiDepRoleInfoDto  对象
     * @param response  对象
     */
    @PostMapping("exportDepKpiDepRels")
    @ApiOperation(value = "部门考核关系导出")
    public void exportDepKpiDepRels(PpmDepkpiDepRelInfoDto ppmKpiDepRoleInfoDto, HttpServletResponse response) {
        ApiResult result = new ApiResult();
        List<PpmDepkpiDepRelInfoDto> list = ppmDepkpiDepRelService.getPpmDepKpiDepRelList(ppmKpiDepRoleInfoDto);
        ExcelUtil.exportExcel(list,PpmDepkpiDepRelInfoDto.class,"部门考核关系.xls",response);
        result.success("数据导出成功");
    }


    /**
     * 导入
     * @param file 文件
     * @param request   对象
     * @return   ApiResult
     */
    @PostMapping("importExcel")
    @ApiOperation(value = "导入", notes = "导出的数据导入")
    public ApiResult importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        boolean falg = ppmDepkpiDepRelService.importExcel(file,user.getUserid());
        if(falg) {
            result.success("导入成功");
        } else {
            result.failure("导入失败");
        }
        return result;
    }

    /**
     * 分页
     * @param ppmDepkpiDepRelInfoDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPagerList")
    @ApiOperation(value ="分页列表查询", notes = "分页列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "director", value = "主持工作经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depcharge", value = "部门分管领导//分管总经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centercharge", value = "中心总经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "1:部门 2：中心 3：一级部 ", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "firstcheck", value = "初审/初评人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheck", value = "终审/终评人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "marker", value = "考核制定人", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sortField", value = "排序列", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sortOrder", value = "排序倒叙，正序", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPagerList(PpmDepkpiDepRelInfoDto ppmDepkpiDepRelInfoDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmDepkpiDepRelService.queryPagerList(ppmDepkpiDepRelInfoDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 删除多个
     * @param ids 对象
     * @return ApiResult
     */
    @DeleteMapping("deletePpmDepKpiDepRelList")
    @ApiOperation(value = "删除", notes = "根据自增id删除数据")
    public ApiResult deletePpmDepKpiDepRelList(String ids) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmDepkpiDepRelService.deletePpmDepKpiDepRelList(ids);
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 更新
     * @param ppmDepkpiDepRelDto 对象
     * @param request   对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePpmDepKpiDepRelDto")
    @ApiOperation(value = "页面更新权限表", notes = "页面更新权限表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "director", value = "主持工作经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depcharge", value = "部门分管领导//分管总经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centercharge", value = "中心总经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "1:部门 2：中心 3：一级部 ", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "firstcheck", value = "初审/初评人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheck", value = "终审/终评人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "marker", value = "考核制定人", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> updatePpmDepKpiDepRelDto(PpmDepkpiDepRelDto ppmDepkpiDepRelDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmDepkpiDepRelService.updatePpmDepKpiDepRelDto(ppmDepkpiDepRelDto,user.getUserid());
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 多个更新
     * @param ppmDepkpiDepRelDto 对象
     * @param request   对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePpmDepKpiDepRelDtos")
    @ApiOperation(value = "页面更新权限表_多个同时更新", notes = "页面更新权限表_多个同时更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "director", value = "主持工作经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depcharge", value = "部门分管领导//分管总经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centercharge", value = "中心总经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "1:部门 2：中心 3：一级部 ", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "firstcheck", value = "初审/初评人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheck", value = "终审/终评人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "marker", value = "考核制定人", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> updatePpmDepKpiDepRelDtos(PpmDepkpiDepRelDto ppmDepkpiDepRelDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmDepkpiDepRelService.updatePpmDepKpiDepRelDtos(ppmDepkpiDepRelDto,user.getUserid());
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }


    /**
     * 添加
     * @param ppmDepkpiDepRelDto 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = {"add"})
    @ApiOperation(value = "add", notes = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "director", value = "主持工作经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depcharge", value = "部门分管领导//分管总经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centercharge", value = "中心总经理", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "1:部门 2：中心 3：一级部 ", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "firstcheck", value = "初审/初评人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheck", value = "终审/终评人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "marker", value = "考核制定人", dataType = "string", paramType = "query"),


    })
    public ApiResult<Object> add(PpmDepkpiDepRelDto ppmDepkpiDepRelDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmDepkpiDepRelService.addPpmDepkpiDepRel(ppmDepkpiDepRelDto);
        if(id > 0){
            result.success("添加成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }


    /**
     * 保存层级设置
     * @param grade 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = {"saveEosDepKpiGrade"})
    @ApiOperation(value = "saveEosDepKpiGrade", notes = "保存层级设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dep", value = "二级部", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "cen", value = "中心", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tec", value = "一级部", dataType = "long", paramType = "query"),
    })
    public ApiResult<Object> saveEosDepKpiGrade(EosDepKpiGrade grade) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmDepkpiDepRelService.saveEosDepKpiGrade(grade);
        if(id > 0){
            result.success("保存成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("保存失败");
        }
        return result;
    }


    /**
     * 获得考核层级业务字典并转化为对象
     * @return ApiResult 对象
     */
    @PostMapping(value = "getEosDepKpiGrade")
    @ApiOperation(value = "getEosDepKpiGrade", notes = "获得考核层级业务字典并转化为对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dep", value = "二级部", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "cen", value = "中心", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tec", value = "一级部", dataType = "long", paramType = "query"),
    })
    public ApiResult<EosDepKpiGrade> getEosDepKpiGrade() {
        ApiResult<EosDepKpiGrade> result =new ApiResult<>();
        EosDepKpiGrade grade = ppmDepkpiDepRelService.getEosDepKpiGrade();
        result.setResults(grade);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
