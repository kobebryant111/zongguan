package com.work.ppm.application.dto.depkpi;

import lombok.Data;


/**
 * 部门模板临时详情表
 *
 */
@Data
public class PpmDepkpiDepmodelDetailTmpDto {
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
     * 评价方式：1：档位；2：任意分值；3：其他分值；
     **/
    private Long evaltype;
    /**
     * 是否需要自评：1：需要；2：不需要
     **/
    private Long selfeval;
    /**
     * 加减分上限分
     **/
    private Long maxpoint;
    /**
     * 加减分下限分
     **/
    private Long minpoint;
    /**
     * 主键
     **/
    private Long id;
    /**
     * 制定部门ID，只有公共指标才有制定部门
     **/
    private Long depid;
    /**
     * 指标id
     **/
    private Long targetid;
    /**
     * 父级id
     **/
    private Long parentid;
    /**
     * 基准分
     **/
    private Long basepoint;
    /**
     * 是否有子节点：0:无子节点；1：有子节点；
     **/
    private Long hasson;
    /**
     * 制定部门id
     **/
    private Long markerdepid;
}
