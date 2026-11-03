package com.tansun.wf.activiti.entity;

import com.tansun.wf.activiti.util.dbtool.entity.BaseFileDbEntity;
import com.tansun.wf.activiti.util.dbtool.entity.SelectRes;
import com.tansun.wf.activiti.util.dbtool.exception.DbToolException;
import com.tansun.wf.activiti.util.dbtool.tool.Operater;

/**
 * 简易文本数据库用户实体
 */
public class FileDbUserEntity extends BaseFileDbEntity {

    private String orgCode;
    private String deptCode;
    private String roleCode;
    private String userNum;
    private String deptName;
    private String orgName;
    private String useId;
    private String roleName;
    private String userLoginName;
    private String userName;
    private String delInd;

    @Override
    public boolean create() {
        return createTable("create table user(orgCode varchar(50),deptCode varchar(50),roleCode varchar(50), userNum varchar(50),deptName varchar(50),orgName varchar(50),useId varchar(50),roleName varchar(50),userLoginName varchar(50),userName varchar(50),delInd varchar(2));");
    }

    @Override
    public boolean drop() {
        return dropTable("user");
    }

    @Override
    public BaseFileDbEntity defaultAttribute() {
        delInd = NULL_VAL;
        return this;
    }

    @Override
    public SelectRes selectAll() {
        return new Operater("select * from user").checkSelect();
    }

    @Override
    public int delete() {
        if(this.userNum==null){
            throw new DbToolException("FileDbOrgEntity删除条件userNum不可为空");
        }
        return new Operater("delete from user where userNum='"+this.userNum+"'").checkDel();
    }

    @Override
    public boolean insertNotVerify() {
        return new Operater(insertMineStr()).checkInsert();
    }

    @Override
    public String insertMineStr() {
        // insert into user(orgCode,deptCode,roleCode,userNum,deptName,orgName,useId,roleName,userLoginName,userName,delInd)values('A00110','DEPT852841603645108224','rl0','U1001','测试部','总部','USR170120000087','平台管理员','superadmin','superadmin','0')
        return "insert into user(orgCode,deptCode,roleCode,userNum,deptName,orgName,useId,roleName,userLoginName,userName,delInd)values('"+orgCode+"','"+deptCode+"','"+roleCode+"','"+userNum+"','"+deptName+"','"+orgName+"','"+useId+"','"+roleName+"','"+userLoginName+"','"+userName+"','"+delInd+"')";
    }

    @Override
    public int update(BaseFileDbEntity origin) {
        return 0;
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

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
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

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDelInd() {
        return delInd;
    }

    public void setDelInd(String delInd) {
        this.delInd = delInd;
    }

}
