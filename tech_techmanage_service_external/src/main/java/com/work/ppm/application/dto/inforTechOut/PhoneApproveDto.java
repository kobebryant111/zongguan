package com.work.ppm.application.dto.inforTechOut;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PhoneApproveDto {
    /**
     * 流程实例ID
     */
    private String instanceid;
    /**
     * 审核结论(01：通过，02：退回，03：授权，04：撤回，05：调度)
     */
    @ApiModelProperty(value = "审核结论(01：通过，02：退回，03：授权，04：撤回，05：调度)", name = "conclusion")
    private String conclusion;
    /**
     * 审核描述
     */
    @ApiModelProperty(value = " 审核描述", name = "suggest")
    private String suggest;
    /**
     * 用户id
     */
    @ApiModelProperty(value = " 用户id", name = "userId")
    private String userId;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", name = "userName")
    private String userName;
}
