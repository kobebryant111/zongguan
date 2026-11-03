package com.work.ppm.application.dto.depkpi;

import lombok.Data;


/**
 * 考核详情数据
 *
 */
@Data
public class PpmDepkpiDepCheckDto {
    /**
     * 退回意见
     **/
    private String suggest;
    /**
     * 考核项id
     **/
    private Long id;
    /**
     * 部门id
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
     * 考核年份
     **/
    private String tyear;
    /**
     * 状态：0：草稿中；1：待初审；2：待终审；3：执行中；4：待自评；5：待初评；6：待终评；7：已完成；
     **/
    private Long status;
    /**
     * 待办人
     **/
    private String dbuser;
    /**
     * 中心
     **/
    private Long centerid;
    /**
     * 终评的分
     **/
    private Double secondcheckpoint;
    /**
     * 是否删除
     **/
    private Long isdel;
    /**
     * 制定部门id
     **/
    private Long markerdepid;

    /**
     * 制定部门名称
     **/
    private String markerdepname;
    /**
     * userid
     **/
    private String userid;

    /**
     * 数量
     **/
    private Long countcontent;

    /**
     * 总分
     **/
    private Double sumpoint;

    /**
     *	公共得分
     **/
    private Long ggsumpoint;
    /**
     *	自定义得分
     **/
    private Long zdysumpoint;
    /**
     *	初评数量
     **/
    private Long firstsum;

    /**
     *	终评数量
     **/
    private Long secondsum;

}
