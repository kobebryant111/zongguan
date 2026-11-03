package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

/**
 * 人脸考勤机设备信息表   实体对象
 */
@Data
public class FaceDeviceInfo {
   /**
   * 主键ID ID
   **/
   @Id
   private Long id;
   /**
   * 设备端口号
   **/
   private String deviceport;
   /**
    * 设备IP
   **/
   private String deviceip;
   /**
   *	设备地址
   **/
   private String deviceaddress;
   /**
   *	SDK编号
   **/
   private String luserid;
   /**
    *	数据源
    **/
   private String datasource;
   /**
    *	所属机构
    **/
   private String compcode;
   /**
    *	是否共享
    **/
   private String isshare;
   /**
    *	0-否，1-是
    **/
   private String deleted;
   /**
    *	预留
    **/
   private String memo;
   /**
    *	预留
    **/
   private String memo2;
   /**
    *	预留
    **/
   private String memo3;
   /**
    *	设备共享目标
    **/
   private String shareobject;

}
