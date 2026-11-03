package com.work.ppm.application.model.otherdeptmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 外包人员监保部台账 实体对象
  */
@Data
public class PpmAccessCardLedger {

	/**
	*	唯一编号
	**/
	@Id
	private String id;
	/**
	 *	继承data的id
	 **/
	private String pid;
	/**
	*	工号
	**/
	private String jobId;
	/**
	*	是否发起归还
	**/
	private String ifCreateReturn;
	/**
	*	是否需要制卡
	**/
	private String ifMakeCard;
	/**
	*	人员编号
	**/
	private String virtualMachineId;
	/**
	*	门禁卡编号
	**/
	private String accessCardNumber;
	/**
	*	是否已领用
	**/
	private String ifReceiveCard;
	/**
	*	领用日期
	**/
	private Date receiveTime;
	/**
	*	是否已归还
	**/
	private String ifReturnCard;
	/**
	*	归还日期
	**/
	private Date returnTime;
	/**
	*	银行卡号
	**/
	private String creditCardNumbers;
	/**
	*	户号
	**/
	private String accountNumber;
	 /**
	  *	开户地所在省份
	  **/
	 private String placeOfAccount;
	 /**
	  *	转出卡银行名称
	  **/
	 private String outBankName;
	 /**
	  *	备注YGB
	  **/
	 private String remarkYgb;
	 /**
	  *	备注JBB
	  **/
	 private String remarkJbb;
	 /**
	  *	是否删除 0否 1是
	  **/
	 private String isDel;
	 /**
	  *	流程状态
	  **/
	 private String state;
	 /**
	  *	创建时间
	  **/
	 private Date createdTime;
	 /**
	  *	创建人
	  **/
	 private String createdBy;
	 /**
	  *	修改时间
	  **/
	 private Date modifiedTime;
	 /**
	  *	修改人
	  **/
	 private String modifiedBy;
}
