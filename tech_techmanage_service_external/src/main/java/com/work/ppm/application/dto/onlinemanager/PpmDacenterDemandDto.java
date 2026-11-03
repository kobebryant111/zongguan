package com.work.ppm.application.dto.onlinemanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.commons.utils.StringUtil;
import lombok.Data;

import java.util.Date;


/**
  * 描述
  */
@Data
public class PpmDacenterDemandDto {
	/**
	*	字段名
	**/
	private Long id;
	 /**
	  *	标题
	  **/
	 private String title;
	 /**
	  *	流程编号
	  **/
	 private String demandNo;
	 /**
	  *	申请人
	  **/
	 private String createBy;
	 /**
	  *	申请日期
	  **/
	 @JsonFormat(pattern = "yyyy-MM-dd")
	 private Date createDate;
	 /**
	  *需求负责人所在部门
	  **/
	 private Long demandDept;
	 /**
	  *	需求负责人
	  **/
	 private String demandBy;
	 /**
	  *	联系电话
	  **/
	 private String phone;
	 /**
	  *	需求分类
	  **/
	 private String demandType;
	 /**
	  *	需求期望上线时间
	  **/
	 @JsonFormat(pattern = "yyyy-MM-dd")
	 private Date demandOnlineDate;
	 /**
	  *	关联主系统 id
	  **/
	 private String mainSystemId;
	 /**
	  *	需求分析概要
	  **/
	 private String demandRemakr;

	 /**
	  *	流程id
	  **/
	 private String processInstId;
	 /**
	  *	关联需求状态
	  **/
	 private String relationDemandStatus;
	 /**
	  *	需求进度
	  **/
	 private Long relationDemandProgress;
	 /**
	  *	关联需求编号
	  **/
	 private String relationDemandNo;
	 /**
	  *	补充说明
	  **/
	 private String relationDemandRemakr;

	/**
	 * 需求状态   1  正常    0  删除   2  草稿
	 */
	private String status;
	/**
	 附件编号
	 */
	 private String files;
	/**
	 * 流程对象
	 */
	 private BpsWorkFlowDto bps;
	/**
	 * 手机附件路径
	 */
	private String dir;
	/**
	 * 手机附件路径
	 */
	 private String separator;
	/**
	 * 查询开始时间
	 */
	 private String startDate;
	/**
	 * 查询结束时间
	 */
	 private String endDate;
	/**
	 * 查询需求期望上线开始时间
	 */
	private String startDemandOnlineDate;
	/**
	 * 查询需求期望上线结束时间
	 */
	private String endDemandOnlineDate;
	/**
	 * 工作流流程状态
	 */
	private String flowStatus;
	/**
	 * 提出时间开始时间
	 */
	private String startBringUpdate;
	/**
	 * 提出时间结束时间
	 */
	private String endBringUpdate;
	/**
	 * 立项时间开始时间
	 */
	private String startBuildDate;
	/**
	 * 立项时间结束时间
	 */
	private String endBuildDate;
	/**
	 * 项目状态
	 */
	private String projectStatus;
	/**
	 *同步需求状态
	 */
	private String demanStatus;


	/**
	 * set 重写  主要处理 oracle in 查询 多个字段 拼接问题
	 * @param projectStatus
	 */
	public void setProjectStatus(String projectStatus) {
		if(StringUtil.isNotBlank(projectStatus)){
			String[] projectStatuss=projectStatus.split(",");
			StringBuilder bld = new StringBuilder();
			for (String splitStr:projectStatuss
			) {
				bld.append("'"+splitStr+"',");
			}
			this.projectStatus=bld.toString().substring(0,bld.toString().length()-1);
		}
	}


}
