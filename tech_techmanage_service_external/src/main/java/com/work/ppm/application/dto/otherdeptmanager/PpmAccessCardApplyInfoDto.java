package com.work.ppm.application.dto.otherdeptmanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;



 /**
  *  监保部台账门禁卡流程详情 传输对象
  */
@Data
@Builder
public class PpmAccessCardApplyInfoDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "String")
	private String id;
	/**
	*	流程编号
	**/
	@ApiModelProperty(name = "instanceId", value = "流程编号", dataType = "String")
	private String instanceId;
	/**
	*	工号
	**/
	@ApiModelProperty(name = "jobId", value = "工号", dataType = "String")
	private String jobId;
	/**
	*	业务id
	**/
	@ApiModelProperty(name = "bizObjectId", value = "业务id", dataType = "String")
	private String bizObjectId;
	/**
	*	部门编号
	**/
	@ApiModelProperty(name = "deptId", value = "部门编号", dataType = "String")
	private String deptId;
	/**
	*	人员编号
	**/
	@ApiModelProperty(name = "virtualMachineId", value = "人员编号", dataType = "String")
	private String virtualMachineId;
	/**
	*	门禁卡编号
	**/
	@ApiModelProperty(name = "accessCardNumber", value = "门禁卡编号", dataType = "String")
	private String accessCardNumber;
	/**
	*	是否已领用
	**/
	@ApiModelProperty(name = "ifReceiveCard", value = "是否已领用", dataType = "String")
	private String ifReceiveCard;
	/**
	*	领用日期
	**/
	@ApiModelProperty(name = "receiveTime", value = "领用日期", dataType = "Date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date receiveTime;
	/**
	*	是否已归还
	**/
	@ApiModelProperty(name = "ifReturnCard", value = "是否已归还", dataType = "String")
	private String ifReturnCard;
	/**
	*	归还日期
	**/
	@ApiModelProperty(name = "returnTime", value = "归还日期", dataType = "Date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date returnTime;
	/**
	*	备注
	**/
	@ApiModelProperty(name = "remark", value = "备注", dataType = "String")
	private String remark;
	/**
	 *	是否删除 0否 1是
	 **/
	@ApiModelProperty(name = "isDel", value = "是否删除 0否 1是", dataType = "String")
	private String isDel;
	/**
	*	创建时间
	**/
	@ApiModelProperty(name = "createdTime", value = "创建时间", dataType = "Date")
	private Date createdTime;
	/**
	*	创建人
	**/
	@ApiModelProperty(name = "createdBy", value = "创建人", dataType = "String")
	private String createdBy;
	/**
	*	修改时间
	**/
	@ApiModelProperty(name = "modifiedTime", value = "修改时间", dataType = "Date")
	private Date modifiedTime;
	/**
	*	修改人
	**/
	@ApiModelProperty(name = "modifiedBy", value = "修改人", dataType = "String")
	private String modifiedBy;

	 /**
	  * 姓名
	  */
	 private String userName;
	 /**
	  * 身份证号
	  */
	 private String idCardNo;
	 /**
	  * 电话
	  */
	 private String telephone;
	 /**
	  * 外包商编号
	  */
	 private String outsourceNo;
	 /**
	  * 外包商名称
	  */
	 private String outsourceName;
	 /**
	  * 部门名称
	  */
	 private String deptName;
	 /**
	  * 是否发起归还
	  */
	 private String ifCreateReturn;
	 /**
	  * 是否需要制卡
	  */
	 private String ifMakeCard;
	 /**
	  * 银行卡号
	  */
	 private String creditCardNumbers;
	 /**
	  * 户号
	  */
	 private String accountNumber;
	 /**
	  * 开户地 PLACE_OF_ACCOUNT
	  */
	 private String placeOfAccount;
	 /**
	  * 性别
	  */
	 private String sex;
	 /**
	  * REMARK_YGB
	  */
	 private String remarkYgb;
	 /**
	  * REMARK_JBB
	  */
	 private String remarkJbb;

	 private String standbyTwo;
}
