package com.work.ppm.application.model.depkpi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 部门模板分配表
 *
 */
@Data
public class PpmDepkpiDepModel {
    /**
     * 主键
     **/
    @Id
    private Long id;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastmodtime;
}
