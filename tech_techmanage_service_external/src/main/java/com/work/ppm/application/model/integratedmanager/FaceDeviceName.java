package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

/**
 * 人脸考勤机设备名称表   实体对象
 */
@Data
public class FaceDeviceName {
   /**
   * 主键ID ID
   **/
   @Id
   private Long id;
   /**
    * 设备IP
    **/
   private String deviceip;
   /**
   * 设备名称
   **/
   private String name;
   /**
    * 设备类型
    **/
   private String type;


}
