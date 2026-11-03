package com.work.ppm.application.dto.basismanager;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;



/**
 * 登录日志
 */
@Data
public class PpmNewloginLogDto {
	/**
	*	自增编号
	**/
	@ApiModelProperty(name = "id", value = "自增编号", dataType = "Long")
	private Long id;
	/**
	*	登录的工号
	**/
	@ApiModelProperty(name = "userId", value = "登录的工号", dataType = "String")
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
	*	是否单点(0:否，1：是)
	**/
	@ApiModelProperty(name = "ifSingle", value = "是否单点(0:否，1：是)", dataType = "Integer")
	private Integer ifSingle;
	/**
	*	单点参数
	**/
	@ApiModelProperty(name = "singleParams", value = "单点参数", dataType = "String")
	private String singleParams;
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
