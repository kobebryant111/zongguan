package com.work.ppm.application.dto.processmanager;

import lombok.Data;

import java.util.Date;

/**
 *dto
 */
@Data
public class PpmMsgTemplateDto {
    /**
     *	字段名
     **/
    private Long id;
    /**
     *	字段名
     **/
    private String templateno;
    /**
     *	字段名
     **/
    private String templatename;
    /**
     *	字段名
     **/
    private String flowname;
    /**
     *	字段名
     **/
    private String templatetype;
    /**
     *	字段名
     **/
    private String content;
    /**
     *	字段名
     **/
    private Long isdel;
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
     * sql 状态 add 添加， update 修改
     */
    private String sqlStatus;
}
