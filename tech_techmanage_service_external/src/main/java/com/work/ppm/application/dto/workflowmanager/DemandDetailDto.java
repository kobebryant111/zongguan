package com.work.ppm.application.dto.workflowmanager;

import com.work.ppm.application.dto.workflowmanager.ext.CommonDetailDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DemandDetailDto extends CommonDetailDto {

    /**
     * 提至供应商时间
     */
    Date supplytime;
    /**
     * 需求详情
     */
    List<DemandDetailInfoDto> infos;
    /**
     * 流程日志
     */
    List<DetailFlowLogDto> flowLog;

}
