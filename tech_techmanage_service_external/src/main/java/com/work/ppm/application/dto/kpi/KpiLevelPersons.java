package com.work.ppm.application.dto.kpi;

import lombok.Data;


/**
 * 描述
 */
@Data
public class KpiLevelPersons {
   /**
   *	等级
   **/
   private String tlevel;
   /**
    *	当前人数
    **/
   private Long currentPersons;
   /**
   *	设置人数
   **/
   private Long setPersons;
   /**
    *	排序列
    **/
   private Long order;
   /**
    *	中心
    **/
   private String centerid;

   /**
    *	中心名称
    **/
   private String centerName;

   /**
    *	当前部门数
    **/
   private Long currentDeps;
   /**
    *	设置部门数
    **/
   private Long setDeps;
   /**
    *	未提交部门名称
    **/
   private String noSubmitDepName;

   /**
    *	当前中心数
    **/
   private Long currentCens;
   /**
    *	设置中心数
    **/
   private Long setCens;

   /**
    *	未提交中心名称
    **/
   private String noSubmitCenterName;


}
