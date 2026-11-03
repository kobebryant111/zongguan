package com.tansun.wf.platform.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tansun.wf.platform.service.BpmnModelService;
import com.tansun.workflow.model.ProcessInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijiang
 * @create 2021-03-23 11:00
 */
@RestController
public class ModelEditorController {


    @Autowired
    private  BpmnModelService  bpmnModelService;

    @RequestMapping(value="/model/json", method = RequestMethod.POST, produces = "application/json")
    public ObjectNode getEditorJson(@RequestBody ProcessInfo processInfo) {
       return  bpmnModelService.findDiagram(processInfo);
    }
}
