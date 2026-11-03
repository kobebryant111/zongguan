package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 信息变更申请信息表 实体对象
  */
@Data
public class PpmOutsourcUpdate {
	 /**
	  *	唯一编号
	  **/
	 @Id
	 private Long id;
	 /**
	  *	流程ID
	  **/
	 private String instanceid;
	 /**
	  *	申请人
	  **/
	 private String applyer;
	 /**
	  *	申请人部门
	  **/
	 private String applyerDept;
	 /**
	  *	变更原因
	  **/
	 private String updateReason;
	 /**
	  *	申请时间
	  **/
	 private String applyerTime;
	 /**
	  *	流程状态码 -1 已关闭 0 未发起 1已发起
	  **/
	 private String state;
	 /**
	  *	流程状态码名称 -1 已关闭 0未发起 >1 根据实际流程节点展示
	  **/
	 private String stateName;
	 /**
	  *	当前处理人编号
	  **/
	 private String currentId;
	 /**
	  *	当前处理人名称
	  **/
	 private String currentName;
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
