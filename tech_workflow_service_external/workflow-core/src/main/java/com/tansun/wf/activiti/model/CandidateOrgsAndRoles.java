package com.tansun.wf.activiti.model;


import java.util.List;

/**
 * 机构与角色用户交集
 */
public class CandidateOrgsAndRoles {
    //角色列表
    private List<String> roleList;
    //机构列表
    private List<String> orgList;

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public List<String> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<String> orgList) {
        this.orgList = orgList;
    }
}
