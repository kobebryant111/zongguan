package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmOutsourcingAccessapply;
import lombok.Data;


/**
 * 描述
 */
@Data
public class PpmOutsourcingAccessapplyInfo extends PpmOutsourcingAccessapply {

   /**
   *	面试结果
   **/
   private String interviewResultname;
   /**
   *	面试官1
   **/
   private String oneInterviewername;
   /**
   *	面试官2
   **/
   private String twoInterviewername;
   /**
    *	总办议案记录
    **/
   private Long ceneralOfficeMotionBe;



}
