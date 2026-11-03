package com.work.ppm.application.model.kpi.ext;

import com.work.ppm.application.model.kpi.PpmKpiItem;
import lombok.Data;

import java.util.Date;


/**
 * 描述
 */
@Data
public class PpmKpiItemPostEvaluationInfo extends PpmKpiItem {
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
    * 主体名称
    **/
   private String mainpartname;


   /**
    * 子数据的ID
    **/
   private Long did;
}
