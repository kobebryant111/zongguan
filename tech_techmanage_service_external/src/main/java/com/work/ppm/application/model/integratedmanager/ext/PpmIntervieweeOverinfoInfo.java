package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmIntervieweeOverinfo;
import lombok.Data;


/**
 * 描述
 */
@Data
public class PpmIntervieweeOverinfoInfo extends PpmIntervieweeOverinfo {

   /**
   *	岗位
   **/
   private String positionname;

   /**
   *	面试结果 0：不通过1：通过
   **/
   private String interviewresultname;


}
