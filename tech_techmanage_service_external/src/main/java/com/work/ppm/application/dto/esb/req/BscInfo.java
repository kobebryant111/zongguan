package com.work.ppm.application.dto.esb.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 通用信息数组2
 */
@Data
@XStreamAlias("bscInfoList")
public class BscInfo {
    /**
     * 应用编号
     */
    @XStreamAlias("AplNo")
    private String AplNo;
    /**
     * 发布日期
     */
    @XStreamAlias("PblsDt")
    private String PblsDt;
    /**
     * 正面图片名称
     */
    @XStreamAlias("PsvImgNm")
    private String PsvImgNm;
    /**
     * BASE64编码
     */
    @XStreamAlias("BASE64Cd")
    private String BASE64Cd;
    /**
     * 内容
     */
    @XStreamAlias("Content")
    private String Content;
    /**
     * 标题名称
     */
    @XStreamAlias("TtlNm")
    private String TtlNm;
    /**
     * 跳转地址2
     */
    @XStreamAlias("Url2")
    private String Url2;

}
