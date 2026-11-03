package com.work.ppm.application.api.onlinemanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.onlinemanager.PpmTeamqaFileCheckDto;
import com.work.ppm.application.services.onlinemanager.IPpmTeamqaFileCheckService;
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
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmTeamqaFileCheckApi"},description="描述该类叫什么")
@RestController
@RequestMapping("ppmTeamqaFileCheck")
@Slf4j
public class PpmTeamqaFileCheckApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmTeamqaFileCheckService ppmTeamqaFileCheckService;

    /**
     * 分页
     * @param ppmTeamqaFileCheckDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowtype", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmTeamqaFileCheckService.queryPager(ppmTeamqaFileCheckDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmTeamqaFileCheckDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowtype", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmTeamqaFileCheckDto> queryDetail(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto) {
        ApiResult<PpmTeamqaFileCheckDto> result =new ApiResult<>();
        PpmTeamqaFileCheckDto item = ppmTeamqaFileCheckService.queryDetail(ppmTeamqaFileCheckDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmTeamqaFileCheckDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "instanceid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "instid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "createuser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowtype", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmTeamqaFileCheck(PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmTeamqaFileCheckService.updatePpmTeamqaFileCheck(ppmTeamqaFileCheckDto);
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
     * @param ppmTeamqaFileCheckDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmTeamqaFileCheck (PpmTeamqaFileCheckDto ppmTeamqaFileCheckDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmTeamqaFileCheckService.deletePpmTeamqaFileCheck(ppmTeamqaFileCheckDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
