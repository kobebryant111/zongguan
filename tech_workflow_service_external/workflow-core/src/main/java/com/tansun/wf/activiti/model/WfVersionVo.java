package com.tansun.wf.activiti.model;

/**
 * 流程时间统计vo
 */
public class WfVersionVo {

    /** 流程键值 */
    private String modelKey;

    /** 流程版本 */
    private String modelVersion;

    /** 流程名称 */
    private String modelName;

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
