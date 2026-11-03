package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.work.ppm.application.dto.workflowmanager.ext.ReadyParamsDto;
import lombok.Data;

import java.util.Date;

@Data
public class DetailFlowLogDto extends ReadyParamsDto {

    /**
     *  审批节点
     */
    String applydesc;
    /**
     * 审批决策 01:通过，02:退回，03:授权，04:撤回
     */
    String conclusion;
    /**
     * 审批时间
     */
    @JsonInclude
    Date createtime;
    /**
     * 审批部门名称
     */
    String deptname;
    /**
     * 审批意见内容
     */
    String suggest;
    /**
     * 审批人联系方式
     */
    String tel;
    /**
     * 审批人用户id
     */
    String userid;
    /**
     * 审批人用户名称
     */
    String username;
}
