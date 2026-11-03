package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 项目分配导出
 */
@Data
@ExcelTarget("PpmAttendanceInfoExportDto")
public class PpmAttendanceInfoExportDto{
    /**
     * 所属部门
     */
    @Excel(name = "所属部门",width = 30, orderNum = "1")
    private String chargeName;
    /**
     * 用户名称
     */
    @Excel(name = "姓名", orderNum = "2")
    private String username;
    /**
     * 工号
     **/
    @Excel(name = "工号", orderNum = "3")
    private String badge;
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
     * 分配状态
     */
    @Excel(name = "是否参与", orderNum = "8",replace = {"正常_0","否_1"})
    private String isdisabled;
    /**
     * 项目编码
     */
    @Excel(name = "项目编码", orderNum = "6")
    private String projectCode;
    /**
     * 项目名称
     */
    @Excel(name = "项目名称", orderNum = "7")
    private String projectName;

    /**
     * 合同金额
     */
    @Excel(name = "项目合同总金额", orderNum = "9")
    private String contractAmount;
    /**
     * 研发人数量
     */
    @Excel(name = "项目研发人数量", orderNum = "10")
    private Integer no;
    /**
     * 项目状态  0  立项中、 1 进行中、 2 收尾中、9 已完结、 3 暂停、 4 废止
     */
    @Excel(name = "项目状态", orderNum = "11" ,replace = {"立项中_0","进行中_1","收尾中_2","已完结_9","暂停_3","废止_4"})
    private String projectState;
//    /**
//     *
//     */
//    @Excel(name = "项目是否删除", orderNum = "12",replace = {"正常_0","删除_1"})
//    private String deleted;

}
