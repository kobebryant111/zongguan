package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class AccessCardInfoDto {

    /**
     *  编号
     */
    String id;
    /**
     *  系统编号
     */
    String jobId;
    /**
     *  姓名
     */
    String userName;
    /**
     *  门禁卡编号
     */
    String accessCardNumber;
    /**
     *  是否已领用
     */
    String ifReceiveCard;
    /**
     *  领用时间
     */
    String receiveTime;
    /**
     *  备注
     */
    String remark;
    /**
     *  备用字段1
     */
    String standbyOne;
    /**
     *  备用字段2
     */
    String standbyTwo;
    /**
     *  备用字段3
     */
    String standbyThree;


}
