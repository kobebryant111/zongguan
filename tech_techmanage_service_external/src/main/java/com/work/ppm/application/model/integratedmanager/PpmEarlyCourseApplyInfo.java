package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 提前入场申请信息
 */
@Data
public class PpmEarlyCourseApplyInfo {
   /**
    *	id
    **/
   @Id
   private Long id;
   /**
    *	pid
    **/
   private Long pid;
   /**
    *	流程id
    **/
   private String instanceid;
   /**
    *	关联面试编号
    **/
   private String interviewId;
   /**
    *	姓名
    **/
   private String userName;
   /**
    * 证件号
    */
   private String cardNo;
   /**
    *	提前入场时间
    **/
   private Date earlyEntryTime;
   /**
    *	是否补全入场材料
    **/
   private String isComplement;
   /**
    * 催办时间
    */
   private Date campaignTime;
   /**
    *	备注
    **/
   private String remark;
   /**
    *	创建时间
    **/
   private Date createdTime;
   /**
    *	创建人
    **/
   private String createdBy;
   /**
    * 修改时间
    */
   private Date modifiedTime;
   /**
    * 修改人
    */
   private String modifiedBy;
}
