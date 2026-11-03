package com.tansun.wf.activiti.model;

/**
 * 流程时间统计返回
 */
public class WfExecutionTimeDto {

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

    public WfExecutionTimeDto() {
    }

    public WfExecutionTimeDto(String name, String modelKey, String modelVersion) {
        this.name = name;
        this.modelKey = modelKey;
        this.modelVersion = modelVersion;
        this.average = 0;
        this.maximum = 0;
        this.minimum = 0;
    }

    public WfExecutionTimeDto(WfVersionVo vo) {
        this(vo.getModelName()+"("+vo.getModelVersion()+")", vo.getModelKey(), vo.getModelVersion());
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

    public static void businessCloneDto(WfExecutionTimeDto source, WfExecutionTimeDto target){
        if(source==null || target==null){
            return;
        }
        if(source.getModelKey()==null || target.getModelKey()==null){
            return;
        }
        if(!source.getModelKey().equals(target.getModelKey())){
            return;
        }
        source.setAverage(target.average);
        source.setMaximum(target.getMaximum());
        source.setMinimum(target.getMinimum());
        source.setModelVersion(target.getModelVersion());
        source.setName(target.getName());
    }
}
