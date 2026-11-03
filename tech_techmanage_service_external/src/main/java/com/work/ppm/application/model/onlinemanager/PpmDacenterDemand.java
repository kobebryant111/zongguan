package com.work.ppm.application.model.onlinemanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述 科管数据中心需求管理线上化需求 实体类
  */
@Data
public class PpmDacenterDemand {
	/**
	*	主键 ID
	**/
	@Id
	private Long id;
	/**
	*	标题
	**/
	@Excel(name = "流程名称", orderNum = "2")
	private String title;
	/**
	*	流程编号
	**/
	@Excel(name = "流程编号", orderNum = "1")
	private String demandNo;
	/**
	*	申请人
	**/
	@Excel(name = "申请人", orderNum = "5")
	private String createBy;
	/**
	*	申请日期
	**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name = "申请日期", orderNum = "5",exportFormat = "yyyy-MM-dd hh:mm:ss")
	private Date createDate;
	/**
	*	需求负责人所在部门
	**/
	private String demandDept;
	/**
	*	需求负责人
	**/
	@Excel(name = "需求负责人", orderNum = "7")
	private String demandBy;
	/**
	*	联系电话
	**/
	private String phone;
	/**
	*	需求分类
	**/
	private String demandType;
	/**
	*	需求期望上线时间
	**/
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "期望上线日期", orderNum = "9",exportFormat = "yyyy-MM-dd")
	private Date demandOnlineDate;
	/**
	*	关联主系统 id
	**/
	private String mainSystemId;
	/**
	*	需求分析概要
	**/
	private String demandRemakr;
	/**
	*	审批状态
	**/
	private String flowStatus;
	/**
	*	流程id
	**/
	private String processInstId;
	/**
	*	关联需求状态
	**/
	private String relationDemandStatus;
	/**
	*	需求进度
	**/
	private Long relationDemandProgress;
	/**
	*	关联需求编号
	**/
	@Excel(name = "意向编号", orderNum = "10")
	private String relationDemandNo;
	/**
	*	补充说明
	**/
	private String relationDemandRemakr;
	 /**
	  * 需求状态   1  正常    0  删除
	  */
	private String status;
}
