package com.work.ppm.commons.lang.notice;

import com.work.ppm.commons.constant.WorkflowSendMsg;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.CryptoUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.IpUtil;
import com.work.workflow.client.soentity.BPSWorkItem;

import java.util.Date;
import java.util.List;

/**
 * 消息通知父类
 */
public class SupperMessageNotice {
    /**
     * 标题
     */
    private String title;
    /**
     * 流程定义
     */
    private String processDefName;
    /**
     * 活动定义id
     */
    private String activityDefId;
    /**
     * 流程实例id
     */
    private String processInstId;
    /**
     * 转移符
     */
    private static final String ESCAPES_SLASH = "\\";
    /**
     * 标记
     */
    private static final String USD_SYMBOL = "$";


    /**
     * 短信消息模板
     * @param content 模板内容
     * @param userNames 目标人名称
     * @param flowname 实例名称
     * @return string
     */
    public String smsTemplate(String content, String userNames, String flowname) {

        content = content.replaceAll("@TIME@", DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm"));
        content = content.replaceAll("@URL@", "");
        content = content.replaceAll("@USERNAME@", userNames);
        content = content.replaceAll("@PROCESSNAME@", flowname);

//        String title = this.processTitle;
        if (title.indexOf(ESCAPES_SLASH) >= 0) {
            title = title.replaceAll("\\\\", "@STR@");
        }
        if (title.indexOf(USD_SYMBOL) >= 0) {
            title = title.replaceAll("\\$", "@STR1@");
        }
        content = content.replaceAll("@PROCESSINSTNAME@", title);
        content = content.replaceAll("@STR@", "\\\\");
        content = content.replaceAll("@STR1@", "\\$");
        // 开始发送短信
        if (WorkflowSendMsg.getProcessorMobileOAApprovalConfig()
                .contains(this.processDefName + "|" + this.activityDefId)) {
            content += "[该工作项支持移动端审批]";
        } else {
            content += "[该工作项暂不支持移动端审批]";
        }
        return content;
    }

    /**
     * 平台模板
     * @param content 模板内容
     * @param username 待发送用户名称
     * @param flowname 实例名称
     * @return string
     */
    public String platformTemplate(String content, String username, String flowname) {
        content = content.replaceAll("@TIME@", DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm"));
        content = content.replaceAll("@URL@", "");
        content = content.replaceAll("@PROCESSNAME@", flowname);

//        String title = this.processTitle;
        if(title.indexOf(ESCAPES_SLASH)>=0){
            title=title.replaceAll("\\\\", "@STR@");
        }
        if(title.indexOf(USD_SYMBOL)>=0){
            title=title.replaceAll("\\$", "@STR1@");
        }
        content = content.replaceAll("@PROCESSINSTNAME@", title);
        content = content.replaceAll("@STR@", "\\\\");
        content = content.replaceAll("@STR1@", "\\$");

        //content = content.replaceAll("@PROCESSINSTNAME@", this.processtitle);
        content = content.replaceAll("@USERNAME@", username);

        if (WorkflowSendMsg.getProcessorMobileOAApprovalConfig()
                .contains(this.processDefName + "|" + this.activityDefId)) {
            content=content+"[该工作项支持平台审批]";
        }else
        {
            content=content+"[该工作项暂不支持平台审批]";
        }
        return  content;
    }

    /**
     * 邮件模板
     * @param content 模板内容
     * @param badge 工号
     * @param username 待发送人
     * @param url 地址
     * @param flowname 实例名称
     * @return 替换模板后的内容
     */
    public String emailTemplate(String content, String badge,String username, String flowname,String url) {
        content = content.replaceAll("@TIME@", DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm"));
        if (content.indexOf("@URL@") != -1) {
            BPSWorkItem workItemParam = new BPSWorkItem();
            workItemParam.setProcessInstID(new Long(this.processInstId).intValue());
            List<BPSWorkItem> bpsList = BpsUtil.
                    queryWorkItemInApprovedAndActivityProcessInst(workItemParam, badge);
            if (null != bpsList && !bpsList.isEmpty()) {
//                        BPSWorkItem bpsworkitem = bpsList.get(0);
                StringBuilder sb = new StringBuilder(bpsList.size());
                StringBuilder md5Sb = new StringBuilder();
                md5Sb.append(badge).append(":").
                        append(IpUtil.getLocalIp()).append(":").append(DateUtil.dateToNoBar(new Date()));
                sb.append(url).append("?userId=").append(badge).append("&processInstId=")
                        .append(this.processInstId).append("&token=").
                        append(CryptoUtil.encoderByMd5(md5Sb.toString()));
                content = content.replaceAll("@URL@", sb.toString());
            } else {
                content = content.replaceAll("@URL@", "");
            }
        }
//        String title = this.processTitle;
        if (title.indexOf(ESCAPES_SLASH) >= 0) {
            title = title.replaceAll("\\\\", "@STR@");
        }
        if (title.indexOf(USD_SYMBOL) >= 0) {
            title = title.replaceAll("\\$", "@STR1@");
        }
        content = content.replaceAll("@PROCESSNAME@", flowname);
        content = content.replaceAll("@PROCESSINSTNAME@", title);
        content = content.replaceAll("@USERNAME@", username);
        content = content.replaceAll("@STR@", "\\\\");
        content = content.replaceAll("@STR1@", "\\$");
        if (WorkflowSendMsg.getProcessorMobileOAApprovalConfig()
                .contains(this.processDefName + "|" + this.activityDefId)) {
            content += "[该工作项支持移动端审批]";
        } else {
            content += "[该工作项暂不支持移动端审批]";
        }
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProcessDefName() {
        return processDefName;
    }

    public void setProcessDefName(String processDefName) {
        this.processDefName = processDefName;
    }

    public String getActivityDefId() {
        return activityDefId;
    }

    public void setActivityDefId(String activityDefId) {
        this.activityDefId = activityDefId;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }
}
