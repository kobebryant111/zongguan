package com.tansun.workflow.api;

import com.tansun.workflow.model.PostInfo;

import java.util.List;
import java.util.Map;

/**
 * 参与者自定义规则逻辑接口
 */
public interface IWorkflowCustomUserService {

    List<PostInfo> execute(Map<String,Object> param);
}