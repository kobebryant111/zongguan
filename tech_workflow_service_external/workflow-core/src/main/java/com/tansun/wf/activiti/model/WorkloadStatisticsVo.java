package com.tansun.wf.activiti.model;

import java.util.List;

public class WorkloadStatisticsVo {

    public static final String TYPE_ORG = "org";
    public static final String TYPE_DEPT = "dept";
    public static final String TYPE_ROLE = "role";
    public static final String TYPE_USER = "user";

    /** user - loginName, dept - deptCode, role - roleCode, org - orgCode */
    private String id;

    private String name;

    private String pid;

    /** 实体类型：org、dept、role、user */
    private String type;

    /** 下级实体 */
    private List<WorkloadStatisticsVo> subs;

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

    public List<WorkloadStatisticsVo> getSubs() {
        return subs;
    }

    public void setSubs(List<WorkloadStatisticsVo> subs) {
        this.subs = subs;
    }

    public WorkloadStatisticsVo() {
    }

    public WorkloadStatisticsVo(String id, String name, String pid, String type) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.type = type;
    }
}
