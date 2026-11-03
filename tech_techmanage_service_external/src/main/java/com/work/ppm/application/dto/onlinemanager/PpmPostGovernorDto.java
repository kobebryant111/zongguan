package com.work.ppm.application.dto.onlinemanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import lombok.Data;

import java.util.Date;



 /**
  * 描述
  */
@Data
@ExcelTarget("ppmPostGovernorDto")
public class PpmPostGovernorDto {
	/**
	*	主键ID
	**/
	private Long id;
	 /**
	  *	标题
	  **/
	private String title;
	/**
	*	申请人
	**/
	private String applyBy;
	/**
	*	申请时间
	**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date applyDate;
	 /**
	  *	后督反馈流程编号
	  **/
	private String psireFeedbackNo;
	 /**
	  *	流程名称
	  **/
	 @Excel(name = "流程名称", orderNum = "1")
	private String psireName;
	 /**
	  *	流程编号
	  **/
	 @Excel(name = "流程编号", orderNum = "2")
	 private String psireNo;
	/**
	*	关联人员
	**/
	private String relationUser;
	 /**
	  *	关联人员姓名
	  **/
	 @Excel(name = "关联人员", orderNum = "3")
	private String relationUserName;
	/**
	*	关联部门ID
	**/
	private String dept;
	/**
	*	关联部门名称
	**/
	@Excel(name = "关联部门", orderNum = "4")
	private String deptName;
	/**
	*	问题分类
	**/
	@Excel(name = "问题分类", orderNum = "5")
	private String problemType;
	/**
	*	问题详情
	**/
	@Excel(name = "问题详情", orderNum = "6")
	private String problemDetail;
	/**
	*	状态  1  正常    0  删除   2  草稿
	**/
	private String status;
	/**
	*	流程id
	**/
	private String processInstId;
	/**
	*	反馈内容
	**/
	private String feedbackContent;
	/**
	*	是否合规
	**/
	private String isCompliance;
	/**
	*	备注
	**/
	private String note;
	 /**
	  *	二级部经理意见
	  **/
	private String twoManagerSuggestion;
	 /**
	  * 流程对象
	  */
	 private BpsWorkFlowDto bps;

	 /**
	  * 查询开始时间
	  */
	 private String startDate;
	 /**
	  * 查询结束时间
	  */
	 private String endDate;
	 /**
	  * 流程状态
	  */
	 private String flowStatus;
	 /**
	  * 主键 id 拼接  逗号间隔
	  */
	 private String ids;
}
