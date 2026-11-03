package com.tansun.wf.decision.service.impl;

import com.tansun.wf.decision.service.InterfaceService;
import com.tansun.wf.decision.util.WorkflowHttpUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 挡板测试数据
 */
@Service("testService")
public class TestServiceImpl implements InterfaceService {

    @Override
    public Map<String, Object> syncInvoke(String url, Map<String, Object> params) {
        try {
            WorkflowHttpUtil.httpPost(url,params,Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
