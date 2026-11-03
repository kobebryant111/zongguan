package com.work.ppm.application.dto.processmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import lombok.Data;
import java.util.Date;


/**
  * 宁管设备变更流程
  */
@Data
public class PpmNetworkStickDto {
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
	@Excel(name = "标题", orderNum = "2")
	private String processTitle;
	/**
	*	变更设备名称
	**/

	private Integer changeDevice;
	/**
	 * 变更设备名称字符串
	 */
	@Excel(name = "变更设备名称", orderNum = "11")
	private String changeDeviceStr;
	/**
	*	变更类型（1：采购:，2：报废）
	**/

	private Integer changeType;
	/**
	 * 变更类型 名称
	 */
	@Excel(name = "变更类型", orderNum = "8")
	private String changeTypeStr;
	/**
	*	描述
	**/
	@Excel(name = "描述", orderNum = "9")
	private String describe;
	/**
	*	各个数据是否正常（1：是，2：否）
	**/
	private Integer dataIfNormal;
	/**
	*	监控变更确认AB岗
	**/
	@Excel(name = "监控变更确认AB岗", orderNum = "15")
	private String monitoringConfirm;
	/**
	*	出入库确认AB岗
	**/
    @Excel(name = "出入库确认AB岗", orderNum = "16")
	private String forConfirm;
	/**
	*	CMDB确认AB岗
	**/
   @Excel(name = "CMDB确认AB岗", orderNum = "17")
	private String cmdbConfirm;
	/**
	*	变更设备名称数据
	**/
	private String changeDeviceData;
	/**
	*	自增编号
	**/
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
	   流程对象
	  */
	private BpsWorkFlowDto bps;
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
	 @Excel(name = "当前处理人", orderNum = "7")
	private String currentToId;
	 /**
	   发起人名称
	  */
	 @Excel(name = "创建人", orderNum = "6")
	private String userName;
	 /**
	   部门名称
	  */
	private String deptName;
	 /**
	  * 手机
	  */
	 private String mobile;

     /**
      *	发起时间字符串
      **/
	 @Excel(name = "创建时间", orderNum = "4")
     private String startDate;

     /**
      * 状态名称
      */
	 @Excel(name = "状态", orderNum = "3")
     private String stateName;
     /**
      * 流程名称
      */
	@Excel(name = "流程类型", orderNum = "1")
     private String processName;
	/**
	 * 最后操作时间字符串
	 */
	@Excel(name = "最后操作时间", orderNum = "5")
     private String lastMofiyDateStr;
     /**
      * ========== 各个数据是否正常 虚拟字段
      * 电池型号
      */
     private String batteryModel;
     /**
      * 电池数量、单节浮充电压、内阻、放电测试是否符合要求
      */
     private String batterySit;
     /**
      * 备注
      */
     @Excel(name = "备注", orderNum = "13")
     private String notes;
     /**
      * 主路转旁路、充电、放电是否正常
      */
     private String orElectricity;
     /**
      * 网络、数据采集是否正常
      */
     private String orNetwork;
     /**
      * 上下线后网络是否正常
      */
     private String upOrNetwork;
     /**
      * S1和S2接线是否正常
      */
     private String orWiring;
     /**
      * STS输出是否正常
      */
     private String orOutput;
     /**
      * 切换测试是否正常
      */
     private String orTest;
     /**
      * 是否激活
      */
     private String activeDisable;
	 /**
	  * 结束时间
	  */
     private String endDate;
    /**
     * 选项名称
   */
    @Excel(name = "选项名称", orderNum = "12")
     private String optionsName;

	/**
	 *	各个数据是否正常（1：是，2：否）
	 **/
	@Excel(name = "各个数据是否正常", orderNum = "10")
	private String dataIfNormalStr;
	/**
	 * 异常说明
	 */
	@Excel(name = "异常说明", orderNum = "14")
	private String exceDesc;
}
