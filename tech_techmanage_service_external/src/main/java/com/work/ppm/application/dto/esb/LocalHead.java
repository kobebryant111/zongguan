package com.work.ppm.application.dto.esb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import lombok.Data;

/**
 * 交易码
 *
 */
@Data
@XStreamAliasType("LOCAL_HEAD")
public class LocalHead {
	//服务请求系统交易的唯一标识
	/**
	 * 交易码
	 * @return xx
	 */
	@XStreamAlias("TranCode")
	public String tranCode;

	/**
	 * txnCd
	 * @return xx
	 */
	@XStreamAlias("TxnCd")
	public String txnCd;

}