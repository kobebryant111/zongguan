package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



/**
 * 考勤修改流程
 */
@Data
public class PpmAttendanceModify {
	/**
	*	自增id
	**/
	@Id
	private Long id;
	/**
	*	流程id
	**/
	private String instanceId;
	/**
	*	部门编号
	**/
	private String depId;
	/**
	*	发起人
	**/
	private String badge;
	/**
	*	修改原因描述
	**/
	private String reasonDes;
	/**
	*	修改内容描述
	**/
	private String contentDes;
	/**
	*	创建时间
	**/
	private Date addDate;
	/**
	*	是否删除(0:未删除，1：删除)
	**/
	private Integer isdel;
	/**
	 发起人名称
	 */
	private String userName;

}
