package com.work.ppm.application.dto.usermanager;

import lombok.Data;


/**
 * 描述
 */
@Data
public class CommOutsourcingPersonSshtDto {
   /**
   *	id
   **/
   private Long id;
   /**
   *	工号
   **/
   private String jobid;
   /**
   *	所属合同id
   **/
   private String sshtid;
   /**
   *	开始时间
   **/
   private String starttime;
   /**
   *	结束时间
   **/
   private String endtime;
   /**
   *	状态 0:进行中,1:已结束
   **/
   private String state;
}
