package com.tansun.wf.activiti.model;

/**
 * 查询工作量统计 返回对象
 */
public class WorkloadStatisticsDto {

    /** 查询类型：1总体 2按年 3按月 */
    private int qtype;

    /** user - loginName, dept - deptCode, role - roleCode, org - orgCode */
    private String id;

    private String name;

    private String pid;

    /** 实体类型：org、dept、role、user */
    private String type;

    /** 创建数 */
    private long createCnt;

    /** 处理数 */
    private long handleCnt;

    public WorkloadStatisticsDto(){}

    public WorkloadStatisticsDto(String name){
        this.setName(name);
        this.setCreateCnt(0);
        this.setHandleCnt(0);
    }

    public WorkloadStatisticsDto(WorkloadStatisticsVo vo, WorkloadStatisticsQo qo){
        this.setId(vo.getId());
        this.setName(vo.getName());
        this.setPid(vo.getPid());
        this.setType(vo.getType());
        this.setQtype(qo.getQtype());
    }

    public long getCreateCnt() {
        return createCnt;
    }

    public void setCreateCnt(long createCnt) {
        this.createCnt = createCnt;
    }

    public long getHandleCnt() {
        return handleCnt;
    }

    public void setHandleCnt(long handleCnt) {
        this.handleCnt = handleCnt;
    }

    public int getQtype() {
        return qtype;
    }

    public void setQtype(int qtype) {
        this.qtype = qtype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
