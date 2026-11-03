package com.work.ppm.application.model.banktxmanager;

import lombok.Data;

import java.util.List;

@Data
public class PpmAprovalListDto {
    /**
     * 调休审批表
     */
    private List<PpmKepAprovalDto> ppmKepAprovalDtos;
    /**
     * 退回原因
     */
    private String comleaveBackReason;
    /**
     * 审批状态
     */
    private String approvalStatus;
}
