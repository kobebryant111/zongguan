package com.work.ppm.application.dto.projectmanager;

/**
 * 流程删除
 */
public class DemandBackDto {
    /**
     * 意向审批业务id或上线项目编号
     */
    private String id;

    /**
     * 删除类型，0，意向审批，1。奋进号上线
     */
    private String delType;

    /**
     * 删除人
     */
    private String userid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDelType() {
        return delType;
    }


    public void setDelType(String delType) {
        this.delType = delType;
    }


    public String getUserid() {
        return userid;
    }


    public void setUserid(String userid) {
        this.userid = userid;
    }
}
