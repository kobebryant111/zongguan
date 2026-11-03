package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmEsbMsgerrlog {
	/**
	*	编号
	**/
	@Id
	private String id;
	/**
	*	接收人
	**/
	private String userid;
	/**
	*	发送标题
	**/
	private String title;
	/**
	*	发送内容
	**/
	private String text;
	/**
	*	日志信息
	**/
	private String loginfo;
}
