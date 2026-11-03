package com.tansun.wf.platform.web;

import com.tansun.wf.activiti.service.WorkflowProcessInstanceService;
import com.tansun.wf.extend.vo.WfResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 接收节点调用接口
 */
@RestController
public class ReceiveSaveRestController {

    @Autowired
    WorkflowProcessInstanceService workflowProcessInstanceService;

    @RequestMapping(
            value = {"/receive/message/execute"},
            method = {RequestMethod.POST}
    )
    @ResponseStatus(HttpStatus.OK)
    public WfResponseVO messageController(@RequestParam String procInstId, @RequestParam String messageName){
        WfResponseVO wfResponseVO = new WfResponseVO();
        wfResponseVO.setStatus(200);
        wfResponseVO.setDescription("执行成功");
        try {
            workflowProcessInstanceService.messageConfirmProcessInstanceId(procInstId,messageName);
        } catch (Exception e) {
            wfResponseVO.setStatus(500);
            wfResponseVO.setDescription("接口执行失败，错误信息为:"+e.getMessage());
            e.printStackTrace();
        }
        return  wfResponseVO;
    }
}
