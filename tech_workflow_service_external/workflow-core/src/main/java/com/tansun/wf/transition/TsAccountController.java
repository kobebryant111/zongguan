package com.tansun.wf.transition;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.model.*;
import com.tansun.wf.activiti.service.WfAccountService;
import com.tansun.wf.extend.vo.WfResponseVO;
import org.camunda.bpm.engine.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ts/account")
public class TsAccountController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WfAccountService wfAccountService;

    @Autowired
    private HistoryService historyService;

    /**
     * 工作量统计
     */
    @RequestMapping("/workloadStatistics")
    public WfResponseVO workloadStatistics(@RequestBody WorkloadStatisticsQo qo){
        try {
            List<WorkloadStatisticsDto> res = wfAccountService.workloadStatistics(qo);
            return WfResponseVO.successResponse(res);
        }catch (Exception e){
            logger.error("工作量统计异常", e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    /**
     * 实例执行时间统计
     */
    @RequestMapping("/executionTime")
    public WfResponseVO executionTime(@RequestBody WfExecutionTimeQo qo){
        try {
            List<WfExecutionTimeDto> res = wfAccountService.executionTime(qo);
            return WfResponseVO.successResponse(res);
        }catch (Exception e){
            logger.error("实例执行时间统计异常", e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    /**
     * 活动实例时间统计
     */
    @RequestMapping("/activityTime")
    public WfResponseVO activityTime(@RequestBody WfActivityTimeQo qo){
        try {
            List<WfActivityTimeDto> res = wfAccountService.activityTime(qo);
            return WfResponseVO.successResponse(res);
        }catch (Exception e){
            logger.error("活动实例统计异常", e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    /**
     * 实例执行状态统计
     */
    @RequestMapping("/executionStat")
    public WfResponseVO executionStat(@RequestBody WfExecutionStatQo qo){
        try {
            List<WfExecutionStatDto> res = wfAccountService.executionStat(qo);
            return WfResponseVO.successResponse(res);
        }catch (Exception e){
            logger.error("实例执行状态统计异常", e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    /**
     * 超时率统计
     */
    @RequestMapping("/timeoutRate")
    public WfResponseVO timeoutRate(@RequestBody WfTimeoutRateQo qo){
        try {
            List<WfTimeoutRateDto> res = wfAccountService.timeoutRate(qo);
            return WfResponseVO.successResponse(res);
        }catch (Exception e){
            logger.error("实例/任务超时率统计异常", e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    /**
     * 超时时长统计（分页）
     */
    @RequestMapping("timeoutDuration")
    public WfResponseVO timeoutDuration(@RequestBody WfTimeoutDurationQo qo){
        try {
            PageInfo<WfTimeoutDurationDto> res = wfAccountService.timeoutDuration(qo);
            return WfResponseVO.successResponse(res);
        }catch (Exception e){
            logger.error("实例/任务超时时间统计异常", e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    @RequestMapping("backTask")
    public WfResponseVO backTask(@RequestBody WfBackTaskQo qo){
        try {
            List<WfBackTaskDto> res = wfAccountService.backTask(qo);
            return WfResponseVO.successResponse(res);
        }catch (Exception e){
            logger.error("实例/任务退回率统计异常", e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }

    @RequestMapping("proVersion")
    public WfResponseVO proVersion(@RequestBody WfProVersionQo qo){
        try {
            PageInfo<WfProVersionDto> res = wfAccountService.proVersion(qo);
            return WfResponseVO.successResponse(res);
        }catch (Exception e){
            logger.error("流程版版本统计异常", e);
            return WfResponseVO.errorResponse(e.getMessage());
        }
    }
}
