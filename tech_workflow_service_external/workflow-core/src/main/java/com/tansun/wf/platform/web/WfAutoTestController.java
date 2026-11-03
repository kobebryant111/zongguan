package com.tansun.wf.platform.web;

import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.mocktest.service.WfAutoTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/wf/autoTest")
public class WfAutoTestController {

    @Autowired
    WfAutoTestService wfAutoTestService;

    @ResponseBody
    @RequestMapping(value = "/start")
    public WfResponseVO start(String processDefKey){
        wfAutoTestService.createExecuteTask(processDefKey);
        return WfResponseVO.successResponse("success");
    }





}
