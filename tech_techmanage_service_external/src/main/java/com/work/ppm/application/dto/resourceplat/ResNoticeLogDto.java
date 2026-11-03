package com.work.ppm.application.dto.resourceplat;

import lombok.Data;

/**
 * 通知日志
 */
@Data
public class ResNoticeLogDto {
    /**
     *	主键
     **/
    private String id;

    /**
     *	申请单ID
     **/
    private String busId;

    /**
     *	通知人员id
     **/
    private String userId;

    /**
     *	通知人员姓名
     **/
    private String userName;

    /**
     *	是否推送短信：1-是，0-否
     **/
    private String msg;

    /**
     *	是否推送微信：1-是，0-否
     **/
    private String webchat;

    /**
     *	是否推送邮件：1-是，0-否
     **/
    private String email;

    /**
     * 推送内容
     **/
    private String noticeMsg;

    /**
     *	推送时间
     **/
    private String noticeTime;

}
