package com.work.ppm.application.model.otherdeptmanager.ext;

import com.work.ppm.application.model.otherdeptmanager.PpmDevelopGroupLedger;
import lombok.Data;

import java.util.Date;


/**
 * 研发小组台账 实体对象
 */
@Data
public class PpmDevelopGroupLedgerExt extends PpmDevelopGroupLedger {
   /**
   *	姓名
   **/
   private String userName;
   /**
   *	供应商编号
   **/
   private String outsourceNo;
   /**
   *	供应商名称
   **/
   private String outsourceName;
   /**
   *	门禁卡编号
   **/
   private String vmAccount;
   /**
   *	岗位编号
   **/
   private String postId;
   /**
   *	岗位名称
   **/
   private String postName;
   /**
   *	入场时间
   **/
   private Date entryTime;
   /**
   *	离场时间
   **/
   private Date leavingTime;
   /**
   *	小组名称
   **/
   private String groupName;
   /**
   *	小组长编号
   **/
   private String groupLeaderId;
   /**
   *	小组长名称
   **/
   private String groupLeaderName;
   /**
   *	小组长门禁卡
   **/
   private String groupLeaderCard;
   /**
   *	小组性质
   **/
   private String grouType;
   /**
   *	部门编号
   **/
   private String deptId;
   /**
   *	部门名称
   **/
   private String deptName;
   /**
   *	状态
   **/
   private String state;
   /**
    *	状态名称
    **/
   private String stateName;
   /**
   *	外包方式
   **/
   private String outsourceType;
   /**
   *	外包方式名称
   **/
   private String outsourceTypeName;

}
