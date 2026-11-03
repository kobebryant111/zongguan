package com.work.ppm.application.model.rdmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

@Data
@ExcelTarget("RdProjectExcel")
public class RdProjectExcel {
    /**
     * 项目编号
     */
    @Excel(name = "项目编号", width = 30 ,orderNum = "1")
    private String projectCode;
    /**
     * 项目名称
     */
    @Excel(name="项目名称" ,orderNum = "2")
    private String projectName;

    /**
     * 项目开始时间
     */
    @Excel(name="项目开始时间", orderNum = "3")
    private String startDate;

    /**
     * 项目结束时间
     */
    @Excel(name="项目结束时间",orderNum = "4")
    private String endDate;

    /**
     *0  立项中、 1 进行中、 2 收尾中、9 已完结、 3 暂停、 4 废止
     */
    @Excel(name="项目状态",replace = {"立项中_0","进行中_1","收尾中_2","已完结_9","暂停_3","废止_4","-_null"} ,orderNum = "5")
    private String projectState;
    /**
     * 关联系统
     */
    @Excel(name = "关联系统",orderNum = "6")
    private String sysName;
    /**
     * 项目主管部门
     */
    @Excel(name="项目主管部门",orderNum = "7")
    private String competentDept;
    /**
     * 项目参与部门
     */
    @Excel(name="项目参与部门",orderNum = "8")
    private String participatingDept;
    /**
     * 项目负责人
     */
    @Excel(name="项目负责人",orderNum = "9")
    private String headUser;
    /**
     * 研发负责人
     */
    @Excel(name="研发负责人",orderNum = "10")
    private String devUser;
    /**
     * 合同预算编号
     */
    @Excel(name="合同预算编号",orderNum = "11")
    private String budgetCode;
    /**
     * 合同预算名称
     */
    @Excel(name="合同预算名称",orderNum = "12")
    private String budgetName;
    /**
     * 合同编号
     */
    @Excel(name="合同编号",orderNum = "13")
    private String contractCode;
    /**
     * 合同名称
     */
    @Excel(name="合同名称",orderNum = "14")
    private String contractName;
    /**
     * 合同开始时间
     */
    @Excel(name="合同开始时间",orderNum = "15")
    private String contractStartDate;
    /**
     * 合同结束时间
     */
    @Excel(name="合同结束时间",orderNum = "16")
    private String contractEndDate;
    /**
     * 合同金额
     */
    @Excel(name="合同金额(万元)",orderNum = "17")
    private String contractAmount;
    /**
     * 合同支付进度
     */
    @Excel(name="合同支付进度",suffix = "%",orderNum = "18")
    private Double paymentScheduling;
    /**
     * 项目分摊比例
     */
    @Excel(name="项目分摊比例",suffix = "%",orderNum = "19")
    private Double projectAllocationRatio;
}
