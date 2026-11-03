package com.work.ppm.application.dto.kpi;

import lombok.Data;


/**
 * 描述
 */
@Data
public class CheckEvalItemsSummary {
   /**
   *	年
   **/
   private String tyear;

   /**
    *	时间
    **/
   private String term;
   /**
   *	工号
   **/
   private String eid;

   /**
    *	角色
    **/
   private String role;

   /**
    *	状态
    **/
   private String itemstatus;

   /**
    *	审批评价人
    **/
   private String check;
   /**
    *	类型
    **/
   private String kpitype;
   /**
    *	kpi项目数
    **/
   private long  kpi_items;

   /**
    *	主表主键
    **/
   private long  pid;

   /**
    *	综合能力评估项目数
    **/
   private long comprehensiveAbility_items;

   /**
    *	履职评估项目数
    **/
   private long postEvaluation_items;

   /**
    *	上半年评语
    **/
   private long first_half_year_comment_items;


   /**
    *	下半年评语
    **/
   private long last_half_year_comment_items;


}
