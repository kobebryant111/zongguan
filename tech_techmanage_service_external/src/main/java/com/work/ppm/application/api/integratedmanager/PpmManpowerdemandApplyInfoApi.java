package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.ManpowerdemandsInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfo;
import com.work.ppm.application.services.integratedmanager.IPpmManpowerdemandApplyInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
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
 * 人力需求详细信息
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmManpowerdemandApplyInfoApi"},description="人力需求详细信息")
@RestController
@RequestMapping("ppmManpowerdemandApplyInfo")
@Slf4j
public class PpmManpowerdemandApplyInfoApi {
    /**
     * 人力需求详细信息
     */
    @Autowired
    private IPpmManpowerdemandApplyInfoService ppmManpowerdemandApplyInfoService;

    /**
     * 分页
     * @param ppmManpowerdemandApplyInfo 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "usertype", value = "类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userlevel", value = "等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "usernum", value = "人数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addreason", value = "外包人员增加原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "personrequire", value = "人员要求", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "supplyids", value = "供应商编号多个", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "supplynames", value = "供应商名称多个", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程INSTANCEID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "otherrequire", value = "其他要求", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isadvertise", value = "是否需要综合部联系招聘", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态 0：未完成，1：已完成", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmManpowerdemandApplyInfoService.queryPager(ppmManpowerdemandApplyInfo,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmManpowerdemandApplyInfo 对象
     * @return ApiResult<PpmManpowerdemandApplyInfo>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "usertype", value = "类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userlevel", value = "等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "usernum", value = "人数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addreason", value = "外包人员增加原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "personrequire", value = "人员要求", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "supplyids", value = "供应商编号多个", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "supplynames", value = "供应商名称多个", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程INSTANCEID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "otherrequire", value = "其他要求", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isadvertise", value = "是否需要综合部联系招聘", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态 0：未完成，1：已完成", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmManpowerdemandApplyInfo> queryDetail(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) {
        ApiResult<PpmManpowerdemandApplyInfo> result =new ApiResult<>();
        PpmManpowerdemandApplyInfo item = ppmManpowerdemandApplyInfoService.queryDetail(ppmManpowerdemandApplyInfo);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmManpowerdemandApplyInfo 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "usertype", value = "类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userlevel", value = "等级", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "usernum", value = "人数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "addreason", value = "外包人员增加原因", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "personrequire", value = "人员要求", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "supplyids", value = "供应商编号多个", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "supplynames", value = "供应商名称多个", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createuserid", value = "创建人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createdate", value = "创建时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "是否删除", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "流程INSTANCEID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "otherrequire", value = "其他要求", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isadvertise", value = "是否需要综合部联系招聘", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态 0：未完成，1：已完成", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmManpowerdemandApplyInfo(PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmManpowerdemandApplyInfoService.updatePpmManpowerdemandApplyInfo(ppmManpowerdemandApplyInfo);
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
     * @param ppmManpowerdemandApplyInfo 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmManpowerdemandApplyInfo (PpmManpowerdemandApplyInfo ppmManpowerdemandApplyInfo) {
        ApiResult result =new ApiResult<>();
        int id = ppmManpowerdemandApplyInfoService.deletePpmManpowerdemandApplyInfo(ppmManpowerdemandApplyInfo);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
    /**
     * 面试查询人力需求根据Id
     * @param manpowerdemandsInfo 对象
     * @return ApiResult<ManpowerdemandsInfoDto>
     */
    @GetMapping("queryManpowerdemandsById")
    @ApiOperation("面试查询人力需求根据Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "instanceid", value = "流程号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "usertype", value = "人员类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "usernum", value = "人员数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "supplytime", value = "提至供应商时间", dataType = "date", paramType = "query"),
    })
    public ApiResult<ManpowerdemandsInfoDto> queryManpowerdemandsById(ManpowerdemandsInfoDto manpowerdemandsInfo) {
        ApiResult<ManpowerdemandsInfoDto> result =new ApiResult<>();
        ManpowerdemandsInfoDto dtoList = ppmManpowerdemandApplyInfoService.queryManpowerdemandsById(manpowerdemandsInfo);
        result.setResults(dtoList);
        result.success("查询成功");
        return result;
    }
}
