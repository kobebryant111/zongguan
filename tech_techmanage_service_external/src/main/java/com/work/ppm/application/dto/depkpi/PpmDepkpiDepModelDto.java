package com.work.ppm.application.dto.depkpi;

import lombok.Data;

import java.util.Date;

/**
 * 部门模板分配表
 *
 */
@Data
public class PpmDepkpiDepModelDto {
    /**
     * 主键
     **/
    private Long id;
    /**
     * 部门类别
     **/
    private Long sign;
    /**
     * 部门id
     **/
    private Long depid;
    /**
     * 模板id
     **/
    private Long modelid;
    /**
     * 提交标志：0：未提交；1：已提交；
     **/
    private Long commitflag;
    /**
     * 是否删除
     **/
    private Long isdel;
    /**
     * 最后修改人
     **/
    private String lastmoduserid;
    /**
     * 最后修改时间
     **/
    private Date lastmodtime;
    /**
     * 模板名称
     **/
    private String name;

    /**
     * 中心id
     **/
    private String centerid;

    /**
     * 模板名称
     **/
    private String modelname;
    /**
     * 中心名称
     **/
    private String centername;
    /**
     * 部门名称
     **/
    private String depname;
}
