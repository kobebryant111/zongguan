package com.work.ppm.application.model.projectmanager.ext;

import com.work.ppm.application.model.projectmanager.PpmProject;
import lombok.Data;

/**
 * 描述
 *
 * @date 2020-08-05
 */
@Data
public class PpmprojectExtInfo extends PpmProject {

    /**
     * 系统名称
     */
    private String sysname;

    /**
     * 员工名称
     */
    private String empname;

    /**
     * 系统用户ID
     */
    private String sysuserid;

    /**
     * 系统用户名称
     */
    private String sysusername;

    /**
     * 计划上线
     */
    private String planonline;

    /**
     * 机构
     */
    private String org;

    /**
     * 运维等级
     */
    private String vel;

    /**
     * 业务部门
     */
    private String busdep;

    /**
     * 上线编号
     */
    private String onlineno;

    /**
     * 是否配合类名称
     */
    private String coupledsysname;

    /**
     * 互联网变更场景名称
     */
    private String safetytestname;
}
