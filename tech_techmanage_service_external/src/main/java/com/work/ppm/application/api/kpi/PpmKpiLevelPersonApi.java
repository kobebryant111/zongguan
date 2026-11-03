package com.work.ppm.application.api.kpi;

import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonDto;
import com.work.ppm.application.services.kpi.IPpmKpiLevelPersonService;
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
 * 考核比例人数设置接口
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiLevelPersonApi"},description="考核比例人数设置接口")
@RestController
@RequestMapping("ppmKpiLevelPerson")
@Slf4j
public class PpmKpiLevelPersonApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKpiLevelPersonService ppmKpiLevelPersonService;

    /**
     * 分页
     * @param ppmKpiLevelPersonDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
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
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKpiLevelPersonDto ppmKpiLevelPersonDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiLevelPersonService.queryPager(ppmKpiLevelPersonDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKpiLevelPersonDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
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
    public ApiResult<PpmKpiLevelPersonDto> queryDetail(PpmKpiLevelPersonDto ppmKpiLevelPersonDto) {
        ApiResult<PpmKpiLevelPersonDto> result =new ApiResult<>();
        PpmKpiLevelPersonDto item = ppmKpiLevelPersonService.queryDetail(ppmKpiLevelPersonDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKpiLevelPersonDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
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
    public ApiResult<Object> updatePpmKpiLevelPerson(PpmKpiLevelPersonDto ppmKpiLevelPersonDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiLevelPersonService.updatePpmKpiLevelPerson(ppmKpiLevelPersonDto);
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
     * @param ppmKpiLevelPersonDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKpiLevelPerson (PpmKpiLevelPersonDto ppmKpiLevelPersonDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiLevelPersonService.deletePpmKpiLevelPerson(ppmKpiLevelPersonDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }



    /**
     * 后台人数比例分页查询
     * @param ppmKpiLevelPersonInfoDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryShowPager")
    @ApiOperation(value ="后台人数比例分页查询", notes = "后台人数比例分页查询")
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

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryShowPager(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiLevelPersonService.queryShowPager(ppmKpiLevelPersonInfoDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }


    /**
     * 人员比例数据导出
     * @param ppmKpiLevelPersonInfoDto  对象
     * @param response  对象
     */
    @PostMapping("exportData")
    @ApiOperation(value = "人员比例数据导出")
    public void exportData(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto, HttpServletResponse response) {
        ApiResult result = new ApiResult();
        ppmKpiLevelPersonService.exportData(ppmKpiLevelPersonInfoDto,response);
        result.success("数据导出成功");
    }

    /**
     * 导入
     * @param file 文件
     * @param request   request
     * @param ppmKpiLevelPersonInfoDto  对象
     * @return   ApiResult
     */
    @PostMapping("importExcel")
    @ApiOperation(value = "导入", notes = "导出的数据导入")
    public ApiResult importExcel(@RequestParam("file") MultipartFile file,PpmKpiLevelPersonInfoDto
            ppmKpiLevelPersonInfoDto, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        boolean falg = ppmKpiLevelPersonService.importExcel(file,ppmKpiLevelPersonInfoDto,user.getUserid());
        if(falg) {
            result.success("导入成功");
        } else {
            result.failure("导入失败");
        }
        return result;
    }


    /**
     * 添加或者更新
     * @param ppmKpiLevelPersonDto 对象
     * @param request   request
     * @return ApiResult
     */
    @PostMapping(value = "updateOrAdd")
    @ApiOperation(value = "添加或者更新", notes = "添加或者更新")
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
    public ApiResult<Object> updateOrAdd(PpmKpiLevelPersonDto ppmKpiLevelPersonDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiLevelPersonService.updateOrAdd(ppmKpiLevelPersonDto,user.getUserid());
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }


    /**
     * 同步数据
     * @param request   request
     * @return ApiResult
     */
    @PostMapping(value = "syncLevelPersonData")
    @ApiOperation(value = "同步数据", notes = "同步数据")
    public ApiResult<Object> syncLevelPersonData(HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiLevelPersonService.syncLevelPersonData(user.getUserid());
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("同步成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("同步失败");
        }
        return result;
    }



    /**
     * 添加或数据
     * @param eosDictEntry 对象
     * @param request   request
     * @return ApiResult
     */
    @PostMapping(value = "addKpiLevelPerson")
    @ApiOperation(value = "添加或数据", notes = "添加或数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dicttypeid", value = "评价主业务字典", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dictid", value = "评价项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dictname", value = "评价项内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sortno", value = "评价项排序列", dataType = "integer", paramType = "query"),
    })
    public ApiResult<Object> addKpiLevelPerson(EosDictEntry eosDictEntry, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiLevelPersonService.addKpiLevelPerson(eosDictEntry,user.getUserid());
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("添加成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败");
        }
        return result;
    }


    /**
     * 添加或者更新
     * @param eosDictEntry 对象
     * @param request   request
     * @return ApiResult
     */
    @PostMapping(value = "deleteKpiLevelPerson")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dicttypeid", value = "评价主业务字典", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dictid", value = "评价项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dictname", value = "评价项内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sortno", value = "评价项排序列", dataType = "integer", paramType = "query"),
    })
    public ApiResult<Object> deleteKpiLevelPerson(EosDictEntry eosDictEntry, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiLevelPersonService.deleteKpiLevelPerson(eosDictEntry,user.getUserid());
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("删除成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
