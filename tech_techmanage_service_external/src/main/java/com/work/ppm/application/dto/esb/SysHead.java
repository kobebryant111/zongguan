package com.work.ppm.application.dto.esb;

import cn.hutool.core.date.DateUtil;
import com.work.ppm.commons.constant.Constants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 请求头
 *
 */
@Data
@XStreamAliasType("SYS_HEAD")
public class SysHead  {

	/**
	 * 消费系统编号 服务请求系统编号
	 * @return String
	 */
	@XStreamAlias("ConsumerId")
    public String consumerId = Constants.ESB_CONSUMER_ID;

    /**
     * 系统流水号 服务请求系统的交易流水号
     * @return String
     */
    @XStreamAlias("ConsumerSeqNo")
    public String consumerSeqNo;

    /**
     * 服务请求系统的日期
     * @return String
     */
    @XStreamAlias("TranDate")
    public String tranDate = DateUtil.format(new Date(),"yyyy-MM-dd");

    /**
     * 服务代码
     * @return xx
     */
    @XStreamAlias("ServiceCode")
    public String serviceCode;

    /**
     *   交易时间
     * @return String
     */
    @XStreamAlias("TranTime")
    public String tranTime = DateUtil.format(new Date(),"HH:mm:ss");

	/**
	 *  服务场景
	 * @return String
	 */
    @XStreamAlias("ServiceScene")
    public String serviceScene;

//    /** 消金esb去掉字段
//     *  // 文件标志
//     * @return  String
//     */
//    @XStreamAlias("FileFlag")
//    public String fileFlag="0";
//
//    /**
//     *  渠道类型
//     * @return String
//     */
//    @XStreamAlias("ChannelType")
//    public String channelType="1";

    /**
     * // 业务流水号
     * @return xx
     */
    @XStreamAlias("OrgConsumerSeqNo")
    public String orgConsumerSeqNo;

    /**
     *  // 服务请求系统的终端标志号
     * @return xx
     */
    @XStreamAlias("TerminalCode")
    public String terminalCode;

    /**
     *  // 服务原始请求系统的终端标志号
     * @return xx
     */
    @XStreamAlias("OrgTerminalCode")
    public String orgTerminalCode;




    /**
     * 系统流水号 服务请求系统的交易流水号
     * @return xx
     */
    @XStreamAlias("SeqNo")
    public String seqNo;

    /**
     * // 服务请求系统的日期
     * @return xx
     */
    @XStreamAlias("TxnDt")
    public String txnDt;

    /**
     *   // 交易时间
     * @return xx
     */
    @XStreamAlias("TxnTm")
    public String txnTm;

    /**
     * 交易結果
     * @return xx
     */
    @XStreamAlias("ReturnStatus")
    public String returnStatus;


    /**
     * 结果
     */
    @XStreamAlias("array")
    public List<SysResult> sysResult;

}