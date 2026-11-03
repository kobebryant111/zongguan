package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.model.usermanager.PpmDaDeptManager;
import com.work.ppm.application.services.usermanager.IPpmDaDeptManagerService;
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
 * 组织树部门和分管总经理之间关系表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmDaDeptManagerApi"},description="组织树部门和分管总经理之间关系表")
@RestController
@RequestMapping("ppmDaDeptManager")
@Slf4j
public class PpmDaDeptManagerApi {
    /**
     * 组织树部门和分管总经理之间关系表
     */
    @Autowired
    private IPpmDaDeptManagerService ppmDaDeptManagerService;

    /**
     * 分页
     * @param ppmDaDeptManager 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心ID", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmDaDeptManager ppmDaDeptManager, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmDaDeptManagerService.queryPager(ppmDaDeptManager,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmDaDeptManager 对象
     * @return ApiResult<PpmDaDeptManager>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心ID", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmDaDeptManager> queryDetail(PpmDaDeptManager ppmDaDeptManager) {
        ApiResult<PpmDaDeptManager> result =new ApiResult<>();
        PpmDaDeptManager item = ppmDaDeptManagerService.queryDetail(ppmDaDeptManager);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmDaDeptManager 对象
     * @return  result
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "centerid", value = "中心ID", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmDaDeptManager(PpmDaDeptManager ppmDaDeptManager) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmDaDeptManagerService.updatePpmDaDeptManager(ppmDaDeptManager);
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
     * @param ppmDaDeptManager 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmDaDeptManager (PpmDaDeptManager ppmDaDeptManager) {
        ApiResult result =new ApiResult<>();
        int id = ppmDaDeptManagerService.deletePpmDaDeptManager(ppmDaDeptManager);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
