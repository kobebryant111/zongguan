package com.tansun.wf.activiti.entity;

import com.tansun.wf.activiti.util.dbtool.entity.BaseFileDbEntity;
import com.tansun.wf.activiti.util.dbtool.entity.SelectRes;
import com.tansun.wf.activiti.util.dbtool.exception.DbToolException;
import com.tansun.wf.activiti.util.dbtool.tool.Operater;

/**
 * 简易文本数据库机构实体
 * @author 罗道宏
 */
public class FileDbOrgEntity extends BaseFileDbEntity {

    private String orgName;
    private String orgCode;
    private String name;
    private String pId;
    private String id;
    private String orgId;
    private String orgLevelCd;
    private String delInd;

    @Override
    public boolean create() {
        return createTable("create table org(orgName varchar(50),orgCode varchar(50),name varchar(50),pId varchar(50),id varchar(50),orgId varchar(50),orgLevelCd varchar(50),delInd varchar(2));");
    }

    @Override
    public boolean drop() {
        return dropTable("org");
    }

    public BaseFileDbEntity setDefaultAttribute(String orgCode, String orgName) {
        this.orgCode = orgCode;
        this.orgName = orgName;
        return defaultAttribute();
    }

    @Override
    public BaseFileDbEntity defaultAttribute() {
        name = NULL_VAL;
        pId = NULL_VAL;
        id = NULL_VAL;
        orgId = NULL_VAL;
        orgLevelCd = NULL_VAL;
        delInd = NO;
        return this;
    }

    @Override
    public SelectRes selectAll() {
        return new Operater("select * from org").checkSelect();
    }

    @Override
    public int delete() {
        if(this.id==null){
            throw new DbToolException("FileDbOrgEntity删除条件id不可为空");
        }
        return new Operater("delete from org where id='"+this.id+"'").checkDel();
    }

    @Override
    public boolean insertNotVerify() {
        return new Operater(insertMineStr()).checkInsert();
    }

    @Override
    public String insertMineStr() {
        // insert into org(orgName,orgCode,name,pId,id,orgId,orgLevelCd,delInd)values('测试分行','A00110','厦门分行(A00110)','A000100','A00110','A00110','1','0')
        return "insert into org(orgName,orgCode,name,pId,id,orgId,orgLevelCd,delInd)values('"+orgName+"','"+orgCode+"','"+name+"','"+pId+"','"+id+"','"+orgId+"','"+orgLevelCd+"','"+delInd+"')";
    }

    @Override
    public int update(BaseFileDbEntity origin) {
        return 0;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgLevelCd() {
        return orgLevelCd;
    }

    public void setOrgLevelCd(String orgLevelCd) {
        this.orgLevelCd = orgLevelCd;
    }

    public String getDelInd() {
        return delInd;
    }

    public void setDelInd(String delInd) {
        this.delInd = delInd;
    }

}
