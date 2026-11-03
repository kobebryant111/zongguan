package com.work.ppm.application.dto.integratedmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *  人脸考勤数据表 传输对象
 */
@Data
public class PpmKqFaceDataDto {

    /**
     * 唯一编号 ID
     **/
    @ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
    private Long id;
    /**
     * IP地址 IP_ADDRESS
     **/
    @ApiModelProperty(name = "ipAddress", value = "IP地址", dataType = "String")
    private String ipAddress;
    /**
     * 工号 CARD_NO
     **/
    @ApiModelProperty(name = "cardNO", value = "工号", dataType = "String")
    private String cardNO;
    /**
     *	打卡时间 PUNCH_TIME
     **/
    @ApiModelProperty(name = "punchTime", value = "打卡时间", dataType = "String")
    private String punchTime;
    /**
     *	状态 STATE
     **/
    @ApiModelProperty(name = "state", value = "状态", dataType = "String")
    private String state;
    /**
     *	创建时间 CREATED_DATE
     **/
    @ApiModelProperty(name = "createdDate", value = "创建时间", dataType = "Date")
    private Date createdDate;
    /**
     *	类型 TYPE
     **/
    @ApiModelProperty(name = "type", value = "类型", dataType = "String")
    private String type;




}
