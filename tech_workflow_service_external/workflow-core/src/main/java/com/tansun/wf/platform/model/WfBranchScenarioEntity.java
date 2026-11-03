package com.tansun.wf.platform.model;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2021/1/25
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */

public class WfBranchScenarioEntity {
    /**
     * id
     */
    private String id;
    /**
     * 流程定义下的索引
     */
    private String index;
    /**
     * 高亮节点id 以,隔开
     */
    private String highlightedNodeId;
    /**
     * 路径字符串(startEvent)-->(sequenceFlow)-->(userTask)-->
     */
    private String pathStr;
    /**
     * 流程定义id
     */
    private String processDefinitionId;

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getHighlightedNodeId() {
        return highlightedNodeId;
    }

    public void setHighlightedNodeId(String highlightedNodeId) {
        this.highlightedNodeId = highlightedNodeId;
    }

    public String getPathStr() {
        return pathStr;
    }

    public void setPathStr(String pathStr) {
        this.pathStr = pathStr;
    }
}
