package com.work.ppm.application.model.integratedmanager;

import lombok.Data;

import java.util.Date;

/**
 * 描述
 */
@Data
public class OutFlowData {
    /**
     * 工号
     */
    private String jobid;
    /**
     * 流程类型
     */
    private String flowtype;
    /**
     * 流程标题
     */
    private String flowtitle;
    /**
     * instanceid
     */
    private String instanceid;
    /**
     * 流程编号
     */
    private String flowid;
    /**
     * 流程的url
     */
    private String flowurl;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 最后审批时间
     */
    private String lastmodtime;
}
