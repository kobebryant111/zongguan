package com.tansun.wf.platform.model;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2021/1/2
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class WfUserTaskEntity {
    private String loginUserName;
    private String realUserName;
    private String deptName;
    private String orgName;
    private String roleName;
    private Integer pendTaskCount;
    private Integer CompleteTaskCount;

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getRealUserName() {
        return realUserName;
    }

    public void setRealUserName(String realUserName) {
        this.realUserName = realUserName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getPendTaskCount() {
        return pendTaskCount;
    }

    public void setPendTaskCount(Integer pendTaskCount) {
        this.pendTaskCount = pendTaskCount;
    }

    public Integer getCompleteTaskCount() {
        return CompleteTaskCount;
    }

    public void setCompleteTaskCount(Integer completeTaskCount) {
        CompleteTaskCount = completeTaskCount;
    }

}
