package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmOutsourcUpdateInfo;
import lombok.Data;

/**
 * 提前入场申请信息
 */
@Data
public class PpmOutsourcUpdateInfoView extends PpmOutsourcUpdateInfo {

   /**
    * 申请人id
    */
   private String applyer;
   /**
    * 申请人名称
    */
   private String applyerName;
   /**
    * 部门编号
    */
   private String applyerDept;
   /**
    * 部门名称
    */
   private String applyerDeptName;
   /**
    * 状态名称
    */
   private String state;
   /**
    * 状态名称
    */
   private String stateName;
   /**
    * 当前处理人
    */
   private String currentId;
   /**
    * 当前处理人名称
    */
   private String currentName;
   /**
    * 状态
    */
   private String presenceState;
   /**
    * 负责人
    */
   private String personInCharge;

   /**
    * 前合同编号编号
    */
   private String newContractIdBeNo;
   /**
    * 前合同编号名称
    */
   private String newContractIdBeName;
   /**
    * 后合同编号编号
    */
   private String newContractIdAfNo;
   /**
    * 后合同编号名称
    */
   private String newContractIdAfName;
   /**
    * 前总办议案编号
    */
   private String ceneralOfficeMotionBeNo;
   /**
    * 前总办议案名称
    */
   private String ceneralOfficeMotionBeName;
   /**
    * 后总办议案编号
    */
   private String ceneralOfficeMotionAfNo;
   /**
    * 后总办议案名称
    */
   private String ceneralOfficeMotionAfName;
   /**
    * 费用结算 过滤
    */
   private String payConfirmId;
   /**
    * 支付确认 过滤
    */
   private String payConfirmNo;
   /**
    * 单价
    */
   private String price;
   /**
    * 上下班时间
    */
   private String workTime;
}
