package com.work.ppm.application.dto.depkpi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 标题信息
 */
@Data
public class PpmDepkpiTitleInfoDto {
    /**
     * 主键
     **/
    private Long id;
    /**
     * 所属部门id
     **/
    private Long depid;
    /**
     * 所属考核项id
     **/
    private Long targetid;
    /**
     * 标题
     **/
    private String name;
    /**
     * 层级
     **/
    private Long levelnum;
    /**
     * 父级标题id
     **/
    private Long parentid;
    /**
     * 是否有子节点：0:无子节点；1：有子节点；
     **/
    private Long hasson;
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
     * 基准分
     **/
    private Long basepoint;
    /**
     * 加减分上限分
     **/
    private Long maxpoint;
    /**
     * 加减分下限分
     **/
    private Long minpoint;
    /**
     * 部门指标明细id
     **/
    private Long detailid;
    /**
     * 所属指标类型
     * :1：公共指标；
     * 2：自定义指标；
     **/
    private Long targettype;
    /**
     * 标题数组
     **/
    private List<PpmDepkpiTitleDto> titlelist;
}
