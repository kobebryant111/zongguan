package com.work.ppm.application.model.systemmanager.ext;

import com.work.ppm.application.model.systemmanager.CommOperator;
import lombok.Data;

/**
 *扩展
 */
@Data
public class CommOperatorInfo extends CommOperator {
    /**
     * 名称
     */
    private String dictname;
    /**
     * 登录名称
     */
    private String operatorname;
    /**
     * 角色名称
     */
    private String rolename;
    /**
     * 角色id
     */
    private String roleid;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 部门名称
     */
    private String location;
    /**
     * 路径
     */
    private String stockpath;
    /**
     * 证件类型
     */
    private String certificateType;
    /**
     * 证件号码
     */
    private String certificateNumber;
    /**
     * 文档名称
     */
    private String docname;
    /**
     * 文档名称
     */
    private String docdomain;
}
