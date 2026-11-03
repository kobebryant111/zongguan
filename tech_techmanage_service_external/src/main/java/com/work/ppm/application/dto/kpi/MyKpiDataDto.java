package com.work.ppm.application.dto.kpi;

import lombok.Data;


/**
 * 描述
 */
@Data
public class MyKpiDataDto {
   /**
    *	年
    **/
   private String tyear;
   /**
    *	工号
    **/
   private String eid;
   /**
    * 我的考核角色数据
    */
  private PpmKpiRoleInfoDto ppmKpiRoleInfoDto;

   /**
    * 我的考核员工上半年主记录数据
    */
   private PpmKpiPmInfoDto emp_kpis_first_half;

   /**
    * 我的考核员工下半年主记录数据
    */
   private PpmKpiPmInfoDto emp_kpis_last_half;
   /**
    * 我的考核经理层全年数据
    */
   private PpmKpiPmInfoDto manager_kpis;
   /**
    * 我的考核年度评价数据
    */
   private PpmKpiPmInfoDto my_kpis_year_eval;
    /**
     * 加密后工号
     */
   private String secertkey;
}
