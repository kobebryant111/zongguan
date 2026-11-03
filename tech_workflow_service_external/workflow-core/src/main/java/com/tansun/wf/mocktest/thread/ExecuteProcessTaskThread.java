package com.tansun.wf.mocktest.thread;

import com.alibaba.fastjson.JSON;
import com.tansun.workflow.model.*;
import com.tansun.workflow.service.WorkFlowClientService;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 自动执行流程线程类
 */
public class ExecuteProcessTaskThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(ExecuteProcessTaskThread.class);

    private ApplicationContext act;

    private WorkFlowClientService workFlowClientService;

    private HistoryService historyService;

    private int MAX_EXECUTE_TASK_COUNT = 100;

    private boolean isComplete = false;

    private String processInstanceId;

    private String processDefKey;

    private Map nextTask;




    public ExecuteProcessTaskThread(ApplicationContext act,String processDefKey) {
        super("ExecuteProcessTaskThread");
        this.processDefKey = processDefKey;
        this.act = act;
        this.workFlowClientService = this.act.getBean(WorkFlowClientService.class);
        this.historyService = this.act.getBean(HistoryService.class);
    }

    @Override
    public void run() {
        int executeTaskCount = 0;

        AutoExecuteProcessContext autoExecuteProcessContext = new AutoExecuteProcessContext();
        autoExecuteProcessContext.setProcessDefKey(processDefKey);
        autoExecuteProcessContext.setWorkFlowClientService(workFlowClientService);
        autoExecuteProcessContext.setApplicationContext(act);


        //从脚本中创建流程启动对象
        ProcessInfo processInfo = createProcessInfo(autoExecuteProcessContext);
        processInfo.setProcessDefineKey(processDefKey);

        //启动流程
        try {
            ProcessBean processBean = workFlowClientService.startProcess(processInfo);
            if (BaseBean.isSuccess(processBean)) {
                this.processInstanceId = processBean.getProcessInstanceId();
                logger.info("{}, processInstanceId: {}",processBean.getRetMsg(),this.processInstanceId);
            }else {
                logger.error("startProcess failed: {}",processBean.getRetMsg());
                return;
            }
        } catch (Exception e) {
            logger.error("startProcess failed: {}",e.getMessage(),e);
            throw new RuntimeException(e);
        }

        autoExecuteProcessContext.setProcessInstanceId(this.processInstanceId);

        //查询下一步待办任务
        getNextTask(autoExecuteProcessContext);

        //循环执行下一步任务
        while (nextTask != null && !this.isComplete && executeTaskCount < MAX_EXECUTE_TASK_COUNT) {
            logger.info("next task detail : {}", JSON.toJSONString(nextTask));
            String taskId = MapUtils.getString(nextTask, "taskId");
            Object powerUsers = MapUtils.getObject(nextTask, "powerUsers");
            String userId = null;
            if (powerUsers != null && powerUsers instanceof List && ((List<?>) powerUsers).size() >0) {
                userId = (String) ((List<?>) powerUsers).get(0);
            }else {
                userId = "submitByTestProcess";
            }
            TaskBean taskBean = new TaskBean();
            taskBean.setUserId(userId);
            taskBean.setTaskId(taskId);
            autoExecuteProcessContext.setNextTaskParams(taskBean);
            autoExecuteProcessContext.setNextTask(nextTask);
            TaskBean executeResult = executeNextTask(autoExecuteProcessContext);
            if (BaseBean.isSuccess(executeResult)) {

            }else {
                logger.error("executeNextTask failed: {}",executeResult.getRetMsg());
                return;
            }

            executeTaskCount++;

            Thread.yield();

            getNextTask(autoExecuteProcessContext);
        }

        if (executeTaskCount >=MAX_EXECUTE_TASK_COUNT){
            logger.error("execute process failed: 流程进入死循环,已经提交{}次，无法继续往下执行！",MAX_EXECUTE_TASK_COUNT);
        }else {
            logger.info("execute process success, processInstanceId: {}", this.processInstanceId);
        }


    }

    /**
     * 查询下一步任务
     * @param autoExecuteProcessContext
     */
    private void getNextTask(AutoExecuteProcessContext autoExecuteProcessContext) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setProcessInstanceId(this.processInstanceId);

        List<Map> nextTaskList = null;
        try {
            TaskBean taskListBean = workFlowClientService.findTaskList(taskInfo);
            if (BaseBean.isSuccess(taskListBean)) {
                nextTaskList = taskListBean.getTaskList();
                logger.info("{},processInstanceId: {}",taskListBean.getRetMsg(),this.processInstanceId);
            }else {
                logger.error("findNextTaskList failed: {}",taskListBean.getRetMsg());
                throw new RuntimeException(taskListBean.getRetMsg());
            }
        } catch (Exception e) {
            logger.error("findNextTaskList failed: {}",e.getMessage(),e);
            throw new RuntimeException(e);
        }

        if (CollectionUtils.isNotEmpty(nextTaskList)) {
            nextTask = nextTaskList.get(0);
        }else {
            HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            this.isComplete = instance.getEndTime() != null;
            this.nextTask = null;
            autoExecuteProcessContext.setNextTask(null);
        }

        if (nextTask == null ){
            if (isComplete){
                logger.info("execute process success, processInstanceId: {}", this.processInstanceId);
            }else {
                throw new RuntimeException("execute process failed: 流程测试失败，找不到可执行的任务");
            }
        }


    }

    private TaskBean executeNextTask(AutoExecuteProcessContext autoExecuteProcessContext) {
        GroovyClassLoader loader = new GroovyClassLoader();
        Class fileCreator = null;
        try {
            fileCreator = loader.parseClass(new File("workflow-core\\src\\main\\java\\com\\tansun\\wf\\mocktest\\thread\\DemoExecuteProcessScript.groovy"));
            GroovyObject object = (GroovyObject) fileCreator.newInstance();
            return (TaskBean) object.invokeMethod("executeNextTask", autoExecuteProcessContext);
        } catch (Exception e) {
            logger.error("执行脚本失败：{}",e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }


    private ProcessInfo createProcessInfo(AutoExecuteProcessContext autoExecuteProcessContext) {
        GroovyClassLoader loader = new GroovyClassLoader();
        Class fileCreator = null;
        try {
            fileCreator = loader.parseClass(new File("workflow-core\\src\\main\\java\\com\\tansun\\wf\\mocktest\\thread\\DemoExecuteProcessScript.groovy"));
            GroovyObject object = (GroovyObject) fileCreator.newInstance();
            return (ProcessInfo) object.invokeMethod("createProcessInfo", autoExecuteProcessContext);
        } catch (Exception e) {
            logger.error("执行脚本失败：{}",e.getMessage(),e);
            throw new RuntimeException(e);
        }

    }


}
