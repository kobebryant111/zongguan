package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude
/**
 * 通用审批接口带参数
 */
public class CommonApproveParamsDto extends CommonApproveProcessDto{

    /**
     *  参数
     */
    Map<String,Object> params;

}
