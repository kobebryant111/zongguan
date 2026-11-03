package com.work.ppm.application.dto.outsourceledger;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.work.ppm.commons.base.BaseEntity;
import lombok.Data;

import java.sql.Date;

@Data
@ExcelTarget("outsourceLedgerDto")
public class OutsourceLedgerDto extends BaseEntity {

    /**
     * id
     */
//    @Excel(name = "主键", orderNum = "1")
    private Long id;
    /**
     * 外包项目名称
     */
    @Excel(name = "外包项目名称", orderNum = "1")
    private String projectName;
    /**
     * 具体外包事项
     */
    @Excel(name = "具体外包事项", orderNum = "2")
    private String descriptions;
    /**
     * 外包业务用工部门
     */
//    @Excel(name = "外包业务用工部门", orderNum = "4")
    private String deptNo;

//    @Excel(name = "部门编号", orderNum = "5")
    @Excel(name = "外包业务用工部门", orderNum = "3")
    private String deptName;
    /**
     * 外包服务提供商
     */
//    @Excel(name = "外包服务提供商", orderNum = "6")
    private String vendor;

//    @Excel(name = "供应商编号", orderNum = "7")
    @Excel(name = "外包服务提供商", orderNum = "4")
    private String vendorName;


    /**
     * 服务商注册地（境外、境内）
     */
    @Excel(name = "服务商注册地", orderNum = "5")
    private String registerPlace;
    /**
     * 合同起始时间
     */
    @Excel(name = "合同起始时间", orderNum = "6", format = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 合同到期日
     */
    @Excel(name = "合同到期日", orderNum = "7", format = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 外包项目金额
     */
    @Excel(name = "外包项目金额（元）", orderNum = "8")
    private Double amount;
    /**
     * 外包项目人数
     */
    @Excel(name = "外包项目人数", orderNum = "9")
    private Double nums;

    /**
     * 服务方式（是否驻场）
     */
    @Excel(name = "服务方式（是否驻场）", orderNum = "10")
    private String isStation;
    /**
     * 是否分包或转包
     */
    @Excel(name = "是否分包或转包", orderNum = "11")
    private String isSubcontract;


    private String isDel;

}
