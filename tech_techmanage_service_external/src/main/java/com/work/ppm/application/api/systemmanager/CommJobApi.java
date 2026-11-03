package com.work.ppm.application.api.systemmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.systemmanager.CommJobDto;
import com.work.ppm.application.model.systemmanager.CommJob;
import com.work.ppm.application.services.systemmanager.ICommJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 岗位
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"CommJobApi"}, description = "岗位")
@RestController
@RequestMapping("commJob")
@Slf4j
public class CommJobApi {
    /**
     * 岗位
     */
    @Autowired
    private ICommJobService commJobService;

    /**
     * 根据部门编号查询岗位
     * @param deptid 部门编号
     * @return ApiResult<List<CommJobDto>>
     */
    @GetMapping("getJobsForDetpId/{deptid}")
    @ApiOperation(value = "根据部门编号查询岗位")
    @ApiImplicitParam(name = "deptid", value = "部门编号", dataType = "string", paramType = "path", required = true)
    public ApiResult<List<CommJobDto>> getJobsForDetpId(@PathVariable("deptid") String deptid) {
        ApiResult<List<CommJobDto>> result =new ApiResult<>();
        List<CommJobDto> jobs = commJobService.getJobsForDetpId(deptid);
        result.setResults(jobs);
        result.success("获取成功");
        return result;
    }

    /**
     * 分页
     * @param commJob 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="queryPager", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "location", value = "中文名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "integer", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(CommJob commJob,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        commJobService.queryPager(commJob,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param commJob 对象
     * @return ApiResult<CommJob>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "location", value = "中文名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "  所属部门", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "updateflg", value = "更新标示 0.HR同步数据  1. 本地新增 2.本地更新", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "JOBID  岗位代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "是否失效", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "lastupdatedate", value = "最后更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "boardline", value = "所属条线", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "jobidhr", value = "JOBID  岗位代码 (HR)", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isprework", value = "  是否主持工作", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobcode", value = "岗位编码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "  备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "disableddate", value = "失效日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "effectdate", value = "生效日期", dataType = "date", paramType = "query"),
			
    })
    public ApiResult<CommJob> queryDetail(CommJob commJob) {
        ApiResult<CommJob> result =new ApiResult<>();
        CommJob item = commJobService.queryDetail(commJob);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param commJob 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "updatePojo", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "location", value = "中文名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "  所属部门", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "updateflg", value = "更新标示 0.HR同步数据  1. 本地新增 2.本地更新", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "JOBID  岗位代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isdisabled", value = "是否失效", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "compid", value = "所属机构", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "lastupdatedate", value = "最后更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "boardline", value = "所属条线", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "jobidhr", value = "JOBID  岗位代码 (HR)", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "isprework", value = "  是否主持工作", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobcode", value = "岗位编码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "  备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "disableddate", value = "失效日期", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "effectdate", value = "生效日期", dataType = "date", paramType = "query"),
			
    })
    public ApiResult<Object> updateCommJob(CommJob commJob) {
        ApiResult<Object> result =new ApiResult<>();
        int id = commJobService.updateCommJob(commJob);
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
     * @param commJob 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deletePojo")
    @ApiOperation(value = "deletePojo", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deleteCommJob (CommJob commJob) {
        ApiResult result =new ApiResult<>();
        int id = commJobService.deleteCommJob(commJob);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
