package com.work.ppm.application.api.basismanager;

import com.work.ppm.application.model.basismanager.PpmKqDutyPlan;
import com.work.ppm.application.services.basismanager.IPpmKqDutyPlanService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 考勤表
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmKqDutyPlanApi"},description="考勤表")
@RestController
@RequestMapping("ppmKqDutyPlan")
@Slf4j
public class PpmKqDutyPlanApi {
    /**
     * 考勤表
     */
    @Autowired
    private IPpmKqDutyPlanService ppmKqDutyPlanService;

    /**
     * 查询考勤类型集合
     * @return ApiResult<List<PpmKqDutyPlan>>
     */
    @GetMapping(value = "queryKqTypeList")
    @ApiOperation(value ="查询考勤类型集合", notes = "查询考勤类型集合")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "主键", dataType = "integer", paramType = "query"),
//			@ApiImplicitParam(name = "kqType", value = "考勤类型A,B,C,D,E,F,G类", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "code", value = "周期，1:周一，2:周二，3:周三，4:周四，5:周五，6:周六，7:周日", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "name", value = "中文描述", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "createDate", value = "创建日期", dataType = "date", paramType = "query"),
//			@ApiImplicitParam(name = "startDate1", value = "上班开始时间1", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "endDate1", value = "下班结束时间1", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "startDate2", value = "上班开始时间2", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "endDate2", value = "下班结束时间2", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "overtime", value = "加班开始时间", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "overHour", value = "加班小时", dataType = "integer", paramType = "query"),
//			@ApiImplicitParam(name = "workHour", value = "工作小时", dataType = "integer", paramType = "query"),
//			@ApiImplicitParam(name = "isOvettime", value = "是否计算加班（周末和平时加班都会计算），1:是 2:否", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "isXiujia", value = "是否存在休假（每天都工作），1:是 2:否", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "isOutdate", value = "是否夸日计算，1:是 2:否", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "isFract", value = "是否跨段，1:是 2:否", dataType = "string", paramType = "query"),
//			@ApiImplicitParam(name = "isLunban", value = "是否轮班", dataType = "string", paramType = "query"),
//
//
//    })
    public ApiResult<List<PpmKqDutyPlan>> queryKqTypeList() {
        ApiResult<List<PpmKqDutyPlan>> result =new ApiResult<>();
        List<PpmKqDutyPlan> list = ppmKqDutyPlanService.getKqTypeList();
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 根据考勤类型查考勤时间
     * @param ppmKqDutyPlan 对象
     * @return ApiResult<PpmKqDutyPlan>
     */
    @GetMapping("queryKqTime")
    @ApiOperation(value = "根据考勤类型查考勤时间")
    @ApiImplicitParam(name = "kqType", value = "考勤类型", required = true,dataType = "string", paramType = "query")
    public ApiResult<PpmKqDutyPlan> queryKqTime(PpmKqDutyPlan ppmKqDutyPlan) {
        ApiResult<PpmKqDutyPlan> result = new ApiResult<>();
        PpmKqDutyPlan item = ppmKqDutyPlanService.getKqTime(ppmKqDutyPlan.getKqType());
        result.setResults(item);
        result.success("获取成功");
        return result;
    }
}
