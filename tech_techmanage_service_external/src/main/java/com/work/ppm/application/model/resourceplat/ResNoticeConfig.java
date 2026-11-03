package com.work.ppm.application.model.resourceplat;

import lombok.Data;

import java.util.Date;

/**
 * 通知设置
 */
@Data
public class ResNoticeConfig {
    /**
     *	主键
     **/
    private String id;

    /**
     *	通知人员ID
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
     *	创建人：id
     **/
    private String createUser;

    /**
     *	创建人：name
     **/
    private String createName;

    /**
     *	创建时间
     **/
    private Date createTime;

    /**
     *	更新人：id
     **/
    private String updateUser;

    /**
     *	更新人：name
     **/
    private String updateName;

    /**
     *	更新时间
     **/
    private Date updateTime;

    /**
     * 发送短信
     * @return Boolean
     */
    public Boolean sendMsg(){
        return "1".equals(msg);
    }

    /**
     * 发送微信
     * @return Boolean
     */
    public Boolean sendWebchat(){
        return "1".equals(webchat);
    }

    /**
     * 发送邮件
     * @return Boolean
     */
    public Boolean sendEmail(){
        return "1".equals(email);
    }

}
