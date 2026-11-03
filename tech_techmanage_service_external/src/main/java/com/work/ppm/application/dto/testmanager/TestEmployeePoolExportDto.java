package com.work.ppm.application.dto.testmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

/**
 * packageName com.work.ppm.application.dto.testmanager
 *
 * @version JDK 8
 * @className TestEmployeePoolExportDto
 * @date 2024/8/15
 * @description 测试人员池导出信息实体
 */
@Data
@ExcelTarget("TestEmployeePoolExportDto")
public class TestEmployeePoolExportDto {
    /**
     *	测试人员
     **/
    @Excel(name = "测试人员", orderNum = "1")
    private String testEmpName;
    /**
     *	工号
     **/
    @Excel(name = "工号", orderNum = "2")
    private String testEmpBadge;
    /**
     *	所属公司
     **/
    @Excel(name = "所属公司", orderNum = "3")
    private String affilCompany;
    /**
     *	员工状态
     **/
    @Excel(name = "员工状态", orderNum = "4" , replace = {"在职_0","离职_1","待入职_2"})
    private String employeeStatus;
    /**
     *	公司内负责人
     **/
    @Excel(name = "公司内负责人", orderNum = "5")
    private String headOfCompany;
    /**
     *	所属测试团队
     **/
    @Excel(name = "所属测试团队", orderNum = "6")
    private String ownedTestTeam;
    /**
     *	所属测试小组
     **/
    @Excel(name = "所属测试小组", orderNum = "7")
    private String ownedTestGroup;
    /**
     *	属性
     **/
    @Excel(name = "属性", orderNum = "8")
    private String empAttribute;
    /**
     *	部门ID
     **/
    @Excel(name = "部门ID", orderNum = "9")
    private String deptId;
    /**
     *	身份证号
     **/
    @Excel(name = "身份证号", orderNum = "10")
    private String certificateNumber;

}
