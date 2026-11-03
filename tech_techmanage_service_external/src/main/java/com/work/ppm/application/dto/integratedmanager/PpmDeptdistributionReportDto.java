package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;


/**
 * 入职部门分配报表
 */
@Data
@ExcelTarget("ppmDeptdistributionReport")
public class PpmDeptdistributionReportDto {
	/**
	*	自增编号
	**/
	private Long id;
	/**
	*	部门编号
	**/
	private String depId;
	/**
	*	各部门总编制数
	**/
	@Excel(name = "各部门总编制数", orderNum = "2")
	private Double depNum;
	/**
	*	研发
	**/
	@Excel(name = "研发", orderNum = "3")
	private Double researchNum;
	/**
	*	产品经理
	**/
	@Excel(name = "产品经理", orderNum = "4")
	private Double productNum;
	/**
	*	应用支持
	**/
	@Excel(name = "应用支持", orderNum = "5")
	private Double appSuportNum;
	/**
	*	系统运维
	**/
	@Excel(name = "系统运维", orderNum = "6")
	private Double systemNum;
	/**
	*	测试
	**/
	@Excel(name = "测试", orderNum = "7")
	private Double testNum;
	/**
	*	职能管理
	**/
	@Excel(name = "职能管理", orderNum = "8")
	private Double functionsifNum;
	/**
	*	批次
	**/
	private String batch;
    /**
    * 部门名称
     */
    @Excel(name = "部门", orderNum = "1")
	private String depName;
}
