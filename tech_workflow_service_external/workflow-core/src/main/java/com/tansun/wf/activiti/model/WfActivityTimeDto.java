package com.tansun.wf.activiti.model;

/**
 * 流程活动时间统计返回
 */
public class WfActivityTimeDto {

    /** 显示名称 */
    private String name;

    /** 平均时间 */
    private int average;

    /** 最大执行时间 */
    private int maximum;

    /** 最小执行时间 */
    private int minimum;

    /** 流程键值 */
    private String modelKey;

    /** 流程版本 */
    private String modelVersion;

    /** 活动ID */
    private String actId;

    /** 活动名称 */
    private String actName;

    public WfActivityTimeDto() {
    }

    public WfActivityTimeDto(String name, String modelKey, String modelVersion, String actId) {
        this.name = name;
        this.modelKey = modelKey;
        this.modelVersion = modelVersion;
        this.actId = actId;
        this.average = 0;
        this.maximum = 0;
        this.minimum = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

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

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public static void businessCloneDto(WfActivityTimeDto sourceActivity, WfActivityTimeDto target){
        if(sourceActivity==null || target==null){
            return;
        }
        if(sourceActivity.getActId()==null || target.getActId()==null){
            return;
        }
        if(!sourceActivity.getActId().equals(target.getActId())){
            return;
        }
        sourceActivity.setAverage(target.average);
        sourceActivity.setMaximum(target.getMaximum());
        sourceActivity.setMinimum(target.getMinimum());
        sourceActivity.setModelVersion(target.getModelVersion());
        sourceActivity.setName(target.getName());
        sourceActivity.setActName(target.getActName());
    }
}
