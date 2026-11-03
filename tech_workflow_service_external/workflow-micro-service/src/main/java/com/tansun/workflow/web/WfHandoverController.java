package com.tansun.workflow.web;

import com.tansun.wf.activiti.model.WfHandoverQo;
import com.tansun.wf.activiti.model.WfHandoverQueryQo;
import com.tansun.wf.activiti.service.WorkflowProcessInstanceService;
import com.tansun.wf.extend.vo.WfResponseVO;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wf/handover")
public class WfHandoverController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TaskService taskService;

    @Autowired
    private WorkflowProcessInstanceService workflowProcessInstanceService;

    @RequestMapping("/queryLoadItem")
    public WfResponseVO queryLoadItem(@RequestBody WfHandoverQueryQo qo) {
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(qo.getLoginUserName()).list();
        return WfResponseVO.successResponse(list.size());
    }

    @RequestMapping("/queryDealOtherItem")
    public WfResponseVO queryDealOtherItem(@RequestBody WfHandoverQueryQo qo){
        //TODO 待办他人的任务如何查询暂时未知
        return WfResponseVO.successResponse(0);
    }

    @RequestMapping("/queryEntrustItem")
    public WfResponseVO queryEntrustItem(@RequestBody WfHandoverQueryQo qo){
        //TODO 委托他人的任务如何查询暂时未知
        return WfResponseVO.successResponse(0);
    }

    @RequestMapping("/queryLoadReceiveItem")
    public WfResponseVO queryLoadReceiveItem(@RequestBody WfHandoverQueryQo qo){
        List<Task> list = taskService.createTaskQuery()
                .taskCandidateUser(qo.getLoginUserName()).list();
        return WfResponseVO.successResponse(list.size());
    }

    @RequestMapping("/queryWorkflowDefItem")
    public WfResponseVO queryWorkflowDefItem(@RequestBody WfHandoverQueryQo qo){
        //TODO 需要交接的流程定义如何查询暂时未知
        return WfResponseVO.successResponse(0);
    }

    @RequestMapping("/queryAgencyRelationshipItem")
    public WfResponseVO queryAgencyRelationshipItem(@RequestBody WfHandoverQueryQo qo){
        //TODO 需要删除的代理关系如何查询暂时未知
        return WfResponseVO.successResponse(0);
    }

    @RequestMapping("/handoverLoadItem")
    public WfResponseVO handoverLoadItem(@RequestBody WfHandoverQo qo) {
        int cnt = workflowProcessInstanceService.handoverLoadItem(qo.getTransferor(), qo.getReceiver());
        return WfResponseVO.successResponse(String.format("成功交接[%d]条任务", cnt));
    }

    @RequestMapping("/handoverDealOtherItem")
    public WfResponseVO handoverDealOtherItem(@RequestBody WfHandoverQo qo){
        //TODO 待办他人的任务如何交接暂时未知
        return WfResponseVO.successResponse("交接成功");
    }

    @RequestMapping("/handoverEntrustItem")
    public WfResponseVO handoverEntrustItem(@RequestBody WfHandoverQo qo){
        //TODO 委托他人的任务如何交接暂时未知
        return WfResponseVO.successResponse("交接成功");
    }

    @RequestMapping("/handoverLoadReceiveItem")
    public WfResponseVO handoverLoadReceiveItem(@RequestBody WfHandoverQo qo){
        int cnt = workflowProcessInstanceService.handoverLoadReceiveItem(qo.getTransferor(), qo.getReceiver());
        return WfResponseVO.successResponse(String.format("成功交接[%d]条任务", cnt));
    }

    @RequestMapping("/handoverWorkflowDefItem")
    public WfResponseVO handoverWorkflowDefItem(@RequestBody WfHandoverQo qo){
        //TODO 需要交接的流程定义如何交接暂时未知
        return WfResponseVO.successResponse("交接成功");
    }

    @RequestMapping("/handoverAgencyRelationshipItem")
    public WfResponseVO handoverAgencyRelationshipItem(@RequestBody WfHandoverQo qo){
        //TODO 需要删除的代理关系如何删除暂时未知
        return WfResponseVO.successResponse("删除成功");
    }
}
