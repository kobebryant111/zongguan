package com.work.ppm.application.model.depkpi.ext;

import com.work.ppm.application.model.depkpi.PpmDepkpiDepCheck;
import lombok.Data;


/**
 * 考核状态
 *
 */
@Data
public class PpmDepkpiDepCheckInfo extends PpmDepkpiDepCheck {
    /**
     * 自定义待办制定数量
     **/
    private Long zdydb_zd;
    /**
     * 公共待办制定数量
     **/
    private Long ggdb_zd;
    /**
     * 自定义待办审核数量
     **/
    private Long zdydb_sh;
    /**
     * 公共待办审核数量
     **/
    private Long ggdb_sh;
    /**
     * 自定义待办自评数量
     **/
    private Long zdydb_zp;
    /**
     * 自定义考核评价数量
     **/
    private Long zdykh_pj;
    /**
     * 公共考核评价数量
     **/
    private Long ggkh_pj;
    /**
     * 考核评级数量
     **/
    private Long kh_pji;
    /**
     * 部门名称
     **/
    private String depname;
    /**
     * 待配置项
     **/
    private String targetnamelist;
    /**
     * 初审/初评人
     **/
    private String firstcheck;
    /**
     * 终审/终评人
     **/
    private String secondcheck;
    /**
     * data
     */
    private PpmDepkpiDepCheckInfo data;
    /**
     * 自定义得分
     **/
    private Double zdypoint;
    /**
     * 公共得分
     **/
    private Double ggpoint;
    /**
     * 总分
     **/
    private String sumpoint;
    /**
     * 中心名称
     **/
    private String centername;
    /**
     * 排序
     **/
    private String ranknum;
    /**
     * 总数
     **/
    private Long countcontent;

    /**
     * 制定部门名称
     **/
    private String markerdepname;
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
    /**
     *	公共未提交数量
     **/
    private Long nocommitnum;
    /**
     *	公共已提交数量
     **/
    private Long commitnum;
    /**
     *	公共已提交数量
     **/
    private Long num;

}
