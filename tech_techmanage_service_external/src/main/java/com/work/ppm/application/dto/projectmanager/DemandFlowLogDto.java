package com.work.ppm.application.dto.projectmanager;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 审批处理意见分为业务二级部经理审批、科技二级部经理审批，及业务处理意见、科技处理意见
 * 时间需要业务处理时间及科技处理时间
 *
 */
@Data
public class DemandFlowLogDto {

    /**
     * 用户编号，工号
     */
    private String userid;

    /**
     * 流程节点
     * value: {业务审核,科技审核}
     */
    private String activename;

    /**
     * 选择决策
     * value: {同意,不同意}
     */
    private String conclusion;

    /**
     * 处理意见
     */
    private String suggest;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

}
