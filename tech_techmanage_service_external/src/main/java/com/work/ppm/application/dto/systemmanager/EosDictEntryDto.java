package com.work.ppm.application.dto.systemmanager;

import lombok.Data;


/**
 * 描述
 */
@Data
public class EosDictEntryDto {
   /**
   *	字段名
   **/
   private String dicttypeid;
   /**
   *	字段名
   **/
   private String dictid;
   /**
   *	字段名
   **/
   private String dictname;
   /**
   *	字段名
   **/
   private Boolean disabled;

   /**
    * 状态
    */
   private String status;
   /**
    * 序列
    */
   private String sortno;

}
