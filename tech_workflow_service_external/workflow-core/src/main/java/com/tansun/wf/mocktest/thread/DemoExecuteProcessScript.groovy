package com.tansun.wf.mocktest.thread

import com.tansun.workflow.model.ProcessInfo
import com.tansun.workflow.model.TaskBean
import com.tansun.workflow.service.WorkFlowClientService

class DemoExecuteProcessScript {

    /**
     * 创建流程启动参数
     * @param context
     * @return
     */
    public ProcessInfo createProcessInfo(AutoExecuteProcessContext context) {
        def info = new ProcessInfo();
        Map<String, Object> mPara = new HashMap<>()
        mPara.put("a", 50)
        mPara.put("isPower", "Y")
        mPara.put("isDept", "N")
        mPara.put("isFuhz", "Y")
        info.setmPara(mPara)
        return info
    }

    /**
     * 执行下一步任务
     * @param context
     * @return
     */
    public TaskBean executeNextTask(AutoExecuteProcessContext context){
        def task = context.getNextTask();
        def service = context.getWorkFlowClientService();
        TaskBean params = context.getNextTaskParams();

        if (task.get('activityId') == 'UserTask_zUowLtPU'){
            params.setNextActivityId('UserTask_YauUqfix')
            return service.backTask(params)
        }else {
            return service.submitTask(params)
        }








    }


}
