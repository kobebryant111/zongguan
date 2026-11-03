package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.work.ppm.application.dto.workflowmanager.ext.CommonDetailDto;
import lombok.Data;

import java.util.List;


@Data
@JsonInclude
public class AccessCardDetailDto extends CommonDetailDto {

    /**
     *  参数
     */
    String currentUserId;
    /**
     *  参数
     */
    String currentUserName;
    /**
     *  参数
     */
    String ifUpdateData;
    /**
     *  参数
     */
    List<AccessCardDetailInfoDto> returnList;
    /**
     * 流程日志
     */
    List<DetailFlowLogDto> flowLog;

}
