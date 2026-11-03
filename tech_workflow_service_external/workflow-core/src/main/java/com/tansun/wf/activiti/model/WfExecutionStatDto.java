package com.tansun.wf.activiti.model;

/**
 * 流程时间统计返回
 */
public class WfExecutionStatDto {

    /** 显示名称 */
    private String name;

    /** 执行中 */
    private int active;

    /** 已完成 */
    private int completed;

    /** 已终止 */
    private int terminated;

    /** 流程键值 */
    private String modelKey;

    /** 流程版本 */
    private String modelVersion;

    public WfExecutionStatDto() {
    }

    public WfExecutionStatDto(String name, String modelKey, String modelVersion) {
        this.name = name;
        this.modelKey = modelKey;
        this.modelVersion = modelVersion;
        this.active = 0;
        this.completed = 0;
        this.terminated = 0;
    }

    public WfExecutionStatDto(WfVersionVo vo) {
        this(vo.getModelName()+"("+vo.getModelVersion()+")", vo.getModelKey(), vo.getModelVersion());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getTerminated() {
        return terminated;
    }

    public void setTerminated(int terminated) {
        this.terminated = terminated;
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

    public static void businessCloneDto(WfExecutionStatDto source, WfExecutionStatDto target){
        if(source==null || target==null){
            return;
        }
        if(source.getModelKey()==null || target.getModelKey()==null){
            return;
        }
        if(!source.getModelKey().equals(target.getModelKey())){
            return;
        }
        source.setActive(target.getActive());
        source.setCompleted(target.getCompleted());
        source.setTerminated(target.getTerminated());
        source.setModelVersion(target.getModelVersion());
        source.setName(target.getName());
    }
}
