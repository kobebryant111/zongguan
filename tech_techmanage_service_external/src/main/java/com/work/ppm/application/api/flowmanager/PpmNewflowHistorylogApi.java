package com.work.ppm.application.api.flowmanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.application.dto.flowmanager.PpmNewflowHistorylogDto;
import com.work.ppm.application.services.flowmanager.IPpmNewflowHistorylogService;
import com.work.ppm.commons.utils.AssertUtil;
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
 * 历史流程日志
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmNewflowHistorylogApi"},description="历史流程日志")
@RestController
@RequestMapping("ppmNewflowHistorylog")
@Slf4j
public class PpmNewflowHistorylogApi {
    /**
     * 历史流程接口
     */
    @Autowired
    private IPpmNewflowHistorylogService ppmNewflowHistorylogService;


    /**
     * 添加
     * @param ppmNewflowHistorylogDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "add")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "flowdraftno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "flowcaseno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "userid", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "examine", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "conclusion", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "suggest", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "createtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "creators", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lastmodtime", value = "", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "lastmoduser", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "applydesc", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isfalse", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "roleid", value = "", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> addPpmNewflowHistorylog(PpmNewflowHistorylogDto ppmNewflowHistorylogDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmNewflowHistorylogService.addHisLog(ppmNewflowHistorylogDto.getFlowcaseno());
        if(id > 0){
            result.success("添加成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("添加失败");
        }
        return result;
    }

    /**
     * 执行添加或者更新操作
     * @param ppmNewflowHistorylogDto 对象
     * @return ApiResult
     */
    @PostMapping("update")
    public ApiResult updatePpmNewflowHistorylog(PpmNewflowHistorylogDto ppmNewflowHistorylogDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmNewflowHistorylogService.updatePpmNewflowHistorylog(ppmNewflowHistorylogDto);
        if(id == 1){
            result.success("添加成功");
        } else if(id == 2) {
            result.success("更新成功");
        } else {
            result.failure("执行操作失败");
        }
        return result;
    }

    /**
     * 删除
     * @param ids id字符串 多个',' 分割
     * @return ApiResult
     */
    @DeleteMapping("delete")
    @ApiOperation(value = "删除", notes = "根据自增id删除数据")
    public ApiResult deletePpmNewflowHistorylog(String ids) {
        ApiResult<Object> result =new ApiResult<>();
        AssertUtil.notNull(ids, "[ID]不能为空");
        int id = ppmNewflowHistorylogService.deletePpmNewflowHistorylog(ids);
        if(id > 0){
            result.success("删除成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 根据流程id 查询
     * @param flowcaseno 流程id
     * @return ApiResult
     */
    @GetMapping("queryList")
    @ApiOperation(value = "根据流程id 查询", notes = "根据流程id 查询")
    @ApiImplicitParam(name="flowcaseno", value = "流程id", required = true, paramType = "query")
    public ApiResult<List<PpmNewflowHistorylogDto>> queryList(String flowcaseno) {
        ApiResult<List<PpmNewflowHistorylogDto>> result = new ApiResult<>();
        List<PpmNewflowHistorylogDto> list = ppmNewflowHistorylogService.queryList(flowcaseno);
        result.setResults(list);
        result.success("查询成功");
        return result;
    }

}
