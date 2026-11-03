package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.work.ppm.application.dto.workflowmanager.ext.CommonDetailDto;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude
public class InterviewDetailDto extends CommonDetailDto {


    /**
     *  人力需求id
     */
    String rlId;
    /**
     *  人力需求名称
     */
    String rlName;
    /**
     *  是否符合要求
     */
    String meet;
    /**
     *  面试官名称
     */
    String msgName;
    /**
     *  原因
     */
    String remarks;
    /**
     * 应聘信息
     */
    List<IntervieweeInfoDto> eeList;
    /**
     *  单价信息
     */
    List<IntervieweePriceInfoDto> prices;
    /**
     *  流程日志
     */
    List<DetailFlowLogDto> flowLog;
    /**
     *  当前处理人编号
     */
    String currentUserId;
    /**
     *  当前处理人名称
     */
    String currentUserName;
    /**
     *  当前处理人名称
     */
    String fileInfo ;
}
