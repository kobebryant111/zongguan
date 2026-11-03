package com.tansun.wf.activiti.service;

import com.tansun.wf.activiti.entity.FileDbDeptEntity;
import com.tansun.wf.activiti.entity.FileDbOrgEntity;
import com.tansun.wf.activiti.entity.FileDbRoleEntity;
import com.tansun.wf.activiti.entity.FileDbUserEntity;
import com.tansun.wf.activiti.util.dbtool.entity.BaseFileDbEntity;
import com.tansun.wf.activiti.util.dbtool.entity.InsertInfo;
import com.tansun.wf.activiti.util.dbtool.entity.SelectRes;
import com.tansun.wf.activiti.util.dbtool.tool.Operater;
import com.tansun.wf.activiti.util.dbtool.tool.Tools;
import com.tansun.workflow.api.AbstractWorkflowEditorService;
import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 编辑器  如机构、部门、用户、角色列表等接口 读取式实现服务
 * @author 罗道宏
 */
@Service
public class FileEditorService extends AbstractWorkflowEditorService {

    @Value("${editor.service.fileEditorService.filePath:F:/0jinshi/temp/}")
    public void setName(String filePath) {
        Tools.path = filePath;
    }


    @Override
    public EditorBean findOrganizationList(EditorInfo editorInfo) {
        String orgCode = editorInfo.getOrgCode();
        String orgName = editorInfo.getOrgName();
        String orgLevelCd = editorInfo.getOrgGrade();
        String sql = "select * from org where delInd='0'";
        if(!StringUtils.isBlank(orgCode)){
            sql = sql + " and orgCode='"+orgCode+"'";
        }
        if(!StringUtils.isBlank(orgName)){
            sql = sql + " and orgName='"+orgName+"'";
        }
        if(!StringUtils.isBlank(orgLevelCd)){
            sql = sql + " and orgLevelCd='"+orgLevelCd+"'";
        }
        return commonGetResult(sql, editorInfo.getCurrentPage(), editorInfo.getPageSize(), "org");
    }

    @Override
    public EditorBean findDepartmentList(EditorInfo editorInfo) {
        String deptCode = editorInfo.getDeptCode();
        String deptName = editorInfo.getDeptName();
        String sql = "select * from dept where delInd='0'";
        if(!StringUtils.isBlank(deptCode)){
            sql = sql + " and deptCode='"+deptCode+"'";
        }
        if(!StringUtils.isBlank(deptName)){
            sql = sql + " and deptName='"+deptName+"'";
        }
        return commonGetResult(sql, editorInfo.getCurrentPage(), editorInfo.getPageSize(), "dept");
    }

    @Override
    public EditorBean findUsersList(EditorInfo editorInfo) {
        String userNum = editorInfo.getUserId();
        String loginName = editorInfo.getUserLoginName();
        String realname = editorInfo.getUserName();
        String loginNameLike = editorInfo.getUserLoginNameLike();
        String realnameLike = editorInfo.getUserNameLike();
        List<String> deptCodes = editorInfo.getDeptCodes();
        List<String> orgCodes = editorInfo.getOrgCodes();
        List<String> roleCodes = editorInfo.getRoleCodes();
        List<String> userLoginNames = editorInfo.getUserLoginNames();
        String sql = "select * from user where delInd='0'";
        if(!StringUtils.isBlank(userNum)){
            sql += " and userNum='"+userNum+"'";
        }
        if(!StringUtils.isBlank(loginName)){
            sql += " and userLoginName='"+loginName+"'";
        }
        if(!StringUtils.isBlank(realname)){
            sql += " and realname='"+realname+"'";
        }
        if(!StringUtils.isBlank(loginNameLike)){
            sql += " and userLoginName like '"+loginNameLike+"'";
        }
        if(!StringUtils.isBlank(realnameLike)){
            sql += " and realname like '"+realnameLike+"'";
        }
        if(deptCodes!=null && !deptCodes.isEmpty()){
            String[] strings = new String[deptCodes.size()];
            sql += " and deptCode in ('"+String.join("','", deptCodes.toArray(strings))+"')";
        }
        if(orgCodes!=null && !orgCodes.isEmpty()){
            String[] strings = new String[orgCodes.size()];
            sql += " and orgCode in ('"+String.join("','", orgCodes.toArray(strings))+"')";
        }
        if(roleCodes!=null && !roleCodes.isEmpty()){
            String[] strings = new String[roleCodes.size()];
            sql += " and roleCode in ('"+String.join("','", roleCodes.toArray(strings))+"')";
        }
        if(userLoginNames!=null && !userLoginNames.isEmpty()){
            String[] strings = new String[userLoginNames.size()];
            sql += " and userLoginName in ('"+String.join("','", userLoginNames.toArray(strings))+"')";
        }
        return commonGetResult(sql, editorInfo.getCurrentPage(), editorInfo.getPageSize(), "user");
    }

    @Override
    public EditorBean findRoleList(EditorInfo editorInfo) {
        String roleCode = editorInfo.getRoleCode();
        String roleName = editorInfo.getRoleName();
        String sql = "select * from role where delInd='0'";
        if(!StringUtils.isBlank(roleCode)){
            sql += " and roleCode='"+roleCode+"'";
        }
        if(!StringUtils.isBlank(roleName)){
            sql += " and roleName='"+roleName+"'";
        }
        return commonGetResult(sql, editorInfo.getCurrentPage(), editorInfo.getPageSize(), "role");
    }

    /**
     * 插入实体
     * @param entity 实体
     * @return 插入结果
     */
    public boolean insert(BaseFileDbEntity entity){
        return entity.insertNotVerify();
    }

