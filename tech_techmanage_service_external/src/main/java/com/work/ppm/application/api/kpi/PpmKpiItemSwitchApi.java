package com.work.ppm.application.api.kpi;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiItemSwitchDto;
import com.work.ppm.application.services.kpi.IPpmKpiItemSwitchService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKpiItemSwitchApi"},description="考核配置发起中心")
@RestController
@RequestMapping("ppmKpiItemSwitch")
@Slf4j
public class PpmKpiItemSwitchApi extends BaseApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKpiItemSwitchService ppmKpiItemSwitchService;

    /**
     * 分页
     * @param ppmKpiItemSwitchDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "launchtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isvisible", value = "是否显示0：显示 1：不显示", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "itemtype", value = "配置项类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relroles", value = "相关角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isswitch", value = "是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itype", value = "考核开关类型", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKpiItemSwitchDto ppmKpiItemSwitchDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKpiItemSwitchService.queryPager(ppmKpiItemSwitchDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKpiItemSwitchDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "launchtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isvisible", value = "是否显示0：显示 1：不显示", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "itemtype", value = "配置项类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relroles", value = "相关角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isswitch", value = "是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itype", value = "考核开关类型", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmKpiItemSwitchDto> queryDetail(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) {
        ApiResult<PpmKpiItemSwitchDto> result =new ApiResult<>();
        PpmKpiItemSwitchDto item = ppmKpiItemSwitchService.queryDetail(ppmKpiItemSwitchDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKpiItemSwitchDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "launchtime", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isvisible", value = "是否显示0：显示 1：不显示", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "itemtype", value = "配置项类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "term", value = "时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "relroles", value = "相关角色", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "isswitch", value = "是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "itype", value = "考核开关类型", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiItemSwitchService.updatePpmKpiItemSwitch(ppmKpiItemSwitchDto);
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
     * @param ppmKpiItemSwitchDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKpiItemSwitch (PpmKpiItemSwitchDto ppmKpiItemSwitchDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiItemSwitchService.deletePpmKpiItemSwitch(ppmKpiItemSwitchDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }


    /**
     * 考核配置发起中心数据发起
     * @param ppmKpiItemSwitchDto 文件
     * @param request   对象
     * @return   ApiResult
     */
    @PostMapping("launch")
    @ApiOperation(value = "考核配置发起中心数据发起", notes = "考核配置发起中心数据发起")
    public ApiResult launch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto, HttpServletRequest request) {
        ApiResult result = new ApiResult();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int i = ppmKpiItemSwitchService.createPpmKpiItemSwitch(ppmKpiItemSwitchDto,user.getUserid());
        if(i>0) {
            result.success("发起成功");
            result.setResults(true);
        } else {
            result.failure("发起失败");
            result.setResults(false);
        }
        return result;
    }

    /**
     * 获取开关配置页面数据
     * @param ppmKpiItemSwitchDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "getPpmKpiItemSwitchList")
    @ApiOperation(value = "获取开关配置页面数据", notes = "获取开关配置页面数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "relroles", value = "相关角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isswitch", value = "是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "考核开关类型", dataType = "string", paramType = "query"),

    })
    public ApiResult<List<PpmKpiItemSwitchDto>> getPpmKpiItemSwitchList(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) {
        ApiResult<List<PpmKpiItemSwitchDto>> result =new ApiResult<>();
        List<PpmKpiItemSwitchDto> lists= ppmKpiItemSwitchService.getPpmKpiItemSwitchList(ppmKpiItemSwitchDto);
        result.setResults(lists);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }


    /**
     * 更新考核开关配置项
     * @param ppmKpiItemSwitchDto 对象
     * @param request   对象
     * @return ApiResult
     */
    @PostMapping(value = "updatePpmKpiItemSwitch")
    @ApiOperation(value = "更新考核开关配置项", notes = "更新考核开关配置项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "launchtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isvisible", value = "是否显示0：显示 1：不显示", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "itemtype", value = "配置项类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "relroles", value = "相关角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isswitch", value = "是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "考核开关类型", dataType = "string", paramType = "query"),
    })
    public ApiResult<Object> updatePpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto,
                                                    HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiItemSwitchService.updatePpmKpiItemSwitch(ppmKpiItemSwitchDto,user.getUserid());
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }


    /**
     * 获取评价设置页面数据
     * @param eosDictEntry 对象
     * @return ApiResult
     */
    @PostMapping(value = "getEosDictEntrys")
    @ApiOperation(value = "获取评价设置页面数据", notes = "获取评价设置页面数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "launchtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isvisible", value = "是否显示0：显示 1：不显示", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "itemtype", value = "配置项类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "relroles", value = "相关角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isswitch", value = "是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "考核开关类型", dataType = "string", paramType = "query"),

    })
    public ApiResult<List<EosDictEntry>> getEosDictEntrys(EosDictEntry eosDictEntry) {
        ApiResult<List<EosDictEntry>> result =new ApiResult<>();
        List<EosDictEntry> lists= ppmKpiItemSwitchService.getEosDictEntrys(eosDictEntry);
        result.setResults(lists);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 评价项设置删除
     * @param eosDictEntry 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delEvalSet")
    @ApiOperation(value = "评价项设置删除", notes = "评价项设置删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dicttypeid", value = "评价主业务字典", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "dictid", value = "评价项分数", dataType = "Long", paramType = "query"),

    })
    public ApiResult delEvalSet (EosDictEntry eosDictEntry) {
        ApiResult result =new ApiResult<>();
        int id = ppmKpiItemSwitchService.delEvalSet(eosDictEntry);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 评价项设置
     * @param eosDictEntry 对象
     * @return ApiResult
     */
    @PostMapping(value = "updateEvalSet")
    @ApiOperation(value = "更新评价项设置", notes = "更新评价项设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dicttypeid", value = "评价主业务字典", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "dictid", value = "评价项分数", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "dictname", value = "评价项内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sortno", value = "评价项排序列", dataType = "integer", paramType = "query"),
    })
    public ApiResult<Object> updateEvalSet(EosDictEntry eosDictEntry) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiItemSwitchService.updateEvalSet(eosDictEntry);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 添加评价项设置
     * @param eosDictEntry 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = {"addEvalSet"})
    @ApiOperation(value = "添加评价项设置", notes = "添加评价项设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dicttypeid", value = "评价主业务字典", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "dictid", value = "评价项分数", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "dictname", value = "评价项内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sortno", value = "评价项排序列", dataType = "integer", paramType = "query"),
    })
    public ApiResult<Object> addEvalSet(EosDictEntry eosDictEntry) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKpiItemSwitchService.addEvalSet(eosDictEntry);
        if(id > 0){
            result.success("添加成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 更新发起考核开关配置项更新发起时间
     * @param ppmKpiItemSwitchDto 对象
     * @param request   对象
     * @return ApiResult
     */
    @PostMapping(value = "updateLaunchPpmKpiItemSwitch")
    @ApiOperation(value = "更新发起考核开关配置项更新发起时间", notes = "更新发起考核开关配置项更新发起时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "launchtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isvisible", value = "是否显示0：显示 1：不显示", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "itemtype", value = "配置项类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "relroles", value = "相关角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isswitch", value = "是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "考核开关类型", dataType = "string", paramType = "query"),
    })
    public ApiResult<Object> updateLaunchPpmKpiItemSwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto,
                                                          HttpServletRequest request) {
        ApiResult<Object> result =new ApiResult<>();
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        int id = ppmKpiItemSwitchService.updateLunchPpmKpiItemSwitch(ppmKpiItemSwitchDto,user.getUserid());
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }
    /**
     * 查询详情
     * @param ppmKpiItemSwitchDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "querySwitch")
    @ApiOperation(value = "查询开关详情", notes = "查询开关详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "launchtime", value = "创建时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "isdisabled", value = "是否禁用0：警用 1：不禁用", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isvisible", value = "是否显示0：显示 1：不显示", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "itemtype", value = "配置项类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "term", value = "时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "relroles", value = "相关角色", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "islaunch", value = "是否发起0：未发起 1：已发起", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isswitch", value = "是否开关0：关闭 1：开启", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "uptuserid", value = "更新人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "uptdate", value = "更新时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "itype", value = "考核开关类型", dataType = "string", paramType = "query"),

    })
    public ApiResult<PpmKpiItemSwitchDto> querySwitch(PpmKpiItemSwitchDto ppmKpiItemSwitchDto) {
        ApiResult<PpmKpiItemSwitchDto> result =new ApiResult<>();
        PpmKpiItemSwitchDto item = ppmKpiItemSwitchService.queryDetailByCondition(ppmKpiItemSwitchDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
