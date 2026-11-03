package com.work.ppm.application.dto.workflowmanager.leave;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.work.ppm.application.dto.workflowmanager.DetailFlowLogDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude
public class LeavingDetailDto{

    String id;

    String applyDeptId;

    String applyDeptName;

    String applyUserId;

    String applyUserName;

    String currentUserId;

    String currentUserName;

    String instanceid;

    String jobid;

    String username;

    String certificateNumber;

    String outsourcNo;

    String outsourcName;

    String deptId;

    String deptName;

    Date lgsj;

    String gzjj;

    String gzjjRy;

    String bgsbjj;

    String bgsbjjRy;

    String vpnjj;

    String vpnjjRy;

    String gpjj;

    String gpjjRy;

    String svnjj;

    String svnjjRy;

    String xljjj;

    String xljjjRy;

    String jfqxjj;

    String jfqxjjRy;

    String xtqxjj;

    String xtqxjjRy;

    String standbyOne;

    String standbyTwo;

    String standbyThree;

    List<DetailFlowLogDto> flowLog;

}
