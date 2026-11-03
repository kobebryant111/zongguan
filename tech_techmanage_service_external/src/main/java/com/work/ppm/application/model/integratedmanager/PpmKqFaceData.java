package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;

/**
 * 考勤人脸数据表 实体对象
 */
@Data
public class PpmKqFaceData {
   /**
   * 唯一编号 ID
   **/
   @Id
   private Long id;
   /**
   * IP地址 IP_ADDRESS
   **/
   private String ipAddress;
   /**
    * 工号 CARD_NO
   **/
   private String cardNO;
   /**
   *	打卡时间 PUNCH_TIME
   **/
   private String punchTime;
   /**
    *	状态 STATE
    **/
   private String state;
   /**
    *	创建日期 CREATED_DATE
    **/
   private Date createdDate;
   /**
    * 类型 TYPE
    */
   private String type;

}
