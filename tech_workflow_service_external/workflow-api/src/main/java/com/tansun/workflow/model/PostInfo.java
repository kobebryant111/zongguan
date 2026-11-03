package com.tansun.workflow.model;

import java.io.Serializable;

/**
 * 角色，机构，岗位，用户以外的id存在groupId字段
 */
public class PostInfo implements Serializable {

    private static final long serialVersionUID = -5624337148151373647L;

    private String userId;
    // 自定义岗位类型，已有的有user、org、dept、role、post，自定义的时候不要和这些重复
    private String type;

    private String orgCode;

    private String deptCode;

    private String roleCode;

    private String groupId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
