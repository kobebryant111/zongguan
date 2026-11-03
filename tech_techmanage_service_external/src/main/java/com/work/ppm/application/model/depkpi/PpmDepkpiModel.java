package com.work.ppm.application.model.depkpi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.work.ppm.commons.annotaion.Id;

import java.util.Date;


/**
 * 部门模板
 *
 */
@Data
public class PpmDepkpiModel {
    /**
     * 主键
     **/
    @Id
    private Long id;
    /**
     * 所属组织级别：1：部门； 2：中心 ；3：一级部 ；
     **/
    private Long sign;
    /**
     * 模板名称
     **/
    private String name;
    /**
     * 备注
     **/
    private String remark;
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
    /**
     * 提交标志：0：未提交；1：已提交；
     **/
    private Long commitflag;
}
