package com.work.ppm.application.api.basismanager;

import com.work.ppm.application.dto.basismanager.PpmQuartzManagerDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.basismanager.PpmQuartzManager;
import com.work.ppm.application.services.basismanager.IPpmQuartzManagerService;
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
 * 定时器管理
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmQuartzManagerApi"},description="定时器管理")
@RestController
@RequestMapping("quartz/ppmQuartzManager")
@Slf4j
public class PpmQuartzManagerApi extends BaseApi {
    /**
     * 定时器接口
     */
    @Autowired
    private IPpmQuartzManagerService ppmQuartzManagerService;

    /**
     * 分页
     * @param ppmQuartzManager 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "triggerName", value = "触发器组名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "expresstion", value = "时间规则表达式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "className", value = "任务实现类类名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "state", value = "运行状态(1:运行，2：停止，默认：1)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createTime", value = "添加时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "des", value = "描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createName", value = "添加人", dataType = "string", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<List<PpmQuartzManager>> queryPager(PpmQuartzManager ppmQuartzManager,Pager pager) {
        ApiResult<List<PpmQuartzManager>> result =new ApiResult<>();
        ppmQuartzManagerService.queryPager(ppmQuartzManager,pager);
        result.setResults((List<PpmQuartzManager>)pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmQuartzManager 对象
     * @return ApiResult<PpmQuartzManager>
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "编号", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "triggerName", value = "触发器组名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "expresstion", value = "时间规则表达式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "className", value = "任务实现类类名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "state", value = "运行状态(1:运行，2：停止，默认：1)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createTime", value = "添加时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "des", value = "描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createName", value = "添加人", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<PpmQuartzManager> queryDetail(PpmQuartzManager ppmQuartzManager) {
        ApiResult<PpmQuartzManager> result =new ApiResult<>();
        PpmQuartzManager item = ppmQuartzManagerService.queryDetail(ppmQuartzManager);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 添加
     * @param ppmQuartzManager 对象
     * @param request  request
     * @return ApiResult<PpmQuartzManager>
     */
    @PostMapping("add")
    @ApiOperation(value = "添加定时器", notes = "添加定时器")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "triggerName", value = "触发器组名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "expresstion", value = "时间规则表达式", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "className", value = "任务实现类类名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "运行状态(1:运行，2：停止，默认：1)", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createTime", value = "添加时间", dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "des", value = "描述", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "createName", value = "添加人", dataType = "string", paramType = "query"),

    })
    public ApiResult<PpmQuartzManager> doAdd(PpmQuartzManager ppmQuartzManager, HttpServletRequest request) {
        ApiResult<PpmQuartzManager> result =new ApiResult<>();
        CommOperatorDoLoginDto sys = super.getCurrentUser(request);
        ppmQuartzManager.setCreateName(sys.getOperatorname() + "(" + sys.getUserid() + ")");
        ppmQuartzManagerService.addPpmQuartzManager(ppmQuartzManager);
        result.success("添加成功");
        return result;
    }

    /**
     * 更新
     * @param ppmQuartzManager 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "triggerName", value = "触发器组名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "expresstion", value = "时间规则表达式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "className", value = "任务实现类类名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "state", value = "运行状态(1:运行，2：停止，默认：1)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createTime", value = "添加时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "des", value = "描述", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "createName", value = "添加人", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmQuartzManager(PpmQuartzManager ppmQuartzManager) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmQuartzManagerService.updatePpmQuartzManager(ppmQuartzManager);
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
     * @param ppmQuartzManager 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmQuartzManager (PpmQuartzManager ppmQuartzManager) {
        ApiResult result =new ApiResult<>();
        int id = ppmQuartzManagerService.deletePpmQuartzManager(ppmQuartzManager);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

    /**
     * 启动或者停止
     * @param ppmQuartzManager 对象
     * @return ApiResult
     */
    @PostMapping(value = {"doEnable","doDisable"})
    @ApiOperation(value = "启动或者停止定时器")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "state", value = "运行状态(1:运行，2：停止，默认：2)", paramType = "query", dataType = "string", required = true)
    })
    public ApiResult doEnableOrDisable(PpmQuartzManager ppmQuartzManager) {
        ApiResult result =new ApiResult<>();
        String msg = "";
        // 启用
        if("1".equals(ppmQuartzManager.getState())) {
            msg = "【"+ ppmQuartzManager.getJobName() +"】启动成功";
            // 停止
        } else if("2".equals(ppmQuartzManager.getState())) {
            msg = "【"+ ppmQuartzManager.getJobName() +"】已停止";
        }
        ppmQuartzManagerService.updatePpmQuartzManager(ppmQuartzManager);
        result.success(msg);
        return result;
    }

    /**
     * 立即执行任务
     * @param dto 参数对象
     * @return  ApiResult
     */
    @PostMapping("executeImmediately")
    @ApiOperation(value = "立即执行任务")
    @ApiImplicitParam(name = "id", value = "编号", paramType = "query", dataType = "string", required = true)
    public ApiResult executeImmediately(PpmQuartzManagerDto dto) {
        ApiResult result =new ApiResult<>();
        boolean falg = ppmQuartzManagerService.executeImmediately(dto);
        if(falg) {
            result.success("执行成功");
        } else {
            result.failure("执行失败");
        }
        return result;
    }
}
