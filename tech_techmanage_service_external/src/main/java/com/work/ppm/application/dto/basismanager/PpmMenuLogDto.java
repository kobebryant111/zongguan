package com.work.ppm.application.dto.basismanager;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;



 /**
  * 菜单日志记录
  */
@Data
public class PpmMenuLogDto {
	/**
	*	自增编号
	**/
	@ApiModelProperty(name = "id", value = "自增编号", dataType = "Long")
	private Long id;
	/**
	*	菜单名称
	**/
	@ApiModelProperty(name = "name", value = "菜单名称", dataType = "String")
	private String name;
	/**
	*	菜单编号
	**/
	@ApiModelProperty(name = "code", value = "菜单编号", dataType = "String")
	private String code;
	/**
	*	工号
	**/
	@ApiModelProperty(name = "userId", value = "工号", dataType = "String")
	private String userId;
	/**
	*	名称
	**/
	@ApiModelProperty(name = "userName", value = "名称", dataType = "String")
	private String userName;
	/**
	*	时间
	**/
	@ApiModelProperty(name = "createTime", value = "时间", dataType = "Date")
	private Date createTime;
	/**
	*	ip
	**/
	@ApiModelProperty(name = "ip", value = "ip", dataType = "String")
	private String ip;
	 /**
	  *	开始时间字符串
	  **/
	 @ApiModelProperty(name = "startTime", value = "开始时间", dataType = "String")
	 private String startTime;
	 /**
	  *	结束时间字符串
	  **/
	 @ApiModelProperty(name = "endTime", value = "结束时间", dataType = "String")
	 private String endTime;
}
