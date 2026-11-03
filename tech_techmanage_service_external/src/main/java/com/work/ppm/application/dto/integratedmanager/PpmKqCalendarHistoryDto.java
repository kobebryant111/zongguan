package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;



 /**
  *  考勤日历修改记录 传输对象
  */
@Data
public class PpmKqCalendarHistoryDto {
	/**
	*	字段名
	**/
	@ApiModelProperty(name = "id", value = "", dataType = "Long")
	private Long id;
	/**
	*	字段名
	**/
	@ApiModelProperty(name = "content", value = "", dataType = "String")
	private String content;
	/**
	*	字段名
	**/
	@ApiModelProperty(name = "userId", value = "", dataType = "String")
	private String userId;
	/**
	*	字段名
	**/
	@ApiModelProperty(name = "userName", value = "", dataType = "String")
	private String userName;
	/**
	*	字段名
	**/
	@ApiModelProperty(name = "updateDate", value = "", dataType = "Date")
	private Date updateDate;
}
