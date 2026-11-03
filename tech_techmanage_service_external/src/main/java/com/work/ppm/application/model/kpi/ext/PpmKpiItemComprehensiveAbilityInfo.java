package com.work.ppm.application.model.kpi.ext;

import com.work.ppm.application.model.kpi.PpmKpiItem;
import lombok.Data;

import java.util.Date;


/**
 * 描述
 */
@Data
public class PpmKpiItemComprehensiveAbilityInfo extends PpmKpiItem {
   /**
    * 标准
    **/
   private String standard;

   /**
    * 工作内容
    **/
   private String workcontent;

   /**
    * 分值
    **/
   private Double value;

   /**
    * 完成时间
    **/
   private Date finishdate;

   /**
    * 子数据的ID
    **/
   private Long did;
}
