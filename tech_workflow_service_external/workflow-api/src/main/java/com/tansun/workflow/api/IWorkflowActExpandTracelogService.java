package com.tansun.workflow.api;

import com.tansun.workflow.model.ActExpandTracelog;

/**
 * 扩展的日志轨迹接口类，会追踪数据采集，规则调用的日志
 */
public interface IWorkflowActExpandTracelogService {

    public void logTrace(ActExpandTracelog log);
}
