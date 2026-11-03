package com.work.ppm.application.model.depkpi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.work.ppm.commons.annotaion.Id;

import java.util.Date;

/**
 * 考核内容数据
 *
 */
@Data
public class PpmDepkpiContent {
    /**
     * 主键
     **/
    @Id
    private Long id;
    /**
     * 年份
     **/
    private String tyear;
    /**
     * 所属部门id
     **/
    private Long depid;
    /**
     * 所属考核项id
     **/
    private Long targetid;
    /**
     * 所属标题id
     **/
    private Long titleid;
    /**
     * 基准分
     **/
    private Long basepoint;
    /**
     * 考核内容
     **/
    private String content;
    /**
     * 评分标准
     **/
    private String standard;
    /**
     * 开始时间
     **/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startdate;
    /**
     * 结束时间
     **/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date enddate;
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
     * 自评结果
     **/
    private String selfevalres;
    /**
     * 自评状态：0：未提交；1：已提交；
     **/
    private Long selfevalstatus;
    /**
     * 初评结果
     **/
    private String firstcheckres;
    /**
     * 初评得分
     **/
    private Double firstcheckpoint;
    /**
     * 初评备注
     **/
    private String firstcheckremark;
    /**
     * 初评提交状态：0：未提交；1：已提交；
     **/
    private Long firstcheckstatus;
    /**
     * 终评结果
     **/
    private String secondcheckres;
    /**
     * 终评得分
     **/
    private Double secondcheckpoint;
    /**
     * 终评备注
     **/
    private String secondcheckremark;
    /**
     * 终评提交状态：0：未提交；1：已提交；
     **/
    private Long secondcheckstatus;
    /**
     * 加减分上限分
     **/
    private Long maxpoint;
    /**
     * 加减分下限分
     **/
    private Long minpoint;
    /**
     * 明细id
     **/
    private Long detailid;
    /**
     * 所属指标类型
     * :1：公共指标；
     * 2：自定义指标；
     **/
    private Long targettype;

    /**
     * 初评时间
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date firstchecktime;

    /**
     * 终评时间
     **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date secondchecktime;
}
