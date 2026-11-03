package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmOutsourcingExt;
import lombok.Data;


/**
 * 描述
 */
@Data
public class PpmOutsourcingExtInfo extends PpmOutsourcingExt {


   /**
   *	考勤类型名称
   **/
   private String attendanceTypename;

   /**
    *	是否申请虚拟机
    **/
   private String virtualDesktopname;

   /**
    *	是否开通SVN权限
    **/
   private String svnLimitname;

   /**
    *	是否特殊权限
    **/
   private String specialAuthname;
   /**
    *	身份证类型
    **/
   private String cardTypename;
   /**
    *	政治面貌
    **/
   private String politicalStatusname;

}
