package com.work.ppm.application.dto.onlinemanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.work.ppm.application.dto.projectmanager.DemandFileDto;
import com.work.ppm.application.dto.projectmanager.DemandFlowLogDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 业务上线接口交互类
 */
@Data
public class OnlineInfoDto {

    /**
     * 上线子项目列表
     */
    private List<OnlineSubProjectInfoDto> onlineSubProjectInfo;

    /**
     * 审批意见列表
     */
    private List<DemandFlowLogDto> demandFlowLogList;

    /**
     * 附件信息
     */
    private List<DemandFileDto> demandFileList;

    /**
     * 申请人
     */
    private String userid;

    /**
     * 申请日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date apply;

    /**
     * 相关项目
     */
    private String projectno;

    /**
     * 申请上线日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planonline;

    /**
     * 上线范围
     */
    private String orgarea;

    /**
     * 上线类型
     */
    private String operatetype;

    /**
     * 部分上线原因
     */
    private String bfsxreason;

    /**
     * 上线内容概述(两句话简述)
     */
    private String content;

    /**
     * 非计划窗口上线原因
     */
    private String fjhreason;

    /**
     * 测试内容
     */
    private String testcontent;

    /**
     * 测试结论
     */
    private String teststatus;

    /**
     * 类别 1：分行上线
     */
    private String onlineType;

    /**
     * 关联可行性编号
     */
    private String reviewno;
}
