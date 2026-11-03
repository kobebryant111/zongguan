package com.tansun.wf.decision.service;

import java.util.Map;

/**
 * 访问外部数据统一接口
 */
public interface InterfaceService {

    public Map<String,Object> syncInvoke(String url,Map<String,Object> params);

}
