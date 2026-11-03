package com.tansun.wf.platform.mapper;

import com.tansun.wf.activiti.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工作量统计MAPPER
 */
public interface WfAccountMapper {

    /** 创建数统计 */
    long workloadStatisticsCreate(WorkloadStatisticsTypesVo typesVo);

    /** 处理数统计 */
    long workloadStatisticsHandle(WorkloadStatisticsTypesVo typesVo);

    /** 创建数统计 */
    List<WorkloadStatisticsDto> workloadStatisticsCreateByDate(WorkloadStatisticsTypesVo typesVo);

    /** 处理数统计 */
    List<WorkloadStatisticsDto> workloadStatisticsHandleByDate(WorkloadStatisticsTypesVo typesVo);

    /** 执行时间统计 */
    List<WfExecutionTimeDto> executionTime(WfExecutionTimeQo qo);

    /** 执行时间统计 */
    List<WfExecutionTimeDto> executionTimeByVersoin(WfExecutionTimeQo qo);

    /** 执行时间统计 */
    List<WfExecutionTimeDto> executionTimeByMonth(WfExecutionTimeQo qo);

    /** 获取流程定义所有版本信息 */
    List<WfVersionVo> queryWfVersionInfo(@Param("key") String key);

    /** 获取活动时间执行时间统计 */
    List<WfActivityTimeDto> activityTime(WfActivityTimeQo qo);

    /** 获取活动时间执行时间统计 */
    List<WfActivityTimeDto> activityTimeByMonth(WfActivityTimeQo qo);

    /** 流程状态统计 */
    List<WfExecutionStatDto> executionStat(WfExecutionStatQo qo);

    /** 流程状态统计 */
    List<WfExecutionStatDto> executionStatByVersoin(WfExecutionStatQo qo);

    /** 流程状态统计 */
    List<WfExecutionStatDto> executionStatByMonth(WfExecutionStatQo qo);

    /** 实例超时率统计 */
    List<WfTimeoutRateDto> instTimeoutRate(WfTimeoutRateQo qo);

    /** 实例活动超时率统计 */
    List<WfTimeoutRateDto> taskTimeoutRate(WfTimeoutRateQo qo);

    /** 实例超时率统计 */
    List<WfTimeoutRateDto> instTimeoutRateVersion(WfTimeoutRateQo qo);

    /** 实例活动超时率统计 */
    List<WfTimeoutRateDto> taskTimeoutRateVersion(WfTimeoutRateQo qo);

    /** 实例超时率统计 */
    List<WfTimeoutRateDto> instTimeoutRateMonth(WfTimeoutRateQo qo);

    /** 实例活动超时率统计 */
    List<WfTimeoutRateDto> taskTimeoutRateMonth(WfTimeoutRateQo qo);

    /** 实例超时时间统计 */
    List<WfTimeoutDurationDto> queryTimeoutDuration(WfTimeoutDurationQo qo);

    /** 任务超时时间统计 */
    List<WfTimeoutDurationDto> queryActTimeoutDuration(WfTimeoutDurationQo qo);

    /** 实例超时率统计 */
    List<WfBackTaskDto> instBackTask(WfBackTaskQo qo);

    /** 活动超时率统计 */
    List<WfBackTaskDto> actBackTask(WfBackTaskQo qo);

    /** 流程版本统计 */
    List<WfProVersionDto> proVersion(WfProVersionQo qo);
}
