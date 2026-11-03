package com.tansun.wf.activiti.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class WorkloadStatisticsTypesVo {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date searchTimeBegin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date searchTimeEnd;

    /** 查询类型：1总体 2按年 3按月 */
    private int qtype;

    private List<String> userLoginNames;

    private List<String> roleCodes;

    private List<String> orgCodes;

    private List<String> deptCodes;

    public WorkloadStatisticsTypesVo(List<String> userLoginNames, List<String> roleCodes, List<String> orgCodes, List<String> deptCodes) {
        this.userLoginNames = userLoginNames;
        this.roleCodes = roleCodes;
        this.orgCodes = orgCodes;
        this.deptCodes = deptCodes;
    }

    public List<String> getUserLoginNames() {
        return userLoginNames;
    }

    public void setUserLoginNames(List<String> userLoginNames) {
        this.userLoginNames = userLoginNames;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public List<String> getOrgCodes() {
        return orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public List<String> getDeptCodes() {
        return deptCodes;
    }

    public void setDeptCodes(List<String> deptCodes) {
        this.deptCodes = deptCodes;
    }

    public Date getSearchTimeBegin() {
        return searchTimeBegin;
    }

    public void setSearchTimeBegin(Date searchTimeBegin) {
        this.searchTimeBegin = searchTimeBegin;
    }

    public Date getSearchTimeEnd() {
        return searchTimeEnd;
    }

    public void setSearchTimeEnd(Date searchTimeEnd) {
        this.searchTimeEnd = searchTimeEnd;
    }

    public int getQtype() {
        return qtype;
    }

    public void setQtype(int qtype) {
        this.qtype = qtype;
    }
}
