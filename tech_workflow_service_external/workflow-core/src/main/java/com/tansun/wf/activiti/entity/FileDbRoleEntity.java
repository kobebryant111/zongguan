package com.tansun.wf.activiti.entity;

import com.tansun.wf.activiti.util.dbtool.entity.BaseFileDbEntity;
import com.tansun.wf.activiti.util.dbtool.entity.SelectRes;
import com.tansun.wf.activiti.util.dbtool.exception.DbToolException;
import com.tansun.wf.activiti.util.dbtool.tool.Operater;

import java.util.Date;

/**
 * 简易文本数据库角色实体
 * @author 罗道宏
 */
public class FileDbRoleEntity extends BaseFileDbEntity {

    private String updateUser;
    private String statusCd;
    private String remark;
    private String updateTime;
    private Integer sort;
    private Integer version;
    private String delInd;
    private String createTime;
    private String roleCode;
    private String roleName;
    private String createUser;
    private String id;
    private String clntendId;

    @Override
    public boolean create() {
        return createTable("create table role(updateUser varchar(50),statusCd varchar(50),remark varchar(50),updateTime varchar(25),sort int,version int,delInd varchar(2),createTime varchar(25),roleCode varchar(50),roleName varchar(50),createUser varchar(50),id varchar(50),clntendId varchar(50));");
    }

    @Override
    public boolean drop() {
        return dropTable("role");
    }

    public BaseFileDbEntity setDefaultAttribute(String roleCode, String roleName){
        this.roleName = roleName;
        this.roleCode = roleCode;
        return defaultAttribute();
    }

    @Override
    public BaseFileDbEntity defaultAttribute() {
        String time = DATE_FORMAT.format(new Date());
        updateUser = NULL_VAL;
        statusCd = NULL_VAL;
        remark = NULL_VAL;
        createUser = NULL_VAL;
        id = NULL_VAL;
        clntendId = NULL_VAL;
        sort = NULL_NUM;
        version = NULL_NUM;
        updateTime = time;
        createTime = time;
        delInd = NO;
        return this;
    }

    @Override
    public SelectRes selectAll() {
        return new Operater("select * from role").checkSelect();
    }

    @Override
    public int delete() {
        if(this.id==null){
            throw new DbToolException("FileDbOrgEntity删除条件id不可为空");
        }
        return new Operater("delete from role where id='"+this.id+"'").checkDel();
    }

    @Override
    public boolean insertNotVerify() {
        return new Operater(insertMineStr()).checkInsert();
    }

    @Override
    public String insertMineStr() {
        // insert into role(updateUser,statusCd,remark,updateTime,sort,version,delInd,createTime,roleCode,roleName,createUser,id,clntendId)values('USR170120000087','1','superadmin','2020-07-09 07:03:08',1,24,'0','2017-01-11 10:47:06','rl0','测试角色','systemAuto','112345678910111213','10002')
        return "insert into role(updateUser,statusCd,remark,updateTime,sort,version,delInd,createTime,roleCode,roleName,createUser,id,clntendId)values('"+updateUser+"','"+statusCd+"','"+remark+"','"+updateTime+"',"+sort+","+version+",'"+delInd+"','"+createTime+"','"+roleCode+"','"+roleName+"','"+createUser+"','"+id+"','"+clntendId+"')";
    }

    @Override
    public int update(BaseFileDbEntity origin) {
        return 0;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDelInd() {
        return delInd;
    }

    public void setDelInd(String delInd) {
        this.delInd = delInd;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClntendId() {
        return clntendId;
    }

    public void setClntendId(String clntendId) {
        this.clntendId = clntendId;
    }

}
