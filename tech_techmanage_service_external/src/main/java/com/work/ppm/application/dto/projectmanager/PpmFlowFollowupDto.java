package com.work.ppm.application.dto.projectmanager;

import lombok.Data;
import java.util.Date;


/**
 * 后督流程
 */
@Data
public class PpmFlowFollowupDto {
	/**
	*	字段名
	**/
	private String id;
	/**
	*	流程类型
	**/
	private String flowid;
	/**
	*	流程id
	**/
	private String instanceid;
	/**
	*	实际维护时间
	**/
	private Date truedealtime;
	/**
	*	状态（1：未开始，2：进行中，3：实施结束，4：验证结束）
	**/
	private String status;
	/**
	*	是否超时（0：未超时，1：超时）
	**/
	private String istimeout;
	/**
	*	开始时间
	**/
	private Date starttime;
	/**
	*	结束时间
	**/
	private Date endtime;
	/**
	*	处理部门
	**/
	private String dealdeptid;
	/**
	*	处理人
	**/
	private String dealerid;
	/**
	*	通知
	**/
	private String remindtype;
	/**
	*	是否开始（1：需审批但未开始，2：需审批且已开始）
	**/
	private String isstart;
	/**
	*	本次更新影响情况确认 
	**/
	private String affectqr;
	/**
	*	实际维护开始时间
	**/
	private Date truestartdealtime;
	/**
	*	关联系统
	**/
	private String systemid;
	/**
	 * 是否同意发送短信 1：同意， 0：不同意
	 */
	private String agree;
	/**
	 * 用户名称
	 */
	private String userId;
}
