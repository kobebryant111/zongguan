package com.work.ppm.application.model.basismanager;

import com.work.ppm.commons.annotaion.Id;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;



 /**
  * 登录日志
  */
@Data
public class PpmNewloginLog {
	/**
	*	自增编号
	**/

	@Id
	@ApiModelProperty(name = "id", value = "编号", dataType = "Long")
	private Long id;
	/**
	*	登录的工号
	**/
	private String userId;
	/**
	*	名称
	**/
	private String userName;
	/**
	*	时间
	**/
	private Date createTime;
	/**
	*	ip
	**/
	private String ip;
	/**
	*	是否单点(0:否，1：是)
	**/
	private Integer ifSingle;
	/**
	*	单点参数
	**/
	private String singleParams;
}
