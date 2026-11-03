package com.work.ppm.application.dto.esb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import lombok.Data;

/**
 * 请求头
 *
 */
@Data
@XStreamAliasType("Ret")
public class SysResult {

	/**
	 * 返回码
	 * @return String
	 */
	@XStreamAlias("ReturnCode")
    public String ReturnCode;

    /**
     * 系统流水号 服务请求系统的交易流水号
     * @return String
     */
    @XStreamAlias("ReturnMsg")
    public String ReturnMsg;


}