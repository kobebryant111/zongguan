package com.work.ppm.application.dto.esb.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;

/**
 * 离场发起申请
 *
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BODY")
public class PpmKqOutsourcingDto {
    /**
     * 行内系统标识
     */
    @XStreamAlias("CmdbSysName")
    private String cmdbSysName;

    /**
     * 行内系统ip
     */
    @XStreamAlias("BankSysIP")
    private String bankSysIP;
    /**
     *  目的系统标识
     */
    @XStreamAlias("HDBnkInrSysNm")
    private String hDBnkInrSysNm;
    /**
     * 子公司系统ip
     */
    @XStreamAlias("HDBnkOutSysIp")
    private String hDBnkOutSysIp;
    /**
     * 交易代码
     */
    @XStreamAlias("TranCode")
    private String TranCode;
    /**
     * 服务代码
     */
    @XStreamAlias("ServiceCode")
    private String ServiceCode;
    /**
     * 服务场景
     */
    @XStreamAlias("ServiceScene")
    private String ServiceScene;
    /**
     * 业务流水号
     */
    @XStreamAlias("OrgConsumerSeqNo")
    private String OrgConsumerSeqNo;
    /**
     * 交易日期
     */
    @XStreamAlias("TranDate")
    private String TranDate;
    /**
     * 交易时间
     */
    @XStreamAlias("TranTime")
    private String TranTime;
    /**
     * 服务名称
     */
    @XStreamAlias("SvcNm")
    private String SvcNm;
    /**
     * 跳转地址
     */
    @XStreamAlias("Url")
    private String Url;
    /**
     * 交易代码
     */
    @XStreamAlias("TxnCd")
    private String TxnCd;
    /**
     * 发送类型
     */
    @XStreamAlias("SendTp")
    private String SendTp;
    /**
     * 透传标志
     */
    @XStreamAlias("PsThgFlg")
    private String PsThgFlg;
    /**
     * 系统流水号
     */
    @XStreamAlias("ConsumerSeqNo")
    private String ConsumerSeqNo;
    /**
     * 消费系统编号
     */
    @XStreamAlias("ConsumerId")
    private String ConsumerId;
    /**
     * ID
     */
    @XStreamAlias("ID")
    private String ID;
    /**
     * 通用信息数组
     */
    @ApiModelProperty("通用信息数组")
    @XStreamImplicit(itemFieldName = "array")
    private List<IssuInfArray> list;
    /**
     * 应用biz
     */
    @XStreamAlias("BizCd")
    private String BizCd;
    /**
     * 推送标志
     */
    @XStreamAlias("SndFlg")
    private boolean SndFlg;
    /**
     * 消息类型、信息类型
     */
    @XStreamAlias("InfTp")
    private String InfTp;
    /**
     * 通用信息数组2
     */
    @XStreamImplicit(itemFieldName = "BscInfo")
    @ApiModelProperty("通用信息数组2")
    private List<BscInfo> bscInfoList;


    /**
     * 组合类型
     */
    @XStreamAlias("CmbTp")
    private String CmbTp;
    /**
     * 通用标志1
     */
    @XStreamAlias("CommFlag1")
    private String CommFlag1;
    /**
     * 消息内容
     */
    @XStreamAlias("MsgCntnt")
    private String MsgCntnt;
    /**
     * 值
     */
    @XStreamAlias("Value")
    private String Value;
    /**
     * 文本信息1
     */
    @XStreamAlias("Rcrdtxt1")
    private String Rcrdtxt1;
    /**
     * 值2
     */
    @XStreamAlias("Value2")
    private String Value2;
    /**
     * 应用编号2
     */
    @XStreamAlias("AplNo2")
    private String AplNo2;
    /**
     * 短信内容2
     */
    @XStreamAlias("ShrtInf2")
    private String ShrtInf2;
    /**
     * 消息描述
     */
    @XStreamAlias("MsgDsc")
    private String MsgDsc;
    /**
     * 跳转地址3
     */
    @XStreamAlias("Url3")
    private String Url3;
    /**
     * 值3
     */
    @XStreamAlias("Value3")
    private String Value3;
    /**
     * 通用标志2
     */
    @XStreamAlias("CommFlag2")
    private String CommFlag2;

}
