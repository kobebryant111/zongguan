package com.work.ppm.application.dto.kpi;

import lombok.Data;

import java.util.List;


/**
 * 描述
 */
@Data
public class PostEvaluationEvalDto {
   /**
    *	主表主键
    **/
   private Long pid;
   /**
    *	工号
    **/
   private String eid;
   /**
    *	指标类型
    **/
   private Long kpitype;
   /**
    *	考核项
    **/
   private String item;
   /**
    *	考核项描述
    **/
   private String itemdescribe;
   /**
    *	权重
    **/
   private Double weight;
   /**
    *	最终得分
    **/
   private Double tscore;
   /**
    *	初评人
    **/
   private String firsteval;
   /**
    *	终评人
    **/
   private String secondeval;
   /**
    *	状态
    **/
   private Long itemstatus;
   /**
    * 综合评价的子项
    */
   List<PpmKpiPmDetailDto> list;

   /**
    * 履职评估分数
    */
   private Double postEvalTscore;

   /**
    * 取履职评估分数人员
    */
   private String postEvalUser;

   /**
    *	主体
    **/
   private String mainpart;

   /**
    *	主体名称
    **/
   private String mainpartname;
}
