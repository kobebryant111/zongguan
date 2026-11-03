package com.tansun.wf.activiti.model;

/**
 * 超时率返回
 */
public class WfTimeoutRateDto {

    /** 显示名称 */
    private String name;

    /** 实例超时 */
    private long instTimeout;

    /** 实例已完成 */
    private long instCompleted;

    /** 实例超时率 */
    private String instRate;

    /** 活动超时 */
    private long actTimeout;

    /** 活动已完成 */
    private long actCompleted;

    /** 活动超时率 */
    private String actRate;

    /** 流程键值 */
    private String modelKey;

    /** 流程版本 */
    private String modelVersion;

    public WfTimeoutRateDto() {
    }

    public WfTimeoutRateDto(String name, String modelKey, String modelVersion) {
        this.name = name;
        this.modelKey = modelKey;
        this.modelVersion = modelVersion;
        this.instCompleted = 0;
        this.instTimeout = 0;
        this.instRate = "0.00%";
        this.actCompleted = 0;
        this.actTimeout = 0;
        this.actRate = "0.00%";
    }

    public WfTimeoutRateDto(WfVersionVo vo) {
        this(vo.getModelName()+"("+vo.getModelVersion()+")", vo.getModelKey(), vo.getModelVersion());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public long getInstTimeout() {
        return instTimeout;
    }

    public void setInstTimeout(long instTimeout) {
        this.instTimeout = instTimeout;
    }

    public long getInstCompleted() {
        return instCompleted;
    }

    public void setInstCompleted(long instCompleted) {
        this.instCompleted = instCompleted;
    }

    public String getInstRate() {
        return instRate;
    }

    public void setInstRate(String instRate) {
        this.instRate = instRate;
    }

    public long getActTimeout() {
        return actTimeout;
    }

    public void setActTimeout(long actTimeout) {
        this.actTimeout = actTimeout;
    }

    public long getActCompleted() {
        return actCompleted;
    }

    public void setActCompleted(long actCompleted) {
        this.actCompleted = actCompleted;
    }

    public String getActRate() {
        return actRate;
    }

    public void setActRate(String actRate) {
        this.actRate = actRate;
    }

    public static void businessCloneDto(WfTimeoutRateDto source, WfTimeoutRateDto target){
        if(source==null || target==null){
            return;
        }
        if(source.getModelKey()==null || target.getModelKey()==null){
            return;
        }
        if(!source.getModelKey().equals(target.getModelKey())){
            return;
        }
        if(target.getInstRate()!=null) {
            source.setInstCompleted(target.getInstCompleted());
            source.setInstRate(target.getInstRate());
            source.setInstTimeout(target.getInstTimeout());
        }
        if(target.getActRate()!=null) {
            source.setActCompleted(target.getActCompleted());
            source.setActRate(target.getActRate());
            source.setActTimeout(target.getActTimeout());
        }
        source.setModelVersion(target.getModelVersion());
        source.setName(target.getName());
    }
}
