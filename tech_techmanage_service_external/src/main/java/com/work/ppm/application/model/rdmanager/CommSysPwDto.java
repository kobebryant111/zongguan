package com.work.ppm.application.model.rdmanager;

import lombok.Data;

import java.util.Date;

@Data
public class CommSysPwDto {
    /**
     * 主键
     */
    private String id;

    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 系统编号
     */
    private String systemNo;

    /**
     * 密码
     */
    private String psd;
    /**
     * 密码规则校验
     */
    private String rulesTorf;

    /**
     * 失效时间
     */
    private Date invaldate;

    /**
     * 有效日期
     */
    private int validtime;

    /**
     * 负责人姓名
     */
    private String responsibleName;

    /**
     * 负责人工号
     */
    private String responsibleJobid;
    /**
     * 随机iv
     */
    private String iv;
    /**
     * 密钥
     */
    private String psdKey;

}
