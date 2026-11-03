package com.work.ppm.application.dto.systemmanager;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户登录session
 */
@Data
public class CommOperatorDoLoginDto {
    /**
     * 登录id
     */
    private Integer operatorid;
    /**
     * 用户id
     */
    private String userid;
    /**
     * 证件类型
     */
    private String certificateType;
    /**
     * 证件号码
     */
    private String certificateNumber;
    /**
     * 用户名称
     */
    private String operatorname;
    /**
     * 密码
     */
    private String password;
    /**
     * 状态
     */
    private String status;
    /**
     * 角色集合
     */
    private List<ImRoleDoLoginDto> roles;
    /**
     * 部门编号
     */
    private String deptId;
    /**
     * 部门名称
     */
    private String location;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 权限码
     */
    private List<String> funcs;

    /**
     * 最后登陆时间
     */
    private Date lastlogin;

    /**
     *失效时间
     */
    private Date invaldate;
}
