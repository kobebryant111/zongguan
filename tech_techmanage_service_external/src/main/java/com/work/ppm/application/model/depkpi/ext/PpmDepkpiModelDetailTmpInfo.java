package com.work.ppm.application.model.depkpi.ext;

import com.work.ppm.application.model.depkpi.PpmDepkpiModelDetailTmp;
import lombok.Data;

/**
 * 模板信息表
 *
 */
@Data
public class PpmDepkpiModelDetailTmpInfo extends PpmDepkpiModelDetailTmp {
    /**
     * 指标名
     **/
    private String targetname;
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
     * 加减分上限分
     **/
    private Long maxpoint;
    /**
     * 加减分下限分
     **/
    private Long minpoint;
}
