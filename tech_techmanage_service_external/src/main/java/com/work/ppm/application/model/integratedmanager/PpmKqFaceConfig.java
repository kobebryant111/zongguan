package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;

/**
 * 考勤人脸配置表 实体对象
 */
@Data
public class PpmKqFaceConfig {
   /**
   * 唯一编号 ID
   **/
   @Id
   private Long id;
   /**
    * 工号 JOB_ID
   **/
   private String jobId;
   /**
   *	图片 PICTURE
   **/
   private String picture;
   /**
    *	下发状态 0未下发 1已下发 ISSUE_STATE
    **/
   private String issueState;
   /**
    *	已下发设备 ISSUE_MACHINE
    **/
   private String issueMachine;
   /**
    *	下发时间 ISSUE_TIME
    **/
   private Date issueTime;
   /**
    * 人员类型
    */
   private String type;

}
