package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 加班开始时间配置表 实体对象
 */
@Data
public class PpmKqOvertimeConfig {
   /**
   * 自增编号 ID
   **/
   @Id
   private Long id;
   /**
    * 加班开始时间 START_TIME
   **/
   private String startTime;
   /**
   *	生效日期 EFFECT_DATE
   **/
   private Date effectDate;
   /**
   *	是否生效 STATUS
   **/
   private String status;
   /**
    *	创建时间 CREATED_TIME
    **/
   private Date createdTime;
   /**
    *	是否生效 where条件 STATUS
    **/
   private String statusWhere;

}
