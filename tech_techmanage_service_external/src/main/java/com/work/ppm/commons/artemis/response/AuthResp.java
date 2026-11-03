package com.work.ppm.commons.artemis.response;

import lombok.Data;

import java.util.Date;

/**
 * 权限配置
 */
@Data
public class AuthResp {
    //人员数据编号
    private String personDataId;
    //人员数据类型， person 人员， org 组织
    private String personDataType;
    //源数据编号，resourceDataType为设备通道时值为设备indexcode；
    //resourceDataType为设备通道分组时值为设备通道分组编号；
    private String resourceIndexCode;
    //资源类型
    private String resourceType;
    //资源数据类型，resource 设备通道， resourceGroup 设备通道分组
    private String resourceDataType;
    //通道号
    private String channelNo;
    //资源通道唯一标识（在权限下载和配置接口入参时对应查询接口会返回，不作为查询接口的查询条件）
    private String channelIndexCode;
    //计划模板编号，为空时，默认为全天候计划模板。
    private String templateId;
    //开始日期
    private Date startTime;
    //结束日期
    private Date endTime;
}
