package com.work.ppm.commons.lang.notice;

import com.work.ppm.application.model.flowmanager.PpmFlowType;
import com.work.ppm.application.model.othermanager.PpmCustomSetting;
import com.work.ppm.application.model.processmanager.PpmFlowInfo;
import com.work.ppm.application.model.processmanager.PpmMsgTemplate;
import com.work.ppm.application.services.flowmanager.IPpmFlowTypeService;
import com.work.ppm.application.services.othermanager.IPpmCustomSettingService;
import com.work.ppm.application.services.processmanager.IPpmFlowInfoService;
import com.work.ppm.application.services.processmanager.IPpmMsgTemplateService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.constant.WorkflowSendMsg;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息通知 - 流程
 *
 */
@Slf4j
public class MessageNotice extends  SupperMessageNotice {
    /**
     * 流程信息
     */
    private IPpmFlowInfoService ppmFlowInfoService = (IPpmFlowInfoService) SpringUtil.getBean("ppmFlowInfoService");
    /**
     * 流程類型
     */
    private IPpmFlowTypeService ppmFlowTypeService = (IPpmFlowTypeService)
            SpringUtil.getBean("ppmFlowTypeService");

    /**
     * 发送消息模板
     */
    private IPpmMsgTemplateService ppmMsgTemplateService = (IPpmMsgTemplateService)
            SpringUtil.getBean("ppmMsgTemplateService");

    /**
     * 发送消息设置
     */
    private IPpmCustomSettingService ppmCustomSettingService = (IPpmCustomSettingService)
            SpringUtil.getBean("ppmCustomSettingService");
    /**
     * 业务字典
     */
    private IEosDictEntryService eosDictEntryService = (IEosDictEntryService)
            SpringUtil.getBean("eosDictEntryService");

    /**
     * 用户工号数据
     */
    private String[] userIds;
    /**
     * 流程定义名称
     */
    private String processDefName;
    /**
     * 当前活动实例ID
     */
    private String activityDefId;
    /**
     * 流程标题
     */
    private String processTitle;
    /**
     * 流程id
     */
    private String processInstId;
    /**
     * 工作项id
     */
    private String[] workItems;
    /**
     * 发送人工号
     */
    private String sendUserId;

    /**
     * 构造函数
     *
     * @param userId         待发送用户 格式如：id@name 用,隔开
     * @param sendUserId 发送人工号
     * @param processDefName 流程定义
     * @param activityDefId  活动定义id
     * @param processtitle   流程名称
     * @param processInstId  流程实例id
     * @param workItems      工作项id 用,隔开
     */
    public MessageNotice(String userId,String sendUserId, String processDefName, String activityDefId,
                         String processtitle, String processInstId, String workItems) {
        this.userIds = userId.split(",");
        this.processDefName = processDefName;
        this.activityDefId = activityDefId;
        this.processTitle = processtitle;
        this.processInstId = processInstId;
        this.workItems = workItems.split(",");
        this.sendUserId = sendUserId;
        super.setTitle(processtitle);
        super.setActivityDefId(activityDefId);
        super.setProcessDefName(processDefName);
        super.setProcessInstId(processInstId);
    }

    /**
     * 发送流程通知
     */
    public void sendFlowNotice() {
        try {
            PpmFlowInfo info = ppmFlowInfoService.queryDetail(this.activityDefId, this.processDefName);
            if (null == info) {
                return;
            }
            PpmFlowType pft = ppmFlowTypeService.queryDetail(this.processDefName.trim());
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>短信发送");
            // 短信
            if ("1".equals(info.getIsclrdx())) {
                sendSMS(info.getClrdxmb(), info.getFlowdraftno(), pft.getFlowname());
            }
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>邮件发送");
            // 邮件
            if ("1".equals(info.getIsclryj())) {
                sendEmail(info.getClryjmb(), info.getFlowdraftno(), pft.getFlowname());
            }
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>手机平台发送");
            // 平台
            if ("1".equals(info.getIsclrpt())) {
                sendPlatform(sendUserId,info.getClrptmb(), info.getFlowdraftno(), pft.getFlowname());
            }
        } catch (Exception e){
            log.error("发送通知异常：", e);
            throw new BizException(e);
        }
    }

