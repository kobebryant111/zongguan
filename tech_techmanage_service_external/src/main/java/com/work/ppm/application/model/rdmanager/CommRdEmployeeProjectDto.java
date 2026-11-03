package com.work.ppm.application.model.rdmanager;

import lombok.Data;

import java.util.List;

@Data
public class CommRdEmployeeProjectDto {
   /**
    *
    */
   private  List<HrProject> hrProjectList;
   /**
    *
    */
   private CommRdEmployeeDto commRdEmployeeDto;
}
