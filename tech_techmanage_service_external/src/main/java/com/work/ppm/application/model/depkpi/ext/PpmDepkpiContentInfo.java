package com.work.ppm.application.model.depkpi.ext;

import com.work.ppm.application.model.depkpi.PpmDepkpiContent;
import lombok.Data;


/**
 * 描述
 *
 */
@Data
public class PpmDepkpiContentInfo extends PpmDepkpiContent {
    /**
     * 考核项名称
     **/
    private String targetname;
    /**
     * 部门名称
     **/
    private String depname;
    /**
     * 标题名称
     **/
    private String titlename;
    /**
     * 父级标题id
     **/
    private Long parentTitleid;
    /**
     * 父级指标明细id
     **/
    private Long parentDetailid;
    /**
     * userid
     **/
    private String userid;
    /**
     * 状态
     **/
    private Long dbstatus;
    /**
     *	公共得分
     **/
    private Double ggsumpoint;
    /**
     *	自定义得分
     **/
    private Double zdysumpoint;
    /**
     *	数量
     **/
    private Long commitsum;
    /**
     * 总分
     **/
    private Double sumpoint;
    /**
     * 总数
     **/
    private Long countcontent;
    /**
     * 部门级别
     **/
    private Long sign;
    /**
     * 状态：1：初评人；2：终评人；
     **/
    private Long isSecond;

    /**
     *	初审/初评人
     **/
    private String firstcheck;
    /**
     *	终审/终评人
     **/
    private String secondcheck;
    /**
     *	代办人
     **/
    private String dbuser;
}
