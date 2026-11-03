package com.work.ppm.application.dto.esb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * EsbProxyReq
 *
 */
@Data
public class EsbProxyReq {
    /**
     * 服务名称
     */
    @XStreamAlias("SvcNm")
    private String svcNm;

    /**
     * 跳转地址
     */
    @XStreamAlias("Url")
    private String url;

    /**
     * 发送类型
     */
    @XStreamAlias("SendTp")
    private String sendTp;

    /**
     * 透传标志
     */
    @XStreamAlias("PsThgFlg")
    private String psThgFlg;

    /**
     * 交易代码
     */
    @XStreamAlias("TxnCd")
    private String txnCd;
}
