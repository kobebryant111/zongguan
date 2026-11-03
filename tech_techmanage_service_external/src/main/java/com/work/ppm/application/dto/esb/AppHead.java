package com.work.ppm.application.dto.esb;


import com.work.ppm.commons.constant.Constants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import lombok.Data;

/**
 * AppHead
 *
 */
@Data
@XStreamAliasType("APP_HEAD")
public class AppHead{
	/**
	 * 交易柜员号  服务请求者的唯一标识
	 * @return String
	 */
	@XStreamAlias("TranTellerNo")
	public String tranTellerNo = Constants.ESB_TRAN_TELLER_NO;

	/**
	 * 交易机构
	 * @return String
	 */
	@XStreamAlias("TranBranchId")
	public String tranBranchId;




}