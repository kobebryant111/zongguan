//package com.tansun.wf.mocktest.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.tansun.wf.platform.mapper.WfBranchScenarioMapper;
//import com.tansun.wf.platform.model.WfBranchScenarioEntity;
//import org.camunda.bpm.engine.RepositoryService;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.camunda.bpm.model.bpmn.BpmnModelInstance;
//import org.camunda.bpm.model.bpmn.instance.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//// camunda-TODO
////@Service
//public class WfTestExecutionPathService {
//
//    @Autowired
//    public RepositoryService repositoryService;
//
//    @Autowired
//    WfBranchScenarioMapper wfBranchScenarioMapper;
//
//
//    protected ObjectMapper objectMapper = new ObjectMapper();
//
//
//    /**
//     * 从表中或者缓存中获取流程可能执行的轨迹列表
//     * @return
//     */
//    public List<WfBranchScenarioEntity> getExecutionList(String processDefinitionId) {
//        //从数据库获取数据如果没有数据在用接口去遍历解析流程图，不为空在持久化到数据库
//        WfBranchScenarioEntity wfBranchScenarioEntity = new WfBranchScenarioEntity();
//        wfBranchScenarioEntity.setProcessDefinitionId(processDefinitionId);
//        List<WfBranchScenarioEntity> list = wfBranchScenarioMapper.queryByEntity(wfBranchScenarioEntity);
//        if(CollectionUtils.isEmpty(list)){
//            // 去计算流程图分支情况做持久化操作。
//            List<Map<String, Object>> listMap = getExecutionPathStrList(processDefinitionId);
//            if(!CollectionUtils.isEmpty(listMap)) {
//                for (Map map : listMap) {
//                    WfBranchScenarioEntity entity = new WfBranchScenarioEntity();
//                    entity.setId(UUID.randomUUID().toString());
//                    entity.setProcessDefinitionId(processDefinitionId);
//                    entity.setIndex(MapUtils.getString(map, "index"));
//                    entity.setHighlightedNodeId(MapUtils.getString(map, "highlightedNodeId"));
//                    entity.setPathStr(MapUtils.getString(map, "pathStr"));
//                    list.add(entity);
//                }
//                wfBranchScenarioMapper.saveEntityList(list);
//            }
//        }
//        return list;
//    }
//
//    /**
//     * 计算流程可能执行的轨迹列表
//     * @return
//     */
//    public List<Map<String, Object>> getExecutionPathStrList(String processDefinitionId) {
//        // camunda-TODO
//        BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefinitionId);
//        Collection<FlowElement> flowElements = bpmnModel.getModelElementsByType(FlowElement.class);
//        List<List<String>> paths = getExecutionPathList(bpmnModel, flowElements);
//        List<Map<String, Object>> allPathsInfo = new ArrayList<>();
//        for (int i = 0; i < paths.size(); i++) {
//            Map<String, Object> pathInfo = new HashMap<>();
//            pathInfo.put("index", i + 1 + "");
//            String pathStr = "";
//            StringBuilder highlightedNodeId = new StringBuilder();
//            for (String node : paths.get(i)) {
//                FlowElement flowElement = bpmnModel.getModelElementById(node);
//                String name = flowElement.getName() == null ? "" : flowElement.getName();
//                String simpleName = flowElement.getClass().getSimpleName();
//
//                String nodeInfo = name + "(" + StringUtils.uncapitalize(simpleName) + ")";
//                highlightedNodeId.append(node + ",");
//                pathStr = pathStr + nodeInfo + "-->";
//            }
//
//            pathInfo.put("pathStr", pathStr.substring(0, pathStr.length() - 3));
//            pathInfo.put("highlightedNodeId",highlightedNodeId.substring(0, highlightedNodeId.length() - 1));
//            allPathsInfo.add(pathInfo);
//        }
//        return allPathsInfo;
//    }
//
//    /**
//     * 获取某个执行路径的信息
//     * @return
//     */
//    public ObjectNode getExecutionPathHighLight(String processDefinitionId, int previewIndex) {
//        // camunda-TODO
//        BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(processDefinitionId);
//        Collection<FlowElement> flowElements = bpmnModel.getModelElementsByType(FlowElement.class);
//        List<List<String>> paths = getExecutionPathList(bpmnModel, flowElements);
//        List<String> strings = paths.get(previewIndex - 1);
//
//        ArrayNode activities = objectMapper.createArrayNode();
//        ArrayNode flows = objectMapper.createArrayNode();
//        ArrayNode historyActivities = objectMapper.createArrayNode();
//
//        for (String id : strings) {
//            FlowElement flowElement = bpmnModel.getModelElementById(id);
//            if (flowElement instanceof SequenceFlow) {
//                flows.add(id);
//            } else {
//                activities.add(id);
//            }
//        }
//        ObjectNode previewInfo = objectMapper.createObjectNode();
//
//        previewInfo.put("activities", activities);
//        previewInfo.put("flows", flows);
//        previewInfo.put("historyActivities", historyActivities);
//        previewInfo.put("processDefinitionId", processDefinitionId);
//        previewInfo.put("processInstanceId", "");
//        return previewInfo;
//    }
//
//    /**
//     * 获取所有可能执行轨迹列表
//     *
//     * @param bpmnModel
//     * @param flowElements
//     * @return
//     */
//    private List<List<String>> getExecutionPathList(BpmnModelInstance bpmnModel, Collection<FlowElement> flowElements) {
//        FlowNode startEvent = null;
//        List<FlowNode> endEvents = new ArrayList<>();
//
//
//        for (FlowElement flowElement : flowElements) {
//            if (flowElement instanceof StartEvent) {
//                startEvent = (FlowNode) flowElement;
//            }
//            if (flowElement instanceof EndEvent) {
//                endEvents.add((FlowNode) flowElement);
//            }
//        }
//
//        List<List<String>> paths = new ArrayList<>();
//        for (FlowNode endEvent : endEvents) {
//            getPathList(startEvent, endEvent, new Stack(), paths, bpmnModel);
//        }
//
//        List<List<String>> mergePaths = mergeParallelGateway(bpmnModel, paths);
//
//        Collections.sort(mergePaths, new Comparator<List<String>>() {
//            @Override
//            public int compare(List<String> o1, List<String> o2) {
//                return o2.size() - o1.size();
//            }
//        });
//
//        return mergePaths;
//    }
//
//
//    /**
//     * 获取节点间的所有路径
//     *
//     * @param startNode
//     * @param endNode
//     * @param stack
//     * @param paths
//     * @param bpmnModel
//     */
//    private void getPathList(FlowNode startNode, FlowNode endNode, Stack stack, List<List<String>> paths, BpmnModelInstance bpmnModel) {
//
//        Object[] objects = stack.toArray();
//        int count = 0;
//        for (Object object : objects) {
//            if (object.equals(startNode.getId())) {
//                count = count + 1;
//            }
//        }
//        if (count >= 2) {
//            return;
//        }
//
//        if (startNode.getId().equals(endNode.getId())) {
//            paths.addAll(stackToString(bpmnModel, stack, endNode.getId()));
//            return;
//        }
//
//        stack.push(startNode.getId());
//        // camunda-TODO
//        Collection<SequenceFlow> outgoingFlows = startNode.getOutgoing();
//        for (SequenceFlow flow : outgoingFlows) {
//            stack.push(flow.getId());
//            // camunda-TODO
////            FlowElement targetFlowElement = bpmnModel.getDocumentElement(flow.getTarget().getId());
//            FlowElement targetFlowElement = bpmnModel.getModelElementById(flow.getTarget().getId());
//            getPathList((FlowNode) targetFlowElement, endNode, stack, paths, bpmnModel);
//            stack.pop();
//        }
//
//        stack.pop();
//    }
//
//    private List<List<String>> stackToString(BpmnModelInstance bpmnModel, Stack stack, String endNodeId) {
//        List<List<String>> paths = new ArrayList<>();
//        List<String> ids = new ArrayList<>();
//        for (Object o : stack.toArray()) {
//            ids.add((String) o);
//        }
//        ids.add(endNodeId);
//
//        //子流程
//        List<SubProcess> subProcessList = new ArrayList<>();
//        for (String id : ids) {
//            // camunda-TODO
//            FlowElement flowElement = bpmnModel.getModelElementById(id);
//            if (flowElement instanceof SubProcess) {
//                subProcessList.add((SubProcess) flowElement);
//            }
//        }
//
//        if (subProcessList.size() == 0) {
//            paths.add(ids);
//        } else {
//            getSubProcessPathIds(bpmnModel, paths, ids, subProcessList, 0);
//
//        }
//
//        return paths;
//    }
//
//    private void getSubProcessPathIds(BpmnModelInstance bpmnModel, List<List<String>> paths, List<String> ids, List<SubProcess> subProcessesList, int subProcessListIndex) {
//
//        if (subProcessListIndex >= subProcessesList.size()) {
//            paths.add(ids);
//            return;
//        }
//
//
//        SubProcess subProcesss = subProcessesList.get(subProcessListIndex);
//        List<List<String>> subProcessAllPathIds = getExecutionPathList(bpmnModel, subProcesss.getFlowElements());
//        for (List<String> subProcessPathId : subProcessAllPathIds) {
//
//            //用subProcessPathId替换ids 数组中的引用
//            List<String> newIds = new ArrayList<>();
//            newIds.addAll(ids);
//            int i = newIds.indexOf(subProcesss.getId());
//            newIds.addAll(i, subProcessPathId);
//
//            getSubProcessPathIds(bpmnModel, paths, newIds, subProcessesList, subProcessListIndex + 1);
//
//        }
//
//
//    }
//
//    /**
//     * 合并并行网关之间的路径
//     *
//     * @param bpmnModel
//     * @param paths
//     * @return
//     */
//    private List<List<String>> mergeParallelGateway(BpmnModelInstance bpmnModel, List<List<String>> paths) {
//
//        List<List<String>> middlePaths = new ArrayList<>();
//        List<String> startParallelGatewayIds = new ArrayList<>();
//
//        //获取所有并行网关对
//        for (List<String> path : paths) {
//            List<ParallelGateway> parallelGateways = new ArrayList<>();
//            for (String nodeId : path) {
//                // camunda-TODO
//                FlowElement flowElement = bpmnModel.getModelElementById(nodeId);
//                if (flowElement instanceof ParallelGateway) {
//                    parallelGateways.add((ParallelGateway) flowElement);
//                }
//            }
//
//            if (parallelGateways.size() % 2 != 0) {
//                continue;
//            }
//            int parallelIndex = parallelGateways.size() / 2;
//            for (int i = 0; i < parallelIndex; i++) {
//                ParallelGateway startNode = parallelGateways.get(parallelIndex - i - 1);
//                ParallelGateway endNode = parallelGateways.get(parallelIndex + i);
//                if (startParallelGatewayIds.contains(startNode.getId())) {
//                    continue;
//                } else {
//                    startParallelGatewayIds.add(startNode.getId());
//                }
//
//                getMiddlePathList(middlePaths, bpmnModel, startNode, endNode, 0, new ArrayList<List<String>>());
//            }
//
//        }
//
//        if (middlePaths.size() == 0) {
//            return paths;
//        }
//
//        List<List<String>> mergePaths = new ArrayList<>();
//        for (List<String> path : paths) {
//
//            for (List<String> middlePath : middlePaths) {
//                List<String> newPath = new ArrayList<>();
//                int startNodeIndex = path.indexOf(middlePath.get(0));
//                int endNodeIndex = path.indexOf(middlePath.get(middlePath.size() - 1));
//                if (startNodeIndex < 0 || endNodeIndex < 0) {
//                    continue;
//                }
//
//                List<String> left = path.subList(0, startNodeIndex + 1);
//                List<String> middle = middlePath.subList(1, middlePath.size() - 1);
//                List<String> right = path.subList(endNodeIndex, path.size());
//                newPath.addAll(left);
//
//                newPath.addAll(middle);
//                newPath.addAll(right);
//                mergePaths.add(newPath);
//            }
//
//        }
//
//
////		//去重
//        List<List<String>> distinctPaths = new ArrayList<>();
//        for (List<String> mergePath : mergePaths) {
//            boolean isContain = false;
//            for (List<String> distinctPath : distinctPaths) {
//                if (CollectionUtils.isEqualCollection(distinctPath, mergePath)) {
//                    isContain = true;
//                }
//            }
//            if (!isContain) {
//                distinctPaths.add(mergePath);
//            }
//
//
//        }
//        return distinctPaths;
//
//    }
//
//
//    private void getMiddlePathList(List<List<String>> middlePaths, BpmnModelInstance bpmnModel, ParallelGateway startNode, ParallelGateway endNode, int i, ArrayList<List<String>> paths) {
//        // camunda-TODO
//        List<SequenceFlow> outgoingFlows = (ArrayList)startNode.getOutgoing();
//        if (i > outgoingFlows.size() - 1) {
//            //合并paths
//            List<String> middlePath = new ArrayList<>();
//            for (List<String> path : paths) {
//                middlePath.addAll(path);
//            }
//            middlePath.add(0, startNode.getId());
//            middlePath.add(middlePath.size(), endNode.getId());
//            middlePaths.add(middlePath);
//            return;
//        }
//        SequenceFlow sequenceFlow = outgoingFlows.get(i);
//        String targetId = sequenceFlow.getTarget().getId();
//        List<List<String>> subPaths = new ArrayList<>();
//
//        // camunda-TODO
//        getPathList((FlowNode) bpmnModel.getModelElementById(targetId), endNode, new Stack(), subPaths, bpmnModel);
//        for (List<String> path : subPaths) {
//            if (i == 0) {
//
//            }
//            List<String> path1 = path.subList(0, path.size() - 1);
//            path1.add(0, sequenceFlow.getId());
//            paths.add(path1);
//            int nextIndex = i + 1;
//            getMiddlePathList(middlePaths, bpmnModel, startNode, endNode, nextIndex, paths);
//            paths.remove(path1);
//        }
//    }
//
//
//}