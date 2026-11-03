package com.work.ppm.application.dto.esb.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;


/**
 * 下发考勤机调用esb入参
 *
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="BODY")
public class PpmKqFaceEsbReqDto {
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
     * SDK编号
     */
    @XStreamAlias("UsrId")
    private String UsrId;

    /**
     * 设备ip
     */
    @XStreamAlias("EqmtIp")
    private String EqmtIp;

    /**
     * 员工信息
     */
    @ApiModelProperty("任务数组")
    @XStreamAlias("array")
    private List<EmpInfoDto> EmpInfArray;

}
