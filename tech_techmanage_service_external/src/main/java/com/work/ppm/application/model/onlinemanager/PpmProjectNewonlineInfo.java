package com.work.ppm.application.model.onlinemanager;

import lombok.Data;

/**
 * 项目上线扩展
 */
@Data
public class PpmProjectNewonlineInfo extends  PpmProjectNewonline {
    /**
     * 项目编号
     */
    private String projectNo;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 科技项目经理名称
     */
    private String techUserName;
    /**
     * 科技项目经理id
     */
    private String techUserId;
    /**
     * 业务项目经理名称
     */
    private String busiUserName;
    /**
     * 业务项目经理id
     */
    private String busiUserId;
    /**
     * 业务部门
     */
    private String busiDeptName;
    /**
     * 实施部门
     */
    private String techDeptName;
    /**
     * 上线编号
     */
    private String onlineno;
}
