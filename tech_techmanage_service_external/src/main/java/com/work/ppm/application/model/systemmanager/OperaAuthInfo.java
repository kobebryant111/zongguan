package com.work.ppm.application.model.systemmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 描述
 */
@Data
public class OperaAuthInfo {
   /**
   *	字段名
   **/
   @Id
   private String authId;
   /**
   *	字段名
   **/
   private String authName;
   /**
   *	字段名
   **/
   private String rolId;
   /**
   *	字段名
   **/
   private String authEncoding;

   /**
    *	字段名
    **/
    private Date creatTime;

   /**
    *	字段名
    **/
   private String creatName;

   /**
    *	字段名
    **/
   private Date updateTime;

   /**
    *	字段名
    **/
   private String updateName;
}