    /**
     * 发送基于流程模板通知 - 且不再流程内主动发起通知的
     */
    public void sendFlowTemplateNotice( ) {
        PpmFlowInfo info = ppmFlowInfoService.queryDetail(this.activityDefId, this.processDefName);
        if (null == info) {
            return;
        }
        PpmFlowType pft = ppmFlowTypeService.queryDetail(this.processDefName.trim());
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>短信发送");
        // 短信
        if ("1".equals(info.getIsclrdx())) {
            sendSMSCustomize(info.getClrdxmb(), info.getFlowdraftno(), pft.getFlowname());
        }
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>邮件发送");
        // 邮件
        if ("1".equals(info.getIsclryj())) {
            sendEmailCustomize(info.getClryjmb(), info.getFlowdraftno(), pft.getFlowname());
        }
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>手机平台发送");
        // 平台
        if ("1".equals(info.getIsclrpt())) {
            sendPlatform(sendUserId,info.getClrptmb(), info.getFlowdraftno(), pft.getFlowname());
        }
    }

    /**
     * 自定义邮件发送
     * @param clrdxmb 模板编号
     * @param flowdraftno  流程定义名称
     * @param flowname 流程实例名称
     */
    private void sendEmailCustomize(String clrdxmb, String flowdraftno, String flowname) {
        PpmMsgTemplate ppmMsgTemplate = ppmMsgTemplateService.queryDetail(Long.parseLong(clrdxmb));
        // 新应用url
        String url = eosDictEntryService.getTypeidForDetail("PPM_URL", "2").getDictname();
        if (null == ppmMsgTemplate) {
            return;
        }
        String[] badges = this.userIds[0].split("@")[0].split("\\|");
        String[] usernames = this.userIds[0].split("@")[1].split("\\|");
        PpmCustomSetting pcs = ppmCustomSettingService.queryDetail(sendUserId, flowdraftno);
        String isDx = "";
        if (null != pcs) {
            isDx = pcs.getIsdx();
        }
        if ("0".equals(isDx)) {
            return;
        }
        String content = ppmMsgTemplate.getContent();
        String username = usernames[0] + "(" + badges[0] + ")";
        content = super.emailTemplate(content,badges[0], username, flowname, url);
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>发送邮件接收者:{}， 内容：{}", this.sendUserId,content);
        SendMessage sendMessage = new SendMessage();
        sendMessage.sendEmail(this.sendUserId + "@work",
                this.processTitle + "[" + flowname + "]", content, null, false);
    }

    /**
     * 自定义短信发型
     * @param clrdxmb  模板编号
     * @param flowdraftno 流程定义名称
     * @param flowname 流程实例名称
     */
    private void sendSMSCustomize(String clrdxmb, String flowdraftno, String flowname) {
        PpmMsgTemplate ppmMsgTemplate = ppmMsgTemplateService.queryDetail(Long.parseLong(clrdxmb));
        if (null == ppmMsgTemplate) {
            return;
        }

        String badge = "";
        String[] userIds = this.userIds[0].split("@")[0].split("\\|");
        String[] userNames = this.userIds[0].split("@")[1].split("\\|");

        PpmCustomSetting pcs = ppmCustomSettingService.queryDetail(this.sendUserId, flowdraftno);
        String isDx = "";
        if (null != pcs) {
            isDx = pcs.getIsdx();
        }
        if ("0".equals(isDx)) {
            return;
        }

        String userName = userNames[0] + "(" + userIds[0] + ")";
        String content = super.smsTemplate(ppmMsgTemplate.getContent(),userName, flowname);
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>发送短信接收者:{}， 内容：{}", this.sendUserId,content);
        SendMessage sendMessage = new SendMessage();
        sendMessage.sendESBText(this.sendUserId, content);

    }

