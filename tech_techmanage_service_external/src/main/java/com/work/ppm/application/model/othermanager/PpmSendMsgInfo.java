package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmSendMsgInfo {
	/**
	*	id
	**/
	@Id
	private Long id;
	/**
	*	发送者
	**/
	private String sender;
	/**
	*	发送类型。01-邮件，02-短信，03-微信
	**/
	private String sendType;
	/**
	*	发送次数
	**/
	private Long sendTimes;
	/**
	*	发送状态。00-待发送，01-发送成功，02-发送失败，03-已删除
	**/
	private String status;
	/**
	*	登记时间
	**/
	private String createTime;
	/**
	*	发送参数
	**/
	private String parameters;
	/**
	*	异常信息
	**/
	private String errorMsg;
	/**
	*	修改时间
	**/
	private String modifyTime;
}
