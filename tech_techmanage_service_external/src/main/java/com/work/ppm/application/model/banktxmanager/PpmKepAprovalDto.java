package com.work.ppm.application.model.banktxmanager;

import lombok.Data;

import java.util.Date;

@Data
public class PpmKepAprovalDto {
    /**
     * 主键
     */
    private String detailId;
    /**
     * 姓名
     */

    private String empName;
    /**
     * 工号
     */
    private String badge;
    /**
     * 调休日期
     */
    private String approvalDate;
    /**
     * 调休日期(Data)
     */
    private Date comleaveData;
    /**
     * 调休时间(天)
     */
    private double intergNum;
    /**
     * 备注
     */
    private String comleaveReason;
    /**
     * 退回原因
     */
    private String comleaveBackReason;

    /**
     *当前审批人
     */
    private Long approver;

    /**
     *当前审批人
     */
    private String approverName;
    /**
     * 审批状态
     */
    private String approvalStatus;
}
