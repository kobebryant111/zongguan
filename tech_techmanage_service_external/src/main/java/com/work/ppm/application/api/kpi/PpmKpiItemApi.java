package com.work.ppm.application.api.kpi;

import com.work.ppm.application.dto.kpi.PpmKpiItemBalanceInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemComprehensiveAbilityInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemKpiInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemPostEvaluationInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiItemDto;
import com.work.ppm.application.services.kpi.IPpmKpiItemService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 考核指标库主表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiItemApi"},description="考核指标库主表")
@RestController
@RequestMapping("ppmKpiItem")
@Slf4j
public class PpmKpiItemApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKpiItemService ppmKpiItemService;

    /**
     * 分页
     * @param ppmKpiItemDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "指标项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userole", value = "使用角色（1：员工，2：经理）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "主体", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKpiItemDto ppmKpiItemDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiItemService.queryPager(ppmKpiItemDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKpiItemDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "指标项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userole", value = "使用角色（1：员工，2：经理）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "主体", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmKpiItemDto> queryDetail(PpmKpiItemDto ppmKpiItemDto) {
        ApiResult<PpmKpiItemDto> result =new ApiResult<>();
        PpmKpiItemDto item = ppmKpiItemService.queryDetail(ppmKpiItemDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKpiItemDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "item", value = "指标项", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userole", value = "使用角色（1：员工，2：经理）", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "itype", value = "指标类型", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "mainpart", value = "主体", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmKpiItem(PpmKpiItemDto ppmKpiItemDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiItemService.updatePpmKpiItem(ppmKpiItemDto);
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
     * @param ppmKpiItemDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKpiItem (PpmKpiItemDto ppmKpiItemDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiItemService.deletePpmKpiItem(ppmKpiItemDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }



    /**
     * 考核kpi指标配置项导出
     * @param ppmKpiItemKpiInfoDto  对象
     * @param response  对象
     */
    @PostMapping("exportKpiItems")
    @ApiOperation(value = "kpi指标配置项导出")
    public void exportKpiItems(PpmKpiItemKpiInfoDto ppmKpiItemKpiInfoDto, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        ApiResult result = new ApiResult();
        List<PpmKpiItemKpiInfoDto> list = ppmKpiItemService.ppmKpiItemInfoListKpi(ppmKpiItemKpiInfoDto);
        for(int i=0;i<list.size();i++){
            if(null!=list.get(i).getUptdate()){
                list.get(i).setUptdateStr(sdf.format(list.get(i).getUptdate()));
            }
        }
        ExcelUtil.exportExcel(list,PpmKpiItemKpiInfoDto.class,"kpi指标配置项.xls",response);
        result.success("数据导出成功");
    }


    /**
     * kpi指标配置项导入
     * @param file 文件
     * @param request   request
     * @return   ApiResult
     */
    @PostMapping("importExcel_kpi")
    @ApiOperation(value = "导入", notes = "导出的数据导入")
    public ApiResult importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        boolean falg = ppmKpiItemService.importExcelKpi(file,user.getUserid());
        if(falg) {
            result.success("导入成功");
        } else {
            result.failure("导入失败");
        }
        return result;
    }

    /**
     * 删除多个
     * @param ids 对象
     * @return ApiResult
     */
    @DeleteMapping("deletePpmKpiItemListKpi")
    @ApiOperation(value = "删除KPI配置项", notes = "根据自增id删除数据")
    public ApiResult deletePpmKpiItemListKpi(String ids) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiItemService.deletePpmKpiItemListKpi(ids);
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 更新kpi指标配置项
     * @param ppmKpiItemDto 对象
     * @param request   对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePpmKpiItemDto_kpi")
    @ApiOperation(value = "更新kpi指标配置项", notes = "更新kpi指标配置项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "item", value = "指标项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userole", value = "使用角色（1：员工，2：经理）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "指标类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "mainpart", value = "主体", dataType = "string", paramType = "query"),
    })
    public ApiResult<Object> updatePpmKpiItemDtoKpi(PpmKpiItemDto ppmKpiItemDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiItemService.updatePpmKpiItemKpi(ppmKpiItemDto,user.getUserid());
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }


    /**
     * 添加kpi指标配置项
     * @param ppmKpiItemDto 对象
     * @param request   对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = {"addPpmKpiItemDto_kpi"})
    @ApiOperation(value = "addPpmKpiItemDto_kpi", notes = "添加kpi指标配置项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "item", value = "指标项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userole", value = "使用角色（1：员工，2：经理）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "指标类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "mainpart", value = "主体", dataType = "string", paramType = "query"),
    })
    public ApiResult<Object> addPpmKpiItemDtoKpi(PpmKpiItemDto ppmKpiItemDto, HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiItemService.addPpmKpiItemKpi(ppmKpiItemDto,user.getUserid());
        if(id > 0){
            result.success("添加成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 分页查询kpi指标配置项
     * @param ppmKpiItemKpiInfoDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPagerList_kpi")
    @ApiOperation(value ="分页列表查询kpi指标配置项", notes = "分页列表查询kpi指标配置项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "item", value = "指标项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userole", value = "使用角色（1：员工，2：经理）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "指标类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "mainpart", value = "主体", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centername", value = "中心名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itypename", value = "类型名称", dataType = "string", paramType = "query"),


            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPagerListKpi(PpmKpiItemKpiInfoDto ppmKpiItemKpiInfoDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiItemService.queryPagerListKpi(ppmKpiItemKpiInfoDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }




    /**
     * 综合能力配置项导出
     * @param ppmKpiItemComprehensiveAbilityInfoDto  对象
     * @param response  对象
     */
    @PostMapping("exportKpiItems_ComprehensiveAbility")
    @ApiOperation(value = "综合能力配置项导出")
    public void exportKpiItemsComprehensiveAbility(PpmKpiItemComprehensiveAbilityInfoDto
                                                               ppmKpiItemComprehensiveAbilityInfoDto,
                                                    HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        ApiResult result = new ApiResult();
        List<PpmKpiItemComprehensiveAbilityInfoDto> list = ppmKpiItemService.
                getPpmKpiItemInfoListComprehensiveAbility(ppmKpiItemComprehensiveAbilityInfoDto);
        for(int i=0;i<list.size();i++){
            if(null!=list.get(i).getFinishdate()){
                list.get(i).setFinishdateStr(sdf.format(list.get(i).getFinishdate()));
            }
        }
        ExcelUtil.exportExcel(list,PpmKpiItemComprehensiveAbilityInfoDto.class,"综合能力配置项.xls",response);
        result.success("数据导出成功");
    }


    /**
     * 综合能力配置项导入
     * @param file 文件
     * @param request   request
     * @return   ApiResult
     */
    @PostMapping("importExcel_ComprehensiveAbility")
    @ApiOperation(value = "导入", notes = "导出的数据导入")
    public ApiResult importExcelComprehensiveAbility(@RequestParam("file") MultipartFile file,
                                                      HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        boolean falg = ppmKpiItemService.importExcelComprehensiveAbility(file,user.getUserid());
        if(falg) {
            result.success("导入成功");
        } else {
            result.failure("导入失败");
        }
        return result;
    }



    /**
     * 删除多个综合能力配置项
     * @param ids 对象
     * @return ApiResult
     */
    @DeleteMapping("deletePpmKpiItemList_ComprehensiveAbility")
    @ApiOperation(value = "删除综合能力配置项", notes = "根据自增id删除数据")
    public ApiResult deletePpmKpiItemListComprehensiveAbility(String ids) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiItemService.deletePpmKpiItemListComprehensiveAbility(ids);
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 更新指标配置项
     * @param ppmKpiItemInfoDto 对象
     * @param request   对象
     * @return ApiResult
     */
    @PostMapping(value = "updateKpiItemData")
    @ApiOperation(value = "更新指标配置项", notes = "更新指标配置项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "item", value = "指标项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userole", value = "使用角色（1：员工，2：经理）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "指标类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "mainpart", value = "主体", dataType = "string", paramType = "query"),
    })
    public ApiResult<Object> updateKpiItemData(@RequestBody PpmKpiItemInfoDto ppmKpiItemInfoDto,
                                               HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiItemService.updateKpiItemData(ppmKpiItemInfoDto,user.getUserid());
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 添加指标配置项
     * @param ppmKpiItemInfoDto 对象
     * @param request   对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = {"addKpiItemData"})
    @ApiOperation(value = "addKpiItemData", notes = "添加指标配置项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "item", value = "指标项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userole", value = "使用角色（1：员工，2：经理）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "指标类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "mainpart", value = "主体", dataType = "string", paramType = "query"),
    })
    public ApiResult<Object> addKpiItemData(@RequestBody PpmKpiItemInfoDto ppmKpiItemInfoDto,
                                            HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiItemService.addKpiItemData(ppmKpiItemInfoDto,user.getUserid());
        if(id > 0){
            result.success("添加成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 分页列表查询综合能力指标配置项
     * @param ppmKpiItemComprehensiveAbilityInfoDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPagerList_ComprehensiveAbility")
    @ApiOperation(value ="分页列表查询综合能力指标配置项", notes = "分页列表查询综合能力指标配置项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "item", value = "指标项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userole", value = "使用角色（1：员工，2：经理）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "指标类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "mainpart", value = "主体", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centername", value = "中心名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itypename", value = "类型名称", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPagerListComprehensiveAbility(PpmKpiItemComprehensiveAbilityInfoDto
                                                          ppmKpiItemComprehensiveAbilityInfoDto,
                                                         Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiItemService.queryPagerListComprehensiveAbility(ppmKpiItemComprehensiveAbilityInfoDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }



    /**
     * 删除多个履职评估配置项
     * @param ids 对象
     * @return ApiResult
     */
    @DeleteMapping("deletePpmKpiItemList_postEvaluation")
    @ApiOperation(value = "删除履职评估配置项", notes = "根据自增id删除数据")
    public ApiResult deletePpmKpiItemListPostEvaluation(String ids) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiItemService.deletePpmKpiItemListPostEvaluation(ids);
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }


    /**
     * 履职评估配置项导出
     * @param ppmKpiItemPostEvaluationInfoDto  对象
     * @param response  对象
     */
    @PostMapping("exportKpiItems_postEvaluation")
    @ApiOperation(value = "履职评估配置项导出")
    public void exportKpiItemsPostEvaluation(PpmKpiItemPostEvaluationInfoDto ppmKpiItemPostEvaluationInfoDto,
                                              HttpServletResponse response) {
        ApiResult result = new ApiResult();
        List<PpmKpiItemPostEvaluationInfoDto> list = ppmKpiItemService.
                getPpmKpiItemInfoListPostEvaluation(ppmKpiItemPostEvaluationInfoDto);
        ExcelUtil.exportExcel(list,PpmKpiItemPostEvaluationInfoDto.class,"履职评估配置项.xls",response);
        result.success("数据导出成功");
    }


    /**
     * 履职评估配置项导入
     * @param file 文件
     * @param request   request
     * @return   ApiResult
     */
    @PostMapping("importExcel_postEvaluation")
    @ApiOperation(value = "导入", notes = "履职评估配置项导入")
    public ApiResult importExcelPostEvaluation(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        boolean falg = ppmKpiItemService.importExcelPostEvaluation(file,user.getUserid());
        if(falg) {
            result.success("导入成功");
        } else {
            result.failure("导入失败");
        }
        return result;
    }



    /**
     * 分页列表查询履职评估指标配置项
     * @param ppmKpiItemPostEvaluationInfoDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPagerList_postEvaluation")
    @ApiOperation(value ="分页列表查询履职评估指标配置项", notes = "分页列表查询履职评估指标配置项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "item", value = "指标项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userole", value = "使用角色（1：员工，2：经理）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "指标类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "mainpart", value = "主体", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centername", value = "中心名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itypename", value = "类型名称", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPagerListPostEvaluation(PpmKpiItemPostEvaluationInfoDto ppmKpiItemPostEvaluationInfoDto,
                                                   Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiItemService.queryPagerListPostEvaluation(ppmKpiItemPostEvaluationInfoDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }


    /**
     * 删除多个平衡计分配置项
     * @param ids 对象
     * @return ApiResult
     */
    @DeleteMapping("deletePpmKpiItemList_balance")
    @ApiOperation(value = "删除平衡计分配置项", notes = "根据自增id删除数据")
    public ApiResult deletePpmKpiItemListBalance(String ids) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiItemService.deletePpmKpiItemListBalance(ids);
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }


    /**
     * 平衡计分配置项导出
     * @param ppmKpiItemBalanceInfoDto  对象
     * @param response  对象
     */
    @PostMapping("exportKpiItems_balance")
    @ApiOperation(value = "平衡计分配置项导出")
    public void exportKpiItemsBalance(PpmKpiItemBalanceInfoDto ppmKpiItemBalanceInfoDto,
                                       HttpServletResponse response) {
        ApiResult result = new ApiResult();
        List<PpmKpiItemBalanceInfoDto> list = ppmKpiItemService.getPpmKpiItemInfoListBalance(ppmKpiItemBalanceInfoDto);
        ExcelUtil.exportExcel(list,PpmKpiItemBalanceInfoDto.class,"平衡计分配置项.xls",response);
        result.success("数据导出成功");
    }


    /**
     * 平衡计分配置项导入
     * @param file 文件
     * @param request   request
     * @return   ApiResult
     */
    @PostMapping("importExcel_balance")
    @ApiOperation(value = "导入", notes = "履职评估配置项导入")
    public ApiResult importExcelBalance(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        boolean falg = ppmKpiItemService.importExcelBalance(file,user.getUserid());
        if(falg) {
            result.success("导入成功");
        } else {
            result.failure("导入失败");
        }
        return result;
    }


    /**
     * 根据id查询指标库主子表数据详情
     * @param ppmKpiItemInfoDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "getKpiItemData")
    @ApiOperation(value = "根据id查询指标库主子表数据详情", notes = "根据id查询指标库主子表数据详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),

    })
    public ApiResult<PpmKpiItemInfoDto> getKpiItemData(PpmKpiItemInfoDto ppmKpiItemInfoDto) {
        ApiResult<PpmKpiItemInfoDto> result =new ApiResult<>();
        PpmKpiItemInfoDto item = ppmKpiItemService.getKpiItemData(ppmKpiItemInfoDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }



    /**
     * 分页列表查询平衡计分指标配置项
     * @param ppmKpiItemBalanceInfoDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPagerList_balance")
    @ApiOperation(value ="分页列表查询平衡计分指标配置项", notes = "分页列表查询平衡计分指标配置项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "item", value = "指标项", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userole", value = "使用角色（1：员工，2：经理）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "指标类型", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "weight", value = "权重", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "mainpart", value = "主体", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centername", value = "中心名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "itypename", value = "类型名称", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPagerListBalance(PpmKpiItemBalanceInfoDto ppmKpiItemBalanceInfoDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiItemService.queryPagerListBalance(ppmKpiItemBalanceInfoDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }


    /**
     * 获取KPI指标项下拉框
     * @param ppmKpiItemDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "getKpiItems")
    @ApiOperation(value = "获取KPI指标项下拉框", notes = "获取KPI指标项下拉框")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userole", value = "使用角色（1：员工，2：经理）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "centerid", value = "中心", dataType = "string", paramType = "query"),

    })
    public ApiResult<List<PpmKpiItemDto>> getKpiItems(PpmKpiItemDto ppmKpiItemDto) {
        ApiResult<List<PpmKpiItemDto>> result =new ApiResult<>();
        List<PpmKpiItemDto> dto = ppmKpiItemService.getKpiItems(ppmKpiItemDto);
        result.setResults(dto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
