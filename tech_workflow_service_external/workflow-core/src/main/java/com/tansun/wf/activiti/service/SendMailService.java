package com.tansun.wf.activiti.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.CollectionUtils;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.tansun.wf.camunda.bpmn.instance.UserTaskParticipant;
import com.tansun.workflow.model.PostInfo;

/**
 * 发送邮件服务类
 *
 */

public class SendMailService {
	@Autowired
	private WorkflowTaskService workflowTaskService;
	@Autowired
	private WorkflowUserService workflowUserService;
	@Autowired
	private WorkflowProcessDefineService workflowProcessDefineService;
	@Autowired
	private TimerConfigService timerConfigService;

	@Autowired
	private TaskService taskService;
	

	private String emailHost;

	private String emailPort;

	private String emailUserName;

	private String emailPassword;

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Transactional
	public String sendTrigerEmail(String id,String processDefinitionId,String activityId,
			String processInstanceId,String executionId,String tenantId,String template,Long remindNum){
		String str="0";
		try {
			//查找需要发邮件的用户
			UserTaskParticipant userTaskParticipant = workflowProcessDefineService.getNodeExtensionProperty(processDefinitionId,activityId,UserTaskParticipant.class,UserTaskParticipant.class);
			
			List<Task> list = taskService.createTaskQuery().executionId(executionId).list();
			List<PostInfo> postInfos = null;
			if (CollectionUtils.isNotEmpty(list)) {
				String   taskId = list.get(0).getId();
				postInfos =this.workflowTaskService.getPostInfos(userTaskParticipant, taskId);
			}else {
				postInfos=this.workflowProcessDefineService.getPostInfos(userTaskParticipant);
			}
			final List<String> users = new ArrayList<String>();
		    if (CollectionUtils.isNotEmpty(postInfos)) {
		    	postInfos.forEach(postInfo->{
		    		String  userId = postInfo.getUserId();
		    		String  orgCd  = postInfo.getOrgCode();
		    		String  roleCd  = postInfo.getRoleCode();
		    		if (userId !=null) {
		    			users.add(userId);
		    		}
		    		if (orgCd != null) {
		    			users.add(orgCd);
		    		}
		    		if (roleCd != null) {
		    			users.add(roleCd);
		    		}
		    	});
		    }
			List<String> emails = workflowUserService.findUserEmails(new ArrayList<String>(users));
			if(null!=emails&&emails.size()>0){
				str= sendEmail(emails,template);
				if("0".equals(str)&&remindNum>1){//修改提醒次数
					timerConfigService.updateTimerConfigNumber(id,remindNum-1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
    public String sendEmail(List<String> receiveMailAccount,String template) throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    
//        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", emailHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        //开启 SSL 安全连接
        // SMTP 服务器的端口
        props.setProperty("mail.smtp.port", emailPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", emailPort);
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(true);             // 设置为debug模式
        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        transport.connect(emailUserName, emailPassword);
//    	transport.connect((String)props.get("mail.smtp.hos t"),emailUserName, emailPassword);
//        System.out.println("连接邮箱服务器成功");
        for(int i=0;i<receiveMailAccount.size();i++){
        	if(!"".equals(receiveMailAccount.get(i))){
        		 // 3. 创建邮件
    	        MimeMessage message = createMessage(session, emailUserName, receiveMailAccount.get(i),template); 
    	        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
    	        transport.sendMessage(message, message.getAllRecipients());
        	}
        }
        // 7. 关闭连接
        transport.close();
        return "0";
    }
    /**
     * 创建邮件
     *
     * @param session 和服务器交互的会话
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMessage(Session session, String sendMail, String receiveMail, String template) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "工作流系统", "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMail, "UTF-8"));
        // 4. Subject: 邮件主题
        message.setSubject("工作流任务提醒", "UTF-8");
        // 5. Content: 邮件正文
        message.setContent(template.replace("${userId}", receiveMail), "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
//      // 8. 将该邮件保存到本地
//      OutputStream out = new FileOutputStream("MyEmail.eml");
//      message.writeTo(out);
//      out.flush();
//      out.close();
        return message;
    }
    

	public String getEmailHost() {
		return emailHost;
	}
	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}
	public String getEmailPort() {
		return emailPort;
	}
	public void setEmailPort(String emailPort) {
		this.emailPort = emailPort;
	}
	public String getEmailUserName() {
		return emailUserName;
	}
	public void setEmailUserName(String emailUserName) {
		this.emailUserName = emailUserName;
	}
	public String getEmailPassword() {
		return emailPassword;
	}
	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}


}