    /**
     * 批量插入（必须是同一张表的数据）
     * @param sqls 插入sql
     */
    public void insertBatch(List<String> sqls){
        List<InsertInfo> insertInfos = new ArrayList<>();
        for(String sql : sqls){
            insertInfos.add(new Operater(sql).getInsertInfo());
        }
        Operater.commit(insertInfos);
    }

    /**
     * 删除实体
     * @param entity 实体
     * @return 删除条数
     */
    public int delete(BaseFileDbEntity entity){
        return entity.delete();
    }

    /**
     * 修改实体
     * @param origin 原实体
     * @param target 目标实体
     * @return 修改条数
     */
    public int update(BaseFileDbEntity origin, BaseFileDbEntity target){
        return target.update(origin);
    }

    private EditorBean commonGetResult(String sql, int currentPage, int pageSize, String type){
        EditorBean res = new EditorBean();
        SelectRes selectRes = new Operater(sql).checkSelect();
        int begin = (currentPage-1)*pageSize;
        int end = begin + pageSize;
        List<Map<String, Object>> list;
        if(begin>selectRes.getTotal()){
            list = new ArrayList<>();
        }else {
            if (end > selectRes.getTotal()) {
                end = selectRes.getTotal();
            }
            list = selectRes.getResults().subList(begin, end);
        }

        res.setTotal(selectRes.getTotal());
        res.setCurrentPageIndex(currentPage);
        switch (type){
            case "org":
                for (Map<String, Object> item : list) {
                    item.put("pId",null);
                }
                res.setOrgList(list); break;
            case "dept":
                for (Map<String, Object> item : list) {
                    item.put("pId",null);
                }
                res.setDeptList(list); break;
            case "user":
                res.setUserList(list); break;
            case "role":
                res.setRoleList(list); break;
        }
        return res;
    }

    public void initFile(){
        new FileDbOrgEntity().create();
        new FileDbDeptEntity().create();
        new FileDbUserEntity().create();
        new FileDbRoleEntity().create();
    }

    public boolean insert(String sql){
        return new Operater(sql).checkInsert();
    }

    public int delete(String sql){
        return new Operater(sql).checkDel();
    }

    public boolean create(BaseFileDbEntity entity){
        return entity.create();
    }

    public boolean drop(BaseFileDbEntity entity){
        return entity.drop();
    }

    /**
     * 机构、部门、用户、角色 测试数据插入
     */
    public void insertTestDataOrg(){
        List<InsertInfo> org = new ArrayList<>();
        for(int i=0; i<1000; i++) {
            org.add(new Operater("insert into org(orgName,orgCode,name,pId,id,orgId,orgLevelCd,delInd)values('测试分行','A00110','厦门分行(A00110)','A000100','A00110','A00110','1','0');").getInsertInfo());
        }
        Operater.commit(org);
    }
    public void insertTestDataDept(){
        List<InsertInfo> dept = new ArrayList<>();
        for(int i=0; i<1000; i++) {
            dept.add(new Operater("insert into dept(deptName,name,pId,id,deptCode,delInd)values('测试部','科技部(DEPT852841603645108224)','pid','DEPT852841603645108224','DEPT852841603645108224','0');").getInsertInfo());
        }
        Operater.commit(dept);
    }
    public void insertTestDataUser(){
        List<InsertInfo> user = new ArrayList<>();
        for(int i=0; i<1000; i++) {
            user.add(new Operater("insert into user(orgCode,deptCode,roleCode,userNum,deptName,orgName,useId,roleName,userLoginName,userName,delInd)values('A00110','DEPT852841603645108224','rl0','U1001','测试部','总部','USR170120000087','平台管理员','superadmin','superadmin','0');").getInsertInfo());
        }
        Operater.commit(user);
    }
    public void insertTestDataRole(){
        List<InsertInfo> role = new ArrayList<>();
        for(int i=0; i<1000; i++) {
            role.add(new Operater("insert into role(updateUser,statusCd,remark,updateTime,sort,version,delInd,createTime,roleCode,roleName,createUser,id,clntendId)values('USR170120000087','1','superadmin','2020-07-09 07:03:08',1,24,'0','2017-01-11 10:47:06','rl0','测试角色','systemAuto','112345678910111213','10002');").getInsertInfo());
        }
        Operater.commit(role);
    }

    /**
     * 机构、部门、用户、角色 测试查询
     */
    public void selectTestData(){
        SelectRes res = new Operater("select * from org;").checkSelect();
        res = new Operater("select * from dept;").checkSelect();
        res = new Operater("select * from user;").checkSelect();
        res = new Operater("select * from role;").checkSelect();
        res = new Operater("select orgName,orgCode,name,pId,id,orgId from org;").checkSelect();
        res = new Operater("select deptName,name,pId,id,deptCode from dept;").checkSelect();
        res = new Operater("select deptName,orgName,useId,roleName,userLoginName,userName from user;").checkSelect();
        res = new Operater("select updateUser,statusCd,remark,updateTime,sort,version,delInd,createTime,roleCode,roleName,createUser,ID,clntendId from role;").checkSelect();
        System.out.println(res);
    }

    /**
     * 机构、部门、用户、角色 测试数据移除
     */
    public void deleteTestData(){
        new Operater("delete from org where orgName='测试分行'").checkDel();
        new Operater("delete from dept where deptName='测试部'").checkDel();
        new Operater("delete from user where deptName='测试部'").checkDel();
        new Operater("delete from role where roleName='测试角色'").checkDel();
    }

}
