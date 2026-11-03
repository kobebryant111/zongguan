package com.work.ppm.application.dto.otherdeptmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


/**
  *  外包人员监保部台账 传输对象
  */
@Data
@Builder
public class PpmAccessCardLedgerDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "String")
	private String id;
	/**
	 *	继承data的id
	 **/
	@ApiModelProperty(name = "pid", value = "继承data的id", dataType = "String")
	private String pid;
	/**
	*	工号
	**/
	@ApiModelProperty(name = "jobId", value = "工号", dataType = "String")
	private String jobId;
	/**
	*	是否发起归还
	**/
	@ApiModelProperty(name = "ifCreateReturn", value = "是否发起归还", dataType = "String")
	private String ifCreateReturn;
	/**
	*	是否需要制卡
	**/
	@ApiModelProperty(name = "ifMakeCard", value = "是否需要制卡", dataType = "String")
	private String ifMakeCard;
	/**
	*	人员编号
	**/
	@ApiModelProperty(name = "virtualMachineId", value = "人员编号", dataType = "String")
	@Excel(name = "人员编号", orderNum = "1")
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
	*	银行卡号
	**/
	@ApiModelProperty(name = "creditCardNumbers", value = "银行卡号", dataType = "String")
	@Excel(name = "银行卡号", orderNum = "2")
	private String creditCardNumbers;
	/**
	*	户号
	**/
	@ApiModelProperty(name = "accountNumber", value = "户号", dataType = "String")
	@Excel(name = "户名", orderNum = "3")
	private String accountNumber;
	 /**
	  *	开户地所在省份
	  **/
	 @ApiModelProperty(name = "placeOfAccount", value = "开户地所在省份", dataType = "String")
	 @Excel(name = "开户地所在省份", orderNum = "4")
	 private String placeOfAccount;
	/**
	  *	转出卡银行名称 OUT_BANK_NAME
	  **/
	 @ApiModelProperty(name = "outBankName", value = "转出卡银行名称", dataType = "String")
	 @Excel(name = "转出卡银行名称", orderNum = "5")
	 private String outBankName;
	 /**
	  *	备注YGB
	  **/
	 @ApiModelProperty(name = "remarkYgb", value = "备注YGB", dataType = "String")
	 @Excel(name = "备注", orderNum = "6")
	 private String remarkYgb;
	 /**
	  *	备注JBB
	  **/
	 @ApiModelProperty(name = "remarkJbb", value = "备注JBB", dataType = "String")
	 private String remarkJbb;
	 /**
	 * 是否删除 0否 1是
	 **/
	 @ApiModelProperty(name = "isDel", value = "是否删除 0否 1是", dataType = "String")
	 private String isDel;
	 /**
	  *	状态 OLD 旧数据 NEW 新数据
	  **/
	 @ApiModelProperty(name = "state", value = "状态 OLD 旧数据 NEW 新数据", dataType = "String")
	 private String state;
	 /**
	  *	备注
	  **/
	 private String remark;
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
	  *	流程编号
	  **/
	 private String deptName;
	 /**
	  *	工号
	  **/
	 private String idCardNo;
	 /**
	 *	性别
	 **/
	 private String sex;
	 /**
	  *	是否发起归还
	  **/
	 private String telephone;
	 /**
	  *	是否需要制卡
	  **/
	 private String outsourceName;
	 /**
	  * 前端修改权限
	  */
	 private String ifUpdateRoles;

	 /**
	  * id集合
	  */
	 private String idList;
}
