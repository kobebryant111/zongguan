package com.tansun.wf.activiti.util.dbtool;

import com.tansun.wf.activiti.entity.FileDbDeptEntity;
import com.tansun.wf.activiti.entity.FileDbOrgEntity;
import com.tansun.wf.activiti.entity.FileDbRoleEntity;
import com.tansun.wf.activiti.entity.FileDbUserEntity;
import com.tansun.wf.activiti.service.FileEditorService;
import com.tansun.wf.activiti.util.dbtool.entity.SelectRes;
import com.tansun.wf.activiti.util.dbtool.tool.Operater;
import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 简易文本数据服务测试类
 * @author 罗道宏
 */
public class DbToolTest {

//    public static void main(String[] args){
//        selectTest1();
//    }

    public static void drop(){
        new FileDbOrgEntity().drop();
        new FileDbRoleEntity().drop();
        new FileDbDeptEntity().drop();
        new FileDbUserEntity().drop();
    }

    public static void selectTest(){
        FileEditorService fileEditorService = new FileEditorService();
        EditorInfo info = new EditorInfo();
        EditorBean res = fileEditorService.findOrganizationList(info);
        System.out.println(res.getOrgList());
    }

    public static void selectTest1(){
        SelectRes res = new FileDbOrgEntity().selectAll();
        res = new FileDbDeptEntity().selectAll();
        res = new FileDbRoleEntity().selectAll();
    }

    public static void insertTest() {
        FileEditorService fileEditorService = new FileEditorService();
        List<String> org = new ArrayList<>();
        org.add(new FileDbOrgEntity().setDefaultAttribute("9000000000", "总行").insertMineStr());
        org.add(new FileDbOrgEntity().setDefaultAttribute("3310000000", "杭州分行").insertMineStr());
        org.add(new FileDbOrgEntity().setDefaultAttribute("3310100000", "杭州城北支行").insertMineStr());
        fileEditorService.insertBatch(org);
        List<String> dept = new ArrayList<>();
        dept.add(new FileDbDeptEntity().setDefaultAttribute("9000120600", "总行反洗钱中心").insertMineStr());
        dept.add(new FileDbDeptEntity().setDefaultAttribute("9000120000", "总行法律合规部").insertMineStr());
        dept.add(new FileDbDeptEntity().setDefaultAttribute("9000180000", "总行运营部").insertMineStr());
        fileEditorService.insertBatch(dept);
        List<String> role = new ArrayList<>();
        role.add(new FileDbRoleEntity().setDefaultAttribute("R9000000", "总行质量管理员").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R7000001", "分行质量管理员").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R5000001", "宁波地区一级支行质量管理员").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R7000000", "分行行长").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R5000000", "宁波地区一级支行行长").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R9000005", "总行反洗钱中心人员").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R9000004", "总行反洗钱中心负责人").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R9000006", "总行法律合规部负责人").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R7000002", "分行反洗钱中心人员").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R9000002", "总行运营部管理人员").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R9000001", "总行运营部负责人").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R9000003", "总行运营部BO审核员").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R7000003", "分行运营管控执行人员").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R5000002", "宁波地区一级支行运营管控执行人员").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R3000000", "二级机构运营管控执行人员").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R3000002", "业务人员").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R3000001", "部门经理").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R1000000", "检查查询角色").insertMineStr());
        role.add(new FileDbRoleEntity().setDefaultAttribute("R1000001", "第三方管户人").insertMineStr());
        fileEditorService.insertBatch(role);
    }

    public static void createTable(){
        FileEditorService fileEditorService = new FileEditorService();
        fileEditorService.initFile();
    }

	public static void bigDataTest() {
        FileEditorService fileEditorService = new FileEditorService();
		for(int i=0;i<100;i++) {
            fileEditorService.insertTestDataOrg();
            fileEditorService.insertTestDataDept();
            fileEditorService.insertTestDataUser();
            fileEditorService.insertTestDataRole();
            System.out.println(i);
        }
        Date begin = new Date();
        EditorInfo param = new EditorInfo();
//        param.setCurrentPage(1);
//        param.setPageSize(10);
//        param.setUserId("U1001");
//        param.setUserLoginNameLike("super");
//        param.setDeptCodes(Collections.singletonList("DEPT852841603645108224"));
//        param.setRoleCodes(Collections.singletonList("rl0"));
        EditorBean res = fileEditorService.findUsersList(param);
        System.out.println(res.getTotal());
        System.out.println((new Date().getTime()-begin.getTime())/1000);
        param = new EditorInfo();
        param.setCurrentPage(1);
        param.setPageSize(10);
        res = fileEditorService.findRoleList(param);
        System.out.println(res.getRoleList());
	}

}
