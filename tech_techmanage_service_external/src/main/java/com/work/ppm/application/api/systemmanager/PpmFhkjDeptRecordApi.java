package com.work.ppm.application.api.systemmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.application.model.systemmanager.PpmFhkjDeptRecord;
import com.work.ppm.application.services.systemmanager.IPpmFhkjDeptRecordService;
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
 * 分行科技配置管理
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmFhkjDeptRecordApi"}, description = "分行科技配置管理")
@RestController
@RequestMapping("ppmFhkjDeptRecord")
@Slf4j
public class PpmFhkjDeptRecordApi {
    /**
     * 分行科技配置管理接口
     */
    @Autowired
    private IPpmFhkjDeptRecordService ppmFhkjDeptRecordService;

    /**
     * 分页
     * @param ppmFhkjDeptRecord 对象
     * @param pager 分页对象
     * @return ApiResult<List> 
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="queryPager", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "0:分行1:子公司", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List>  queryPager(PpmFhkjDeptRecord ppmFhkjDeptRecord,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmFhkjDeptRecordService.queryPager(ppmFhkjDeptRecord,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmFhkjDeptRecord 对象
     * @return ApiResult<PpmFhkjDeptRecord>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "0:分行1:子公司", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmFhkjDeptRecord> queryDetail(PpmFhkjDeptRecord ppmFhkjDeptRecord) {
        ApiResult<PpmFhkjDeptRecord> result =new ApiResult<>();
        PpmFhkjDeptRecord item = ppmFhkjDeptRecordService.queryDetail(ppmFhkjDeptRecord);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmFhkjDeptRecord 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "update", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptname", value = "部门名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "0:分行1:子公司", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmFhkjDeptRecord(PpmFhkjDeptRecord ppmFhkjDeptRecord) {
        ApiResult<Object> result =new ApiResult<>();

        int id = ppmFhkjDeptRecordService.updatePpmFhkjDeptRecord(ppmFhkjDeptRecord);

        if(StringUtil.isNotEmpty(ppmFhkjDeptRecord.getId())){

            if(id > 0){
                result.setMessage("添加成功");
                result.setStatus(ApiCode.SUCCESS);
            } else {
                result.setStatus(ApiCode.UPATE_FAIL);
                result.setMessage("添加失败");
            }
            return result;
        }

        if(id > 0){
            result.setMessage("更新成功");
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ppmFhkjDeptRecord 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "delete", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "string", paramType = "query"),

    })
    public ApiResult deletePpmFhkjDeptRecord (PpmFhkjDeptRecord ppmFhkjDeptRecord) {
        ApiResult result =new ApiResult<>();
        int id = ppmFhkjDeptRecordService.deletePpmFhkjDeptRecord(ppmFhkjDeptRecord);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
