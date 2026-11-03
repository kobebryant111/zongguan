package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class ReturnCardInfoDto {

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
     *  是否已归还
     */
    String ifReturnCard;
    /**
     *  归还时间
     */
    String returnTime;
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
