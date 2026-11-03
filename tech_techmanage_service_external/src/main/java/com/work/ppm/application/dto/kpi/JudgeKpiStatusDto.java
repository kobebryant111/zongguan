package com.work.ppm.application.dto.kpi;

import lombok.Data;


/**
 * 根据人员判断当前考核项所属评价阶段
 */
@Data
public class JudgeKpiStatusDto {
   /**
    *	人员工号
    **/
   private String eid;
   /**
    *	评价人工号
    **/
   private String check;
   /**
    * 当前状态
    */
  private long status;

   /**
    * 标志：是审批还是评价
    */
  private String flag;

    /**
     * 年份
     */
    private String tyear;

}