    /**
     * 发送平台
     * @param sendUserId  发送人id
     * @param clrdxmb     模板编号
     * @param flowdraftno 流程定义名称
     * @param flowname    流程实例名称
     */
    private void sendPlatform(String sendUserId,String clrdxmb, String flowdraftno, String flowname) {
        PpmMsgTemplate ppmMsgTemplate = ppmMsgTemplateService.queryDetail(Long.parseLong(clrdxmb));
        for (int i = this.userIds.length - 1; i >= 0; i--) {
            String[] badges = this.userIds[i].split("@")[0].split("\\|");
            String[] usernames = this.userIds[i].split("@")[1].split("\\|");
            for (int i1 = badges.length - 1; i1 >= 0; i1--) {
                PpmCustomSetting pcs = ppmCustomSettingService.queryDetail(badges[i1], flowdraftno);
                String isDx = "";
                if (null != pcs) {
                    isDx = pcs.getIsdx();
                }
                if ("0".equals(isDx)) {
                    continue;
                }
                String content = ppmMsgTemplate.getContent();
                String name = usernames[i1] + "(" + badges[i1] + ")";
                content = super.platformTemplate(content, name, flowname);
                log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>【新应用】手机平台通知内容：{}", content);
                SendMessage sendMessage = new SendMessage();

                if(WorkflowSendMsg.getProcessorMobileOAApprovalConfig()
                        .contains(this.processDefName + "|" + this.activityDefId)) {
                    sendMessage.sendMobileOa(badges[i1],null, this.workItems[i], content, "01",
                            this.processDefName,this.processInstId, true);
                } else {
                    sendMessage.sendMobileOa(badges[i1],sendUserId, this.workItems[i], content, "02",
                            this.processDefName,this.processInstId, true);
                }

            }
        }
    }

    /**
     * 邮件发送
     *
     * @param clrdxmb     模板编号
     * @param flowdraftno 流程定义名称
     * @param flowname    流程实例名称
     */
    private void sendEmail(String clrdxmb, String flowdraftno, String flowname) {
        PpmMsgTemplate ppmMsgTemplate = ppmMsgTemplateService.queryDetail(Long.parseLong(clrdxmb));
        // 新应用url
        String url = eosDictEntryService.getTypeidForDetail("PPM_URL", "2").getDictname();
        if (null == ppmMsgTemplate) {
            return;
        }
        for (int i = this.userIds.length - 1; i >= 0; i--) {
            String[] badges = this.userIds[i].split("@")[0].split("\\|");
            String[] usernames = this.userIds[i].split("@")[1].split("\\|");
            for (int i1 = badges.length - 1; i1 >= 0; i1--) {
                PpmCustomSetting pcs = ppmCustomSettingService.queryDetail(badges[i1], flowdraftno);
                String isDx = "";
                if (null != pcs) {
                    isDx = pcs.getIsdx();
                }
                if ("0".equals(isDx)) {
                    continue;
                }

                String content = ppmMsgTemplate.getContent();
                String username = usernames[i1] + "(" + badges[i1] + ")";
                content = super.emailTemplate(content,badges[i1], username, flowname, url);
                log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>发送邮件接收者:{}， 内容：{}", badges[i1],content);
                SendMessage sendMessage = new SendMessage();
                sendMessage.sendEmail(badges[i1] + "@work",
                        this.processTitle + "[" + flowname + "]", content, null, true);
            }
        }

    }

    /**
     * 发送短信
     *
     * @param clrdxmb     模板编号
     * @param flowdraftno 流程定义名称
     * @param flowname    实例名称
     */
    private void sendSMS(String clrdxmb, String flowdraftno, String flowname) {
        PpmMsgTemplate ppmMsgTemplate = ppmMsgTemplateService.queryDetail(Long.parseLong(clrdxmb));
        if (null == ppmMsgTemplate) {
            return;
        }

        String badge = "";
        for (int i = this.userIds.length - 1; i >= 0; i--) {
            String[] userIds = this.userIds[i].split("@")[0].split("\\|");
            String[] userNames = this.userIds[i].split("@")[1].split("\\|");
            for (int i1 = userIds.length - 1; i1 >= 0; i1--) {
                PpmCustomSetting pcs = ppmCustomSettingService.queryDetail(userIds[i1], flowdraftno);
                String isDx = "";
                if (null != pcs) {
                    isDx = pcs.getIsdx();
                }
                if ("0".equals(isDx)) {
                    continue;
                }
                if (StringUtil.isNotBlank(badge)) {
                    badge = userIds[i1];
                } else {
                    badge += "," + userIds[i1];
                }
            }           // for end
            String userName = userNames[i] + "(" + userIds[i] + ")";
            String content = super.smsTemplate(ppmMsgTemplate.getContent(),userName, flowname);
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>发送短信接收者:{}， 内容：{}", badge,content);
            SendMessage sendMessage = new SendMessage();
            sendMessage.sendESBText(badge, content);
        }               // for end
    }
}
