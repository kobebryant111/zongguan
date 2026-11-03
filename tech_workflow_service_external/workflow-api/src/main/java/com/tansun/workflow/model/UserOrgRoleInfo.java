package com.tansun.workflow.model;

/**
 * 返回给前端构造参与人选择树
 */
public class UserOrgRoleInfo {

    /**存放角色编码，机构编码，用户编码，部门编码*/
    private String id;

    /**存放角色名，机构名，用户名，部门名*/
    private String name;

    /**父节点id*/
    private String pid;

    /**类型：org（机构）role（角色）user（用户）dept（部门）*/
    private String type;

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
