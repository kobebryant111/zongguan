package com.work.ppm.application.model.depkpi.ext;

import com.work.ppm.application.model.depkpi.PpmDepkpiDepmodelDetail;
import lombok.Data;

/**
 * 考核内容映射表
 *
 */
@Data
public class PpmDepkpiDepmodelDetailInfo extends PpmDepkpiDepmodelDetail {
    /**
     * 指标名
     **/
    private String targetname;
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
    /**
     * 部门标识
     **/
    private Long sign;
    /**
     * 制定人
     **/
    private String marker;
    /**
     * 年份
     **/
    private String tyear;
}
