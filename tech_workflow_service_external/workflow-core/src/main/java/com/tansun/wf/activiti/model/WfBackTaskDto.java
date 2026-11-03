package com.tansun.wf.activiti.model;

/**
 * 流程退回统计返回
 */
public class WfBackTaskDto {

    /** 显示名称 */
    private String name;

    /** 版本号 */
    private String modelVersion;

    /** 发起数 */
    private long startCnt;

    /** 退回数 */
    private long backCnt;

    /** 退回率 */
    private String backRate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartCnt() {
        return startCnt;
    }

    public void setStartCnt(long startCnt) {
        this.startCnt = startCnt;
    }

    public long getBackCnt() {
        return backCnt;
    }

    public void setBackCnt(long backCnt) {
        this.backCnt = backCnt;
    }

    public String getBackRate() {
        return backRate;
    }

    public void setBackRate(String backRate) {
        this.backRate = backRate;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }
}
