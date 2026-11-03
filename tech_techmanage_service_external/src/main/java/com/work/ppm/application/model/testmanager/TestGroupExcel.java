package com.work.ppm.application.model.testmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

@Data
@ExcelTarget("TestGroupExcel")
public class TestGroupExcel {

    /**
     * 测试小组名称
     */
    @Excel(name = "测试小组名称", width = 30 ,orderNum = "1")
    private String testGroupName;
    /**
     * 所属测试团队
     */
    @Excel(name = "所属测试团队", width = 30 ,orderNum = "2")
    private String teamName;
    /**
     * 项目经理
     */
    @Excel(name = "项目经理", width = 30 ,orderNum = "3")
    private String teamMansger;
    /**
     * 小组组长
     */
    @Excel(name = "小组组长", width = 30 ,orderNum = "4")
    private String testEmpName;
    /**
     * 小组成员数
     */
    @Excel(name = "小组成员数", width = 30 ,orderNum = "5")
    private int groupMemberNo;
    /**
     * 小组成员
     */
    @Excel(name = "小组成员", width = 30 ,orderNum = "6")
    private String groupMember;
}
