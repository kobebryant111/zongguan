package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmStaffTransferInfo;
import lombok.Data;


/**
 *  人员移交详情信息 传输对象
 */
@Data
public class PpmStaffTransferInfoExt extends PpmStaffTransferInfo {
   /**
    * 姓名
    */
   private String userName;
   /**
    * 供应商名称
    */
   private String supplierName;
   /**
    *移交前所属部门名称
    */
   private String deptIdBeforeName;
    /**
     *移交前项目经理名称
     */
   private String pmIdBeforeName;
    /**
     *移交后所属部门名称
     */
   private String deptIdAfterName;
    /**
     *移交后项目经理名称
     */
   private String pmIdAfterName;
    /**
     *移交后系统名称
     */
   private String systemName;
    /**
     *移交后项目名称
     */
    private String projectName;
   /**
     *移交状态名称
     */
   private String transferStateName;

    /**
     *申请人编号
     */
    private String applyerId;
    /**
     *申请人名称
     */
    private String applyerName;
    /**
     *申请人部门
     */
    private String applyerDeptId;
    /**
     *申请人部门名称
     */
    private String applyerDeptName;
    /**
     * 流程状态
     */
    private String state;
    /**
     * 流程状态名称
     */
    private String stateName;
    /**
     * 外包方式名称
     */
    private String outsourceTypeName;
    /**
     * 删除的参数
     */
    private String delVal;
}
