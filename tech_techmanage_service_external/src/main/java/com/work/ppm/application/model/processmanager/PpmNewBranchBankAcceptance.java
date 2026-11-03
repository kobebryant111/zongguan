package com.work.ppm.application.model.processmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 新支行交付流程
  */
@Data
public class PpmNewBranchBankAcceptance {
	/**
	*	自增编号
	**/
	@Id
	private Long id;
	/**
	*	流程id
	**/
	private String instanceId;
	/**
	*	发起人id
	**/
	private String badge;
	/**
	*	发起人部门
	**/
	private String deptId;
	/**
	*	发起人联系方式
	**/
	private String contact;
	/**
	*	发起时间
	**/
	private Date createDate;
	/**
	*	流程类型
	**/
	private String processType;
	/**
	*	流程标题
	**/
	private String processTitle;
	/**
	*	流程说明
	**/
	private String processDescribe;
	/**
	*	Netcool监控责任人
	**/
	private String netcoolManagerId;
	/**
	*	Netcool监控是否上线(0-否，1-是)
	**/
	private Integer netcoolDecision;
	/**
	*	UPS监控责任人
	**/
	private String upsManagerId;
	/**
	*	UPS监控是否上线(0-否，1-是)
	**/
	private Integer upsDecision;
	/**
	*	ACK策略责任人
	**/
	private String ackManagerId;
	/**
	*	ACK策略是否上线(0-否，1-是)
	**/
	private Integer ackDecision;
	/**
	*	发起人名称
	**/
	private String userName;
	/**
	*	当前处理人
	**/
	private String currentId;
	/**
	*	最后一个更新时间
	**/
	private Date lastMofiyDate;
	/**
	*	状态 1:进行中，2：已完成，3：已删除
	**/
	private Integer state;
}
