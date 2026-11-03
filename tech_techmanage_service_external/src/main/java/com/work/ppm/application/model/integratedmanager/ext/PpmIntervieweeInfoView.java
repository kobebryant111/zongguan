package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmIntervieweeInfo;
import lombok.Data;


/**
 * 描述
 */
@Data
public class PpmIntervieweeInfoView extends PpmIntervieweeInfo {

   /**
    * 部门编号
    */
   private String deptNo;
   /**
    * 部门名称
    */
   private String deptName;
   /**
    * 花名册 证件号
    */
   private String cardNo;
   /**
    * 外包商 名称
    */
   private String outSourceName;
   /**
    * 面试关联 编号
    */
   private String interviewId;
   /**
    * 姓名
    */
   private String userName;
}
