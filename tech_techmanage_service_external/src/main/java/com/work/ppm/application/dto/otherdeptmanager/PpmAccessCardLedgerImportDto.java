package com.work.ppm.application.dto.otherdeptmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;


/**
  *  外包人员监保部台账 传输对象
  */
@Data
public class PpmAccessCardLedgerImportDto {

	/**
	*	人员编号
	**/
	@Excel(name = "人员编号", orderNum = "1")
	private String virtualMachineId;

	/**
	*	银行卡号
	**/
	@Excel(name = "银行卡号", orderNum = "2")
	private String creditCardNumbers;
	/**
	*	户号
	**/
	@Excel(name = "户名", orderNum = "3")
	private String accountNumber;
	 /**
	  *	开户地所在省份
	  **/
	 @Excel(name = "开户地所在省份", orderNum = "4")
	 private String placeOfAccount;
	/**
	  *	转出卡银行名称 OUT_BANK_NAME
	  **/
	 @Excel(name = "转出卡银行名称", orderNum = "5")
	 private String outBankName;
	 /**
	  *	备注YGB
	  **/
	 @Excel(name = "备注", orderNum = "6")
	 private String remarkYgb;

}
