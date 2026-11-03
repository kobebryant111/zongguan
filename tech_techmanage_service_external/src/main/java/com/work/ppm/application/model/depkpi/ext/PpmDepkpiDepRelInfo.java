package com.work.ppm.application.model.depkpi.ext;

import com.work.ppm.application.model.depkpi.PpmDepkpiDepRel;
import lombok.Data;



/**
 * 部门考核关系
 */
@Data
public class PpmDepkpiDepRelInfo extends PpmDepkpiDepRel {
   /**
   *	部门名称
   **/
   private String depname;
   /**
   *	主持工作经理名称
   **/
   private String directorname;
   /**
   *	部门分管领导//分管总经理名称
   **/
   private String depchargename;
   /**
   *	中心总经理名称
   **/
   private String centerchargename;
   /**
   *	中心名称
   **/
   private String centername;
   /**
   *	初审/初评人名称
   **/
   private String firstcheckname;
   /**
   *	终审/终评人名称
   **/
   private String secondcheckname;
   /**
   *	考核制定人名称
   **/
   private String markername;
   /**
    *	标志类型名称
    **/
   private String signname;

   /**
    *	模板id
    **/
   private Long modelid;
   /**
    *	提交标志：0：未提交；1：已提交；
    **/
   private Long commitflag;
   /**
    *	模板名称
    **/
   private String modelname;
}
