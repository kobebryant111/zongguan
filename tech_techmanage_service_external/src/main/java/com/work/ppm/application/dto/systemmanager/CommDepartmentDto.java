package com.work.ppm.application.dto.systemmanager;

import lombok.Data;

/**
 * 部门
 *
 */
@Data
public class CommDepartmentDto {
    /**
     * 所属上级部门
     **/
    private Integer adminid;
    /**
     * 部门代码
     **/
    private Integer depid;
    /**
     * 部门编码
     * (十位)
     **/
    private String depcode;
    /**
     * 中文名称
     **/
    private String location;
    /**
     * 所属机构
     **/
    private Integer compid;
    /**
     * 入参属性
     */
    private String objectcode;
    /**
     * 入参属性
     */
    private String objectgrade;
    /**
     * 入参属性
     */
    private boolean auth;
    /**
     * 入参属性
     */
    private String allowRole;
    /**
     * 入参属性
     */
    private String allowDept;
    /**
     * 机构编码
     */
    private String compcode;
    /**
     * 分行IT支持部经理名称
     */
    private String empname;
    /**
     * 分行IT支持部经理id
     */
    private String badge;
    /**
     * 分行运营部总经理名称
     */
    private String username;
    /**
     * 分行运营部总经理id
     */
    private String userid;
}
