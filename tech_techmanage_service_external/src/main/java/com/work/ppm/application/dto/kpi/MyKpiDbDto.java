package com.work.ppm.application.dto.kpi;

import lombok.Data;



/**
 * 描述
 */
@Data
public class MyKpiDbDto {
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
    *	员工上半年录入的KPI条数
    **/
   private long  emp_formulate_kpis_first_half;

   /**
    *	员工下半年录入的KPI条数
    **/
   private long emp_formulate_kpis_last_half;

   /**
    *	经理层全年kpi的制定
    **/
   private long managers_formulate_kpis;

   /**
    *	员工kpi自评上半年
    **/
   private long emp_self_eval_kpis_first_half;

   /**
    *	员工kpi自评下半年
    **/
   private long emp_self_eval_kpis_last_half;

   /**
    *	经理kpi自评
    **/
   private long managers_self_eval_kpis;

   /**
    *	经理kpi半年度自评
    **/
   private long managers_self_eval_kpis_half_year;

   /**
    * 员工上半年kpi审批
    **/
   private long  emp_kpis_approve_first_half;

   /**
    * 员工下半年kpi审批
    **/
   private long emp_kpis_approve_last_half;

   /**
    * 经理层kpi审批
    **/
   private long managers_kpis_approve;


   /**
    * 员工上半年评价
    **/
   private long  emp_eval_first_half;

   /**
    * 员工下半年评价
    **/
   private long emp_eval_last_half;

   /**
    * 经理层评价
    **/
   private long managers_eval;
   /**
    * 经理评价
    **/
   private long manager_eval;

   /**
    * 履职评估下级对上级的待评价条数
    **/
   private long subordinate_evaluation;


   /**
    * 履职评估上级对下级的待评价条数
    **/
   private long supersiorPost_evaluation;

   /**
    * 部门年度考核数据确认
    **/
   private long depYearData_confirm;


   /**
    * 中心年度考核数据初评
    **/
   private long centerYearData_first_eval;


   /**
    * 科技部年度考核数据终评
    **/
   private long tecYearData_second_eval;



}
