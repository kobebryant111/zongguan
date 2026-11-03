package com.work.ppm.application.model.kpi.ext;

import com.work.ppm.application.model.kpi.PpmKpiPmDetail;
import lombok.Data;



/**
 * 描述
 */
@Data
public class PpmKpiPmDetailInfo extends PpmKpiPmDetail {
   /**
    * 考核项状态名称
    */
   private String itemstatusname;

   /**
    * 初评人姓名
    */
   private String firstevalname;


   /**
    * 终评人姓名
    */
   private String secondevalname;

   /**
    *	初评平均分
    **/
   private Double firstevalavgscore;

   /**
    *	终评平均分
    **/
   private Double secondevalavgscore;


   /**
    * 主考核项状态
    */
   private String status;

   /**
    * 主考核项状态名称
    */
   private String statusname;

   /**
    * 中心id
    */
   private String centerid;

   /**
    * 中心名称
    */
   private String centername;

   /**
    * 部门编号
    */
   private String depid;

   /**
    * 部门名称
    */
   private String depname;

   /**
    * 名称
    */
   private String eidname;


   /**
    * 角色编号
    */
   private String role;


   /**
    * 角色名称
    */
   private String rolename;

   /**
    *	待评价项数
    **/
   private long evalitems;


}
