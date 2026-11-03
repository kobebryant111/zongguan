package com.tansun.wf.activiti.entity;

import com.tansun.wf.activiti.util.dbtool.entity.BaseFileDbEntity;
import com.tansun.wf.activiti.util.dbtool.entity.SelectRes;
import com.tansun.wf.activiti.util.dbtool.exception.DbToolException;
import com.tansun.wf.activiti.util.dbtool.tool.Operater;

/**
 * 简易文本数据库部门实体
 * @author 罗道宏
 */
public class FileDbDeptEntity extends BaseFileDbEntity {

    private String deptName;
    private String deptCode;
    private String delInd;
    private String name;
    private String pId;
    private String id;

    @Override
    public boolean create() {
        return createTable("create table dept(deptName varchar(50),name varchar(50),pId varchar(50),id varchar(50),deptCode varchar(50),delInd varchar(2));");
    }

    @Override
    public boolean drop() {
        return dropTable("dept");
    }

    public BaseFileDbEntity setDefaultAttribute(String deptCode, String deptName) {
        this.deptCode = deptCode;
        this.deptName = deptName;
        return defaultAttribute();
    }

    @Override
    public BaseFileDbEntity defaultAttribute() {
        delInd = NO;
        pId = NULL_VAL;
        id = NULL_VAL;
        name = NULL_VAL;
        return this;
    }

    @Override
    public SelectRes selectAll() {
        return new Operater("select * from dept").checkSelect();
    }

    @Override
    public int delete() {
        if(this.id==null){
            throw new DbToolException("FileDbOrgEntity删除条件id不可为空");
        }
        return new Operater("delete from dept where id='"+this.id+"'").checkDel();
    }

    @Override
    public boolean insertNotVerify() {
        return new Operater(insertMineStr()).checkInsert();
    }

    @Override
    public String insertMineStr() {
        // insert into dept(deptName,name,pId,id,deptCode,delInd)values('测试部','科技部(DEPT852841603645108224)','pid','DEPT852841603645108224','DEPT852841603645108224','0')
        return "insert into dept(deptName,name,pId,id,deptCode,delInd)values('"+deptName+"','"+name+"','"+pId+"','"+id+"','"+deptCode+"','"+delInd+"')";
    }

    @Override
    public int update(BaseFileDbEntity origin) {
        return 0;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDelInd() {
        return delInd;
    }

    public void setDelInd(String delInd) {
        this.delInd = delInd;
    }

}
