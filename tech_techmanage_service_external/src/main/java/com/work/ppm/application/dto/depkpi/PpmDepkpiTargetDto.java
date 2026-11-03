package com.work.ppm.application.dto.depkpi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * 指标
 *
 */
@Data
public class PpmDepkpiTargetDto {
    /**
     * 主键
     **/
    private Long id;
    /**
     * 所属组织级别：1：部门； 2：中心 ；3：一级部 ；
     **/
    private Long sign;
    /**
     * 指标名
     **/
    private String name;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 层级
     **/
    private Long levelnum;
    /**
     * 指标类型：1：公共指标；2：自定义指标；
     **/
    private Long targettype;
    /**
     * 制定部门ID，只有公共指标才有制定部门
     **/
    private Long depid;
    /**
     * 评价方式：1：档位；2：任意分值；3：其他分值；
     **/
    private Long evaltype;
    /**
     * 是否需要自评：1：需要；2：不需要
     **/
    private Long selfeval;
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
     * 加减分上限分
     **/
    private Long maxpoint;
    /**
     * 加减分下限分
     **/
    private Long minpoint;

    /**
     * 考核制定人
     **/
    private String marker;

    /**
     * 部门名称
     **/
    private String depname;
}
