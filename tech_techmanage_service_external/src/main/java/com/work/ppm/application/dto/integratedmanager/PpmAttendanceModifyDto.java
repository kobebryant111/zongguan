package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import lombok.Data;
import java.util.Date;



 /**
  * 考勤修改流程
  */
@Data
@ExcelTarget("ppmAttendanceModifyDto")
public class PpmAttendanceModifyDto {
	/**
	*	自增id
	**/
	private Long id;
	/**
	*	流程id
	**/
	private String instanceId;
	/**
	*	部门编号
	**/
	private String depId;
	/**
	*	发起人
	**/
	private String badge;
	/**
	*	修改原因描述
	**/
	@Excel(name = "修改原因描述", orderNum = "4")
	private String reasonDes;
	/**
	*	修改内容描述
	**/
	@Excel(name = "修改内容描述", orderNum = "5")
	private String contentDes;
	/**
	*	创建时间
	**/
	private Date addDate;
	/**
	*	是否删除(0:未删除，1：删除)
	**/
	private Integer isdel;
	 /**
	  发起人名称
	  */
	@Excel(name = "发起人", orderNum = "2")
	private String userName;

	 /**
	  bps 对象
	  */
	private BpsWorkFlowDto bps;
	 /**
	  * 部门名称
	  */
	 @Excel(name = "所在部门", orderNum = "3")
	 private String deptName;
	 /**
	   创建时间 字符串
	  */
	 @Excel(name = "发起时间", orderNum = "1")
	 private String addDateStr;
}
