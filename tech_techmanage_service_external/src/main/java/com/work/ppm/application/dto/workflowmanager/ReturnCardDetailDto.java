package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.work.ppm.application.dto.workflowmanager.ext.CommonDetailDto;
import lombok.Data;

import java.util.List;


@Data
@JsonInclude
public class ReturnCardDetailDto extends CommonDetailDto {

    /**
     *  当前处理人编号
     */
    String currentUserId;
    /**
     *  当前处理人名称
     */
    String currentUserName;
    /**
     *  是否可修改字段
     */
    String ifUpdateData;
    /**
     *  门禁卡归还信息
     */
    List<ReturnCardDetailInfoDto> returnList;
    /**
     * 流程日志
     */
    List<DetailFlowLogDto> flowLog;

}
