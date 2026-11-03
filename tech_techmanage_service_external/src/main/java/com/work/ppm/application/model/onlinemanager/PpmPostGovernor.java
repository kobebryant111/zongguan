package com.work.ppm.application.model.onlinemanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 后督反馈
  */
@Data
public class PpmPostGovernor {
	/**
	*	主键ID
	**/
	@Id
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
	 @Excel(name = "后督反馈流程编号", orderNum = "7")
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
	private String problemType;
	/**
	*	问题详情
	**/
	@Excel(name = "问题详情", orderNum = "6")
	private String problemDetail;
	/**
	*		状态  1  正常    0  删除   2  草稿
	**/
	private String status;
	/**
	*	流程id
	**/
	private String processInstId;
	/**
	*	反馈内容
	**/
	@Excel(name = "反馈内容", orderNum = "8")
	private String feedbackContent;
	/**
	*	是否合规
	**/
	@Excel(name = "是否合规", orderNum = "10")
	private String isCompliance;
	/**
	*	备注
	**/
	@Excel(name = "备注", orderNum = "11")
	private String note;
	 /**
	  *	二级部经理意见
	  **/
	 @Excel(name = "二级部经理意见", orderNum = "9")
	 private String twoManagerSuggestion;
}
