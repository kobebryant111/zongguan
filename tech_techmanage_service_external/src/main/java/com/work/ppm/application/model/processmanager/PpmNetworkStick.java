package com.work.ppm.application.model.processmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 宁管设备变更流程
  */
@Data
public class PpmNetworkStick {
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
	private Integer processType;
	/**
	*	流程标题
	**/
	private String processTitle;
	/**
	*	变更设备名称
	**/
	private Integer changeDevice;
	/**
	*	变更类型（1：采购:，2：报废）
	**/
	private Integer changeType;
	/**
	*	描述
	**/
	private String describe;
	/**
	*	各个数据是否正常（1：是，2：否）
	**/
	private Integer dataIfNormal;
	/**
	*	监控变更确认AB岗
	**/
	private String monitoringConfirm;
	/**
	*	出入库确认AB岗
	**/
	private String forConfirm;
	/**
	*	CMDB确认AB岗
	**/
	private String cmdbConfirm;
	/**
	*	变更设备名称数据
	**/
	private String changeDeviceData;
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
	  状态
	  */
	 private Integer state;
	 /**
	  最后一个更新时间
	  */
	 private Date lastMofiyDate;
	 /**
	  * 当前处理人
	  */
	 private String currentToId;
     /**
     * 发起人名称
      */
	 private String userName;
	 /**
	  * 异常说明
	  */
	 private String exceDesc;
}
