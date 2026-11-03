package com.tansun.wf.mocktest.service;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tansun.wf.mocktest.thread.ExecuteProcessTaskThread;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import com.tansun.workflow.service.WorkFlowClientService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 自动化模拟测试
 */
@Service
public class WfAutoTestService implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(getClass());


    protected ConcurrentHashMap tempIds = new ConcurrentHashMap(1000);

    public ExecutorService executorService = null;

    private ApplicationContext act;

    @PostConstruct
    private void initThreadPool(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("-execute-process-runner-%d").build();
        this.executorService = new ThreadPoolExecutor(50,100,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(500),namedThreadFactory);
    }

    @Autowired
    WorkFlowClientService workFlowClientService;

    public String startProcess(ProcessInfo processInfo){
//        processInfo.setUserId("");
        processInfo.setProcessDefineKey("cesaqw");
        HashMap<String, Object> mPara = new HashMap<>();
        mPara.put("a", 25);
        processInfo.setmPara(mPara);
//        processInfo.setFormData();
//        processInfo.setTaskExpandData();
//        processInfo.setTenantId();
//        processInfo.setVariables();
//        processInfo.setStartDeptId();
//        processInfo.setStartOrgId();
//        processInfo.setStartParentOrgId();
//        processInfo.setStartRoleId();
//        processInfo.setStartRoleIdList();
//        processInfo.setStartTimeSort();
        try {
            ProcessBean processBean = workFlowClientService.startProcess(processInfo);
            return processBean.getProcessInstanceId();



        } catch (Exception e) {
            e.printStackTrace();
        }
        return "a";
    }

    /**
     * 自动提交
     * @throws Exception
     */
    public void submit(TaskBean taskBean ){

        try {
            TaskBean taskList = workFlowClientService.submitTask(taskBean);
            System.out.println(JSON.toJSONString(taskBean));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }

    }

    public void createExecuteTask(String processDefKey) {
        ExecuteProcessTaskThread executeProcessTaskThread =new ExecuteProcessTaskThread(act,processDefKey);
        this.executorService.submit(executeProcessTaskThread);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.act = applicationContext;
    }
}
