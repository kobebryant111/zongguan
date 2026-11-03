package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmQuestionList {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	试题编号
	**/
	private String questionno;
	/**
	*	提干
	**/
	private String content;
	/**
	*	描述
	**/
	private String description;
	/**
	*	试题类型
	**/
	private String questiontype;
	/**
	*	是否删除，0为未删除，1为删除，默认为0
	**/
	private Integer isdel;
	/**
	*	创建时间
	**/
	private Date createtime;
	/**
	*	创建者
	**/
	private String creator;
	/**
	*	最后更新时间
	**/
	private Date lastmodtime;
	/**
	*	最后操作者
	**/
	private String lastmoduser;
	/**
	*	答案
	**/
	private String answer;
	/**
	*	答案解析
	**/
	private String answerexplain;
	/**
	*	注释
	**/
	private String annotation;
	/**
	*	题池类型
	**/
	private String questionpooltype;
}
