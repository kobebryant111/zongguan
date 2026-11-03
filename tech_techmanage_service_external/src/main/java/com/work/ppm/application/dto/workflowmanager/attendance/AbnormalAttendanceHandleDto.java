package com.work.ppm.application.dto.workflowmanager.attendance;

import com.work.ppm.application.dto.workflowmanager.ext.ReadyParamsDto;
import lombok.Data;

@Data
public class AbnormalAttendanceHandleDto extends ReadyParamsDto {
    /**
     *  编号
     */
    private String id;
    /**
     * 项目经理编号
     */
    private String pmId;
    /**
     * 异常原因
     */
    private String abnormalReason;
    /**
     * 备注
     */
    private String remark;

}
