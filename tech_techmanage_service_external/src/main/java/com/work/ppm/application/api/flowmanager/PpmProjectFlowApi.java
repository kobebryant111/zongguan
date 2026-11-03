package com.work.ppm.application.api.flowmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.flowmanager.PpmProjectFlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.flowmanager.PpmProjectFlow;
import com.work.ppm.application.services.flowmanager.IPpmProjectFlowService;
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
 * 流程对象
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmProjectFlowApi"}, description = "流程对象")
@RestController
@RequestMapping("ppmProjectFlow")
@Slf4j
public class PpmProjectFlowApi extends BaseApi {
    /**
     * 流程对象接口
     */
    @Autowired
    private IPpmProjectFlowService ppmProjectFlowService;

    /**
     * 我的归档
     * @param ppmProjectFlow 对象
     * @param pager 分页对象
     * @param request request
     * @return ApiResult<List>
     */
    @PostMapping(value = "getDoneWorkItem")
    @ApiOperation(value ="我的归档", notes = "我的归档")
    public ApiResult<List> getDoneWorkItem(PpmProjectFlowDto ppmProjectFlow, Pager pager, HttpServletRequest request) {
        ApiResult<List> result =new ApiResult<>();
        CommOperatorDoLoginDto userInfo =  getCurrentUser(request);
        ppmProjectFlow.setIsdel(0);
        ppmProjectFlow.setLastmoduser(userInfo.getUserid());
        ppmProjectFlow.setFlowstatus("已结束");
        ppmProjectFlowService.queryPager(ppmProjectFlow,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmProjectFlow 对象
     * @return ApiResult<PpmProjectFlow>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowtitle", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowurl", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowcreateuserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "startdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "enddate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "flowstatus", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "clr", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "activityname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "clrid", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmProjectFlow> queryDetail(PpmProjectFlow ppmProjectFlow) {
        ApiResult<PpmProjectFlow> result =new ApiResult<>();
        PpmProjectFlow item = ppmProjectFlowService.queryDetail(ppmProjectFlow);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmProjectFlow 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "projectno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowtitle", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowurl", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowcreateuserid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "startdate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "enddate", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "flowstatus", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "clr", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "activityname", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "clrid", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmProjectFlow(PpmProjectFlow ppmProjectFlow) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmProjectFlowService.updatePpmProjectFlow(ppmProjectFlow);
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
     * @param ppmProjectFlow 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deletePojo")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmProjectFlow (PpmProjectFlow ppmProjectFlow) {
        ApiResult result =new ApiResult<>();
        int id = ppmProjectFlowService.deletePpmProjectFlow(ppmProjectFlow);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
