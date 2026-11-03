package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;
import java.util.Date;



 /**
  * 外包黑名称
  */
@Data
@ExcelTarget("ppmOutsourcingBlacklistDto")
public class PpmOutsourcingBlacklistDto {
	/**
	*	姓名
	**/
	@Excel(name = "姓名", orderNum = "1")
	private String userName;
	/**
	*	身份证
	**/
	@Excel(name = "身份证号", orderNum = "2")
	private String icard;
	/**
	*	二级部
	**/
	private String depId;
	/**
	*	供应商
	**/

	private String orgCode;
	/**
	*	离职时间
	**/
	private Date officeDate;
	/**
	*	原因
	**/
	@Excel(name = "原因", orderNum = "6")
	private String why;
	/**
	*	创建时间
	**/
	private Date addDate;
	/**
	*	创建人工号
	**/
	private String addUserId;
	/**
	*	创建人名称
	**/
	private String addUserName;
	/**
	*	是否删除（0:正常，1：删除）
	**/
	private Integer isdel;
	/**
	*	自增编号
	**/
	private Long id;

	 /**
	  *	二级部部门名称
	  **/
	 @Excel(name = "二级部", orderNum = "3")
	 private String depName;
	 /**
	  *	供应商名称
	  **/
	 @Excel(name = "供应商", orderNum = "4")
	 private String supplier;
	 /**
	  *	离职时间
	  */
	 @Excel(name = "离职时间", orderNum = "5")
	 private String officeDateStr;
}
