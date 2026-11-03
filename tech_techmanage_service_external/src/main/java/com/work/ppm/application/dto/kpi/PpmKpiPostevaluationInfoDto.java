package com.work.ppm.application.dto.kpi;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 履职评估表
 */
@Data
public class PpmKpiPostevaluationInfoDto {

   /**
    *	主键
    **/
   @Id
   private Long id;
   /**
    *	履职评估主键
    **/
   private Long pid;
   /**
    *	考核项
    **/
   private String item;
   /**
    *	考核项描述
    **/
   private String itemdescribe;
   /**
    *	考核标准
    **/
   private String plan;
   /**
    *	考核标准描述
    **/
   private String plandescribe;
   /**
    *	分值
    **/
   private Double value;
   /**
    *	完成时间
    **/
   private Date finishdate;
   /**
    *	工作内容
    **/
   private String workremark;
   /**
    *	初评分
    **/
   private Double firstevalscore;
   /**
    *	初评比重
    **/
   private Double firstevalgoal;
   /**
    *	终评分
    **/
   private Double secondevalscore;
   /**
    *	终评比重
    **/
   private Double secondevalgoal;
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
    *	初评时间
    **/
   private Date firstevaltime;
   /**
    *	终评时间
    **/
   private Date secondevaltime;
   /**
    *	详细项状态
    **/
   private Long itemstatus;
   /**
    *	权重
    **/
   private Double weight;
   /**
    *	考核主体
    **/
   private String mainpart;
   /**
    *	创建时间
    **/
   private Date createtime;
   /**
    *	创建人
    **/
   private String createuserid;
   /**
    *	工号
    **/
   private String eid;
   /**
    *	周期
    **/
   private String term;
   /**
    *	年份
    **/
   private String tyear;

   /**
   *	状态名称
   **/
   private String itemstatusname;
   /**
   *	名称
   **/
   private String eidname;

   /**
    *	部门名称
    **/
   private String depname;
   /**
    *	中心名称
    **/
   private String centername;
   /**
    * 评价人
    */
   private String check;

}
