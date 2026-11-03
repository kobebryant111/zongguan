package com.tansun.wf.platform.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tansun.wf.platform.service.BaseProcessDefinitionDiagramLayoutResource;

@RestController
public class ProcessDefinitionDiagramController {
     
    @Autowired
    private  BaseProcessDefinitionDiagramLayoutResource  definitionDiagramLayoutResource;
	
    @RequestMapping(value="/process-definition/{processDefinitionId}/diagram-layout", method = RequestMethod.GET, produces = "application/json")
	public  ObjectNode  getDiagram(@PathVariable String processDefinitionId) {
    	
    	return  definitionDiagramLayoutResource.getDiagramNode(null, processDefinitionId);	
    	//return  bpmnModelService.findDiagram(processDefinitionId);
	}
    
//    @RequestMapping(value = "/diagram-viewer/{processInstanceId}/highlights", method = RequestMethod.GET, produces = "application/json")
//    public ObjectNode getHighlighted(@PathVariable String processInstanceId) {
//    	return this.bpmnModelService.getHighlighted(processInstanceId);
//    	
//    }
    
    

}
