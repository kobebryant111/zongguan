package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude
public class InterviewApproveProcessDto extends CommonApproveProcessDto{

    /**
     *  参数
     */
    Map<String,Object> params;

}
