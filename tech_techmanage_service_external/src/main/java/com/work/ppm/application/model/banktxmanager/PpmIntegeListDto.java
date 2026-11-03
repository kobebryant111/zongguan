package com.work.ppm.application.model.banktxmanager;

import lombok.Data;

import java.util.List;

@Data
public class PpmIntegeListDto {
    /***
     *积分信息表
     */
    private List<IntegInfo> integInfoList;
    /**
     * 状态
     */
    private String status;
    /**
     * 退回原因
     */
    private String integBackReason;
}
