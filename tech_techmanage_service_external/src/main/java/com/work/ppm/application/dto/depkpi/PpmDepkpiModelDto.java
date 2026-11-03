package com.work.ppm.application.dto.depkpi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * 模板
 */
@Data
public class PpmDepkpiModelDto {
    /**
     * 主键
     **/
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

    /**
     * 是否有子节点：0：否；1：是；
     **/
    private Long hasson;

    /**
     * 部门id；
     **/
    private Long depid;


}
