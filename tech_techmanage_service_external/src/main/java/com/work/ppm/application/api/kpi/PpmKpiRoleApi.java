package com.work.ppm.application.api.kpi;

import com.work.ppm.application.dto.kpi.JudgeKpiStatusDto;
import com.work.ppm.application.dto.kpi.PpmKpiRoleInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiRoleDto;
import com.work.ppm.application.services.kpi.IPpmKpiRoleService;
import com.work.ppm.commons.utils.AssertUtil;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 考核角色权限表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiRoleApi"},description="考核角色权限表")
@RestController
@RequestMapping("ppmKpiRole")
@Slf4j
public class PpmKpiRoleApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKpiRoleService ppmKpiRoleService;

    /**
     * 分页
     * @param ppmKpiRoleDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "头衔", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "一级审核评价人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "二级审核评价人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKpiRoleDto ppmKpiRoleDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiRoleService.queryPager(ppmKpiRoleDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKpiRoleDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "头衔", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "一级审核评价人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "二级审核评价人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
			
    })
    public ApiResult<PpmKpiRoleDto> queryDetail(PpmKpiRoleDto ppmKpiRoleDto) {
        ApiResult<PpmKpiRoleDto> result =new ApiResult<>();
        PpmKpiRoleDto item = ppmKpiRoleService.queryDetail(ppmKpiRoleDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKpiRoleDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePpmKpiRole")
    @ApiOperation(value = "更新权限表", notes = "更新权限表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "headship", value = "头衔", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstcheck", value = "一级审核评价人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "secondcheck", value = "二级审核评价人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmKpiRole(PpmKpiRoleDto ppmKpiRoleDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiRoleService.updatePpmKpiRole(ppmKpiRoleDto);
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
     * @param ppmKpiRoleDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deletePpmKpiRole")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKpiRole (PpmKpiRoleDto ppmKpiRoleDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiRoleService.deletePpmKpiRole(ppmKpiRoleDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 插入数据
     * @param ppmKpiRoleDto 对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "insertPpmKpiRoles")
    @ApiOperation(value = "插入数据", notes = "插入数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "headship", value = "头衔", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "firstcheck", value = "一级审核评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheck", value = "二级审核评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),

    })
    public ApiResult<Object> insertPpmKpiRoles(PpmKpiRoleDto ppmKpiRoleDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int res = ppmKpiRoleService.insertPpmKpiRoles(ppmKpiRoleDto,user.getUserid());
        if(res >=0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("发起失败");
        }
        return result;
    }
    /**
     * 考核角色导出
     * @param ppmKpiRoleInfoDto  对象
     * @param response  对象
     */
    @PostMapping("exportKpiRoles")
    @ApiOperation(value = "考核角色导出")
    public void exportKpiRoles(PpmKpiRoleInfoDto ppmKpiRoleInfoDto, HttpServletResponse response) {
        ApiResult result = new ApiResult();
        List<PpmKpiRoleInfoDto> list = ppmKpiRoleService.getPpmKpiRoleView(ppmKpiRoleInfoDto);
        ExcelUtil.exportExcel(list,PpmKpiRoleInfoDto.class,"考核角色.xls",response);
        result.success("数据导出成功");
    }


    /**
     * 导入
     * @param file 文件
     * @param request   request
     * @return   ApiResult
     */
    @PostMapping("importExcel")
    @ApiOperation(value = "导入", notes = "导出的数据导入")
    public ApiResult importExcel( @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        boolean falg = ppmKpiRoleService.importExcel(file,user.getUserid());
        if(falg) {
            result.success("导入成功");
        } else {
            result.failure("导入失败");
        }
        return result;
    }

    /**
     * 分页
     * @param ppmKpiRoleInfoDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPagerList")
    @ApiOperation(value ="分页列表查询", notes = "分页列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "headship", value = "头衔", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "firstcheck", value = "一级审核评价人",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheck", value = "二级审核评价人",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sortField", value = "排序列", dataType = "string",
                    paramType = "query",defaultValue = "depid"),
            @ApiImplicitParam(name = "sortOrder", value = "排序倒叙，正序", dataType = "string",
                    paramType = "query",defaultValue = "asc"),
            @ApiImplicitParam(name = "sortRequirdField", value = "唯一排序列", dataType = "string",
                    paramType = "query",defaultValue = "r.id"),
    })
    public ApiResult queryPagerList(PpmKpiRoleInfoDto ppmKpiRoleInfoDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiRoleService.queryPagerList(ppmKpiRoleInfoDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 删除
     * @param ids id字符串 多个',' 分割
     * @return ApiResult
     */
    @DeleteMapping("deletePpmKpiRoleList")
    @ApiOperation(value = "删除", notes = "根据自增id删除数据")
    public ApiResult deletePpmKpiRoleList(String ids) {
        ApiResult<Object> result =new ApiResult<>();
        AssertUtil.notNull(ids, "[ID]不能为空");
        int id = ppmKpiRoleService.deletePpmKpiRoleList(ids);
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
     * @param ppmKpiRoleDto 对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePpmKpiRoleDto")
    @ApiOperation(value = "页面更新权限表", notes = "页面更新权限表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "headship", value = "头衔", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "firstcheck", value = "一级审核评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheck", value = "二级审核评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),

    })
    public ApiResult<Object> updatePpmKpiRoleDto(PpmKpiRoleDto ppmKpiRoleDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiRoleService.updatePpmKpiRoleDto(ppmKpiRoleDto,user.getUserid());
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
     * @param ppmKpiRoleDto 对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePpmKpiRoleDtos")
    @ApiOperation(value = "页面更新权限表_多个同时更新", notes = "页面更新权限表_多个同时更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "headship", value = "头衔", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "firstcheck", value = "一级审核评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheck", value = "二级审核评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "ids", value = "主键已，分割", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> updatePpmKpiRoleDtos(PpmKpiRoleDto ppmKpiRoleDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiRoleService.updatePpmKpiRoleDtos(ppmKpiRoleDto,user.getUserid());
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
     * @param ppmKpiRoleDto 对象
     * @param request 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = {"add"})
    @ApiOperation(value = "add", notes = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "headship", value = "头衔", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "firstcheck", value = "一级审核评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheck", value = "二级审核评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),

    })
    public ApiResult<Object> add(PpmKpiRoleDto ppmKpiRoleDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiRoleService.addPpmKpiRole(ppmKpiRoleDto,user.getUserid());
        if(id > 0){
            result.success("添加成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }


    /**
     * 同步给考核权限数据
     * @param ppmKpiRoleDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePpmKpiPm")
    @ApiOperation(value = "同步给考核权限数据", notes = "同步给考核权限数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
    })
    public ApiResult<Object> updatePpmKpiPm(PpmKpiRoleDto ppmKpiRoleDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiRoleService.updatePpmKpiPm(ppmKpiRoleDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("同步失败");
        }
        return result;
    }


    /**
     * 根据工号查询详情
     * @param ppmKpiRoleDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "getPpmKpiRoleByBadge")
    @ApiOperation(value = "根据工号查询详情", notes = "根据工号查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query" ,required = true),
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "headship", value = "头衔", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "firstcheck", value = "一级审核评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheck", value = "二级审核评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "最后修改时间", dataType = "date", paramType = "query"),

    })
    public ApiResult<PpmKpiRoleDto> getPpmKpiRoleByBadge(PpmKpiRoleDto ppmKpiRoleDto) {
        ApiResult<PpmKpiRoleDto> result =new ApiResult<>();
        PpmKpiRoleDto item = ppmKpiRoleService.getPpmKpiRoleDtoByBadge(ppmKpiRoleDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 根据条件查询详情
     * @param ppmKpiRoleInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "getPpmKpiRoleInfo")
    @ApiOperation(value = "根据条件查询详情", notes = "根据条件查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query" ,required = true),
            @ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "role", value = "角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "headship", value = "头衔", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "firstcheck", value = "一级审核评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheck", value = "二级审核评价人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduserid", value = "最后修改人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = " 最后修改时间", dataType = "date", paramType = "query"),

            @ApiImplicitParam(name = "firstcheckname", value = "一级审核评价人姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "secondcheckname", value = "二级审核评价人姓名", dataType = "string", paramType = "query"),

    })
    public ApiResult<PpmKpiRoleInfoDto> getPpmKpiRoleInfo(PpmKpiRoleInfoDto ppmKpiRoleInfoDto) {
        ApiResult<PpmKpiRoleInfoDto> result =new ApiResult<>();
        PpmKpiRoleInfoDto item = ppmKpiRoleService.getPpmKpiRoleInfo(ppmKpiRoleInfoDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 通过当前人和审批人判断考核所处阶段
     * @param judgeKpiStatusDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "judgeStatus")
    @ApiOperation(value = "通过当前人和审批人判断考核所处阶段", notes = "通过当前人和审批人判断考核所处阶段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eid", value = "人员工号", dataType = "string", paramType = "query",required = true),
            @ApiImplicitParam(name = "check", value = "评价人工号",
                    dataType = "string", paramType = "query",required = true),
            @ApiImplicitParam(name = "status", value = "当前状态", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "是审批还是评价审批：sp 评价：pj",
                    dataType = "string", paramType = "query",required = true),
    })
    public ApiResult<Object> judgeStatus(JudgeKpiStatusDto judgeKpiStatusDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiRoleService.judgeStatus(judgeKpiStatusDto);
        if(id > 0){
            result.setMessage(id+"");
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("查询失败");
        }
        return result;
    }
}
