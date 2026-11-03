package com.work.ppm.application.dto.otherdeptmanager;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.excel.annotation.Excel;
import java.util.Date;



/**
 *  研发小组台账列表 传输对象
 */
@Data
@ExcelTarget("PpmDevelopGroupInfoDto")
public class PpmDevelopGroupInfoDto {
	/**
	 *	唯一编号
	 **/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "String")
	private String id;
	/**
	 *	部门编号
	 **/
	@ApiModelProperty(name = "deptId", value = "部门编号", dataType = "String")
	private String deptId;
	/**
	 *	部门名称
	 **/
	@ApiModelProperty(name = "deptName", value = "部门名称", dataType = "String")
	@Excel(name = "部门名称", orderNum = "1")
	private String deptName;
	/**
	 *	小组名称
	 **/
	@ApiModelProperty(name = "groupName", value = "小组名称", dataType = "String")
	@Excel(name = "小组名称", orderNum = "2")
	private String groupName;
	/**
	 *	小组长编号
	 **/
	@ApiModelProperty(name = "groupLeaderId", value = "小组长编号", dataType = "String")
	private String groupLeaderId;
	/**
	 *	小组长名称
	 **/
	@ApiModelProperty(name = "groupLeaderName", value = "小组长名称", dataType = "String")
	@Excel(name = "小组长名称", orderNum = "3")
	private String groupLeaderName;
	/**
	 *	小组长虚拟机号
	 **/
	@ApiModelProperty(name = "groupLeaderCard", value = "小组长虚拟机号", dataType = "String")
	@Excel(name = "小组长虚拟机号", orderNum = "4")
	private String groupLeaderCard;
	/**
	 *	小组性质
	 **/
	@ApiModelProperty(name = "groupType", value = "小组性质", dataType = "String")
	@Excel(name = "小组性质", orderNum = "5", replace = {"研发_1","测试_2"})
	private String groupType;
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
}
