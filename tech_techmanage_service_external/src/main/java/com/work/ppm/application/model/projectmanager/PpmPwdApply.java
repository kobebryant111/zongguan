package com.work.ppm.application.model.projectmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



/**
 * 密管密码流程
 */
@Data
public class PpmPwdApply {
	/**
	*	是否需获取本部门其他系统用户(01：是，02：否)
	**/
	private String isgetbydep;
	/**
	*	原因
	**/
	private String getbydepreason;
	/**
	*	是否有相关流程
	**/
	private String isrelate;
	/**
	*	1
	**/
	private String isnew;
	/**
	*	详细原因
	**/
	private String bfreason;
	/**
	*	关联流程节点
	**/
	private String relateactivity;
	/**
	*	关联流程编号（主要用于关联流程是多人同时处理时，区分发起通用密码获取时哪个处理的）
	**/
	private String dealcode;
	/**
	*	本次更新影响情况确认
	**/
	private String affectqr;
	/**
	*	临时获取管理员用户原因
	**/
	private String adminreason;
	/**
	*	是否已在关联流程中写明要获取的管理员用户及步骤
	**/
	private String ishavestep;
	/**
	*	是否临时获取管理员用户
	**/
	private String isinerimadmin;
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	流程id
	**/
	private String instanceid;
	/**
	*	密码获取编号
	**/
	private String applycode;
	/**
	*	标题
	**/
	private String title;
	/**
	*	申请人所在部门
	**/
	private String applyer;
	/**
	*	申请人所在部门
	**/
	private String applyerdeptid;
	/**
	*	联系电话
	**/
	private String applyertel;
	/**
	*	申请时间
	**/
	private Date applytime;
	/**
	*	是否紧急流程
	**/
	private String isurgent;
	/**
	*	使用时长（小时）
	**/
	private String usehour;
	/**
	*	开始使用时间
	**/
	private Date usestarttime;
	/**
	*	申请原因
	**/
	private String reason;
	/**
	*	关联流程编号
	**/
	private String relateflow;
	/**
	*	关联流程状态
	**/
	private String relateflowstatus;
	/**
	*	详细原因
	**/
	private String reasonnote;
	/**
	*	审批人
	**/
	private String spuserid;
	/**
	*	密码到期时间
	**/
	private Date endtime;
	/**
	*	是否延期
	**/
	private String isdelay;
	/**
	*	延期理由
	**/
	private String delayreason;
	/**
	*	处理结果意见
	**/
	private String res;
	/**
	*	0:执行，-1：删除
	**/
	private String isenable;
	/**
	*	创建人
	**/
	private String creator;
	/**
	*	创建时间
	**/
	private Date creattime;
	/**
	*	修改人
	**/
	private String updater;
	/**
	*	修改时间
	**/
	private Date updatetime;
	/**
	*	实际开始使用时间
	**/
	private Date realstarttime;
	/**
	*	使用结束时间
	**/
	private Date useendtime;
	/**
	*	审批人B
	**/
	private String spuserbid;
	/**
	*	字段名
	**/
	private String relateflowname;
	/**
	*	相关业务系统
	**/
	private String systemid;
	/**
	*	计划开始时间
	**/
	private Date planstarttime;
	/**
	*	预计时长
	**/
	private String planusehour;
	/**
	*	计划结束时间
	**/
	private Date planendtime;
	/**
	*	字段名
	**/
	private String relateinstanceid;
	/**
	*	备注
	**/
	private String note;
}
