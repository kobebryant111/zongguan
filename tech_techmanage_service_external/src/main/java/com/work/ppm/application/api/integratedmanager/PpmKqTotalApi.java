package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqTotalDto;
import com.work.ppm.application.model.integratedmanager.OutOverTimeMonthData;
import com.work.ppm.application.model.integratedmanager.ext.OutOvertimeOrderDataInfo;
import com.work.ppm.application.services.integratedmanager.IPpmKqTotalService;
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
 * 类描述
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKqTotalApi"},description="外包考勤月汇总")
@RestController
@RequestMapping("ppmKqTotal")
@Slf4j
public class PpmKqTotalApi {
    /**
     * 类接口描述
     */
    @Autowired
    private IPpmKqTotalService ppmKqTotalService;

    /**
     * 分页
     * @param ppmKqTotalDto 对象
     * @param pager 分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value ="分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tmonth", value = "月", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mworktime", value = "标准时数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mworkday", value = "标准天数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "worktime", value = "工作时数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workday", value = "工作天数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "unworkday", value = "旷工天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "lvworkday", value = "请假天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "latemin", value = "迟到分钟", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "earmin", value = "早退分钟", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "latecs", value = "迟到次数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "earcs", value = "早退次数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "overtime", value = "加班时数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "overtimeday", value = "加班天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "othertime", value = "其他", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isfz", value = "是否封帐", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "二级部门经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tiaoxiutime", value = "调休时间", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "outsourcetype", value = "外包方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "cfmuserid", value = "项目负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "所属部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "wjdkcs", value = "忘记打卡次数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "xiujiatime", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "lrrgh", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ysynj", value = "", dataType = "double", paramType = "query"),
			
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPager(PpmKqTotalDto ppmKqTotalDto, Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        ppmKqTotalService.queryPager(ppmKqTotalDto,pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 查询详情
     * @param ppmKqTotalDto 对象
     * @return ApiResult 对象
     */
    @GetMapping(value = "queryDetail")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tmonth", value = "月", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mworktime", value = "标准时数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mworkday", value = "标准天数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "worktime", value = "工作时数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workday", value = "工作天数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "unworkday", value = "旷工天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "lvworkday", value = "请假天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "latemin", value = "迟到分钟", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "earmin", value = "早退分钟", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "latecs", value = "迟到次数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "earcs", value = "早退次数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "overtime", value = "加班时数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "overtimeday", value = "加班天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "othertime", value = "其他", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isfz", value = "是否封帐", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "二级部门经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tiaoxiutime", value = "调休时间", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "outsourcetype", value = "外包方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "cfmuserid", value = "项目负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "所属部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "wjdkcs", value = "忘记打卡次数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "xiujiatime", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "lrrgh", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ysynj", value = "", dataType = "double", paramType = "query"),
			
    })
    public ApiResult<PpmKqTotalDto> queryDetail(PpmKqTotalDto ppmKqTotalDto) {
        ApiResult<PpmKqTotalDto> result =new ApiResult<>();
        PpmKqTotalDto item = ppmKqTotalService.queryDetail(ppmKqTotalDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 更新
     * @param ppmKqTotalDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tyear", value = "年", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tmonth", value = "月", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mworktime", value = "标准时数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mworkday", value = "标准天数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "worktime", value = "工作时数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "workday", value = "工作天数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "unworkday", value = "旷工天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "lvworkday", value = "请假天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "latemin", value = "迟到分钟", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "earmin", value = "早退分钟", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "latecs", value = "迟到次数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "earcs", value = "早退次数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "overtime", value = "加班时数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "overtimeday", value = "加班天数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "othertime", value = "其他", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isfz", value = "是否封帐", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "二级部门经理", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "tiaoxiutime", value = "调休时间", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "outsourcetype", value = "外包方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "cfmuserid", value = "项目负责人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "所属部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "wjdkcs", value = "忘记打卡次数", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "xiujiatime", value = "", dataType = "double", paramType = "query"),
			@ApiImplicitParam(name = "lrrgh", value = "", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ysynj", value = "", dataType = "double", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmKqTotal(PpmKqTotalDto ppmKqTotalDto) {
        ApiResult<Object> result =new ApiResult<>();
        int id = ppmKqTotalService.updatePpmKqTotal(ppmKqTotalDto);
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
     * @param ppmKqTotalDto 对象
     * @return ApiResult
     */
    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seid", value = "编号", dataType = "Long", paramType = "query"),

    })
    public ApiResult deletePpmKqTotal (PpmKqTotalDto ppmKqTotalDto) {
        ApiResult result =new ApiResult<>();
        int id = ppmKqTotalService.deletePpmKqTotal(ppmKqTotalDto);
        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.DELETE_FAIL);
            result.setMessage("删除失败");
        }
        return result;
    }

	/**
	 * 查询外包加班月度统计数据
	 * @param outDataDto 对象
	 * @return ApiResult 对象
	 */
	@GetMapping(value = "getOutOvertimeMonthData")
	@ApiOperation(value = "查询外包加班月度统计数据", notes = "查询外包加班月度统计数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
	})
	public ApiResult<OutOverTimeMonthData> getOutOvertimeMonthData(OutDataDto outDataDto) {
		ApiResult<OutOverTimeMonthData> result =new ApiResult<>();
		OutOverTimeMonthData item = ppmKqTotalService.getOutOvertimeMonthData(outDataDto);
		result.setResults(item);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}

	/**
	 * 查询外包考核月度比例数据
	 * @param outDataDto 对象
	 * @return ApiResult 对象
	 */
	@GetMapping(value = "getOutEvalOrderData")
	@ApiOperation(value = "查询外包考核月度比例数据", notes = "查询外包考核月度比例数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "jobid", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "deptno", value = "部门", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "year", value = "年份", dataType = "string", paramType = "query"),
	})
	public ApiResult<OutOvertimeOrderDataInfo> getOutEvalOrderData(OutDataDto outDataDto) {
		ApiResult<OutOvertimeOrderDataInfo> result =new ApiResult<>();
		OutOvertimeOrderDataInfo item = ppmKqTotalService.getOutOvertimeOrderData(outDataDto);
		result.setResults(item);
		result.setStatus(ApiCode.SUCCESS);
		return result;
	}
}
