package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmQuestionOption {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	问题序号
	**/
	private String questionno;
	/**
	*	选项序号
	**/
	private String optioncode;
	/**
	*	选项内容
	**/
	private String optioncontent;
	/**
	*	是否删除，0未删除，1已删除
	**/
	private String isdel;
}
