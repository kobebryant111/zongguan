package com.work.ppm.application.dto.usesysresourcealter;

import com.work.ppm.application.model.usesysresourcealter.ResUseSys;
import com.work.ppm.application.model.usesysresourcealter.ResUseSysOther;

import java.util.List;

/**
 * 应用系统资源变更流程dto
 */
public class ResUseSysDto {

    /**
     *	ids
     **/
    private String ids;

    /**
     *	应用系统id
     **/
    private String appsysno;

    /**
     *	ip
     **/
    private String ip;

    /**
     *	序列号
     **/
    private String serialno;

    /**
     *	资源分类
     **/
    private String resclassify;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getFlowno() {
        return flowno;
    }

    public void setFlowno(String flowno) {
        this.flowno = flowno;
    }

    /**
     *	流程编号
     **/
    private String flowno;

    /**
     *	应用系统名称
     **/
    private String appsysname;

    /**
     * 开始时间（查询使用）
     */
    private String startTime;

    /**
     * 结束时间（查询使用）
     */
    private String endTime;

    /**
     * 申请时间（查询使用）
     */
    private String rangeDate;

    public String getAppsysname() {
        return appsysname;
    }

    public void setAppsysname(String appsysname) {
        this.appsysname = appsysname;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRangeDate() {
        return rangeDate;
    }

    public void setRangeDate(String rangeDate) {
        this.rangeDate = rangeDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 状态
     */
    private String status;

    /**
     * 应用系统资源变更主表
     **/
    private ResUseSys list;

    public String getResclassify() {
        return resclassify;
    }

    public void setResclassify(String resclassify) {
        this.resclassify = resclassify;
    }
    /**
     * 应用系统资源变更附表
     **/
    private List<ResUseSysOther> subList;

    public String getAppsysno() {
        return appsysno;
    }

    public void setAppsysno(String appsysno) {
        this.appsysno = appsysno;
    }
    public ResUseSys getList() {
        return list;
    }

    public void setList(ResUseSys list) {
        this.list = list;
    }

    public List<ResUseSysOther> getSubList() {
        return subList;
    }

    public void setSubList(List<ResUseSysOther> subList) {
        this.subList = subList;
    }

}
