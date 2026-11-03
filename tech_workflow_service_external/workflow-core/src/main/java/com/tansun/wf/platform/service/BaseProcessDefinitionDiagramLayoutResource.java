package com.tansun.wf.platform.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.impl.bpmn.behavior.BoundaryEventActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParse;
import org.camunda.bpm.engine.impl.bpmn.parser.EventSubscriptionDeclaration;
import org.camunda.bpm.engine.impl.core.model.Properties;
import org.camunda.bpm.engine.impl.jobexecutor.TimerDeclarationImpl;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.pvm.PvmActivity;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.process.*;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.ErrorEventDefinition;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BaseProcessDefinitionDiagramLayoutResource {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private HistoryService historyService;

	public ObjectNode getDiagramNode(String processInstanceId, String processDefinitionId) {

		List<String> highLightedFlows = Collections.<String> emptyList();
		List<String> highLightedActivities = Collections.<String> emptyList();

		Map<String, ObjectNode> subProcessInstanceMap = new HashMap<String, ObjectNode>();

		ProcessInstance processInstance = null;
		if (processInstanceId != null) {
			processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if (processInstance == null) {
				throw new RuntimeException("Process instance could not be found");
			}
			processDefinitionId = processInstance.getProcessDefinitionId();

			List<ProcessInstance> subProcessInstances = runtimeService
					.createProcessInstanceQuery()
					.superProcessInstanceId(processInstanceId).list();

			for (ProcessInstance subProcessInstance : subProcessInstances) {
				String subDefId = subProcessInstance.getProcessDefinitionId();

				String superExecutionId = ((ExecutionEntity) subProcessInstance)
						.getSuperExecutionId();
				ProcessDefinitionEntity subDef = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(subDefId);

				ObjectNode processInstanceJSON = new ObjectMapper().createObjectNode();
				processInstanceJSON.put("processInstanceId", subProcessInstance.getId());
				processInstanceJSON.put("superExecutionId", superExecutionId);
				processInstanceJSON.put("processDefinitionId", subDef.getId());
				processInstanceJSON.put("processDefinitionKey", subDef.getKey());
				processInstanceJSON.put("processDefinitionName", subDef.getName());

				subProcessInstanceMap.put(superExecutionId, processInstanceJSON);
			}
		}

		if (processDefinitionId == null) {
			throw new RuntimeException("No process definition id provided");
		}

		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);

		if (processDefinition == null) {
			throw new RuntimeException("Process definition " + processDefinitionId + " could not be found");
		}

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		// Process definition
		JsonNode pdrJSON = getProcessDefinitionResponse(processDefinition);

		if (pdrJSON != null) {
			responseJSON.set("processDefinition", pdrJSON);
		}

		// Highlighted activities
		if (processInstance != null) {
			ArrayNode activityArray = new ObjectMapper().createArrayNode();
			ArrayNode flowsArray = new ObjectMapper().createArrayNode();

			highLightedActivities = runtimeService.getActiveActivityIds(processInstanceId);
			highLightedFlows = getHighLightedFlows(processInstanceId, processDefinition);
			for (String activityName : highLightedActivities) {
				activityArray.add(activityName);
			}

			for (String flow : highLightedFlows)
				flowsArray.add(flow);

			responseJSON.set("highLightedActivities", activityArray);
			responseJSON.set("highLightedFlows", flowsArray);
		}

		// Pool shape, if process is participant in collaboration
		if (processDefinition.getParticipantProcess() != null) {
			ParticipantProcess pProc = processDefinition.getParticipantProcess();

			ObjectNode participantProcessJSON = new ObjectMapper().createObjectNode();
			participantProcessJSON.put("id", pProc.getId());
			if (StringUtils.isNotEmpty(pProc.getName())) {
				participantProcessJSON.put("name", pProc.getName());
			} else {
				participantProcessJSON.put("name", "");
			}
			participantProcessJSON.put("x", pProc.getX());
			participantProcessJSON.put("y", pProc.getY());
			participantProcessJSON.put("width", pProc.getWidth());
			participantProcessJSON.put("height", pProc.getHeight());

			responseJSON.set("participantProcess", participantProcessJSON);
		}

		// Draw lanes

		if (processDefinition.getLaneSets() != null && !processDefinition.getLaneSets().isEmpty()) {
			ArrayNode laneSetArray = new ObjectMapper().createArrayNode();
			for (LaneSet laneSet : processDefinition.getLaneSets()) {
				ArrayNode laneArray = new ObjectMapper().createArrayNode();
				if (laneSet.getLanes() != null && !laneSet.getLanes().isEmpty()) {
					for (Lane lane : laneSet.getLanes()) {
						ObjectNode laneJSON = new ObjectMapper().createObjectNode();
						laneJSON.put("id", lane.getId());
						if (StringUtils.isNotEmpty(lane.getName())) {
							laneJSON.put("name", lane.getName());
						} else {
							laneJSON.put("name", "");
						}
						laneJSON.put("x", lane.getX());
						laneJSON.put("y", lane.getY());
						laneJSON.put("width", lane.getWidth());
						laneJSON.put("height", lane.getHeight());

						List<String> flowNodeIds = lane.getFlowNodeIds();
						ArrayNode flowNodeIdsArray = new ObjectMapper().createArrayNode();
						for (String flowNodeId : flowNodeIds) {
							flowNodeIdsArray.add(flowNodeId);
						}
						laneJSON.set("flowNodeIds", flowNodeIdsArray);

						laneArray.add(laneJSON);
					}
				}
				ObjectNode laneSetJSON = new ObjectMapper().createObjectNode();
				laneSetJSON.put("id", laneSet.getId());
				if (StringUtils.isNotEmpty(laneSet.getName())) {
					laneSetJSON.put("name", laneSet.getName());
				} else {
					laneSetJSON.put("name", "");
				}
				laneSetJSON.set("lanes", laneArray);

				laneSetArray.add(laneSetJSON);
			}

			if (laneSetArray.size() > 0)
				responseJSON.set("laneSets", laneSetArray);
		}

		ArrayNode sequenceFlowArray = new ObjectMapper().createArrayNode();
		ArrayNode activityArray = new ObjectMapper().createArrayNode();
		BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);

