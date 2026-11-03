package com.work.ppm.application.api.flowmanager;

import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 流程日志
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmFlowLogApi"}, description = "流程日志")
@RestController
@RequestMapping("ppmFlowLog")
@Slf4j
public class PpmFlowLogApi {
    /**
     * 流程日志接口
     */
    @Autowired
    private IPpmFlowLogService ppmFlowLogService;

    /**
     * 分页
     *
     * @param ppmFlowLog 对象
     * @param pager 分页对象
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "flowcaseno", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flowtitle", value = "", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List>  queryPager(PpmFlowLog ppmFlowLog, Pager pager) {
        ApiResult<List> result = new ApiResult<>();
        ppmFlowLogService.queryPager(ppmFlowLog, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 获取流程日志集合
     * @param ppmFlowLog 对象
     * @return ApiResult<List<PpmFlowLogDto>>
     */
    @GetMapping("queryList")
    @ApiOperation(value = "获取流程日志集合", notes = "获取流程日志集合")
    @ApiImplicitParam(name = "flowcaseno", value = "流程id", dataType = "string", paramType = "query", required = true)
    public ApiResult<List<PpmFlowLogDto>> queryList(PpmFlowLog ppmFlowLog) {
        ApiResult<List<PpmFlowLogDto>> result = new ApiResult<>();
        List<PpmFlowLogDto> list = ppmFlowLogService.queryList(ppmFlowLog);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 查询详情
     *
     * @param ppmFlowLog 对象
     * @return ApiResult<PpmFlowLog>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "flowdraftno", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flowcaseno", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "examine", value = "", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "conclusion", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggest", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "1-????0-???", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applydesc", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isfalse", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roleid", value = "", dataType = "string", paramType = "query"),

    })
    public ApiResult<PpmFlowLog> queryDetail(PpmFlowLog ppmFlowLog) {
        ApiResult<PpmFlowLog> result = new ApiResult<>();
        PpmFlowLog item = ppmFlowLogService.queryDetail(ppmFlowLog);

        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     *
     * @param ppmFlowLog 对象
     * @return  ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "flowdraftno", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "flowcaseno", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "examine", value = "", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "conclusion", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "suggest", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isdel", value = "1-????0-???", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "applydesc", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptno", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isfalse", value = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roleid", value = "", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> updatePpmFlowLog(PpmFlowLog ppmFlowLog) {
        ApiResult<Object> result = new ApiResult<>();
        int id = ppmFlowLogService.updatePpmFlowLog(ppmFlowLog);
        if (id == 1) {
            result.success("添加成功");
        } else if(id == 2)  {
            result.success("更新成功");
        } else {
            result.failure("执行操作失败");
        }
        return result;
    }

    /**
     * 删除
     *
     * @param ids 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmFlowLog(String ids) {
        ApiResult result = new ApiResult<>();
        String[] idss = ids.split(",");
        int id = ppmFlowLogService.deletePpmFlowLog(idss);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 流程日志分页查询（包含历史流程日志）
     * @param ppmFlowLogDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping("queryContainHisPager")
    @ApiOperation(value = "流程日志分页查询（包含历史流程日志）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flowcaseno", value = "流程实例id", paramType = "query"),
            @ApiImplicitParam(name = "flowdraftno", value = "流程实例名称", paramType = "query", defaultValue = "com.work.ppm.bps.rlwbxqsq_new"),
    })
    public ApiResult<List<PpmFlowLogDto>> queryContainHisPager(PpmFlowLogDto ppmFlowLogDto, Pager pager) {
        ApiResult<List<PpmFlowLogDto>> result = new ApiResult<>();
        ppmFlowLogDto.setIsdel(0L);
        ppmFlowLogService.queryContainHisPager(ppmFlowLogDto, pager);
        result.setTotal(pager.getTotal());
        result.setResults((List<PpmFlowLogDto>)pager.getList());
        result.success("查询成功");
        return result;
    }
}
