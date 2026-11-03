package com.tansun.wf.platform.query;

import com.tansun.wf.extend.db.WfBaseQuery;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/25
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class WfTaskExpandDataQuery extends WfBaseQuery {
    /**流程实例id */
    private String processInstanceId;
    /**流程定义ID*/
    private String processDefineId;
    /**代办任务id */
    private String taskId;
    /**数据库行名*/
    private String fieldColumn;
    /**数据库行名对应的value*/
    private String fieldValue;

    public String getFieldColumn() {
        return fieldColumn;
    }

    public void setFieldColumn(String fieldColumn) {
        this.fieldColumn = fieldColumn;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefineId() {
        return processDefineId;
    }

    public void setProcessDefineId(String processDefineId) {
        this.processDefineId = processDefineId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
