package com.work.ppm.application.dto.workflowmanager.ext;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class CommonDetailDto extends ReadyParamsDto{

    /**
     *  编号
     */
    String id;
    /**
     *  申请人部门
     */
    String applyDeptId;
    /**
     *  申请人部门名称
     */
    String applyDeptName;
    /**
     *  申请人编号
     */
    String applyUserId;
    /**
     *  申请人名称
     */
    String applyUserName;

    /**
     * 流程实例id
     */
    String instanceid;


}
