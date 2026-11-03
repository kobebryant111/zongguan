package com.work.ppm.application.model.usesysresourcealter;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;

/**
 * 应用系统变更主表
 */
@Data
public class ResUseSys extends ResProjectFlow {
   /**
   *	主键
   **/
   @Id
   private String id;
   /**
   *	应用系统编号
   **/
   private String appsysno;
   /**
   *	应用系统名称
   **/
   private String appsysname;
    /**
     *	应用系统所属部门
     **/
    private String depid;
    /**
     *	系统管理员A岗
     **/
    private String sysuser;
   /**
   *	流程编号
   **/
   private String flowno;
   /**
   *	变更内容
   **/
   private String altercontent;
   /**
   *	流程实例ID
   **/
   private String processInstID;
   /**
   *	"状态
字典表：res_status
1新建、2待审核、3审核中、4审核通过、5拒绝、6删除
"
   **/
   private String status;
   /**
   *	状态变更时间
   **/
   private Date statustime;
   /**
   *	创建人
   **/
   private String creator;
   /**
   *	创建时间
   **/
   private Date createtime;
   /**
   *	创建人姓名
   **/
   private String creatorname;
}
