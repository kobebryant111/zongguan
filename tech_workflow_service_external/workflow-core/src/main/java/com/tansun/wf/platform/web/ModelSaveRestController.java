package com.tansun.wf.platform.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tansun.wf.platform.service.BpmnModelService;

/**
 * @author lijiang
 * @create 2019-09-09 14:25
 */
// camunda-TODO
@RestController
public class ModelSaveRestController {

    Logger logger = LoggerFactory.getLogger(ModelSaveRestController.class);

    @Autowired
    private  BpmnModelService   bpmnModelService;

    @RequestMapping(
            value = {"/model/{modelId}/save"},
            method = {RequestMethod.POST}
    )
    @ResponseStatus(HttpStatus.OK)
    public void saveModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values) throws Exception{
       this.bpmnModelService.saveModel(modelId, values);
    }
    
   
    

}
