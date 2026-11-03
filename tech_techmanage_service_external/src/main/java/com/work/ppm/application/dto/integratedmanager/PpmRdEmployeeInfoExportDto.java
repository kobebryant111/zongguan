package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

@Data
@ExcelTarget("PpmRdEmployeeInfoExportDto")
public class PpmRdEmployeeInfoExportDto {

    /**
     * 所属部门
     */
    @Excel(name = "所属部门", width = 30 ,orderNum = "1")
    private String chargeName;
    /**
     * 工号
     **/
    @Excel(name = "工号", orderNum = "2")
    private String badge;
    /**
     * 用户名称
     */
    @Excel(name = "姓名", orderNum = "3")
    private String empname;
    /**
     * 0.全职 1.兼职研发1（[85%以上)、2兼职研发2 [70%-85%)、3兼职研发3 [50%-70%)
     */
    @Excel(name = "研发人员性质", width = 20 ,orderNum = "4",replace =
            {"全职100%_0","兼职研发1[85%以上)_1","兼职研发2[70%-85%)_2","兼职研发3[50%-70%)_3"})
    private String nature;
    /**
     * 员工状态
     **/
    @Excel(name = "员工状态", orderNum = "5",replace = {"在岗_0","转岗_1","离职_2"})
    private String empstatus;
    /**
     * 到岗日期
     **/
    @Excel(name = "到岗日期", orderNum = "6")
    private String arrivalTime;
    /**
     * 离岗日期
     **/
    @Excel(name = "离岗日期", orderNum = "7")
    private String leaveTime;

}
