package com.work.ppm.application.model.otherdeptmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 考勤信息表-其他部门 实体对象
  */
@Data
public class PpmKqRecordOther {
	/**
	*	唯一编号
	**/
	@Id
	private String id;
	/**
	*	工号
	**/
	private String jobId;
	/**
	*	姓名
	**/
	private String deptId;
	/**
	*	考勤日期
	**/
	private Date attendanceDate;
	/**
	*	上班时间
	**/
	private Date workingTime;
	/**
	*	下班时间
	**/
	private Date closingTime;
	/**
	*	是否节假日
	**/
	private String isHoliday;
	/**
	 *	是否节假日
	 **/
	private String whetherOvernight;
	/**
	*	创建时间
	**/
	private Date createdTime;
	/**
	*	创建人
	**/
	private String createdBy;
	/**
	*	修改时间
	**/
	private Date modifiedTime;
	/**
	*	修改人
	**/
	private String modifiedBy;
}
