package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class IntervieweePriceInfoDto {

    /**
     * 供应商名称
     */
    String outsourcname;
    /**
     * 人员类型
     */
    String positionName;
    /**
     * 一档（1-3）
     */
    String firstPrice;
    /**
     * 二档（3-5）
     */
    String secondPrice;
    /**
     * 三档（5-7）
     */
    String thirdPrice;
    /**
     * 四档（7年以上）
     */
    String fourthPrice;
    /**
     * 人员类型编码
     */
    String remarks;


}
