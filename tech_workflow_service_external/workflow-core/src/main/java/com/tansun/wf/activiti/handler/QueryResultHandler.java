package com.tansun.wf.activiti.handler;

import java.util.Map;

/**
 * 查询结果处理器：
 * 处理查询结果一条record
 *
 */
public interface QueryResultHandler {
	/**
	 * 目的:将一条记录需要的属性转换为Map<String,Object>类型
	 * @param
	 * @return map
	 */
	public Map<String, Object> handlerRecord(Object record);
}
