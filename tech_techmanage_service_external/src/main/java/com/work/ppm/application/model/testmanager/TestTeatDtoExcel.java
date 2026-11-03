package com.work.ppm.application.model.testmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

@Data
@ExcelTarget("TestTeatDtoExcel")
public class TestTeatDtoExcel {

    /**
     * 团队名称
     */
    @Excel(name = "团队名称", width = 30 ,orderNum = "1")
    private String teamName;
    /**
     * 团队项目经理
     */
    @Excel(name = "团队项目经理", width = 30 ,orderNum = "2")
    private String teamMansger;
    /**
     * 团队成员数
     */
    @Excel(name = "团队成员数", width = 30 ,orderNum = "3")
    private int teamMemberNo;
    /**
     * 测试小组数
     */
    @Excel(name = "测试小组数", width = 30 ,orderNum = "4")
    private int testGroupNo;
}
