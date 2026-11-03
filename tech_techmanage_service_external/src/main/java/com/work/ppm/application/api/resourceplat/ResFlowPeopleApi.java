package com.work.ppm.application.api.resourceplat;


import com.work.ppm.application.model.resourceplat.ResFlowPeople;
import com.work.ppm.application.services.resourceplat.IResFlowPeopleService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 流程参与人员配置
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"ResExpandApi"})
@RestController
@RequestMapping("resFlowPeople")
@Slf4j
public class ResFlowPeopleApi extends BaseApi {
    /**
     * 人员配置接口
     */
    @Autowired
    private IResFlowPeopleService resFlowPeopleService;

    /**
     * 获取配置人员列表数据
     *
     * @return ApiResult
     */
    @PostMapping(value = "queryFlowPeopleList")
    public ApiResult<List> queryFlowPeopleList() {
        ApiResult<List> result = new ApiResult<>();
        List<Map<String, Object>> list =resFlowPeopleService.queryFlowPeopleList();
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 添加
     * @param resFlowPeople 对象
     * @return ApiResult<ResFlowPeople>
     */
    @PostMapping("insertFlowPeople")
    @ApiOperation(value = "添加人员", notes = "添加人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "nodeId", value = "流程节点定义ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "nodeName", value = "流程节点定义名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "用户名称", dataType = "string", paramType = "query"),

    })
    public ApiResult<ResFlowPeople> insertFlowPeople(ResFlowPeople resFlowPeople) {
        ApiResult<ResFlowPeople> result =new ApiResult<>();
        resFlowPeopleService.insertFlowPeople(resFlowPeople);
        result.success("添加成功");
        return result;
    }

    /**
     * 删除
     *
     * @param id 主键
     * @return result
     */
    @PostMapping(value = "delFlowPeople")
    @ApiOperation(value = "删除配置人员信息", notes = "删除配置人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "string", paramType = "delete"),
    })
    public ApiResult<String> delFlowPeople(String id) {
        ApiResult<String> result = new ApiResult<>();
        try {
            resFlowPeopleService.deleteFlowPeople(id);
            result.setStatus(ApiCode.SUCCESS);
            result.setMessage("操作成功");
        } catch (Exception e) {
            result.setStatus(ApiCode.FAIL);
            result.setMessage("操作成功");
            log.error(e.getMessage(), e);
        }
        return result;
    }

}
