package com.tansun.wf.activiti.service;

import com.alibaba.fastjson.JSON;
import com.tansun.workflow.api.IWorkflowController;
import com.tansun.workflow.model.TaskInfo;
import com.tansun.workflow.utils.RestTemplateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author lijiang
 * @create 2019-12-11 15:15
 */
@Component
public class HttpWorkController implements IWorkflowController {

    private String address;

    private List<String> methods;

    public static final String CREATE_TASK_EVENT = "createTaskEvent";
    public static final String PROCESS_COMPLETED_TRANS = "processCompletedTrans";
    public static final String AFTER_VOTING_COMPLETED = "afterVotingCompleted";

    public static Logger logger = LoggerFactory.getLogger(IWorkflowController.class.toString());

    @Override
    public Map<String, Object> beforeStartProcessTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> afterStartProcessTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> beforeSubmitTaskTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> afterSubmitTaskTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> beforeBackPreTaskTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> afterBackPreTaskTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> beforeRebackTaskTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> afterRebackTaskTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> beforeSuspendProcessTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> afterSuspendProcessTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> beforeActivateProcessTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> afterActivateProcessTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> beforeTransferTaskTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> afterTransferTaskTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> beforeBackFirstTaskTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> afterBackFirstTaskTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> beforeBackTaskTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> afterBackTaskTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> beforeTerminateProcessTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Object> afterTerminateProcessTrans(Map<String, Object> map) {
        return null;
    }

    @Override
    public void processCompletedTrans(Map<String, Object> map, String beanName) {
        try {
            doPost(address + "/processCompletedTrans", map,null);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createTaskEvent(TaskInfo taskInfo) {
        try {
            doPost(address + "/createTaskEvent", taskInfo,null);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterVotingCompleted(Map<String, Object> map) {
        try {
            doPost(address + "/afterVotingCompleted", map,null);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 通用远程调用请求
     * @return
     */
    private <T> T doPost(String url, Object params, Class<T> resultType) throws Exception {
        String paramsJson = JSON.toJSONString(params);
        logger.info("url is :" + url);
        logger.info("mehtod: {}, invoke params is :" + paramsJson);
        String result = RestTemplateUtils.post(url, paramsJson);
        logger.info("result content is :" + result);
        T t = JSON.parseObject(result, resultType);
        return t;
    }    

    public  <T> T  restPost(String  url,Object params,Class<T>  resultType) throws  Exception{
    	   return  this.doPost(url, params, resultType);
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }
}
