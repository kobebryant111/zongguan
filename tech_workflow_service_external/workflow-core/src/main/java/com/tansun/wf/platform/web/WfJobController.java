package com.tansun.wf.platform.web;


import com.tansun.wf.activiti.service.WorkflowService;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.model.WfJobEntity;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/wf/wfJob")
public class WfJobController {

    @Autowired
    private WorkflowService workflowService;

    @RequestMapping("/list")
    @ResponseBody
    public WfResponseVO query(@RequestParam Map<String,Object> reqMap) {
        Map<String, Object> errorServiceTask = workflowService.findErrorServiceTask(reqMap);
        return WfResponseVO.successResponse(errorServiceTask);
    }


    @RequestMapping("/retryServiceTask")
    @ResponseBody
    public WfResponseVO retryServiceTask(@RequestBody TaskInfo taskInfo){
        TaskBean taskBean = workflowService.retryServiceTask(taskInfo);
        if (taskBean.getRetCode().equals("0")){
            return WfResponseVO.successResponse(taskBean);
        }else{
            return WfResponseVO.errorResponse(taskBean);
        }

    }
}
