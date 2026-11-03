package com.tansun.wf.activiti.service;

import com.alibaba.fastjson.JSON;
import com.tansun.workflow.api.IWorkflowCustomUserService;
import com.tansun.workflow.model.PostInfo;
import com.tansun.workflow.utils.RestTemplateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HttpCustomUserService implements IWorkflowCustomUserService {
    @Override
    public List<PostInfo> execute(Map<String, Object> param) {
        return null;
    }

    public static Logger logger = LoggerFactory.getLogger(IWorkflowCustomUserService.class.toString());

    /**
     * 通用远程调用请求
     *
     * @return
     */
    public List<PostInfo> execute(String url, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<>();
        }
        String paramsJson = JSON.toJSONString(param);
        logger.info("url is :" + url);
        logger.info("mehtod: {}, invoke params is :" + paramsJson);

        String result = RestTemplateUtils.post(url, paramsJson);
        logger.info("result content is :" + result);
        return JSON.parseArray(result, PostInfo.class);

    }
}