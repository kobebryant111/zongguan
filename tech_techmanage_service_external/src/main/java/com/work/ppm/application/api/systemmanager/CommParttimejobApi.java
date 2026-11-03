package com.work.ppm.application.api.systemmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.systemmanager.CommParttimejob;
import com.work.ppm.application.services.systemmanager.ICommParttimejobService;
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
 * 行员兼职管理
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"CommParttimejobApi"}, description = "行员兼职管理")
@RestController
@RequestMapping("commParttimejob")
@Slf4j
public class CommParttimejobApi {
    /**
     * 行员兼职管理
     */
    @Autowired
    private ICommParttimejobService commParttimejobService;

    /**
     * 分页
     * @param commParttimejob 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="queryPager", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "兼职机构代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "兼职部门代码", dataType = "integer", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List> queryPager(CommParttimejob commParttimejob,Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        commParttimejobService.queryPager(commParttimejob,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param commParttimejob 对象
     * @return ApiResult<CommParttimejob>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "queryDetail", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "compid", value = "兼职机构代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "boardline", value = "兼职条线", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "headship1", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdisable", value = "失效标示", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "enddate", value = "兼职结束时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "partid", value = "兼职ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "兼职岗位代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "costcut", value = "成本分摊比例", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "begindate", value = "兼职开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "兼职员工代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "兼职部门代码", dataType = "integer", paramType = "query"),
			
    })
    public ApiResult<CommParttimejob> queryDetail(CommParttimejob commParttimejob) {
        ApiResult<CommParttimejob> result =new ApiResult<>();
        CommParttimejob item = commParttimejobService.queryDetail(commParttimejob);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param commParttimejob 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "updatePojo")
    @ApiOperation(value = "updatePojo", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "兼职机构代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "boardline", value = "兼职条线", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "headship1", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdisable", value = "失效标示", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "enddate", value = "兼职结束时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "partid", value = "兼职ID", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "兼职岗位代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "costcut", value = "成本分摊比例", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "begindate", value = "兼职开始时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "eid", value = "兼职员工代码", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "兼职部门代码", dataType = "integer", paramType = "query"),
			
    })
    public ApiResult<Object> updateCommParttimejob(CommParttimejob commParttimejob) {
        ApiResult<Object> result =new ApiResult<>();
        int id = commParttimejobService.updateCommParttimejob(commParttimejob);
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
     * @param commParttimejob 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "deletePojo")
    @ApiOperation(value = "deletePojo", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deleteCommParttimejob (CommParttimejob commParttimejob) {
        ApiResult result =new ApiResult<>();
        int id = commParttimejobService.deleteCommParttimejob(commParttimejob);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }
}
