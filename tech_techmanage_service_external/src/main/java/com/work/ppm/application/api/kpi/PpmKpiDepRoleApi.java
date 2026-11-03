package com.work.ppm.application.api.kpi;

import com.work.ppm.application.dto.kpi.PpmKpiDepRoleInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiDepRoleDto;
import com.work.ppm.application.services.kpi.IPpmKpiDepRoleService;
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
 * 部门角色类
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiDepRoleApi"},description="部门角色")
@RestController
@RequestMapping("ppmKpiDepRole")
@Slf4j
public class PpmKpiDepRoleApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKpiDepRoleService ppmKpiDepRoleService;

    /**
     * 分页
     * @param ppmKpiDepRoleDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
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
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKpiDepRoleDto ppmKpiDepRoleDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiDepRoleService.queryPager(ppmKpiDepRoleDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKpiDepRoleDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
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
			
    })
    public ApiResult<PpmKpiDepRoleDto> queryDetail(PpmKpiDepRoleDto ppmKpiDepRoleDto) {
        ApiResult<PpmKpiDepRoleDto> result =new ApiResult<>();
        PpmKpiDepRoleDto item = ppmKpiDepRoleService.queryDetail(ppmKpiDepRoleDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKpiDepRoleDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
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
			
    })
    public ApiResult<Object> updatePpmKpiDepRole(PpmKpiDepRoleDto ppmKpiDepRoleDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiDepRoleService.updatePpmKpiDepRole(ppmKpiDepRoleDto);
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
     * @param ppmKpiDepRoleDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKpiDepRole (PpmKpiDepRoleDto ppmKpiDepRoleDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiDepRoleService.deletePpmKpiDepRole(ppmKpiDepRoleDto);
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
     * @param ppmKpiDepRoleDto 对象
     * @param request   对象
     * @return ApiResult
     */
    @PostMapping(value = "insertPpmKpiDepRoles")
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

    })
    public ApiResult<Object> insertPpmKpiDepRoles(PpmKpiDepRoleDto ppmKpiDepRoleDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int res = ppmKpiDepRoleService.insertPpmKpiDepRoles(ppmKpiDepRoleDto,user.getUserid());
        if(res >=0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("发起失败");
        }
        return result;
    }


    /**
     * 考核部门角色导出
     * @param ppmKpiDepRoleInfoDto  对象
     * @param response  对象
     */
    @PostMapping("exportKpiDepRoles")
    @ApiOperation(value = "考核部门角色导出")
    public void exportKpiDepRoles(PpmKpiDepRoleInfoDto ppmKpiDepRoleInfoDto, HttpServletResponse response) {
        ApiResult result = new ApiResult();
        List<PpmKpiDepRoleInfoDto> list = ppmKpiDepRoleService.getPpmKpiDepRoleView(ppmKpiDepRoleInfoDto);
        ExcelUtil.exportExcel(list,PpmKpiDepRoleInfoDto.class,"部门考核角色.xls",response);
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
        boolean falg = ppmKpiDepRoleService.importExcel(file,user.getUserid());
        if(falg) {
            result.success("导入成功");
        } else {
            result.failure("导入失败");
        }
        return result;
    }

    /**
     * 分页
     * @param ppmKpiDepRoleInfoDto 对象
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
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sortField", value = "排序列", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sortOrder", value = "排序倒叙，正序", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPagerList(PpmKpiDepRoleInfoDto ppmKpiDepRoleInfoDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiDepRoleService.queryPagerList(ppmKpiDepRoleInfoDto,pager);
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
    @DeleteMapping("deletePpmKpiDepRoleList")
    @ApiOperation(value = "删除", notes = "根据自增id删除数据")
    public ApiResult deletePpmKpiDepRoleList(String ids) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiDepRoleService.deletePpmKpiDepRoleList(ids);
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
     * @param ppmKpiDepRoleDto 对象
     * @param request   对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePpmKpiDepRoleDto")
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

    })
    public ApiResult<Object> updatePpmKpiDepRoleDto(PpmKpiDepRoleDto ppmKpiDepRoleDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiDepRoleService.updatePpmKpiDepRoleDto(ppmKpiDepRoleDto,user.getUserid());
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
     * @param ppmKpiDepRoleDto 对象
     * @param request   对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePpmKpiRoleDtos")
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

    })
    public ApiResult<Object> updatePpmKpiRoleDtos(PpmKpiDepRoleDto ppmKpiDepRoleDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiDepRoleService.updatePpmKpiDepRoleDtos(ppmKpiDepRoleDto,user.getUserid());
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
     * @param ppmKpiDepRoleDto 对象
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


    })
    public ApiResult<Object> add(PpmKpiDepRoleDto ppmKpiDepRoleDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiDepRoleService.addPpmKpiDepRole(ppmKpiDepRoleDto);
        if(id > 0){
            result.success("添加成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }


    /**
     * 根据条件查询数据
     * @param ppmKpiDepRoleDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "getPpmKpiDepRoleList")
    @ApiOperation(value = "根据条件查询数据", notes = "根据条件查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "otype", value = "类型（1：二级部，2：中心，3：一级部）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "role", value = "应用角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "tlevel", value = "等级A。。。", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "nums", value = "人数", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
    })
    public ApiResult<List<PpmKpiDepRoleDto>> getPpmKpiDepRoleList(PpmKpiDepRoleDto ppmKpiDepRoleDto) {
        ApiResult<List<PpmKpiDepRoleDto>> result =new ApiResult<>();
        List<PpmKpiDepRoleDto> dtos = ppmKpiDepRoleService.getPpmKpiDepRoleList(ppmKpiDepRoleDto);
        result.setResults(dtos);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
