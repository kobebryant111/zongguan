package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 考勤日历修改记录 实体对象
  */
@Data
public class PpmKqCalendarHistory {
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String content;
	/**
	*	字段名
	**/
	private String userId;
	/**
	*	字段名
	**/
	private String userName;
	/**
	*	字段名
	**/
	private Date updateDate;
}
