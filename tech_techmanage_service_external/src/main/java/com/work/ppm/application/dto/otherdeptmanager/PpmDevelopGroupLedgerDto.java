package com.work.ppm.application.dto.otherdeptmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;



 /**
  *  研发小组台账 传输对象
  */
@Data
public class PpmDevelopGroupLedgerDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "String")
	private String id;
	/**
	*	系统管理
	**/
	@ApiModelProperty(name = "jobId", value = "系统编号", dataType = "String")
	@Excel(name = "系统编号", orderNum = "1")
	private String jobId;
	/**
	*	小组编号
	**/
	@ApiModelProperty(name = "groupId", value = "小组编号", dataType = "String")
	private String groupId;
	/**
	*	是否星级人员
	**/
	@ApiModelProperty(name = "ifStarPerson", value = "是否星级人员", dataType = "String")
	@Excel(name = "是否星级人员", orderNum = "13", replace = {"是_Y","否_N"})
	private String ifStarPerson;
	/**
	*	备注
	**/
	@ApiModelProperty(name = "remark", value = "备注", dataType = "String")
	@Excel(name = "备注", orderNum = "14")
	private String remark;
	/**
	*	创建人
	**/
	@ApiModelProperty(name = "createdBy", value = "创建人", dataType = "String")
	private String createdBy;
	/**
	*	创建时间
	**/
	@ApiModelProperty(name = "createdTime", value = "创建时间", dataType = "Date")
	private Date createdTime;
	/**
	*	修改人
	**/
	@ApiModelProperty(name = "modifiedBy", value = "修改人", dataType = "String")
	private String modifiedBy;
	/**
	*	修改时间
	**/
	@ApiModelProperty(name = "modifiedTime", value = "修改时间", dataType = "Date")
	private Date modifiedTime;

	 /**
	  *	姓名
	  **/
	 @Excel(name = "姓名", orderNum = "2")
	 private String userName;
	 /**
	  *	供应商编号
	  **/
	 private String outsourceNo;
	 /**
	  *	供应商名称
	  **/
	 @Excel(name = "供应商", orderNum = "4")
	 private String outsourceName;
	 /**
	  *	门禁卡编号
	  **/
	 @Excel(name = "虚拟机号", orderNum = "3")
	 private String vmAccount;
	 /**
	  *	岗位编号
	  **/
	 private String postId;
	 /**
	  *	岗位名称
	  **/
	 private String postName;
	 /**
	  *	入场时间
	  **/
	 @JsonFormat(pattern = "yyyy-MM-dd")
	 @Excel(name = "入场时间", orderNum = "7", exportFormat = "yyyy-MM-dd")
	 private Date entryTime;
	 /**
	  *	离场时间
	  **/
	 @JsonFormat(pattern = "yyyy-MM-dd")
	 @Excel(name = "离场时间", orderNum = "8", exportFormat = "yyyy-MM-dd")
	 private Date leavingTime;
	 /**
	  *	小组名称
	  **/
	 @Excel(name = "小组名称" ,orderNum = "10")
	 private String groupName;
	 /**
	  *	小组长编号
	  **/
	 private String groupLeaderId;
	 /**
	  *	小组长名称
	  **/
	 @Excel(name = "小组长姓名" ,orderNum = "11")
	 private String groupLeaderName;
	 /**
	  *	小组长虚拟机号
	  **/
	 @Excel(name = "小组长虚拟机号" ,orderNum = "12")
	 private String groupLeaderCard;
	 /**
	  *	小组性质
	  **/
	 private String groupType;
	 /**
	  *	部门编号
	  **/
	 private String deptId;
	 /**
	  *	部门名称
	  **/
	 @Excel(name = "所属部门" ,orderNum = "9")
	 private String deptName;
	 /**
	  *	状态
	  **/
	 private String state;
	 /**
	  *	状态
	  **/
	 @Excel(name = "状态", orderNum = "6")
	 private String stateName;
	 /**
	  *	外包方式
	  **/
	 private String outsourceType;
	 /**
	  *	外包方式名称
	  **/
	 @Excel(name = "类型", orderNum = "5")
	 private String outsourceTypeName;
}