//	    // Activities and their sequence-flows
//
		for (ActivityImpl activity : processDefinition.getActivities()) {
			if (activity.getActivityBehavior() instanceof MultiInstanceActivityBehavior) {
				List<ActivityImpl>  lists = activity.getActivities();
				if (lists != null && lists.size()>0) {
					ActivityImpl  parAct  =  lists.get(0);
					parAct.getIncomingTransitions().clear();
					PvmActivity backDestination = null;
					if (activity.getIncomingTransitions().size()>0) {
						PvmTransition  pvmTransition = activity.getIncomingTransitions().get(0);
						backDestination = pvmTransition.getDestination();
						((TransitionImpl)pvmTransition).setDestination(parAct);
					}
					parAct.getIncomingTransitions().add(activity.getIncomingTransitions().get(0));
					parAct.getOutgoingTransitions().clear();

					ActivityImpl backSource = null;
					if (activity.getOutgoingTransitions().size()>0) {
						PvmTransition  pvmTransition = activity.getOutgoingTransitions().get(0);
						TransitionImpl  pvmTran =  (TransitionImpl) pvmTransition;
						backSource = pvmTran.getSource();
						pvmTran.setSource(parAct);
						parAct.getOutgoingTransitions().add(activity.getOutgoingTransitions().get(0));

					}
					getActivity(processInstanceId, parAct, activityArray, sequenceFlowArray,
							processInstance, highLightedFlows, subProcessInstanceMap,bpmnModelInstance);
					parAct.getIncomingTransitions().clear();
					parAct.getOutgoingTransitions().clear();
					if(backDestination!=null){
						PvmTransition pvmTransition = activity.getIncomingTransitions().get(0);
						((TransitionImpl)pvmTransition).setDestination((ActivityImpl)backDestination);
					}
					if(backSource!=null){
						PvmTransition pvmTransition = activity.getIncomingTransitions().get(0);
						((TransitionImpl)pvmTransition).setSource(backSource);
					}
				}

			}else {
				getActivity(processInstanceId, activity, activityArray, sequenceFlowArray,
						processInstance, highLightedFlows, subProcessInstanceMap,bpmnModelInstance);
			}
		}

		responseJSON.set("activities", activityArray);
		responseJSON.set("sequenceFlows", sequenceFlowArray);


		return responseJSON;
	}

	private List<String> getHighLightedFlows(String processInstanceId, ProcessDefinitionEntity processDefinition) {

		List<String> highLightedFlows = new ArrayList<String>();
		List<HistoricActivityInstance> historicActivityInstances = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricActivityInstanceStartTime().asc().list();

		List<String> historicActivityInstanceList = new ArrayList<String>();
		for (HistoricActivityInstance hai : historicActivityInstances) {
			historicActivityInstanceList.add(hai.getActivityId());
		}

		// add current activities to list
		List<String> highLightedActivities = runtimeService.getActiveActivityIds(processInstanceId);
		historicActivityInstanceList.addAll(highLightedActivities);

		// activities and their sequence-flows
		for (ActivityImpl activity : processDefinition.getActivities()) {
			int index = historicActivityInstanceList.indexOf(activity.getId());

			if (index >= 0 && index + 1 < historicActivityInstanceList.size()) {
				List<PvmTransition> pvmTransitionList = activity
						.getOutgoingTransitions();
				for (PvmTransition pvmTransition : pvmTransitionList) {
					String destinationFlowId = pvmTransition.getDestination().getId();
					if (destinationFlowId.equals(historicActivityInstanceList.get(index + 1))) {
						highLightedFlows.add(pvmTransition.getId());
					}
				}
			}
		}
		return highLightedFlows;
	}

	private void getActivity(String processInstanceId, ActivityImpl activity, ArrayNode activityArray,
							 ArrayNode sequenceFlowArray, ProcessInstance processInstance, List<String> highLightedFlows,
							 Map<String, ObjectNode> subProcessInstanceMap,BpmnModelInstance bpmnModelInstance) {

		ObjectNode activityJSON = new ObjectMapper().createObjectNode();

		// Gather info on the multi instance marker
		String  mulType =(String) activity.getProperty("type");

		String multiInstance = (String) activity.getProperty("multiInstance");
		if (multiInstance != null) {
			if (!"sequential".equals(multiInstance)) {
				multiInstance = "parallel";
			}
		}
		if ("multiInstanceBody".equals(mulType)) {
			multiInstance = "parallel";
		}

		ActivityBehavior activityBehavior = activity.getActivityBehavior();
		// Gather info on the collapsed marker
		Boolean collapsed = (activityBehavior instanceof CallActivityBehavior);
		Boolean expanded = (Boolean) activity.getProperty(BpmnParse.PROPERTYNAME_ISEXPANDED);
		if (expanded != null) {
			collapsed = !expanded;
		}

		Boolean isInterrupting = null;
		if (activityBehavior instanceof BoundaryEventActivityBehavior) {
			//isInterrupting = ((BoundaryEventActivityBehavior) activityBehavior).isInterrupting();
		}

		// Outgoing transitions of activity
		for (PvmTransition sequenceFlow : activity.getOutgoingTransitions()) {
			String flowName = (String) sequenceFlow.getProperty("name");
			boolean isHighLighted = (highLightedFlows.contains(sequenceFlow.getId()));
			boolean isConditional = sequenceFlow.getProperty(BpmnParse.PROPERTYNAME_CONDITION) != null &&
					!((String) activity.getProperty("type")).toLowerCase().contains("gateway");
			boolean isDefault = sequenceFlow.getId().equals(activity.getProperty("default"))
					&& ((String) activity.getProperty("type")).toLowerCase().contains("gateway");

			List<Integer> waypoints = ((TransitionImpl) sequenceFlow).getWaypoints();
			ArrayNode xPointArray = new ObjectMapper().createArrayNode();
			ArrayNode yPointArray = new ObjectMapper().createArrayNode();
			ObjectNode flowJSON = new ObjectMapper().createObjectNode();
			for (int i = 0; i < waypoints.size(); i += 2) { // waypoints.size()
				// minimally 4: x1, y1,
				// x2, y2
				xPointArray.add(waypoints.get(i));
				yPointArray.add(waypoints.get(i + 1));
			}


			flowJSON.put("id", sequenceFlow.getId());
			flowJSON.put("name", flowName);

			SequenceFlow flow = (SequenceFlow) bpmnModelInstance.getModelElementById(sequenceFlow.getId());
			flowJSON.put("flow", "(" + flow.getSource().getId() + ")--"
					+ sequenceFlow.getId() + "-->("
					+ flow.getTarget().getId()+ ")");

			if (isConditional)
				flowJSON.put("isConditional", isConditional);
			if (isDefault)
				flowJSON.put("isDefault", isDefault);
			if (isHighLighted)
				flowJSON.put("isHighLighted", isHighLighted);

			flowJSON.set("xPointArray", xPointArray);
			flowJSON.set("yPointArray", yPointArray);

			flowJSON.put("sourceAnchor", xPointArray.get(0));
			flowJSON.put("targetAnchor", yPointArray.get(0));
			sequenceFlowArray.add(flowJSON);
		}

		// Nested activities (boundary events)
		ArrayNode nestedActivityArray = new ObjectMapper().createArrayNode();
		for (ActivityImpl nestedActivity : activity.getActivities()) {
			nestedActivityArray.add(nestedActivity.getId());
		}

		Properties properties = activity.getProperties();
		ObjectNode propertiesJSON = new ObjectMapper().createObjectNode();
		Map<String,Object>  propMap =  properties.toMap();

		for (String key : propMap.keySet()) {

			Object prop = propMap.get(key);

			if (prop instanceof String)
				if (((String) propMap.get(key)).equals("noneEndEvent")) {
					propertiesJSON.put(key,"endEvent");
				}else if ("boundaryTimer".equals((String)prop)) {
					propMap.put("type","timerCatchEvent");
					propertiesJSON.put(key, (String) propMap.get(key));
				}else
					propertiesJSON.put(key, (String) propMap.get(key));
			else if (prop instanceof Integer)
				propertiesJSON.put(key, (Integer) propMap.get(key));
			else if (prop instanceof Boolean)
				propertiesJSON.put(key, (Boolean) propMap.get(key));
			else if ("initial".equals(key)) {
				ActivityImpl act = (ActivityImpl) propMap.get(key);
				propertiesJSON.put(key, act.getId());
			}
			else if ("timerDeclarations".equals(key)) {
				HashMap<String,TimerDeclarationImpl> timerDeclarations = (HashMap<String,TimerDeclarationImpl>) propMap.get(key);
				ArrayNode timerDeclarationArray = new ObjectMapper().createArrayNode();
				propertiesJSON.put("type","timerCatchEvent");
				// propMap.put("type","timerCatchEvent");
				if (timerDeclarations != null)
					for (String timerDeclarationKey : timerDeclarations.keySet()) {
						TimerDeclarationImpl timerDeclaration = timerDeclarations.get(timerDeclarationKey);
						ObjectNode timerDeclarationJSON = new ObjectMapper().createObjectNode();

						timerDeclarationJSON.put("isExclusive", timerDeclaration.isExclusive());
						if (timerDeclaration.getRepeat() != null)
							timerDeclarationJSON.put("repeat", timerDeclaration.getRepeat());

						//timerDeclarationJSON.put("retries", String.valueOf(timerDeclaration.getRetries()));
						timerDeclarationJSON.put("type", timerDeclaration.getJobHandlerType());
						//timerDeclarationJSON.put("configuration", timerDeclaration.getJobHandlerConfiguration());
						//timerDeclarationJSON.put("expression", timerDeclaration.getDescription());

						timerDeclarationArray.add(timerDeclarationJSON);
					}
				if (timerDeclarationArray.size() > 0)
					propertiesJSON.set(key, timerDeclarationArray);
				// TODO: implement getting description
			} else if ("eventDefinitions".equals(key)) {

				ArrayList<EventSubscriptionDeclaration> eventDefinitions = null;

				Object    obj =  propMap.get(key);
				if (obj instanceof ArrayList) {
					eventDefinitions = (ArrayList<EventSubscriptionDeclaration>) obj;

				}else if (obj  instanceof  Map) {
					Map<String,EventSubscriptionDeclaration> map  =  (Map<String, EventSubscriptionDeclaration>) obj;
					eventDefinitions  = new  ArrayList<>();
					for (String  tmKey: map.keySet()) {

						eventDefinitions.add(map.get(tmKey));
					}
				}
				ArrayNode eventDefinitionsArray = new ObjectMapper().createArrayNode();

				if (eventDefinitions != null) {
					for (EventSubscriptionDeclaration eventDefinition : eventDefinitions) {
						ObjectNode eventDefinitionJSON = new ObjectMapper().createObjectNode();

						if (eventDefinition.getActivityId() != null)
							eventDefinitionJSON.put("activityId",eventDefinition.getActivityId());

						//eventDefinitionJSON.put("eventName", eventDefinition.getEventName());
						eventDefinitionJSON.put("eventType", eventDefinition.getEventType());
						eventDefinitionJSON.put("isAsync", eventDefinition.isAsync());
						eventDefinitionJSON.put("isStartEvent", eventDefinition.isStartEvent());
						eventDefinitionsArray.add(eventDefinitionJSON);
					}
				}

				if (eventDefinitionsArray.size() > 0)
					propertiesJSON.set(key, eventDefinitionsArray);

				// TODO: implement it
			} else if ("errorEventDefinitions".equals(key)) {
				ArrayList<ErrorEventDefinition> errorEventDefinitions = (ArrayList<ErrorEventDefinition>) propMap.get(key);
				ArrayNode errorEventDefinitionsArray = new ObjectMapper().createArrayNode();

				if (errorEventDefinitions != null) {
					for (ErrorEventDefinition errorEventDefinition : errorEventDefinitions) {
						ObjectNode errorEventDefinitionJSON = new ObjectMapper().createObjectNode();

						if (errorEventDefinition.getError() != null)
							errorEventDefinitionJSON.put("errorCode", errorEventDefinition.getError().getErrorCode());
						else
							errorEventDefinitionJSON.putNull("errorCode");

//	            errorEventDefinitionJSON.put("handlerActivityId",
//	            errorEventDefinition.getHandlerActivityId());

						errorEventDefinitionsArray.add(errorEventDefinitionJSON);
					}
				}


				if (errorEventDefinitionsArray.size() > 0)
					propertiesJSON.set(key, errorEventDefinitionsArray);
			}

		}

		if ("callActivity".equals(propMap.get("type"))) {
			CallActivityBehavior callActivityBehavior = null;

			if (activityBehavior instanceof CallActivityBehavior) {
				callActivityBehavior = (CallActivityBehavior) activityBehavior;
			}

			if (callActivityBehavior != null) {
				//TODO Camunda
				// propertiesJSON.put("processDefinitonKey", callActivityBehavior.);

				// get processDefinitonId from execution or get last processDefinitonId
				// by key
				ArrayNode processInstanceArray = new ObjectMapper().createArrayNode();
				if (processInstance != null) {
					List<Execution> executionList = runtimeService.createExecutionQuery()
							.processInstanceId(processInstanceId)
							.activityId(activity.getId()).list();
					if (!executionList.isEmpty()) {
						for (Execution execution : executionList) {
							ObjectNode processInstanceJSON = subProcessInstanceMap.get(execution.getId());
							processInstanceArray.add(processInstanceJSON);
						}
					}
				}

				// If active activities nas no instance of this callActivity then add
				// last definition
				//TODO Camunda
//	        if (processInstanceArray.size() == 0 && StringUtils.isNotEmpty(callActivityBehavior.getProcessDefinitonKey())) {
//	          // Get last definition by key
//	          ProcessDefinition lastProcessDefinition = repositoryService
//	              .createProcessDefinitionQuery()
//	              .processDefinitionKey(callActivityBehavior.getProcessDefinitonKey())
//	              .latestVersion().singleResult();
//
//	          // TODO: unuseful fields there are processDefinitionName, processDefinitionKey
//	          if (lastProcessDefinition != null) {
//	            ObjectNode processInstanceJSON = new ObjectMapper().createObjectNode();
//	            processInstanceJSON.put("processDefinitionId", lastProcessDefinition.getId());
//	            processInstanceJSON.put("processDefinitionKey", lastProcessDefinition.getKey());
//	            processInstanceJSON.put("processDefinitionName", lastProcessDefinition.getName());
//	            processInstanceArray.add(processInstanceJSON);
//	          }
//	        }

				if (processInstanceArray.size() > 0) {
					propertiesJSON.set("processDefinitons", processInstanceArray);
				}
			}
		}

		activityJSON.put("activityId", activity.getId());

		activityJSON.set("properties", propertiesJSON);
		if (multiInstance != null)
			activityJSON.put("multiInstance", multiInstance);
		if (collapsed)
			activityJSON.put("collapsed", collapsed);
		if (nestedActivityArray.size() > 0)
			activityJSON.set("nestedActivities", nestedActivityArray);
		if (isInterrupting != null)
			activityJSON.put("isInterrupting", isInterrupting);

		activityJSON.put("x", activity.getX());
		activityJSON.put("y", activity.getY());
		activityJSON.put("width", activity.getWidth());
		activityJSON.put("height", activity.getHeight());
		if (!propertiesJSON.get("type").asText().equals("multiInstanceBody")) {
			activityArray.add(activityJSON);
		}

		// Nested activities (boundary events)
		for (ActivityImpl nestedActivity : activity.getActivities()) {
			getActivity(processInstanceId, nestedActivity, activityArray, sequenceFlowArray,
					processInstance, highLightedFlows, subProcessInstanceMap,bpmnModelInstance);
		}
	}

	private JsonNode getProcessDefinitionResponse(ProcessDefinitionEntity processDefinition) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode pdrJSON = mapper.createObjectNode();
		pdrJSON.put("id", processDefinition.getId());
		pdrJSON.put("name", processDefinition.getName());
		pdrJSON.put("key", processDefinition.getKey());
		pdrJSON.put("version", processDefinition.getVersion());
		pdrJSON.put("deploymentId", processDefinition.getDeploymentId());
		pdrJSON.put("isGraphicNotationDefined", isGraphicNotationDefined(processDefinition));
		return pdrJSON;
	}

	private boolean isGraphicNotationDefined(ProcessDefinitionEntity processDefinition) {
		return ((ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinition.getId()))
				.isGraphicalNotationDefined();
	}
}