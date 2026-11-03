package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmEarlyCourseApplyInfo;
import lombok.Data;


/**
 * 提前入场申请信息
 */
@Data
public class PpmEarlyCourseApplyInfoView extends PpmEarlyCourseApplyInfo {

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
   private String deptNo;
   /**
    * 部门名称
    */
   private String deptName;
   /**
    * 人员类型
    */
   private String position;
   /**
    * 供应商编号
    */
   private String wbsId;
   /**
    * 供应商名称
    */
   private String outSourceName;
   /**
    * 年限
    */
   private String workyears;
   /**
    * 单价
    */
   private String suggestprice;
   /**
    * 单价
    */
   private String deptId;
   /**
    * 申请人 筛选
    */
   private String applyerValue;
}
