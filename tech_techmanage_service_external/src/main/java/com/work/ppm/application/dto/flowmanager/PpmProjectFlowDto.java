package com.work.ppm.application.dto.flowmanager;

import lombok.Data;

import java.util.Date;

/**
 *实体类
 */
@Data
public class PpmProjectFlowDto {
    /**
     *	字段名
     **/
    private Long id;
    /**
     *	字段名
     **/
    private String projectno;
    /**
     *	字段名
     **/
    private String flowtitle;
    /**
     *	字段名
     **/
    private String flowname;
    /**
     *	字段名
     **/
    private String instanceid;
    /**
     *	字段名
     **/
    private String flowid;
    /**
     *	字段名
     **/
    private String flowurl;
    /**
     *	字段名
     **/
    private String flowcreateuserid;
    /**
     *	字段名
     **/
    private Date startdate;
    /**
     *	字段名
     **/
    private Date enddate;
    /**
     *	字段名
     **/
    private String flowstatus;
    /**
     *	字段名
     **/
    private Integer isdel;
    /**
     *	字段名
     **/
    private Date createtime;
    /**
     *	字段名
     **/
    private String creators;
    /**
     *	字段名
     **/
    private Date lastmodtime;
    /**
     *	字段名
     **/
    private String lastmoduser;
    /**
     *	字段名
     **/
    private String clr;
    /**
     *	字段名
     **/
    private String activityname;
    /**
     *	字段名
     **/
    private String clrid;
    /**
     *	流程节点id
     **/
    private String activityid;
    /**
     * 部门编号
     */
    private String deptNo;
    /**
     * 用户id，多个, 分开
     */
    private String users;
    /**
     * 授权搜索条件
     */
    private String userData;
    /**
     * 流程实例名称
     */
    private String processDefName;
}
