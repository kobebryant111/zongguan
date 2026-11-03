package com.tansun.wf.platform.service;

import com.tansun.workflow.model.ProcessInfo;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface BpmnModelService {

	ObjectNode  findDiagram(ProcessInfo processInfo);

	void saveModel(String modelId, MultiValueMap<String, String> values) throws Exception;
}
