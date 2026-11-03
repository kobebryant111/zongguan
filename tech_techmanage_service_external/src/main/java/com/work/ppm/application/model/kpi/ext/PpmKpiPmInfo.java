package com.work.ppm.application.model.kpi.ext;

import com.work.ppm.application.model.kpi.PpmKpiPm;
import lombok.Data;



/**
 * 描述
 */
@Data
public class PpmKpiPmInfo extends PpmKpiPm {

   /**
   *	审批人1
   **/
   private String firstcheckname;
   /**
   *	审批人2
   **/
   private String secondcheckname;

   /**
    *	姓名
    **/
   private String eidname;

   /**
    *	审核人
    **/
   private String check;


   /**
    *	部门名称
    **/
   private String depname;




   /**
    *	中心名称
    **/
   private String centername;



   /**
    *	初评人
    **/
   private String firstevalname;


   /**
    *	终评人
    **/
   private String secondevalname;


   /**
    *	状态名称
    **/
   private String statusname;


   /**
    *	角色名称
    **/
   private String rolename;

   /**
    *	待评价项数
    **/
   private Long evalitems;

   /**
    *	子项状态
    **/
   private Long itemstatus;

   /**
    *	子状态名称
    **/
   private String itemstatusname;

   /**
    *	预期总分
    **/
   private Double expectscore;

   /**
    * 未填写项
    **/
   private Long nofillitem;

}
