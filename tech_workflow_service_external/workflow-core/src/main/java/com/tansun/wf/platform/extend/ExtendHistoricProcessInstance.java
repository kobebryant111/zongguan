package com.tansun.wf.platform.extend;


import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2021/1/18
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class ExtendHistoricProcessInstance extends HistoricProcessInstanceEntity {
    private String nowActivityId;
    private String suspensionState;

    public String getSuspensionState() {
        return suspensionState;
    }

    public void setSuspensionState(String suspensionState) {
        this.suspensionState = suspensionState;
    }

    public String getNowActivityId() {
        return nowActivityId;
    }

    public void setNowActivityId(String nowActivityId) {
        this.nowActivityId = nowActivityId;
    }
}
